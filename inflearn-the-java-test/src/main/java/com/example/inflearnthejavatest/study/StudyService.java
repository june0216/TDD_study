package com.example.inflearnthejavatest.study;

import com.example.inflearnthejavatest.domain.Member;
import com.example.inflearnthejavatest.domain.Study;
import com.example.inflearnthejavatest.member.MemberService;

import java.util.Optional;

public class StudyService {
	private final MemberService memberService;
	private final StudyRepository repository;

	public StudyService(MemberService memberService, StudyRepository repository) {
		assert memberService != null;//null이면 안된다. -> 자바의 assert 키워드를 통해서 null이면 nullpointerException
		assert repository != null;
		this.memberService = memberService;
		this.repository = repository;
	}

	public Study createNewStudy(Long memberId, Study study)
	{
		Optional<Member> member = memberService.findById(memberId);
		study.setOwner(member.orElseThrow(() -> new IllegalArgumentException("Member doesn't exist for id: '" + memberId + "'")));
		return repository.save(study);
	}

}
