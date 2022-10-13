package com.example.inflearnthejavatest.study;

import com.example.inflearnthejavatest.domain.Member;
import com.example.inflearnthejavatest.domain.Study;
import com.example.inflearnthejavatest.member.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class StudyServiceTest {

	@Test
	void createStudyService() {
		//studyService 안에 레포지토리에 의존하고 있다. -> mock을 쓰기 좋은 환경
		MemberService memberService = new MemberService() {
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
		StudyRepository repository = new StudyRepository() {
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
		};
		StudyService studyService = new StudyService(memberService, repository);
	}

}