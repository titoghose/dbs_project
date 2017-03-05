import json

import sys
from flask import Flask, request, jsonify
from flaskext.mysql import MySQL
# from flask_restful import Resource, Api


db_user = sys.argv[1]
db_pass = sys.argv[2]
db_name = sys.argv[3]
db_host = sys.argv[4]
app = Flask(__name__)
app.config['MYSQL_DATABASE_USER'] = db_user
app.config['MYSQL_DATABASE_PASSWORD'] = db_pass
app.config['MYSQL_DATABASE_DB'] = db_name
app.config['MYSQL_DATABASE_HOST'] = db_host

mysql = MySQL()
mysql.init_app(app)


@app.route('/signIn')
def sign_in():
    conn = mysql.connect()
    cursor = conn.cursor()
    data = request.get_json()
    username = data["username"]
    password = data["password"]
    query = "SELECT * FROM Person WHERE username = " + username + "AND password = " + password + ";"
    cursor.execute(query)
    res = cursor.fetchall()
    c = cursor.rowcount()
    values = []
    for i in range(0, i):
        values.append({
            "uid" : res[j][0],
            "uname" : res[j][1],
            "unumber" : res[j][2],
            "uusername" : res[j][3],
            "upassword" : res[j][4],
            "uwho" : res[j][5],
            "udesc" : res[j][6]})
    cursor.close()
    return jsonify(values)

# api.add_resource(CreateUser, '/CreateUser')

if __name__ == '__main__':
    app.run(debug=True)
