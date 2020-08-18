package nts.sample.impls;

import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletContextListener;

import nts.arc.layer.infra.servlet.context.listener.ServletContextListenerCollector;
import nts.arc.task.schedule.internal.config.CdiJobFactoryInitializer;
import nts.arc.task.schedule.internal.config.CustomQuartzInitializerListener;

public class ServletContextListenerCollectorImpl implements ServletContextListenerCollector {

	private static final List<ServletContextListener> LISTENERS = Arrays.asList(
			new CustomQuartzInitializerListener(),
			new CdiJobFactoryInitializer()
			);
	
	@Override
	public List<ServletContextListener> collect() {
		return LISTENERS;
	}

}
