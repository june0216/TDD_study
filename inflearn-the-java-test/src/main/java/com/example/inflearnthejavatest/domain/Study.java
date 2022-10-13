package com.example.inflearnthejavatest.domain;

public class Study {
	public Study(int limit, String name) {
		this.limit = limit;
		this.name = name;
	}

	private StudyStatus status = StudyStatus.DRAFT;
	private int limit;

	private String name;

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Study{" +
				"status=" + status +
				", limit=" + limit +
				", name='" + name + '\'' +
				'}';
	}

	public Study(int limit){
		if(limit < 0)
		{
			throw new IllegalArgumentException("limit 은 0보다 커야 한다. ");
		}
		this.limit = limit;
	}

	public StudyStatus getStatus() {
		return this.status;
	}

	public int getLimit() {
		return limit;
	}
}
