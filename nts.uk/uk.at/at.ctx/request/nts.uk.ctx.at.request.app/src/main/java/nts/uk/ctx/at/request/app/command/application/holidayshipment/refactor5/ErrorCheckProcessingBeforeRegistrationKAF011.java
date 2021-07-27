package nts.uk.ctx.at.request.app.command.application.holidayshipment.refactor5;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.NewBeforeRegister;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ActualContentDisplay;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.MsgErrorOutput;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveApp;
import nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp.RecruitmentApp;

/**
 * @author thanhpv
 * @URL: UKDesign.UniversalK.就業.KAF_申請.KAF011_振休振出申請.A：振休振出申請（新規）.アルゴリズム.登録前のエラーチェック処理
 */
@Stateless
public class ErrorCheckProcessingBeforeRegistrationKAF011 {

	@Inject
	private PreRegistrationErrorCheck PreRegistrationErrorCheck;
	
//	@Inject
//	private OtherCommonAlgorithm otherCommonAlgorithm;
	
//	@Inject
//	private InterimRemainDataMngCheckRegister interimRemainDataMngCheckRegister;
	
	@Inject
	private NewBeforeRegister newBeforeRegister;
	
//	@Inject
//	private HolidayApplicationSettingRepository holidayApplicationSettingRepo;
	
	/**
	 * 登録前のエラーチェック処理(Xử lý error check trước khi đăng ký)
	 * @param companyId 会社ID
	 * @param abs 振休申請
	 * @param rec 振出申請
	 * @param represent 代行申請か
	 * @param opErrorFlag 承認ルートのエラーフラグ
	 * @param opActualContentDisplayLst 表示する実績内容
	 * @param appDispInfoStartupOutput 表示する実績内容 
	 * 
	 */
	public void processing(String companyId, Optional<AbsenceLeaveApp> abs, Optional<RecruitmentApp> rec, boolean represent, List<MsgErrorOutput> msgErrorLst, List<ActualContentDisplay> opActualContentDisplayLst, AppDispInfoStartupOutput appDispInfoStartup) {
		
		//登録前エラーチェック（新規）(Check error trước khi đăng ký (New)
		this.PreRegistrationErrorCheck.errorCheck(companyId, abs, rec, 
												opActualContentDisplayLst,
												appDispInfoStartup.getAppDispInfoNoDateOutput().getEmployeeInfoLst().get(0), 
												appDispInfoStartup.getAppDispInfoWithDateOutput().getEmpHistImport().getEmploymentCode(),
												Optional.empty(), 
												Optional.empty());
		//振休残数不足チェック (Check số nghỉ bù thiếu)
		this.checkForInsufficientNumberOfHolidays(companyId, appDispInfoStartup.getAppDispInfoNoDateOutput().getEmployeeInfoLst().get(0).getSid(), abs, rec);
		
		if(rec.isPresent()) {
			this.newBeforeRegister.processBeforeRegister_New(companyId, EmploymentRootAtr.APPLICATION, represent, rec.get(), null, msgErrorLst, new ArrayList<>(), appDispInfoStartup);
		}
		
		if(abs.isPresent()) {
			this.newBeforeRegister.processBeforeRegister_New(companyId, EmploymentRootAtr.APPLICATION, represent, abs.get(), null, msgErrorLst, new ArrayList<>(), appDispInfoStartup);
		}
		//TODO
	}
	
	
	/**
	 * 振休残数不足チェック (Check số nghỉ bù thiếu)
	 */
	public void checkForInsufficientNumberOfHolidays(String companyId, String employeeId, Optional<AbsenceLeaveApp> abs, Optional<RecruitmentApp> rec) {
		//4.社員の当月の期間を算出する (Tính thời gian tháng hiện tại của nhân viên)
		//PeriodCurrentMonth PeriodCurrentMonth = this.otherCommonAlgorithm.employeePeriodCurrentMonthCalculate(companyId, employeeId, GeneralDate.today());
		
		//ドメインモデル「休暇申請設定」を取得する - (lấy domain 「休暇申請設定」)
		// cần xác nhận với anh phượng domain này đang tồn tại hai cái 
		
		//Optional<HolidayApplicationSetting> HolidayApplicationSetting = holidayApplicationSettingRepo.findSettingByCompanyId(companyId);
		
		
		
		/** Đã trao đổi vs anh PhượngDV chỗ này tạm thời pending - 21/12/2020
		 * 
		 * 
		if(abs.isPresent()) {
			InterimRemainCheckInputParam inputParam = new InterimRemainCheckInputParam(
					companyId, 
					employeeId, 
					datePeriod, 
					false, 
					abs.get().getAppDate().getApplicationDate(), 
					new DatePeriod(abs.get().getAppDate().getApplicationDate(), abs.get().getAppDate().getApplicationDate()), 
					true, 
					recordData, 
					scheData, 
					appData, 
					false, 
					abs.get()., 
					false, 
					false, 
					true, 
					false, 
					true);
			EarchInterimRemainCheck earchInterimRemainCheck = this.interimRemainDataMngCheckRegister.checkRegister(inputParam); 
			if(!earchInterimRemainCheck.isChkSubHoliday()) {
				throw new BusinessException("Msg_1409", "代休不足区分");
			}
			if(!earchInterimRemainCheck.isChkPause()) {
				throw new BusinessException("Msg_1409", "振休不足区分");
			}
			if(!earchInterimRemainCheck.isChkAnnual()) {
				throw new BusinessException("Msg_1409", "年休不足区分");
			}
			if(!earchInterimRemainCheck.isChkFundingAnnual()) {
				throw new BusinessException("Msg_1409", "積休不足区分");
			}
			if(earchInterimRemainCheck.isChkSpecial()) {
				throw new BusinessException("Msg_1409", "特休不足区分 ");
			}
		}
		*/
	}
}


