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
        name = content['uname']
        number  = content['unumber']
        username =content['uusername']
        password = content['upassword']
        who = content['uwho']
        desc = content['udesc']
        conn = mysql.connect()
        cursor = conn.cursor()
        cursor.execute("INSERT INTO requiry_user(uname,unumber,uusername,upassword,uwho,udesc) VALUES(%s,%s,%s,%s,%s,%s)",(name,number,username,password,who,desc))
        conn.commit()
        cursor.close()
    else:
        error = 'Error occured'
    if error is None:
        return "success"
    else:
        return "Failed"


if __name__ == '__main__':
    app.run(host='0.0.0.0')
