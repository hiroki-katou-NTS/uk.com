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
import nts.uk.ctx.at.request.dom.application.appabsence.appforspecleave.AppForSpecLeaveRepository;
import nts.uk.ctx.at.request.dom.vacation.history.service.PlanVacationRuleExport;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.AbsenceTenProcess;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.AnnualHolidaySetOutput;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.LeaveSetOutput;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.SubstitutionHolidayOutput;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class AbsenceServiceProcessImpl implements AbsenceServiceProcess{
	@Inject
	private AppAbsenceRepository appAbsenceRepository;
	@Inject
	private ApplicationApprovalService_New appRepository;
	@Inject
	private PlanVacationRuleExport planVacationRuleExport;
	@Inject
	private AbsenceTenProcess absenceTenProcess;
	@Inject
	private AppForSpecLeaveRepository repoSpecLeave;
	
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
	public void createAbsence(AppAbsence domain, Application_New newApp) {
		// insert Application
		this.appRepository.insert(newApp);
		// insert Absence
		this.appAbsenceRepository.insertAbsence(domain);
		if(domain.getHolidayAppType().equals(HolidayAppType.SPECIAL_HOLIDAY) && domain.getAppForSpecLeave() != null){
			repoSpecLeave.addSpecHd(domain.getAppForSpecLeave());
		}
		
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
	/**
	 * @author hoatt
	 * 14.休暇種類表示チェック
	 * @param companyID
	 * @param sID
	 * @param baseDate
	 * @return
	 */
	@Override
	public CheckDispHolidayType checkDisplayAppHdType(String companyID, String sID, GeneralDate baseDate) {
		//A4_3 - 年休設定
		boolean isYearManage = false;
		//A4_4 - 代休管理設定
		boolean isSubHdManage = false;
		//A4_5 - 振休管理設定
		boolean isSubVacaManage = false;
		//A4_8 - 積立年休設定
		boolean isRetentionManage = false;
		// TODO Auto-generated method stub
		//10-1.年休の設定を取得する
		AnnualHolidaySetOutput annualHd = absenceTenProcess.getSettingForAnnualHoliday(companyID);
		isYearManage = annualHd.isYearHolidayManagerFlg();
		//10-4.積立年休の設定を取得する
		isRetentionManage = absenceTenProcess.getSetForYearlyReserved(companyID, sID, baseDate);
		//10-2.代休の設定を取得する
		SubstitutionHolidayOutput subHd = absenceTenProcess.getSettingForSubstituteHoliday(companyID, sID, baseDate);
		isSubHdManage = subHd.isSubstitutionFlg();
		//10-3.振休の設定を取得する
		LeaveSetOutput leaveSet = absenceTenProcess.getSetForLeave(companyID, sID, baseDate);
		isSubVacaManage = leaveSet.isSubManageFlag();
		return new CheckDispHolidayType(isYearManage, isSubHdManage, isSubVacaManage, isRetentionManage);
	}
	
}
