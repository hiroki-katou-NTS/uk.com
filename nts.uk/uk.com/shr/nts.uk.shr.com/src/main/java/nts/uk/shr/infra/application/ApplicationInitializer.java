package nts.uk.shr.infra.application;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;

import nts.arc.layer.ws.exception.ServerError;

@ApplicationScoped
public class ApplicationInitializer {

	public void initialized(@Observes @Initialized(ApplicationScoped.class) Object event) {
		
		ServerError.EXPOSES_DEFAILS_OF_ERROR = true;
	}
}
