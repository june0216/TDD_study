# study_springTest
인프런 강의(백기선)
# 1부. JUnit

## JUnit 5: 소개

자바 개발자가 가장 많이 사용하는 테스팅 프레임워크.

- [https://www.jetbrains.com/lp/devecosystem-2019/java/](https://www.jetbrains.com/lp/devecosystem-2019/java/)
    - “단위 테스트를 작성하는 자바 개발자 93% JUnit을 사용함.”
- 자바 8 이상을 필요로 함.
- 대체제: TestNG, Spock, ...
- 스프링 2.2로 올리면서 junit5로
- JUnit4는 하나의 jar파일이 있고 다른 라이브러리를 참조해서 했어야 하는데 JUnit5는 그 자체로 모듈화가 되어있다.

[https://lh5.googleusercontent.com/9HALQDUQRNhaLh3bPfXDTn-pi2sceLx7Fdx1ASnDFBxq-IH5JNACX3Gn2O7U9AW0owqfZzjpom_SzM4Yx-ZGoIWSUmE7V3ymE8XK6FswJ7U8t3NQzcHDAQr88lm2exQ3NYQLPH9bclOEGwMtJOLPOxbmTT9qxtO1vN_uzTmh-3DEYxmjdfC7_koE](https://lh5.googleusercontent.com/9HALQDUQRNhaLh3bPfXDTn-pi2sceLx7Fdx1ASnDFBxq-IH5JNACX3Gn2O7U9AW0owqfZzjpom_SzM4Yx-ZGoIWSUmE7V3ymE8XK6FswJ7U8t3NQzcHDAQr88lm2exQ3NYQLPH9bclOEGwMtJOLPOxbmTT9qxtO1vN_uzTmh-3DEYxmjdfC7_koE)

Platform:  테스트를 실행해주는 런처 제공. TestEngine API 제공.

[플랫폼 위에 주피터와 vintage가 있는 것임]

Jupiter: TestEngine API 구현체로 JUnit 5를 제공.

Vintage: JUnit 4와 3을 지원하는 TestEngine 구현체.

참고:

- [https://junit.org/junit5/docs/current/user-guide/](https://junit.org/junit5/docs/current/user-guide/)

## JUnit 5: 시작하기

스프링 부트 프로젝트 만들기

- 2.2+ 버전의 스프링 부트 프로젝트를 만든다면 기본으로 JUnit 5 의존성 추가 됨.

환경 = maven, java 11, dependency는 아무 것도 하지 않음 

스프링 부트 프로젝트 사용하지 않는다면?

```json
<dependency>

<artifactId>junit-jupiter-engine</artifactId>

<scope>test</scope>
```

---

기본 애노테이션

- @Test
- @BeforeAll / @AfterAll
- @BeforeEach / @AfterEach
- @Disabled

## JUnit 5: 테스트 이름 표시하기

[테스트 이름 짓기]

메소드 이름으로 어떤 테스트인지 알아내게 한다. 

언더바를 이용하여 하기 → 가독성이 좋다. 

1) @DisplayNameGeneration

- Method와 Class 레퍼런스를 사용해서 테스트 이름을 표기하는 방법 설정.
- 기본 구현체로 ReplaceUnderscores 제공

```json
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)//어떻게 디스플레이할 것인지에대한 전략 구현체를 넣는다.

결과 
언더바를 공백으로 바꾼 이름으로 나온다. 
ex) create_new_study() => create new study
```

2) @DisplayName → 더 추천하는 방법이다. 

- 어떤 테스트인지 테스트 이름을 보다 쉽게 표현할 수 있는 방법을 제공하는 애노테이션.
- 이모지도 가능
- @DisplayNameGeneration 보다 우선 순위가 높다.

```json
@DisplayName("스터디 만들기")
```

다른 방법 참고:

- [https://junit.org/junit5/docs/current/user-guide/#writing-tests-display-names](https://junit.org/junit5/docs/current/user-guide/#writing-tests-display-names)

## JUnit 5: Assertion

⇒ 검증하고자 하는 내용 확인

org.junit.jupiter.api.Assertions.*

마지막 매개변수로 Supplier<String> 타입의 인스턴스를 람다 형태로 제공할 수 있다.

- 복잡한 메시지 생성해야 하는 경우 사용하면 실패한 경우에만 해당 메시지를 만들게 할 수 있다.

[AssertJ](https://joel-costigliola.github.io/assertj/), [Hemcrest](https://hamcrest.org/JavaHamcrest/), [Truth](https://truth.dev/) 등의 라이브러리를 사용할 수도 있다.

| 실제 값이 기대한 값과 같은지 확인 | assertEqulas(expected, actual) |
| --- | --- |
| 값이 null이 아닌지 확인 | assertNotNull(actual) |
| 다음 조건이 참(true)인지 확인 | assertTrue(boolean) |
| 모든 확인 구문 확인 | assertAll(executables...) |
| 예외 발생 확인 | assertThrows(expectedType, executable) |
| 특정 시간 안에 실행이 완료되는지 확인 | assertTimeout(duration, executable) |

[assertEquals]

```json
@Test
	@DisplayName("스터디 만들기")
	void create() {
		Study study = new Study(-10);
		assertNotNull(study);
		assertEquals(StudyStatus.DRAFT, study.getStatus(), ()-> "스터디를 처음 만들면 상태값이 DRAFT여야 한다.");
		//assertEquals(기대하는 값, 실제 값, "스터디를 처음 만들면 상태값이 DRAFT여야 한다."); 순서가 바뀌어도 되긴 함
		//메세지 부분에서 그냥 1) 문자열과 2) 람다식으로 표현하는 방법이 있는데 후자가 더 좋은 이유는 생성을 필요한 시점에만 하기 때문이다. 그냥 문자열로 한다면 테스트가 실패하든 성공하든 상관없이 항상 생성한다.
		/*assertEquals(StudyStatus.DRAFT, study.getStatus(), new Supplier<String>() {
			@Override
			public String get() {
				return "스터디를 처음 만들면 DRAFT여야 한다.";
			}
		});*/
		assertTrue(study.getLimit() > 0, "스터디 최대 참석 인원은 0보다 커야 한다. ");
		
		System.out.println("create");
	}
```

→ 위의 상황에서 assertEquals만 실패해도 그 뒤에 있는 asserTrue는 실행이 되지 않는다. 

⇒ 모두 실행하기 위해서는 “AssertAll”을 사용한다. 

```java
 @Test
	@DisplayName("스터디 만들기")
	void create() {
		Study study = new Study(-10);
		assertAll(//Executable = 메소드이다. 
				() -> assertNotNull(study),
				() -> assertEquals(StudyStatus.DRAFT, study.getStatus(), ()-> "스터디를 처음 만들면 상태값이 DRAFT여야 한다."),
				() -> assertTrue(study.getLimit() > 0, "스터디 최대 참석 인원은 0보다 커야 한다. ")
		);
		
		System.out.println("create");
	}
```

[assertTimeout]

```java
@Test
	//@Disabled -> 이 테스트만 실행하지 않도록 함
	void create_new_study() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Study(-10));
		System.out.println("create1");
		assertTimeout(Duration.ofMillis(1000), () -> {
			new Study(10);
			Thread.sleep(300);
		});
		//assertTimeoutPreemptively = 원하는 시간 내에 종료되지 않으면 그냥 멈추기 , ThreadLocal 일 경우 스프링이 만든 트랜잭션이 제대로 되지 않아 테스트가 잘못된 방향으로 갈 수 있음 

		assertEquals("limit 은 0보다 커야 한다.", exception.getMessage());
	}
```

## JUnit 5: 조건에 따라 테스트 실행하기

특정한 조건을 만족하는 경우에 테스트를 실행하는 방법.

org.junit.jupiter.api.Assumptions.*

- assumeTrue(조건)
- assumingThat(조건, 테스트)

@Enabled___ 와 @Disabled___

- OnOS
- OnJre
- IfSystemProperty
- IfEnvironmentVariable
- If

```java
@Test
	@DisplayName("스터디 만들기")
	//(1) @EnabledOnOs({OS.MAC, OS.LINUX})//os에 따라 다른 테스트 설정 <->@DisabledOnOs
	//(2) @EnabledOnJre({JRE.JAVA_8})//특정 자바 버전에만 실행 가능 
	//(3) @EnabledEnvironmentVariable(named = "TEST_ENV", matches = "LOCAL")
	void create_new_study_again()
	{
		String test_env = System.getenv("TEST_ENV");
		System.out.println(test_env);
		//테스트 환경이 로컬인 경우만 다음과 같은 테스트 실행
		assumeTrue("LOCAL".equalsIgnoreCase(test_env));
		
		//2가지 방법 
		assumimgThat("LOCAL".equalsIgnoreCase(test_env), () -> {
			Study actual = new Study(10);
			assertThat(actual.getLimit()).isGreaterThan(0);
		});
	}
```

[단위 테스트 고찰]

- 단위를 생각할 때 행동의 단위라고 생각하여 상관없다고 생각하는 사람이 있고 아니라고 하는 사람도 있다.

(단위의 범위를 정하자)

외부 api 테스트 응답을 보낼 수 있다. 

테스트 서버가 없으면 mock을 해야 한다.
