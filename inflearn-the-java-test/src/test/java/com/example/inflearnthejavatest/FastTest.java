package com.example.inflearnthejavatest;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)//메소드에서 사용하겠다
@Retention(RetentionPolicy.RUNTIME)//런타임 동안에 계속 적용하겠다.
@Tag("fast")//fast라는 태그를 붙여달라
@Test
public @interface FastTest {
	//@Tag("fast")//fast라는 태그를 붙여달라
	//@Test
	// 이 두 개의 어노테이션을 메타 어노테이션으로 사용해서 composed 어노테이션을 조합해서 만든 것이다.
}
