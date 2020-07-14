package nts.uk.ctx.at.request.app.command.application.gobackdirectly;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;


import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.app.find.application.gobackdirectly.InforGoBackCommonDirectDto;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.ApplicationType_Old;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.ConfirmMsgOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.PrePostAtr_Old;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly_Old;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.service.GoBackDirectlyRegisterService;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.service.InforGoBackCommonDirectOutput_Old;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.service.InforWorkTime;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.service.InforWorkType;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.GoBackDirectlyCommonSetting;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class CheckInsertGoBackCommandHandler extends CommandHandler<InsertApplicationGoBackDirectlyCommand>{
	@Inject
	private GoBackDirectlyRegisterService goBackDirectlyRegisterService;
	@Override
	protected void handle(CommandHandlerContext<InsertApplicationGoBackDirectlyCommand> context) {
		String companyId = AppContexts.user().companyId();
		InsertApplicationGoBackDirectlyCommand command = context.getCommand();		
		//get new Application Item
		Application_New newApp = Application_New.firstCreate(
				companyId, 
				EnumAdaptor.valueOf(command.appCommand.getPrePostAtr(), PrePostAtr_Old.class),  
				command.appCommand.getApplicationDate(),
				EnumAdaptor.valueOf(command.appCommand.getApplicationType(), ApplicationType_Old.class), 
				command.appCommand.getEnteredPersonSID(),
				new AppReason(command.appCommand.getApplicationReason()));
		// get new GoBack Direct Item
//		GoBackDirectly newGoBack = new GoBackDirectly(
//				companyId, 
//				newApp.getAppID(),
//				command.goBackCommand.workTypeCD, 
//				command.goBackCommand.siftCD, 
//				command.goBackCommand.workChangeAtr,
//				command.goBackCommand.goWorkAtr1, 
//				command.goBackCommand.backHomeAtr1,
//				command.goBackCommand.workTimeStart1, 
//				command.goBackCommand.workTimeEnd1,
//				command.goBackCommand.workLocationCD1, 
//				command.goBackCommand.goWorkAtr2,
//				command.goBackCommand.backHomeAtr2, 
//				command.goBackCommand.workTimeStart2,
//				command.goBackCommand.workTimeEnd2, 
//				command.goBackCommand.workLocationCD2);
//		InforGoBackCommonDirectDto ig = command.getInforGoBackCommonDirectDto();
//		InforGoBackCommonDirectOutput inforGoBackCommonDirectOutput = new InforGoBackCommonDirectOutput(
//				new InforWorkType(ig.getWorkType().getWorkType(), ig.getWorkType().getNameWorkType()),
//				new InforWorkTime(ig.getWorkTime().getWorkTime(), ig.getWorkTime().getNameWorkTime()), 
//				new AppDispInfoStartupOutput(
//						ig.getAppDispInfoStartupDto().appDispInfoNoDateOutput.toDomain(), 
//						ig.getAppDispInfoStartupDto().appDispInfoWithDateOutput.toDomain(),
//						ig.getAppDispInfoStartupDto().appDetailScreenInfo == null ? Optional.ofNullable(null) :
//						Optional.of(ig.getAppDispInfoStartupDto().appDetailScreenInfo.toDomain())), 
//				GoBackDirectlyCommonSetting.createFromJavaType(
//						ig.getGobackDirectCommonDto().getCompanyID(),
//						ig.getGobackDirectCommonDto().getWorkChangeFlg(), 
//						ig.getGobackDirectCommonDto().getWorkChangeTimeAtr(), 
//						ig.getGobackDirectCommonDto().getPerformanceDisplayAtr(), 
//						ig.getGobackDirectCommonDto().getContraditionCheckAtr(), 
//						ig.getGobackDirectCommonDto().getWorkType(),
//						ig.getGobackDirectCommonDto().getLateLeaveEarlySetAtr(), 
//						ig.getGobackDirectCommonDto().getCommentContent1(), 
//						ig.getGobackDirectCommonDto().getCommentFontWeight1(), 
//						ig.getGobackDirectCommonDto().getCommentFontColor1(), 
//						ig.getGobackDirectCommonDto().getCommentContent2(), 
//						ig.getGobackDirectCommonDto().getCommentFontWeight2(), 
//						ig.getGobackDirectCommonDto().getCommentFontColor2()), 
//				ig.getLstWorkType().stream().map(item -> item.toDomain()).collect(Collectors.toList()), 
//				Optional.of(newGoBack));
//		boolean agentAtr = false;
//		boolean mode = true;
//		
////		共通登録前のエラーチェック処理
//		List<ConfirmMsgOutput> confirmMsgLst = goBackDirectlyRegisterService.checkBeforRegisterNew(companyId, agentAtr, newApp, newGoBack ,inforGoBackCommonDirectOutput, mode);
//		
////		「確認メッセージ」リストがあるか確認する
//		if(!confirmMsgLst.isEmpty()) {
//			
//		}else {
////			「直行直帰登録」処理を実行する
//			// register 
//			
//		}
		
	}

}
