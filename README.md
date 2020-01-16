
<p>A voting system for deciding where to have lunch.</p>
<p>REST API using Hibernate/Spring/SpringMVC <strong>without frontend</strong>.</p>
<ul>
<li>2 types of users: admin and regular users</li>
<li>Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)</li>
<li>Menu changes each day (admins do the updates)</li>
<li>Users can vote on which restaurant they want to have lunch at</li>
<li>Only one vote counted per user</li>
<li>If user votes again the same day:
<ul>
<li>If it is before 11:00 we asume that he changed his mind.</li>
<li>If it is after 11:00 then it is too late, vote can't be changed</li>
</ul>
</li>
</ul>
<p>Each restaurant provides new menu each day.</p>

## REST API documentation

### Registration
`curl -s -X POST -H http://localhost:8080/rest/register 'Content-Type: application/json; charset=UTF-8' -d '{ "name" : "NewUser", "email" : "newUser@mail.com", "password" : "{}password" }'curl -s -X POST -H http://localhost:8080/rest/register 'Content-Type: application/json; charset=UTF-8' -d '{ "name" : "NewUser", "email" : "newUser@mail.com", "password" : "{}password" }'`


### User
#### Profile:
get profile <br>
`curl -s http://localhost:8080/rest/profile --user user-1@yandex.ru:password1`

update profile <br>
`curl -s -X PUT http://localhost:8080/rest/profile --user user-1@yandex.ru:password1 -d '{"name":"New name", "email":"user-1@yandex.ru", "password":"{}password1"}' -H 'Content-Type:application/json;charset=UTF-8'` 

delete profile <br>
`curl -s -X DELETE http://localhost:8080/rest/profile --user user-1@yandex.ru:password1` 

#### Voting:
get all restaurants for today <br>
`curl -s http://localhost:8080/rest/rating/restaurants --user user-1@yandex.ru:password1`

get menu for restaurant with id 100006 <br>
`curl -s http://localhost:8080/rest/rating/menu/100006 --user user-1@yandex.ru:password1`

get menu for all restaurants for today <br>
`curl -s http://localhost:8080/rest/rating/menu --user user-1@yandex.ru:password1`

get rating for today <br>
`curl -s http://localhost:8080/rest/rating --user user-1@yandex.ru:password1`

get rating for restaurant 100006 for today <br>
`curl -s http://localhost:8080/rest/rating/100006 --user user-1@yandex.ru:password1`

show current vote made by user <br>
`curl -s http://localhost:8080/rest/rating/vote --user user-1@yandex.ru:password1`

vote for restaurant with id 100006 <br>
`curl -s -X POST http://localhost:8080/rest/rating/vote/100006 --user user-1@yandex.ru:password1`
	

### Admin
#### Users:
get all users <br>
`curl -s http://localhost:8080/rest/admin/users --user admin@gmail.com:admin`

get user with id 100001 <br>
`curl -s http://localhost:8080/rest/admin/users/100001 --user admin@gmail.com:admin`

add new user <br>
`curl -s -X POST http://localhost:8080/rest/admin/users --user admin@gmail.com:admin -d '{"name":"NewUser", "email":"newUser@mail.com", "password":"{}password"}' -H 'Content-Type:application/json;charset=UTF-8'curl -s -X POST http://localhost:8080/rest/admin/users --user admin@gmail.com:admin -d '{"name":"NewUser", "email":"newUser@mail.com", "password":"{}password"}' -H 'Content-Type:application/json;charset=UTF-8` 

update user with id 100001 <br>
`curl -s -X PUT http://localhost:8080/rest/admin/users/100001 --user admin@gmail.com:admin -d '{"name":"NewUser", "email":"email@mail.com", "password":"{}password"}' -H 'Content-Type:application/json;charset=UTF-8'` 

get user with email admin@gmail.com <br>
`curl -s http://localhost:8080/rest/admin/users/by?email=admin@gmail.com --user admin@gmail.com:admin`

delete user with id 100001 <br>
`curl -s -X DELETE http://localhost:8080/rest/admin/users/100001 --user admin@gmail.com:admin`


#### Restaurants:
get all restaurants <br>
`curl -s http://localhost:8080/rest/admin/restaurants --user admin@gmail.com:admin`

get all distinct restaurants name  <br>
`curl -s http://localhost:8080/rest/admin/restaurants/names --user admin@gmail.com:admin`

get restaurant with id 100006 <br>
`curl -s http://localhost:8080/rest/admin/restaurants/100006 --user admin@gmail.com:admin
`
get all restaurants for May 31, 2015 <br>
`curl -s http://localhost:8080/rest/admin/restaurants/all/2015-05-31 --user admin@gmail.com:admin`

get menu for restaurant with id 100006 <br>
`curl -s http://localhost:8080/rest/admin/restaurants/menu/100006 --user admin@gmail.com:admin`

get menu for May 31, 2015 <br>
`curl -s http://localhost:8080/rest/admin/restaurants/menu/date/2015-05-31 --user admin@gmail.com:admin`

get dish with id 100011 <br>
`curl -s http://localhost:8080/rest/admin/restaurants/dish/100011 --user admin@gmail.com:admin`

add restaurant "New restaurant" for May 30, 2015 <br>
`curl -s -X POST http://localhost:8080/rest/admin/restaurants --user admin@gmail.com:admin -d '{"name":"New restaurant", "date":"2015-05-30"}' -H 'Content-Type:application/json;charset=UTF-8'`

add dish "New dish" with price 500 for restaurant with id 100006 <br>
`curl -s -X POST http://localhost:8080/rest/admin/restaurants/100006 --user admin@gmail.com:admin -d '{"name":"New dish", "price":500}' -H 'Content-Type:application/json;charset=UTF-8' `

delete dish with id 100011 (it belongs to restaurant with id 100006) <br>
`curl -s -X DELETE http://localhost:8080/rest/admin/restaurants/100006/100011  --user admin@gmail.com:admin`

delete restaurant with id 100006 <br>
`curl -s -X DELETE http://localhost:8080/rest/admin/restaurants/100006  --user admin@gmail.com:admin`

update dish with id 100011 (it belongs to restaurant with id 100006) <br>
`curl -s -X PUT http://localhost:8080/rest/admin/restaurants/100006/100011 --user admin@gmail.com:admin -d '{"name":"Updated name", "price":500}' -H 'Content-Type:application/json;charset=UTF-8'` 

update restaurant with id 100006 <br>
`curl -s -X PUT http://localhost:8080/rest/admin/restaurants/100006 --user admin@gmail.com:admin -d '{"name":"Updated name", "date":"2015-05-30"}' -H 'Content-Type:application/json;charset=UTF-8'` 


#### Rating:
get all votes of users <br>
`curl -s http://localhost:8080/rest/admin/rating/all --user admin@gmail.com:admin`

get rating for May 30, 2015 <br>
`curl -s http://localhost:8080/rest/admin/rating/all/2015-05-30 --user admin@gmail.com:admin`

get all ratings for restaurant with the same name as has restaurant with id 100007 <br> 
`curl -s http://localhost:8080/rest/admin/rating/100007 --user admin@gmail.com:admin`

get rating for restaurant with id 100006 for May 30, 2015  <br>
`curl -s http://localhost:8080/rest/admin/rating/100006/2015-05-30 --user admin@gmail.com:admin`

get vote of user 100001 dated with May 30, 2015  <br>
`curl -s http://localhost:8080/rest/admin/rating/user/100001/2015-05-30 --user admin@gmail.com:admin`

delete all old votes <br>
`curl -s -X DELETE http://localhost:8080/rest/admin/rating --user admin@gmail.com:admin`

delete votes older than May 31, 2015 <br> 
`curl -s -X DELETE http://localhost:8080/rest/admin/rating/2015-05-31 --user admin@gmail.com:admin`