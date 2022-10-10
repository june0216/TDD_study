package com.example.inflearnthejavatest;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;
import java.util.function.Supplier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

//@ExtendWith(FindSlowTestExtension.class)//커스터마이징 못함(단점)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)//어떻게 디스플레이할 것인지에대한 전략 구현체를 넣는다.
@TestInstance(TestInstance.Lifecycle.PER_CLASS)//메소드 전체 하나의 인스턴스 공유
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)//순서 정해줌 -> 메소드에 @Order(num)순위를 적어준다
class StudyTest {// 이 클래스는 test 실행마다 인스턴스를 만든다. -> test 간의 의존성을 없애기 위해서 (테스트 순서는 정해진 것이 아니다)
	//하지만 클래스 당 하나의 인스턴스를 만들어서 공유하는 방법이 있다.

	int value = 1;

	@RegisterExtension//필드 내에서 사용 가능 -> 인스턴스 만들어서 등록 -> 커스터마이징 가능
	static FindSlowTestExtension findSlowTestExtension = new FindSlowTestExtension(1000L);
	@Order(1)
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




	@Order(2)
	@Test
	//@Disabled -> 이 테스트만 실행하지 않도록 함
	void create_new_study() throws InterruptedException {
		Thread.sleep(1005L);//느린 테스트
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

	@FastTest //@Test + @Tag("fast") 를 내포함 -> 문자열을 직접 입력하는 부분이 없기 때문에 오류 가능성 적어짐
	@DisplayName("스터디 만들기 fast")
	void create_new_study_test_composed_Annotation() {//로컬 환경
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


	@DisplayName("스터디 만들기")
	@RepeatedTest(value = 10, name = "{displayName}, {currentRepetition}/{totalRepetition}")//반복할 테스트의 횟수 작성
	void repeatTest(RepetitionInfo repetitionInfo )
	{
		//인자 RepetitionInfo를 통해 몇번을 반복하는지, 총 몇번을 반복해야 하는지 알 수 있음
		System.out.println("test"+repetitionInfo.getCurrentRepetition() + "/" + repetitionInfo.getTotalRepetitions());
	}

	@DisplayName("스터디 만들기")
	@ParameterizedTest(name = "{index} {displayName} message={0}")//각 파라미터 개수만큼 반복하면서 실행한다.
	@ValueSource(strings = {"날씨가", "많이", "추워지고", "있네요"})//4가지의 파라미터 경우의 수를 제공
	@EmptySource//파라미터에 빈 값을 넣어준다 //빈 값 + Null => @NullAndEmptySource
	@NullSource//파라미터에 null을 넣어준다.
	void parameterizedTest(@ConvertWith(StudyConverter.class) Study study)
	{
		System.out.println(study.getLimit());
	}
	//integer 값인데 Study 로 변환하고 싶음 -> "converter" 만들기
	static class StudyConverter extends SimpleArgumentConverter{//1개의 arg일 때
		@Override
		protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException
		{
			assertEquals(Study.class, targetType, "Can only to Study");
			return new Study(Integer.parseInt(source.toString()));
		}


	}

	@DisplayName("스터디 만들기")
	@ParameterizedTest(name = "{index} {displayName} message={0}")//각 파라미터 개수만큼 반복하면서 실행한다.
	@CsvSource({"10, '자바 스터디'", "20 스프링"})
	void parameterizedTest2(Integer limit, String name)
	{
		Study study = new Study(limit, name);
		System.out.println(study);
	}

	@DisplayName("스터디 만들기")
	@ParameterizedTest(name = "{index} {displayName} message={0}")//각 파라미터 개수만큼 반복하면서 실행한다.
	@CsvSource({"10, '자바 스터디'", "20 스프링"})
	void parameterizedTest3(ArgumentsAccessor argumentsAccessor)// 방법2 (@AggregateWith(StudyAggregator.class) Study study)
	{
		Study study = new Study(argumentsAccessor.getInteger(0), argumentsAccessor.getString(1));
		System.out.println(study);
	}

	static class StudyAggregator implements ArgumentsAggregator{// 반드시 static 이거나 public 이어야 한다

		@Override
		public Object aggregateArguments(ArgumentsAccessor argumentsAccessor, ParameterContext parameterContext) throws ArgumentsAggregationException {
			return new Study(argumentsAccessor.getInteger(0), argumentsAccessor.getString(1));
		}
	}

//순서를 명확하게 드러내지 않은 이유는 제대로된 단위 테스트는 의존성이 없어야 한다. (다른 테스트 코드에 영향을 미치면 안 된다)
	//하지만 시나리오 테스트를 하고 싶을 경우 순차적으로 유즈 케이스마다 상태 정보를 유지하면서 데이터도 공유해야 하므로 인스턴스를 매번 만드는 거









	@BeforeAll //모든 테스트를 실행하기 전에 딱 한 번만 호출된다.
	static void beforeAll()//이 어노테이션을 사용하기 위해서 반드시 static만 가능하다 private은 불가능 , default는 가능 -> 하지만 모든 메소드들에 대해 인스턴스를 1개만 만든다면 적용 x
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