package nts.uk.ctx.at.request.app.command.application.applicationlist;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.setting.company.request.RequestSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.RequestSettingRepository;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.AfterhandRestriction;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.AppTypeSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.BeforehandRestriction;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.ReceptionRestrictionSetting;
import nts.uk.shr.com.context.AppContexts;
/**
 * update list before, after hand restriction and apptype set list
 * @author yennth
 *
 */
@Stateless
public class UpdateAppTypeBfCommandHandler extends CommandHandler<AppTypeBfCommand>{
	@Inject
	private RequestSettingRepository reqRep;

	@Override
	protected void handle(CommandHandlerContext<AppTypeBfCommand> context) {
		AppTypeBfCommand data = context.getCommand();
		String companyId = AppContexts.user().companyId();
		Optional<RequestSetting> requestOp = reqRep.findByCompany(companyId);
		List<ReceptionRestrictionSetting> beforeList = new ArrayList<>();
		List<AppTypeSetting> appList = new ArrayList<>();
		// convert list before after from command to domain
		for(BfReqSetCommand item : data.getBeforeAfter()){
			ReceptionRestrictionSetting bfDomain = ReceptionRestrictionSetting.toDomain(item.getAppType(), 
					BeforehandRestriction.toDomain(item.getRetrictPreMethodFlg(), 
													item.getRetrictPreUseFlg(), 
													item.getRetrictPreDay(),
													item.getRetrictPreTimeDay(),
													item.getPreOtTime(),
													item.getNormalOtTime(),
													item.getRetrictPreDay(),
													item.getRetrictPreUseFlg()), 
					AfterhandRestriction.toDomain(item.getRetrictPostAllowFutureFlg()));
			beforeList.add(bfDomain);
		}
		// convert list app type set from command to domain
		for(AppTypeSetCommand obj : data.getAppType()){
			AppTypeSetting appDomain = AppTypeSetting.toDomain(obj.getDisplayInitialSegment(), 
					obj.getCanClassificationChange(), 
					obj.getDisplayFixedReason(), 
					obj.getSendMailWhenApproval(), 
					obj.getSendMailWhenRegister(), 
					obj.getDisplayAppReason(), 
					obj.getAppType());
			appList.add(appDomain);
		}
		if(requestOp.isPresent()){
			reqRep.update(beforeList, appList);
		}
	}
}
