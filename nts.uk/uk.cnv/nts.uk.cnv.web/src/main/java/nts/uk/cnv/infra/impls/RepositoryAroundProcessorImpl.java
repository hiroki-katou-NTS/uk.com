package nts.uk.cnv.infra.impls;

import javax.enterprise.context.RequestScoped;
import javax.interceptor.InvocationContext;

import nts.arc.layer.infra.data.intercept.RepositoryAroundProcessor;

@RequestScoped
public class RepositoryAroundProcessorImpl implements RepositoryAroundProcessor {

	@Override
	public Object process(InvocationContext context) {

		try {
			return context.proceed();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
