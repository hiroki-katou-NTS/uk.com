package nts.uk.ctx.at.request.app.command.application.common;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.ApplicationRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 12.詳細画面取消の処理
 * @author tutk
 *
 */
@Stateless
@Transactional
public class DetailScreenCancelProcess extends CommandHandler<UpdateApplicationCommand> {
	 
	@Inject
	private ApplicationRepository appRepo;
//	/**
//	 *  update
//	 * @param applicationID
//	 */
//	public void detailScreenCancelProcess(String applicationID) {
//		String companyID = AppContexts.user().companyId();
//
//	}
	@Override
	protected void handle(CommandHandlerContext<UpdateApplicationCommand> context) {
		String companyID = AppContexts.user().companyId();
		
		Optional<Application> app = appRepo.getAppById(companyID, context.getCommand().getApplicationID());
		if(app.isPresent()) {
			appRepo.updateById(companyID, context.getCommand().getApplicationID());
		}else {
			throw new BusinessException("K ton tai");
		}
	}
}
