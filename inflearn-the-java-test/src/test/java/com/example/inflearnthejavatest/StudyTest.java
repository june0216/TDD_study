package com.example.inflearnthejavatest;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;

import java.time.Duration;
import java.util.function.Supplier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;


@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)//어떻게 디스플레이할 것인지에대한 전략 구현체를 넣는다.
class StudyTest {

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

	/*	@Test
	@DisplayName("스터디 만들기")
	void create() {
		Study study = new Study(-10);
		assertNotNull(study);
		assertEquals(StudyStatus.DRAFT, study.getStatus(), ()-> "스터디를 처음 만들면 상태값이 DRAFT여야 한다.");
		//assertEquals(기대하는 값, 실제 값, "스터디를 처음 만들면 상태값이 DRAFT여야 한다."); 순서가 바뀌어도 되긴 함
		//메세지 부분에서 그냥 1) 문자열과 2) 람다식으로 표현하는 방법이 있는데 후자가 더 좋은 이유는 생성을 필요한 시점에만 하기 때문이다. 그냥 문자열로 한다면 테스트가 실패하든 성공하든 상관없이 항상 생성한다.
		*//*assertEquals(StudyStatus.DRAFT, study.getStatus(), new Supplier<String>() {
			@Override
			public String get() {
				return "스터디를 처음 만들면 DRAFT여야 한다.";
			}
		});*//*
		assertTrue(study.getLimit() > 0, "스터디 최대 참석 인원은 0보다 커야 한다. ");

		System.out.println("create");
	}*/




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
		//다른 방법으로도 가능 assertThat(actual.getLimit()).isGreaterThan(0);

		assertEquals("limit 은 0보다 커야 한다.", exception.getMessage());
	}

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

		/*또는
		assumimgThat("LOCAL".equalsIgnoreCase(test_env), () -> {
			Study actual = new Study(10);
			assertThat(actual.getLimit()).isGreaterThan(0);
		});
		 */
	}
//=================================================
	@Test
	@DisplayName("스터디 만들기 fast")
	@Tag("fast")//edit configuration에서 Tag를 fast로 한 것만 실행
	void create_new_study_test() {//로컬 환경
		Study actual = new Study(10);
		assertThat(actual.getLimit()).isGreaterThan(0);
	}

	@Test
	@DisplayName("스터디 만들기 slow")
	@Tag("slow")//원격 환경
	void create_new_study_test2() {
		Study actual = new Study(10);
		assertThat(actual.getLimit()).isGreaterThan(0);
	}





	@BeforeAll //모든 테스트를 실행하기 전에 딱 한 번만 호출된다.
	static void beforeAll()//이 어노테이션을 사용하기 위해서 반드시 static만 가능하다 private은 불가능 , default는 가능
	{//리턴타입 불가능 => 꼭 "static void"라고 생각하기
		System.out.println("before all");
	}

	@AfterAll// 모든 테스트를 실행하고 나서 한 번 실행된다.
	static void AfterAll(){
		System.out.println("after all");
	}

	@BeforeEach //각각의 테스트 이전에 한 번 실행
	void beforeEach() {
		System.out.println("before Each");
	}

	@AfterEach //각각의 테스트 이후에 한 번 실행
	void afterEach() {
		System.out.println("after Each");
	}


	/*
	* 결과
 before all
before Each
create
after Each
before Each
create1
after Each
after all
	* */



}