package nts.uk.ctx.at.request.app.command.application.common;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.request.app.command.application.applicationlist.ReflectAfterApproveAsyncCmd;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.applicationreflect.service.AppReflectManager;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class ReflectAplicationCommmandHandler extends CommandHandlerWithResult<String, Integer>{
	@Inject
	private ApplicationRepository_New repoApp;
	
	@Inject
	private AppReflectManager appReflectManager;
	@Override
	protected Integer handle(CommandHandlerContext<String> context) {
			//get list application by list id
			Optional<Application_New> app = repoApp.findByID(AppContexts.user().companyId(), context.getCommand());
			if(!app.isPresent()){
				throw new BusinessException("Khong tim thay applicaion" + context.getCommand());
			}	
			Application_New application = app.get();
			if((application.getPrePostAtr().equals(PrePostAtr.PREDICT)&&
						(application.getAppType().equals(ApplicationType.OVER_TIME_APPLICATION)
						|| application.getAppType().equals(ApplicationType.BREAK_TIME_APPLICATION)))
					|| application.getAppType().equals(ApplicationType.GO_RETURN_DIRECTLY_APPLICATION)
					|| application.getAppType().equals(ApplicationType.WORK_CHANGE_APPLICATION)
					|| application.getAppType().equals(ApplicationType.ABSENCE_APPLICATION)
					|| application.getAppType().equals(ApplicationType.COMPLEMENT_LEAVE_APPLICATION))
			{
				appReflectManager.reflectEmployeeOfApp(application);
				Optional<Application_New> app1 = repoApp.findByID(AppContexts.user().companyId(), context.getCommand());
				if(!app1.isPresent()){
					throw new BusinessException("Khong tim thay applicaion" + context.getCommand());
				}
				return app1.get().getVersion().intValue();
			}else{
				return application.getVersion().intValue();
			}
			
	}
}
