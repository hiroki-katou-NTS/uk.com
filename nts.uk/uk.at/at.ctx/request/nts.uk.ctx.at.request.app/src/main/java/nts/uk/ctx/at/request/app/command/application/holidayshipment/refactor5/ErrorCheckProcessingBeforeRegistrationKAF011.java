package nts.uk.ctx.at.request.app.command.application.holidayshipment.refactor5;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateful;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.app.find.application.common.service.other.output.ActualContentDisplayDto;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.PeriodCurrentMonth;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveApp;
import nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp.RecruitmentApp;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.EarchInterimRemainCheck;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainCheckInputParam;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainDataMngCheckRegister;

/**
 * @author thanhpv
 * @URL: UKDesign.UniversalK.就業.KAF_申請.KAF011_振休振出申請.A：振休振出申請（新規）.アルゴリズム.登録前のエラーチェック処理
 */
@Stateful
public class ErrorCheckProcessingBeforeRegistrationKAF011 {

	@Inject
	private PreRegistrationErrorCheck PreRegistrationErrorCheck;
	
	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithm;
	
	@Inject
	private InterimRemainDataMngCheckRegister interimRemainDataMngCheckRegister;
	
	/**
	 * 登録前のエラーチェック処理(Xử lý error check trước khi đăng ký)
	 * @param companyId 会社ID
	 * @param abs 振休申請
	 * @param rec 振出申請
	 * @param represent 代行申請か
	 * @param opErrorFlag 承認ルートのエラーフラグ
	 * @param opActualContentDisplayLst 表示する実績内容
	 */
	public void processing(String companyId, Optional<AbsenceLeaveApp> abs, Optional<RecruitmentApp> rec, boolean represent, Integer opErrorFlag, List<ActualContentDisplayDto> opActualContentDisplayLst) {
		
		//登録前エラーチェック（新規）(Check error trước khi đăng ký (New)
		this.PreRegistrationErrorCheck.errorCheck(companyId, abs, rec, opActualContentDisplayLst);
		//振休残数不足チェック (Check số nghỉ bù thiếu)
		this.checkForInsufficientNumberOfHolidays(companyId, employeeId, abs, rec);
		
		//TODO
	}
	
	
	/**
	 * 振休残数不足チェック (Check số nghỉ bù thiếu)
	 */
	public void checkForInsufficientNumberOfHolidays(String companyId, String employeeId, Optional<AbsenceLeaveApp> abs, Optional<RecruitmentApp> rec) {
		//4.社員の当月の期間を算出する (Tính thời gian tháng hiện tại của nhân viên)
		PeriodCurrentMonth PeriodCurrentMonth = this.otherCommonAlgorithm.employeePeriodCurrentMonthCalculate(companyId, employeeId, GeneralDate.today());
		
		//ドメインモデル「休暇申請設定」を取得する - (lấy domain 「休暇申請設定」)
		// cần xác nhận với anh phượng domain này đang tồn tại hai cái 
		
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
		
	}
	
	/**
	 * 2-1.新規画面登録前の処理
	 */
	public void processingBeforeNewScreenRegistration() {
		
	}
	
}
