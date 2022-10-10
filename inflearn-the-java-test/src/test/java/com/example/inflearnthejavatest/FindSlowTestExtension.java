package com.example.inflearnthejavatest;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class FindSlowTestExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {
	private long THRESHOLD;

	public FindSlowTestExtension(long THRESHOLD) {
		this.THRESHOLD = THRESHOLD;
	}

	@Override
	public void beforeTestExecution(ExtensionContext context) throws Exception {
		ExtensionContext.Store store =getStore(context);//데이터를 넣고 빼는
		store.put("STATR_TIME", System.currentTimeMillis());
	}

	@Override
	public void afterTestExecution(ExtensionContext context) throws Exception {
		String testMethodName = context.getRequiredTestMethod().getName();
		//SlowTest annotation = requiredTestMethod.getAnnotation(SlowTest.class);//자바 리플렉션
		//if(annotation != null){

		//}
		ExtensionContext.Store store =getStore(context);//데이터를 넣고 빼
		long start_time = store.remove("STATR_TIME", long.class);
		long duration = System.currentTimeMillis() - start_time;
		if(duration > THRESHOLD )//&& annotation == null
		{
			System.out.printf("Please consider mark method [%s] with @SlowTest. \n", testMethodName);
		}

	}

	private ExtensionContext.Store getStore(ExtensionContext context)
	{
		String testClassName = context.getRequiredTestClass().getName();
		String testMethodName = context.getRequiredTestMethod().getName();
		return context.getStore(ExtensionContext.Namespace.create(testClassName, testMethodName));
	}
}
