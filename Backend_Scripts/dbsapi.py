import json

import flask
from flask import request
from flask import Flask
from flaskext.mysql import MySQL
from flask import jsonify

mysql = MySQL()

# MySQL configurations
app = Flask(__name__)
app.config['MYSQL_DATABASE_USER'] = 'root'
app.config['MYSQL_DATABASE_PASSWORD'] = ''
app.config['MYSQL_DATABASE_DB'] = 'aayush'
app.config['MYSQL_DATABASE_HOST'] = 'localhost'
mysql.init_app(app)


@app.route('/SignIn', methods=['POST'])
def signin():
    if request.method == 'POST':
        content = request.get_json()
        print(content)
        username = content["uUsername"]
        password = content["uPassword"]
        conn = mysql.connect()
        cursor = conn.cursor()
        cursor.execute(
            "SELECT uID,uName,uNumber,uEmail,uUsername,uPassword,uWho,uDesc FROM requiry_user WHERE uUsername = '" + username + "' and uPassword = '" + password + "';")
        data = cursor.fetchall()
        cursor.close()
        print(data)
        values = []
        if len(data) != 0:
            values.append({
                "uID": data[0][0],
                "uName": data[0][1],
                "uNumber": data[0][2],
                "uEmail": data[0][3],
                "uUsername": data[0][4],
                "uPassword": data[0][5],
                "uWho": data[0][6],
                "uDesc": data[0][7]
            })
            return jsonify(values)

        return "Failed"
    else:
        return "Failed"


@app.route('/Discussions', methods=['POST'])
def discussion():
    if request.method == 'POST':
        content = request.get_json()
        print(content)
        msg = content["msg"]
        uID = content["uId"]
        pID = content["uProjectId"]
        conn = mysql.connect()
        cursor = conn.cursor()
        cursor.execute("INSERT INTO discussion(uID,pID,msg) VALUES(%s,%s,%s);", (uID, pID, msg))
        conn.commit()
        cursor.close()
        return "success"
    else:
        return "Failed"


@app.route('/DiscussionsQuery', methods=['POST'])
def discussionquery():
    if request.method == 'POST':
        content = request.get_json()
        pID = content["uProjectId"]
        conn = mysql.connect()
        cursor = conn.cursor()
        cursor.execute(
            "SELECT uUsername,msg,sTime FROM discussion d,requiry_user u WHERE d.uID = u.uID AND d.pID = %s ORDER BY sTime;", (pID))
        data = cursor.fetchall()
        i = cursor.rowcount
        jsonstr = []
        for j in range(0, i, 1):
            datastr = {
                "uName": data[j][0],
                "msg": data[j][1],
                "sTime": data[j][2]
            }
            jsonstr.append(datastr)
        cursor.close()
        return jsonify(jsonstr)
    else:
        return "error"


@app.route('/DeleteUser', methods=['POST'])
def deleteuser():
    if request.method == 'POST':
        content = request.get_json()
        username = content["uUsername"]
        conn = mysql.connect()
        cursor = conn.cursor()
        cursor.execute("DELETE FROM requiry_user WHERE uUSername = %s;", (username))
        cursor.close()
        return "success"
    else:
        return "Failed"


@app.route('/SignUp', methods=['POST'])
def signup():
    error = None
    if request.method == 'POST':
        content = request.get_json()
        print(content)
        name = content['uName']
        number = content['uNumber']
        email = content['uEmail']
        username = content['uUsername']
        password = content['uPassword']
        who = content['uWho']
        desc = content['uDesc']
        conn = mysql.connect()
        cursor = conn.cursor()
        cursor.execute(
            "INSERT INTO requiry_user(uName,uNumber,uEmail,uUsername,uPassword,uWho,uDesc) VALUES(%s,%s,%s,%s,%s,%s,%s);",
            (name, number, email, username, password, who, desc))
        conn.commit()
        cursor.close()
    else:
        error = 'Error occured'
    if error is None:
        return "success"
    else:
        return "Failed"


@app.route('/EditProfile', methods=['POST'])
def editprofile():
    error = None
    if request.method == 'POST':
        content = request.get_json()
        print(content)
        name = content['uName']
        number = content['uNumber']
        email = content['uEmail']
        username = content['uUsername']
        password = content['uPassword']
        who = content['uWho']
        desc = content['uDesc']
        conn = mysql.connect()
        cursor = conn.cursor()
        cursor.execute(
            "UPDATE requiry_user SET uName = %s,uNumber = %s,uEmail = %s,uPassword = %s,uWho = %s,uDesc = %s WHERE uUsername = %s;",
            (name, number, email, password, who, desc, username))
        conn.commit()
        cursor.close()
    else:
        error = 'Error occured'
    if error is None:
        return "success"
    else:
        return "Failed"


@app.route('/ProFeed', methods=['GET'])
def profeed():
    if request.method == 'GET':
        conn = mysql.connect()
        cursor = conn.cursor()
        query = "SELECT pID,pName,uName AS pCreated_By,pDomain,pDesc,pDateStarts,pDateEnds,pLink FROM projects p,requiry_user u WHERE p.uID=u.uID ;"
        cursor.execute(query)
        data = cursor.fetchall()
        i = cursor.rowcount
        jsonstr = []
        for j in range(0, i, 1):
            datastr = {
                "pID": data[j][0],
                "pName": data[j][1],
                "pCreated_By": data[j][2],
                "pDomain": data[j][3],
                "pDesc": data[j][4],
                "pDateStarts": data[j][5],
                "pDateEnds": data[j][6],
                "pLink":data[j][7]
            }
            jsonstr.append(datastr)
        cursor.close()
        return jsonify(jsonstr)
    else:
        return "{ error_occured:1 }"


@app.route('/Contributor', methods=['POST'])
def contribution():
    if request.method == 'POST':
        conn = mysql.connect()
        cursor = conn.cursor()
        data = request.get_json()
        print(data)
        pid = data["pID"]
        cursor.execute(
            "SELECT u.uID,uName,uNumber,uEmail,uUsername,uPassword,uWho,uDesc FROM requiry_user u,contribution c WHERE c.uID = u.uID AND pID = %s ",
            (pid))
        res = cursor.fetchall()
        i = cursor.rowcount
        values = []
        for j in range(0, i, 1):
            values.append({
                "uID": res[j][0],
                "uName": res[j][1],
                "uNumber": res[j][2],
                "uEmail": res[j][3],
                "uUsername": res[j][4],
                "uPassword": res[j][5],
                "uWho": res[j][6],
                "uDesc": res[j][7]})
        cursor.close()
        return jsonify(values)


@app.route('/DeleteProject', methods=['POST'])
def deleteproject():
    if request.method == 'POST':
        content = request.get_json()
        pid = content["pID"]
        conn = mysql.connect()
        cursor = conn.cursor()
        cursor.execute("DELETE FROM projects WHERE pID = %s;", pid)
        cursor.close()
        return "success"
    else:
        return "Failed"


@app.route('/CreateProject', methods=['POST'])
def createproject():
    error = None
    if request.method == 'POST':
        content = request.get_json()
        print(content)
        name = content['pName']
        created_by = content['pCreated_by']
        domain = content['pDomain']
        desc = content['pDesc']
        date_ends = content['pETC']
        pLink = content['pLink']
        conn = mysql.connect()
        cursor = conn.cursor()
        cursor.execute(
            "INSERT INTO projects(pName, uID, pDomain, pDesc, pDateEnds, pLink) VALUES(%s, %s, %s, %s, %s, %s);",
            (name, created_by, domain, desc, date_ends,pLink))
        conn.commit()
        cursor.close()
    else:
        error = 'Error occured'
    if error is None:
        return "success"
    else:
        return "Failed"


@app.route('/getEmail', methods=['POST'])
def contribution():
    if request.method == 'POST':
        conn = mysql.connect()
        cursor = conn.cursor()
        data = request.get_json()
        print(data)
        pID = data["pID"]
        cursor.execute(
            "SELECT uEmail from projects p, requiry_user r WHERE p.uID = r.uID and p.pID = %s;",(pID))
        res = cursor.fetchall()
        i = cursor.rowcount
        values = []
        for j in range(0, i, 1):
            values.append({"uEmail": res[j][0]})
        cursor.close()
        return jsonify(values)

if __name__ == '__main__':
    app.run(host='0.0.0.0')
