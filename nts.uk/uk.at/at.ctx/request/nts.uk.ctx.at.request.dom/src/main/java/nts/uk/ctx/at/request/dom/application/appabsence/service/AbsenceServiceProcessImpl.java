package nts.uk.ctx.at.request.dom.application.appabsence.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationApprovalService_New;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.appabsence.AppAbsence;
import nts.uk.ctx.at.request.dom.application.appabsence.AppAbsenceRepository;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;
import nts.uk.ctx.at.request.dom.vacation.history.service.PlanVacationRuleExport;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.service.Period;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class AbsenceServiceProcessImpl implements AbsenceServiceProcess{
	@Inject
	private AppAbsenceRepository appAbsenceRepository;
	@Inject
	private ApplicationApprovalService_New appRepository;
	@Inject
	private PlanVacationRuleExport planVacationRuleExport;
	
	@Override
	public SpecialLeaveInfor getSpecialLeaveInfor(String workTypeCode) {
		SpecialLeaveInfor specialLeaveInfor = new SpecialLeaveInfor();
		boolean relationFlg = false;
		boolean mournerDisplayFlg = false;
		boolean displayRelationReasonFlg = false;
		int maxDayRelate = 0;
		//指定した勤務種類に特別休暇に当てはまるかチェックする
		
		return specialLeaveInfor;
	}

	@Override
	public void CreateAbsence(AppAbsence domain, Application_New newApp) {
		// insert Application
		this.appRepository.insert(newApp);
		// insert Absence
		this.appAbsenceRepository.insertAbsence(domain);
		
	}

	/**
	 * 13.計画年休上限チェック
	 */
	@Override
	public void checkLimitAbsencePlan(String cID, String sID, String workTypeCD, GeneralDate sDate, GeneralDate eDate,
			HolidayAppType hdAppType) {
		//INPUT．休暇種類をチェックする(check INPUT. phân loại holidays)
		if(hdAppType.equals(HolidayAppType.ANNUAL_PAID_LEAVE)){//INPUT．休暇種類が年休
			//計画年休の上限チェック(check giới hạn trên của plan annual holidays)
			boolean check = planVacationRuleExport.checkMaximumOfPlan(cID, sID, workTypeCD, new DatePeriod(sDate, eDate));
			if(check){
				//Msg_1345を表示
				throw new BusinessException("Msg_1345");
			}
		}
	}
	
}
