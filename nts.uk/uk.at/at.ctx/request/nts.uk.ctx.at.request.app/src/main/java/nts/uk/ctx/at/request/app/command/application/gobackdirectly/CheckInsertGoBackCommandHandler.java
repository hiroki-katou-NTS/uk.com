package nts.uk.ctx.at.request.app.command.application.gobackdirectly;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.service.GoBackDirectlyRegisterService;
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
//		Application_New newApp = Application_New.firstCreate(
//				companyId, 
//				EnumAdaptor.valueOf(command.appCommand.getPrePostAtr(), PrePostAtr.class),  
//				GeneralDate.fromString(command.appCommand.getAppDate(), "yyyy/MM/dd"),
//				EnumAdaptor.valueOf(command.appCommand.getAppType(), ApplicationType.class), 
//				command.appCommand.getEmployeeIDLst().get(0),
//				new AppReason(command.appCommand.getOpAppReason()));
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
