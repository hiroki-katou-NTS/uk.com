package nts.uk.ctx.at.request.app.command.setting.company.otrestappcommon;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetting;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author yennth
 *
 */
@Stateless
@Transactional
public class UpdateOvertimeRestAppCommonSetCmdHandler extends CommandHandler<OvertimeRestAppCommonSetCmd>{
	@Inject
	private OvertimeRestAppCommonSetRepository otRestRep;

	@Override
	protected void handle(CommandHandlerContext<OvertimeRestAppCommonSetCmd> context) {
		String companyId = AppContexts.user().companyId();
		OvertimeRestAppCommonSetCmd data = context.getCommand();
		OvertimeRestAppCommonSetting otRestAppCom = OvertimeRestAppCommonSetting.createSimpleFromJavaType(companyId, data.getAppType(), data.getDivergenceReasonInputAtr(), 
																											data.getDivergenceReasonFormAtr(), 0, data.getPreDisplayAtr(), 
																											data.getPreExcessDisplaySetting(), data.getBonusTimeDisplayAtr(),
																											0, data.getPerformanceDisplayAtr(), data.getPerformanceExcessAtr(), 
																											0, data.getExtratimeDisplayAtr(), data.getExtratimeExcessAtr(), 
																											data.getAppDateContradictionAtr(), data.getCalculationOvertimeDisplayAtr());
		otRestRep.update(otRestAppCom);
	}
	
}
