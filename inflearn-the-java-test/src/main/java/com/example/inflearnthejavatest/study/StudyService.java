package com.example.inflearnthejavatest.study;

import com.example.inflearnthejavatest.domain.Member;
import com.example.inflearnthejavatest.domain.Study;

import java.util.Optional;

public class StudyService {
	private final MemberSerivce memberSerivce;
	private final StudyRepository repository;

	public StudyService(MemberSerivce memeberSerivce, StudyRepository repository) {
		assert memberService != null;//null이면 안된다.
		assert repository != null;
		this.memeberSerivce = memeberSerivce;
		this.repository = repository;
	}

	public Study createNewStudy(Long memberId, Study study)
	{
		Optional<Member> member = memberService.findById(memberId);
		study.setOwner(member.orElseThrow(() -> new IllegalArgumentException("Member doesn't exist for id: '" + memberId + "'")));
		return repository.save(study);
	}

}
