package nts.uk.ctx.at.record.dom.dailyprocess.calc.ootsuka;

import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkCalcSetting;
import nts.uk.ctx.at.shared.dom.worktype.DailyWork;
import nts.uk.ctx.at.shared.dom.worktype.WorkAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeSet;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeSetCheck;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeUnit;

/**
 * 大塚専用処理(実装)
 * @author keisuke_hoshina
 *
 */
@Stateless
public class OotsukaProcessServiceImpl implements OotsukaProcessService{

	@Override
	public WorkType getOotsukaWorkType(WorkType workType,
									   Optional<FixedWorkCalcSetting> calcMethodOfFixWork,
									   TimeLeavingOfDailyPerformance attendanceLeaving) {
		if(decisionOotsukaMode(workType,calcMethodOfFixWork,attendanceLeaving)) {
			return createOotsukaWorkType(workType);
		}
		else {
			return workType;
		}
	}
	
	/**
	 * 大塚用勤務種類の作成
	 * @param workType マスタから取得した勤務種類
	 * @return 作成した勤務種類
	 */
	private WorkType createOotsukaWorkType(WorkType workType) {
		
		val workTypeSet = workType.getWorkTypeSetByAtr(WorkAtr.OneDay);
		
		val dailyWork = new DailyWork(WorkTypeUnit.OneDay, 
									  WorkTypeClassification.Attendance, 
									  workType.getDailyWork().getMorning(), 
									  workType.getDailyWork().getAfternoon());
		
		
		val createWorkType = new WorkType(workType.getCompanyId(), 
									workType.getWorkTypeCode(), 
									workType.getSymbolicName(), 
									workType.getName(), 
									workType.getAbbreviationName(), 
									workType.getMemo(), 
									dailyWork, 
									workType.getDeprecate(), 
									workType.getCalculateMethod());
		WorkTypeSet createWorkTypeSet = new WorkTypeSet(workTypeSet.getCompanyId(), 
														workTypeSet.getWorkTypeCd(), 
														workTypeSet.getWorkAtr(), 
														workTypeSet.getDigestPublicHd(), 
														workTypeSet.getHolidayAtr(), 
														workTypeSet.getCountHodiday(), 
														workTypeSet.getCloseAtr(), 
														workTypeSet.getSumAbsenseNo(), 
														workTypeSet.getSumSpHodidayNo(), 
														WorkTypeSetCheck.NO_CHECK, 
														WorkTypeSetCheck.NO_CHECK, 
														workTypeSet.getGenSubHodiday(),
														WorkTypeSetCheck.NO_CHECK
														);
		createWorkType.getWorkTypeSetList().add(createWorkTypeSet);
		createWorkType.getWorkTypeSetList().add(workType.getWorkTypeSetByAtr(WorkAtr.Monring));
		createWorkType.getWorkTypeSetList().add(workType.getWorkTypeSetByAtr(WorkAtr.Afternoon));
		
		return null;
	}

	/**
	 * 大塚モード判断処理
	 * @return
	 */
	private boolean decisionOotsukaMode(WorkType workType,
										Optional<FixedWorkCalcSetting> calcMethodOfFixWork,
										TimeLeavingOfDailyPerformance attendanceLeaving) {
		//勤務計算をする　＆＆　打刻漏れをしていない
		if(decisionAbleCalc(workType,calcMethodOfFixWork) && attendanceLeaving.isLeakageStamp()) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * 日勤務で計算するかどうか判断
	 * @param workType 勤務種類
	 * @param isCalcInVacation 休暇時の計算
	 * @return
	 */
	private boolean decisionAbleCalc(WorkType workType,Optional<FixedWorkCalcSetting> calcMethodOfFixWork) {
		//休暇時の計算を取得
		if(workType != null && false) {//calcMethodOfFixWork.isPresent()) {
			return workType.getDailyWork().isOneOrHalfAnnualHoliday()
					&& workType.getDailyWork().isOneOrHalfDaySpecHoliday()
					&& workType.getDailyWork().isOneOrHalfDayYearlyReserved();
		}
		//しない
		else {
			return false;
		}
	}
}
