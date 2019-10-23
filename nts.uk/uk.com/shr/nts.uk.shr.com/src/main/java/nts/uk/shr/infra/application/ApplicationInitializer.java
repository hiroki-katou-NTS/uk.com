package nts.uk.shr.infra.application;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.CDI;

import lombok.extern.slf4j.Slf4j;
import nts.arc.layer.infra.data.log.RepositoryLogger;
import nts.arc.layer.ws.exception.ServerError;
import nts.arc.layer.ws.preprocess.filters.RequestPerformanceDiagnose;
import nts.uk.shr.com.system.config.InitializeWhenDeploy;

@ApplicationScoped
@Slf4j
public class ApplicationInitializer {

	public void initialized(@Observes @Initialized(ApplicationScoped.class) Object event) {
		
		log.info("ApplicationInitializer START");
		
		ServerError.EXPOSES_DEFAILS_OF_ERROR = true;
		RequestPerformanceDiagnose.THRESHOLD_MILLISEC_TO_WARN = 500;
		RepositoryLogger.ENABLE_LOGGING = false;
		RepositoryLogger.THRESHOLD_MILLISECS_TO_WARN = 300;
		
		this.executeAllInitializeWhenDeploy();

		log.info("ApplicationInitializer END");
	}
	
	public void reinitialize() {

		log.info("ApplicationInitializer RE-INIT START");
		
		this.executeAllInitializeWhenDeploy();
		
		log.info("ApplicationInitializer RE-INIT END");
	}
	
	private void executeAllInitializeWhenDeploy() {
		
		CDI.current().select(InitializeWhenDeploy.class).forEach(obj -> obj.initialize());
	}
}
