import json

import sys
from flask import Flask, request, jsonify
from flaskext.mysql import MySQL
# from flask_restful import Resource, Api



app = Flask(__name__)
app.config['MYSQL_DATABASE_USER'] = 'root'
app.config['MYSQL_DATABASE_PASSWORD'] = ''
app.config['MYSQL_DATABASE_DB'] = 'aayush'
app.config['MYSQL_DATABASE_HOST'] = 'localhost'

mysql = MySQL()
mysql.init_app(app)


@app.route('/signIn',methods = ['POST'])
def sign_in():
    conn = mysql.connect()
    cursor = conn.cursor()
    data = request.get_json()
    print(data)
    username = data["username"]
    password = data["password"]
    cursor.execute( "SELECT * FROM requiry_user WHERE uUsername =%s AND uPassword =%s;",(username,password))
    res = cursor.fetchall()
    values = []
    i = cursor.rowcount
    for j in range(0, i,1):
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
    app.run(host='0.0.0.0')
