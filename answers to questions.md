# Questions
1. How long did you spend on the coding test? What would you add to your solution if you spent more time on it? If you didn't spend much time on the coding test then use this as an opportunity to explain what you would add.

2. What was the most useful feature that was added to Java 8? Please include a snippet of code that shows how you've used it.

3. What is your favourite framework / library / package that you love but couldn't use in the task? What do you like about it so much?

4. What great new thing you learnt about in the past year and what are you looking forward to learn more about over the next year?

5. How would you track down a performance issue in production? Have you ever had to do this? Can you add anything to your implementation to help with this?

6. How would you improve the APIs that you just used?

7. Please describe yourself in JSON format.

8. What is the meaning of life?

# Answers
1. In total, I spent around 5 days. The first days, I started by reading the ratpack documentation and different tutorials. Then I did some exercises for ratpack, redis and jwt implementation. As it was my first time that I developed an API, I wanted to test different tools for each component. Then the next step was to develop the API endpoints according to the requirements. When i finished with the development, I continued with the deployment in google cloud and finally i gave a try for writing some unit testing.
\
If I had more time, I would focus much more on unit testing. At the moment I have implemented some functions for testing but they are not developed in the correct way. I know that each test should be independent mocking external dependencies such as Redis. I added them in this repo because I believe unit testing must be part of the projects, but until now I didn't have the chance to have a lot of practical experience, especially in backend. In addition, one more thing would be to refactor the code (e.g. Singleton pattern for gson) and I would like to implement kubernetes too.

2. One of the best features that Java 8 introduced was Lambda expressions, functions that no name is needed and can be defined right in place. With this feature, functional programming now is possible in Java. Before that, developers had to create anonymous classes, making it difficult to maintain. Using lambda expressions and stream api, the code is shorter, more meaningful and just by reading it, we can understand what it does.
Lambda
```java
RatpackServer.start(ratpackServerSpec -> ratpackServerSpec
                .handlers(chain -> chain
                        .all(new CORSHandler())
                        .path("login", new AuthHandler())
                        .path("transactions", new TransactionHandler())
                        .path("balance", new AccountHandler())
                        .path("spend", new AccountHandler())
                ));
```

3. Previous year I had the chance to develop and maintain post code using Spring Boot framework. Spring Boot takes responsibility for the project configurations. As a result the developer doesn't need to spend time on settings (e.g. database) and he is mostly focused on developing the bussiness logic. Moreover, by using annotations(plus lombok) the code is reduced and accomplish to write cleaner and more descriptive code.
\
Note: Lombok was not used because this project is code exercise.
4. The past year I was mostly responsible for developing the UI using React and and a bit of Redux. One of the most interesting things was the implementation of micro frontends. I was aware of the micro services in APIs but not for the UI. For the next year I am looking forward to learn more about backend technologies and different tools about deployment such as docker, kubernetes.

5. In my previous experience I didn’t have the chance of doing something similar. The first thing that I would check is CPU and memory usage to identify the source of the problem. Depending on the technologies, I would check the architecture setup and try to isolate parts of the system and test them separately. E.g. in case of using docker I would check logs of the specific container. Finally review and refactor code for any leaks in the system or DB queries. 
\
In my current implementation I would add logs information and metrics plugin. Moreover, kubernetes would be appropriate to use for spinning multiple containers, in order to keep the application online and responsive and have time to investigate the issue.

6. Firstly one main improvement is to use dependency injection in different situations, such as redis, instead of singleton pattern. Currently, JwtTokenUtil.class and RedisPool.class are used in all files that are developed. That's why singleton design pattern is implemented. While the api is growing, dependency injection using Guice is a better way to go.
\
The main class would be refactored to this: 
```java
RatpackServer.start(ratpackServerSpec -> ratpackServerSpec
                .registry(Guice.registry(bindingsSpec -> bindingsSpec
                        .bind(RedisPool.class)
                        .bind(JwtTokenUtil.class)
                ))
                .handlers(chain -> chain
                        .all(new CORSHandler())
                        .path("login", new AuthHandler())
                        .path("transactions", new TransactionHandler())
                        .path("balance", new AccountHandler())
                        .path("spend", new AccountHandler())
                ));
```

and one way could be to pass each dependecy as parameter in the class function (method injection):
```java
public void handle(Context ctx, RedisPool pool, JwtTokenUtil util, String base) throws Exception {
    Response response = ctx.getResponse();
    ...
}
```

Furthermore, as we are referring to production code, two important assets would be to write more log information and automation-integration tests. Also, as the time passes the data (e.g. transactions) will increase and database will be essential. Ratpack is non-blocking, so ```blocking.get()``` will be used for querying the database. 

E.g. Blocking.get() with Redis
```java
Blocking.get(() -> RedisPool.smembers("transactions#" + JwtTokenUtil.getUidFromToken(token)))
		.then(transactionsSet -> {
			final List<Transaction> transactionsList = new ArrayList<>();
			for (String transactionStr : transactionsSet) {
				transactionsList.add(gson.fromJson(transactionStr, Transaction.class));
			}

			//Sort transactions by date
			Collections.sort(transactionsList,
					Comparator.comparing(Transaction::getDate, Comparator.reverseOrder()));

			ctx.render(json(transactionsList));
		});
```
Also, we are gonna have large payloads and pagination would be the solution. Finally, microservices (account api, transactions api) and kubernetes is the last resource to improve the current APIs.

7.
```json
{
	"first_name": "Anastasios",
	"last_name": "Kostas",
	"age": 29,
	"date_birth": "20-05-1992",
	"nationality": "greek",
	"livesin": "Douglas, Isle of Man",
	"education": "Computer Science",
	"occupation": "Full-Stack Developer",
	"interests": [
		"Programming",
		"Cycling",
		"Travelling",
		"Volunteering"
	],
	"skills": [
		"Responsibility",
		"Organizing",
		"Problem Solving",
		"Communication"
	],
	"languages": [
		{
			"language": "Greek",
			"level": "Native"
		},
		{
			"language": "English",
			"level": "Fluent"
		},
		{
			"language": "German",
			"level": "Basic"
		},
		{
			"language": "Spanish",
			"level": "Entry"
		},
	]
 }
```
8. For me, the meaning of life is to live pursuing your dreams. Live every day with passion and no regrets, balancing your personal and professional life. Feeling that at the end of the day, you are one step closer towards achieving your dreams.
