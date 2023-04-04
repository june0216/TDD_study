package com.example.inflearnthejavatest.study;

import com.example.inflearnthejavatest.domain.Member;
import com.example.inflearnthejavatest.domain.Study;
import com.example.inflearnthejavatest.member.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

	@Mock
	MemberService memberService;
	@Mock
	StudyRepository studyRepository;
	/*		MemberService memberService = mock(MemberService.class); //mock 메소드를 사용해서 mock 객체를 만든다.
		StudyRepository studyRepository = mock(StudyRepository.class);*/

	@Test
	void createStudyService(MemberService memberService, StudyRepository studyRepository) { // 테스트의 인스턴스를 만드는 서비스
		//studyService 안에 레포지토리에 의존하고 있다. -> mock을 쓰기 좋은 환경

		Optional<Member> optional = memberService.findById(1L);
		assertNull(optional); // 비어있는지 확인 ->
		memberService.validate(2L); // 아무런 행위를 하지 않는다.
		StudyService studyService = new StudyService(memberService, studyRepository);
		assertNotNull(studyService);

		//-===
		Member member = new Member();
		member.setId(1l);
		member.setEmail("ziui@naver.com"); // 이렇게 하나의 객체가 있다고 가정했을 때 아래의 것을 확인하자
		when(memberService.findById(1L)).thenReturn(Optional.of(member));//목객체를 스터빙한 것이다.


	}

	@Test
	void createNewStudyService(MemberService memberService, StudyRepository studyRepository) { // 테스트의 인스턴스를 만드는 서비스
		//studyService 안에 레포지토리에 의존하고 있다. -> mock을 쓰기 좋은 환경


		StudyService studyService = new StudyService(memberService, studyRepository);
		assertNotNull(studyService);

		//(1)
		Member member = new Member();
		member.setId(1l);
		member.setEmail("ziui@naver.com"); // 이렇게 하나의 객체가 있다고 가정했을 때 아래의 것을 확인하자
		when(memberService.findById(any())).thenReturn(Optional.of(member));//목객체를 스터빙한 것이다. 이떄 any는 특정 숫자를 지정할 때의 문제로 인해 이를 방지하고자 만든 메소드이며 아무 값이나 넣어도 된따는 것을 의미한다.
		//argument matchers

		Optional<Member> findById = memberService.findById(1L);
		assertEquals("ziui@naver.com", findById.get().getEmail());

		Study study = new Study(10, "java");
		studyService.createNewStudy(1L, study); // 이 안에 memberService.findById가 있는데 이때 위에서 목 객체를 생성해줬으므로
		//그것을 실행하며 findBy(1L); 을 하면 위에서 만든 member 객체가 반환된다.

		// (2) 예외를 던질 수도 있음
		doThrow(new IllegalArgumentException()).when(memberService).validate(1L);//when일 조건에 예외를 던지도록 한다.
		assertThrows(IllegalArgumentException.class, ()-> { // 예외를 던지는지 확인
			memberService.validate(1L);
		});

		//(3) 순서를 정해서 실행할 수 있다.
		when(memberService.findById(any()))
				.thenReturn(Optional.of(member))//첫번째는 정상적인 멤버 객체를 리턴한다.
				.thenThrow(new RuntimeException()) //두번 째 호출되면 예외를 던진다.
				.thenReturn(Optional.empty());//3번째 호출되면 비어있는 값을 던지도록 조작함
		Optional<Member> byId = memberService.findById(1L);

		//1번째 호출
		assertEquals("ziui@naver.com", byId.get().getEmail());
		//2번째 호출 -> 예외를 던지게 함
		assertThrows(RuntimeException.class, () -> {
			memberService.findById(2L);
		});

		assertEquals(Optional.empty(), memberService.findById(3L));


	}
//목을 사용하지 않을 경우 다음과 같이 작성해야 한다.
/*		MemberService memberService = new MemberService() { //인터페이스 memberService를 인라인으로 구현한다.
			@Override
			public Optional<Member> findById(Long memberId) {
				return Optional.empty();
			}

			@Override
			public void validate(Long memberId) {

			}

			@Override
			public void notify(Study newstudy) {

			}

			@Override
			public void notify(Member member) {

			}

		};
		StudyRepository studyRepository = new StudyRepository() {
			@Override
			public List<Study> findAll() {
				return null;
			}

			@Override
			public List<Study> findAll(Sort sort) {
				return null;
			}

			@Override
			public List<Study> findAllById(Iterable<Long> iterable) {
				return null;
			}

			@Override
			public <S extends Study> List<S> saveAll(Iterable<S> iterable) {
				return null;
			}

			@Override
			public void flush() {

			}

			@Override
			public <S extends Study> S saveAndFlush(S s) {
				return null;
			}

			@Override
			public void deleteInBatch(Iterable<Study> iterable) {

			}

			@Override
			public void deleteAllInBatch() {

			}

			@Override
			public Study getOne(Long aLong) {
				return null;
			}

			@Override
			public <S extends Study> List<S> findAll(Example<S> example) {
				return null;
			}

			@Override
			public <S extends Study> List<S> findAll(Example<S> example, Sort sort) {
				return null;
			}

			@Override
			public Page<Study> findAll(Pageable pageable) {
				return null;
			}

			@Override
			public <S extends Study> S save(S s) {
				return null;
			}

			@Override
			public Optional<Study> findById(Long aLong) {
				return Optional.empty();
			}

			@Override
			public boolean existsById(Long aLong) {
				return false;
			}

			@Override
			public long count() {
				return 0;
			}

			@Override
			public void deleteById(Long aLong) {

			}

			@Override
			public void delete(Study study) {

			}

			@Override
			public void deleteAll(Iterable<? extends Study> iterable) {

			}

			@Override
			public void deleteAll() {

			}

			@Override
			public <S extends Study> Optional<S> findOne(Example<S> example) {
				return Optional.empty();
			}

			@Override
			public <S extends Study> Page<S> findAll(Example<S> example, Pageable pageable) {
				return null;
			}

			@Override
			public <S extends Study> long count(Example<S> example) {
				return 0;
			}

			@Override
			public <S extends Study> boolean exists(Example<S> example) {
				return false;
			}
		};*/

}