package nts.uk.shr.infra.application;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.CDI;

import lombok.extern.slf4j.Slf4j;
import nts.arc.layer.ws.exception.ServerError;
import nts.uk.shr.infra.i18n.resource.I18NResourcesForUK;

@ApplicationScoped
@Slf4j
public class ApplicationInitializer {

	public void initialized(@Observes @Initialized(ApplicationScoped.class) Object event) {
		
		log.info("ApplicationInitializer START");
		
		ServerError.EXPOSES_DEFAILS_OF_ERROR = true;
		
		CDI.current().select(I18NResourcesForUK.class).get().initialize();

		log.info("ApplicationInitializer END");
	}
}
