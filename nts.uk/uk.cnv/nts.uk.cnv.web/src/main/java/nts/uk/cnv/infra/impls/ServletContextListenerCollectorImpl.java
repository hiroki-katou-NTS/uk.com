package nts.uk.cnv.infra.impls;

import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletContextListener;

import nts.arc.layer.infra.servlet.context.listener.ServletContextListenerCollector;

public class ServletContextListenerCollectorImpl implements ServletContextListenerCollector {

	private static final List<ServletContextListener> LISTENERS = Arrays.asList(

			);

	@Override
	public List<ServletContextListener> collect() {
		return LISTENERS;
	}

}
