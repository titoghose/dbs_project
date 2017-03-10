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
@app.route('/SignUp',methods = ['POST'])
def signup():
    error = None
    if request.method == 'POST':
        content = request.get_json()
        print(content)
        name = content['uName']
        number  = content['uNumber']
        email = content['uEmail']
        username =content['uUsername']
        password = content['uPassword']
        who = content['uWho']
        desc = content['uDesc']
        conn = mysql.connect()
        cursor = conn.cursor()
        cursor.execute("INSERT INTO requiry_user(uName,uNumber,uEmail,uUsername,uPassword,uWho,uDesc) VALUES(%s,%s,%s,%s,%s,%s,%s)",(name,number,email,username,password,who,desc))
        conn.commit()
        cursor.close()
    else:
        error = 'Error occured'
    if error is None:
        return "success"
    else:
        return "Failed"
@app.route('/EditProfile',methods = ['POST'])
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
        cursor.execute("UPDATE requiry_user SET uName = %s,uNumber = %s,uEmail = %s,uPassword = %s,uWho = %s,uDesc = %s WHERE uUsername = %s;",(name,number,email,password,who,desc,username))
        conn.commit()
        cursor.close()
    else:
        error = 'Error occured'
    if error is None:
        return "success"
    else:
        return "Failed"
@app.route('/ProFeed',methods = ['GET'])
def profeed():
    if request.method=='GET':
        conn = mysql.connect()
        cursor = conn.cursor()
        query = "SELECT pName,uName AS pCreated_By,pDomain,pDesc,pDateStarts,pDateEnds FROM projects p,requiry_user u WHERE p.uID=u.uID ;"
        cursor.execute(query)
        data = cursor.fetchall()
        i = cursor.rowcount
        jsonstr = []
        for j in range(0, i, 1):
            datastr = {
                "pName": data[j][0],
                "pCreated_By": data[j][1],
                "pDomain": data[j][2],
                "pDesc":data[j][3],
                "pDateStarts":data[j][4],
                "pDateEnds":data[j][5]
            }
            jsonstr.append(datastr)
        cursor.close()
        return jsonify(jsonstr)
    else:
        return "{ error_occured:1 }"


if __name__ == '__main__':
    app.run(host='0.0.0.0')
