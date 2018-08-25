package nts.uk.shr.com.security.audittrail.async;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

import lombok.val;
import nts.arc.task.AsyncTask;
import nts.arc.task.AsyncTaskService;
import nts.arc.time.GeneralDateTime;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.ScreenIdentifier;
import nts.uk.shr.com.security.audittrail.AuditTrailTransaction;
import nts.uk.shr.com.security.audittrail.UserInfoAdaptorForLog;
import nts.uk.shr.com.security.audittrail.basic.LogBasicInformation;
import nts.uk.shr.com.security.audittrail.basic.LoginInformation;
import nts.uk.shr.com.security.audittrail.correction.CorrectionLoggingAgent;
import nts.uk.shr.com.security.audittrail.correction.processor.CorrectionProcessorId;

@ApplicationScoped
public class AuditTrailAsyncExecutor implements CorrectionLoggingAgent {

	/**
	 * AsyncTaskService
	 */
	@Inject
	private AsyncTaskService asyncTaskService;
	
	@Inject
	private UserInfoAdaptorForLog userInfoAdaptor;
	
	@Override
	public void requestProcess(String operationId, CorrectionProcessorId processorId, HashMap<String, Serializable> parameters) {
		
		val userContext = AppContexts.user();
		
		val userInfo = userContext.isEmployee() 
				? this.userInfoAdaptor.findByEmployeeId(userContext.employeeId())
				: this.userInfoAdaptor.findByUserId(userContext.userId());
		
		
		val basicInfo = new LogBasicInformation(
				operationId,
				userContext.companyId(),
				userInfo,
				LoginInformation.byAppContexts(),
				GeneralDateTime.now(),
				userContext.roles(),
				AppContexts.requestedWebApi().getScreenIdentifier(),
				Optional.empty());
		
		val task = AsyncTask.builder()
				.withContexts()
				.keepsTrack(false)
				.build(() -> {
					val auditTrailTransaction = CDI.current().select(AuditTrailTransaction.class).get();
					auditTrailTransaction.begin(basicInfo, processorId, parameters);
				});
		
		this.asyncTaskService.execute(task);
	}

}
