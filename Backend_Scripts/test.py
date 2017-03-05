from flask import Flask

# from flask_restful import Resource, Api

app = Flask(__name__)


@app.route('/test')
def test():
    return "Hello, World!"


# api.add_resource(CreateUser, '/CreateUser')

if __name__ == '__main__':
    app.run(debug=True)
