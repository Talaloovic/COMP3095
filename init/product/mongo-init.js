print('START');

db = db.getSiblingDb('product-service');

db.createUser(
    {
        user: 'admin',
        pwd: 'password',
        roles: [ {role: 'readWrite', db:'product-service'}]
    }
);

db.createCollection('user');

print('END');