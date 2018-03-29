package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.calculationattribute.AutoCalOfLeaveEarlySetting;
import nts.uk.ctx.at.record.dom.calculationattribute.AutoCalcSetOfDivergenceTime;
import nts.uk.ctx.at.record.dom.calculationattribute.CalAttrOfDailyPerformance;
import nts.uk.ctx.at.record.dom.calculationattribute.enums.DivergenceTimeAttr;
import nts.uk.ctx.at.record.dom.calculationattribute.enums.LeaveAttr;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.AffiliationInforState;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.EmpCalAndSumExeLogRepository;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ExecutionLog;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionType;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalFlexOvertimeSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalOvertimeSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalRestTimeSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.BaseAutoCalSetting;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalRaisingSalarySetting;
import nts.uk.ctx.at.shared.dom.workrule.overtime.AutoCalculationSetService;

@Stateless
public class ResetDailyPerforDomainServiceImpl implements ResetDailyPerforDomainService {

	@Inject
	private ReflectWorkInforDomainService reflectWorkInforDomainService;

	@Inject
	private WorkInformationRepository workInformationRepository;

	@Inject
	private EmpCalAndSumExeLogRepository empCalAndSumExeLogRepository;
	
	@Inject
	private AutoCalculationSetService autoCalculationSetService;

	@Override
	public void resetDailyPerformance(String companyID, String employeeID, GeneralDate processingDate,
			String empCalAndSumExecLogID, ExecutionType reCreateAttr) {
		Optional<WorkInfoOfDailyPerformance> workInfoOfDailyPerformance = this.workInformationRepository
				.find(employeeID, processingDate);

		if (workInfoOfDailyPerformance.isPresent()) {
			// 再設定する区分を取得(get data 一部再設定区分, execution log)
			Optional<ExecutionLog> executionLog = this.empCalAndSumExeLogRepository
					.getByExecutionContent(empCalAndSumExecLogID, 0);

			if (executionLog.isPresent()) {
				if (executionLog.get().getDailyCreationSetInfo().isPresent()) {
					if (executionLog.get().getDailyCreationSetInfo().get().getPartResetClassification().isPresent()) {
						//計算区分を日別実績に反映する(Reflect 計算区分 in 日別実績)
						if (executionLog.get().getDailyCreationSetInfo().get().getPartResetClassification().get()
								.getCalculationClassificationResetting() == true) {

							BaseAutoCalSetting baseAutoCalSetting = this.autoCalculationSetService
									.getAutoCalculationSetting(companyID, employeeID, processingDate);

							AutoCalSetting flexExcessTime = new AutoCalSetting(
									baseAutoCalSetting.getFlexOTTime().getFlexOtTime().getUpLimitORtSet(),
									baseAutoCalSetting.getFlexOTTime().getFlexOtTime().getCalAtr());
							AutoCalFlexOvertimeSetting autoCalFlexOvertimeSetting = new AutoCalFlexOvertimeSetting(flexExcessTime);
							
							AutoCalRaisingSalarySetting autoCalRaisingSalarySetting = new AutoCalRaisingSalarySetting(false, false);

							// number 3
							AutoCalRestTimeSetting holidayTimeSetting = new AutoCalRestTimeSetting(
									new AutoCalSetting(baseAutoCalSetting.getRestTime().getRestTime().getUpLimitORtSet(),
											baseAutoCalSetting.getRestTime().getRestTime().getCalAtr()),
									new AutoCalSetting(baseAutoCalSetting.getRestTime().getLateNightTime().getUpLimitORtSet(),
											baseAutoCalSetting.getRestTime().getLateNightTime().getCalAtr()));

							// number 4
							AutoCalOvertimeSetting overtimeSetting = new AutoCalOvertimeSetting(
									new AutoCalSetting(baseAutoCalSetting.getNormalOTTime().getEarlyOtTime().getUpLimitORtSet(),
											baseAutoCalSetting.getNormalOTTime().getEarlyOtTime().getCalAtr()),
									new AutoCalSetting(baseAutoCalSetting.getNormalOTTime().getEarlyMidOtTime().getUpLimitORtSet(),
											baseAutoCalSetting.getNormalOTTime().getEarlyMidOtTime().getCalAtr()),
									new AutoCalSetting(baseAutoCalSetting.getNormalOTTime().getNormalOtTime().getUpLimitORtSet(),
											baseAutoCalSetting.getNormalOTTime().getNormalOtTime().getCalAtr()),
									new AutoCalSetting(baseAutoCalSetting.getNormalOTTime().getNormalMidOtTime().getUpLimitORtSet(),
											baseAutoCalSetting.getNormalOTTime().getNormalMidOtTime().getCalAtr()),
									new AutoCalSetting(baseAutoCalSetting.getNormalOTTime().getLegalOtTime().getUpLimitORtSet(),
											baseAutoCalSetting.getNormalOTTime().getLegalOtTime().getCalAtr()),
									new AutoCalSetting(baseAutoCalSetting.getNormalOTTime().getLegalMidOtTime().getUpLimitORtSet(),
											baseAutoCalSetting.getNormalOTTime().getLegalMidOtTime().getCalAtr()));
							
							AutoCalOfLeaveEarlySetting autoCalOfLeaveEarlySetting = new AutoCalOfLeaveEarlySetting(LeaveAttr.NOT_USE, LeaveAttr.NOT_USE);
							
							AutoCalcSetOfDivergenceTime autoCalcSetOfDivergenceTime = new AutoCalcSetOfDivergenceTime(DivergenceTimeAttr.NOT_USE);

							CalAttrOfDailyPerformance calAttrOfDailyPerformance = new CalAttrOfDailyPerformance(
									employeeID, processingDate, autoCalFlexOvertimeSetting, autoCalRaisingSalarySetting, holidayTimeSetting, overtimeSetting, autoCalOfLeaveEarlySetting,
									autoCalcSetOfDivergenceTime);

						}
						// 特定日を日別実績に反映する(Reflect 日別実績)
						if (executionLog.get().getDailyCreationSetInfo().get().getPartResetClassification().get()
								.getSpecificDateClassificationResetting() == true) {
//							 reflectWorkInforDomainService.reflectSpecificDate(companyID, employeeID, processingDate, workPlaceID);
						}
						// 所属情報を反映する(Reflect info 所属情報)
						if (executionLog.get().getDailyCreationSetInfo().get().getPartResetClassification().get()
								.getMasterReconfiguration() == true) {
							AffiliationInforState affiliationInforState = this.reflectWorkInforDomainService
									.createAffiliationInforOfDailyPerfor(companyID, employeeID, processingDate,
											empCalAndSumExecLogID);
						}
						// 短時間勤務時間帯を反映する(reflect 短時間勤務時間帯)
						if (executionLog.get().getDailyCreationSetInfo().get().getPartResetClassification().get()
								.getResetTimeChildOrNurseCare() == true) {
							// TODO
							// Dung code
						}
						// 休業再設定(reSetting 休業)
						if (executionLog.get().getDailyCreationSetInfo().get().getPartResetClassification().get()
								.getClosedHolidays() == true) {
							// TODO
						}
						// 就業時間帯再設定(reSetting worktime)
						if (executionLog.get().getDailyCreationSetInfo().get().getPartResetClassification().get()
								.getResettingWorkingHours() == true) {
							// TODO
							// Dung code
						}
						// 打刻を取得する(get info stamp)
						if (executionLog.get().getDailyCreationSetInfo().get().getPartResetClassification().get()
								.getReflectsTheNumberOfFingerprintChecks() == true) {
							// TODO
							// dung code
						}
					}
				}
			}
		}
	}

}
