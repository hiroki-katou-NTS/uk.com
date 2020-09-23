package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.NewBeforeRegister;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.overtime.service.CheckWorkingInfoResult;
@Stateless
public class DetailBeforeUpdateImpl implements DetailBeforeUpdate {

	@Inject
	private NewBeforeRegister newBeforeRegister;
	
	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithm;
	
	@Inject
	private ApplicationRepository applicationRepository;
	
	public void processBeforeDetailScreenRegistration(String companyID, String employeeID, GeneralDate appDate,
			int employeeRouteAtr, String appID, PrePostAtr postAtr, int version, String wkTypeCode,
			String wkTimeCode, AppDispInfoStartupOutput appDispInfoStartupOutput) {
		//勤務種類、就業時間帯チェックのメッセージを表示
		displayWorkingHourCheck(companyID, wkTypeCode, wkTimeCode);
		// 選択した勤務種類の矛盾チェック(check sự mâu thuẫn của worktype đã chọn)
		// selectedWorkTypeConflictCheck();
		
		Application application = applicationRepository.findByID(companyID, appID).get();
		GeneralDate startDate = application.getAppDate().getApplicationDate();
		GeneralDate endDate = application.getAppDate().getApplicationDate();
		// 申請する開始日～申請する終了日までループする
		for(GeneralDate loopDate = startDate; loopDate.beforeOrEquals(endDate); loopDate = loopDate.addDays(1)){
			if(application.getPrePostAtr() == PrePostAtr.PREDICT && application.getAppType() == ApplicationType.OVER_TIME_APPLICATION){
				newBeforeRegister.confirmCheckOvertime(companyID, application.getEmployeeID(), loopDate, appDispInfoStartupOutput);
			}else{
				// アルゴリズム「確定チェック」を実施する
				newBeforeRegister.confirmationCheck(companyID, application.getEmployeeID(), loopDate, appDispInfoStartupOutput);
			}
		}

		exclusiveCheck(companyID, appID, version);
	}
	
	/**
	 * 勤務種類、就業時間帯チェックのメッセージを表示
	 * @param companyID
	 * @param wkTypeCode
	 * @param wkTimeCode
	 */
	@Override
	public void displayWorkingHourCheck(String companyID, String wkTypeCode, String wkTimeCode) {
		// 12.マスタ勤務種類、就業時間帯データをチェック
		CheckWorkingInfoResult checkResult = otherCommonAlgorithm.checkWorkingInfo(companyID, wkTypeCode, wkTimeCode);
		if (checkResult.isWkTypeError() || checkResult.isWkTimeError()) {
			String text = "";
			if (checkResult.isWkTypeError()) {
				text = "勤務種類コード" + wkTypeCode;
			}
			if (checkResult.isWkTimeError()) {
				text = "就業時間帯コード" + wkTimeCode;
			}
			if (checkResult.isWkTypeError() && checkResult.isWkTimeError()) {
				text = "勤務種類コード" + wkTypeCode + "、" + "就業時間帯コード" + wkTimeCode;
				;
			}
			throw new BusinessException("Msg_1530", text);
		}
	}

	/**
	 * 1.排他チェック
	 */
	public void exclusiveCheck(String companyID, String appID, int version) {
		if (applicationRepository.findByID(companyID, appID).isPresent()) {
			Application application = applicationRepository.findByID(companyID, appID).get();
			if (application.getVersion() != version) {
				throw new BusinessException("Msg_197");
			}
		} else {
			throw new BusinessException("Msg_198");
		}
	}

	/**
	 * 4-1.詳細画面登録前の処理 (CMM045)
	 * 
	 * @author hoatt
	 */
	@Override
	public boolean processBefDetailScreenReg(String companyID, String employeeID, GeneralDate appDate,
			int employeeRouteAtr, String appID, PrePostAtr postAtr, int version, AppDispInfoStartupOutput appDispInfoStartupOutput) {
		// 選択した勤務種類の矛盾チェック(check sự mâu thuẫn của worktype đã chọn)
		// selectedWorkTypeConflictCheck();

		Application application = applicationRepository.findByID(companyID, appID).get();
		GeneralDate startDate = application.getAppDate().getApplicationDate();
		GeneralDate endDate = application.getAppDate().getApplicationDate();
		// 申請する開始日～申請する終了日までループする
		for(GeneralDate loopDate = startDate; loopDate.beforeOrEquals(endDate); loopDate = loopDate.addDays(1)){
			if(loopDate.equals(GeneralDate.today()) && application.getPrePostAtr().equals(PrePostAtr.PREDICT) && application.isOverTimeApp()){
				newBeforeRegister.confirmCheckOvertime(companyID, application.getEmployeeID(), loopDate, appDispInfoStartupOutput);
			}else{
				// アルゴリズム「確定チェック」を実施する
				newBeforeRegister.confirmationCheck(companyID, application.getEmployeeID(), loopDate, appDispInfoStartupOutput);
			}
		}
		
		// アルゴリズム「排他チェック」を実行する(thực hiện xử lý 「排他チェック」)
		return exclusiveCheckErr(companyID, appID, version);
	}

	@Override
	public boolean exclusiveCheckErr(String companyID, String appID, int version) {
		if (applicationRepository.findByID(companyID, appID).isPresent()) {
			Application application = applicationRepository.findByID(companyID, appID).get();
			if (application.getVersion() != version) {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}
}
