package nts.uk.ctx.at.request.app.command.application.appabsence;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.request.app.command.application.holidayshipment.SaveHolidayShipmentCommandHandler;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.IFactoryApplication;
import nts.uk.ctx.at.request.dom.application.appabsence.AppAbsence;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;
import nts.uk.ctx.at.request.dom.application.appabsence.service.AbsenceServiceProcess;
import nts.uk.ctx.at.request.dom.application.appabsence.service.SpecialLeaveInfor;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.RegisterAtApproveReflectionInfoService_New;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.after.NewAfterRegister_New;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.NewBeforeRegister_New;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CreatAppAbsenceCommandHandler extends CommandHandlerWithResult<CreatAppAbsenceCommand, ProcessResult>{
	
	final static String DATE_FORMAT = "yyyy/MM/dd";
	@Inject
	private IFactoryApplication iFactoryApplication;
	@Inject
	private NewBeforeRegister_New newBeforeRegister;
	@Inject
	private SaveHolidayShipmentCommandHandler saveHolidayShipmentCommandHandler;
	@Inject 
	private AbsenceServiceProcess absenceServiceProcess;
	@Inject
	private NewAfterRegister_New newAfterRegister;

	@Inject
	private RegisterAtApproveReflectionInfoService_New registerService;
	
	@Override
	protected ProcessResult handle(CommandHandlerContext<CreatAppAbsenceCommand> context) {
		CreatAppAbsenceCommand command = context.getCommand();
		// 会社ID
		String companyID = AppContexts.user().companyId();
		// 申請ID
		String appID = IdentifierUtil.randomUniqueId();
		// Create Application
		GeneralDate startDate = command.getStartDate() == null ? null : GeneralDate.fromString(command.getStartDate(), DATE_FORMAT);
		GeneralDate endDate = command.getEndDate() == null ? null : GeneralDate.fromString(command.getEndDate(), DATE_FORMAT);
		Application_New appRoot = iFactoryApplication.buildApplication(appID, startDate,
				command.getPrePostAtr(), command.getApplicationReason(), command.getApplicationReason().replaceFirst(":", System.lineSeparator()),
				ApplicationType.ABSENCE_APPLICATION, startDate, endDate, command.getEmployeeID());
		AppAbsence appAbsence = new AppAbsence(companyID,
				appID,
				command.getHolidayAppType(),
				command.getWorkTypeCode(),
				command.getWorkTimeCode(),
				command.isHalfDayFlg(), 
				command.isChangeWorkHour(),
				command.getAllDayHalfDayLeaveAtr(), 
				command.getStartTime1(),
				command.getEndTime1(),
				command.getStartTime2(),
				command.getEndTime2());
		// 2-1.新規画面登録前の処理を実行する
		newBeforeRegister.processBeforeRegister(appRoot,0);
		// 7.登録時のエラーチェック
		checkBeforeRegister(command,startDate,endDate,true);
		// insert
		absenceServiceProcess.CreateAbsence(appAbsence, appRoot);
		// 2-2.新規画面登録時承認反映情報の整理
		registerService.newScreenRegisterAtApproveInfoReflect(appRoot.getEmployeeID(), appRoot);
		// 2-3.新規画面登録後の処理を実行
		return newAfterRegister.processAfterRegister(appRoot);

	}
	public void checkBeforeRegister(CreatAppAbsenceCommand command,GeneralDate startDate,GeneralDate endDate,boolean isInsert){
		int countDay = 0;
		for(int i = 0; startDate.compareTo(endDate) + i <= 0; i++){
			GeneralDate appDate = startDate.addDays(i);
			countDay = countDay + 1;
			// 休暇・振替系申請存在チェック
			if(isInsert){
				saveHolidayShipmentCommandHandler.vacationTransferCheck(command.getEmployeeID(), appDate, command.getPrePostAtr());
			}
			if(command.getHolidayAppType() == HolidayAppType.DIGESTION_TIME.value){
				// 11.時間消化登録時のエラーチェック :TODO
			}
		}
		//選択する休暇種類が特別休暇の場合
		if(command.getHolidayAppType() == HolidayAppType.SPECIAL_HOLIDAY.value){
			// 10.特別休暇の情報を取得する;
			SpecialLeaveInfor specialLeaveInfor = this.absenceServiceProcess.getSpecialLeaveInfor(command.getWorkTypeCode());
			// 当てはまるフラグ(output)がtrue AND 付与方法(output)が単独付与
			if(specialLeaveInfor.isApplicableFlg() && specialLeaveInfor.getMethodOfGranting() == 0){
				if(specialLeaveInfor.isRelationFlg()){
					// 画面上の喪主チェック状態をチェックする
					if(specialLeaveInfor.isMournerDisplayFlg()){
						if(countDay > specialLeaveInfor.getMaxDayMourner()){
							throw new BusinessException("Msg_632", Integer.toString(specialLeaveInfor.getMaxDayMourner())); 
						}
					}else{
						if(countDay > specialLeaveInfor.getMaxDayNotMourner()){
							throw new BusinessException("Msg_632", Integer.toString(specialLeaveInfor.getMaxDayNotMourner())); 
						}
					}
				}else{
					// 続柄の表示フラグがfalse
					if(countDay > specialLeaveInfor.getMaxDayFixed()){
						throw new BusinessException("Msg_632", Integer.toString(specialLeaveInfor.getMaxDayFixed())); 
					}
				}
			}
		}
		
	}

}
