//package nts.uk.ctx.at.record.dom.dailyprocess.calc;
//
//import lombok.Value;
//import lombok.val;
//import nts.uk.ctx.at.record.dom.daily.CalcMethodOfNoWorkingDay;
//import nts.uk.ctx.at.record.dom.raborstandardact.FlexCalcMethod;
//import nts.uk.ctx.at.record.dom.raborstandardact.flex.SettingOfFlexWork;
//import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
//import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
//import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.HolidayCalcMethodSet;
//import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalculationCategoryOutsideHours;
//import nts.uk.ctx.at.shared.dom.worktype.VacationCategory;
//import nts.uk.ctx.at.shared.dom.worktype.WorkType;
//import nts.uk.shr.com.time.TimeWithDayAttr;
//
///**
// * フレックス就業時間内時間帯
// * @author keisuke_hoshina
// *
// */
//@Value
//public class FlexWithinWorkTimeSheet {
//	private TimeSpanForCalc coreTimeSheet;
//	
//	/**
//	 * 休日控除時間の計算
//	 */
//	public AttendanceTime calcHolidayDeductionTime(WorkType workType) {
//		int useTime = VacationClass.vacationTimeOfcalcDaily(workType,VacationCategory.Holiday).valueAsMinutes();
//		useTime += VacationClass.vacationTimeOfcalcDaily(workType,VacationCategory.SubstituteHoliday).valueAsMinutes();
//		return new AttendanceTime(useTime);
//	}
//	
//	/**
//	 * 代休使用時間の計算
//	 * @return
//	 */
//	public AttendanceTime calcSubstituteHoliday(WorkType workType) {
//		return VacationClass.vacationTimeOfcalcDaily(workType, VacationCategory.SubstituteHoliday);
//	}
//	
//	/**
//	 * フレックス時間を計算するか判定し就業時間内時間帯を作成する
//	 * @return
//	 */
//	public FlexWithinWorkTimeSheet createWithinWorkTimeSheetAsFlex(CalcMethodOfNoWorkingDay calcMethod) {
//		if(calcMethod.isCalclateFlexTime()) {
//			if(/*勤務種類を基に非勤務日であるか判定　非勤務日である*/) {
//				return new FlexWithinWorkTimeSheet(new TimeSpanForCalc(new TimeWithDayAttr(0),new TimeWithDayAttr(0)));
//			}
//		}
//		/*フレックス時間の計算*/
//		int flexTime = calcFlexTime();
//		/*事前申請を上限とする制御*/
//	}
//	
//	/**
//	 * フレックス時間の計算
//	 */
//	public int calcFlexTime(HolidayCalcMethodSet holidayCalcMethodSet,AutoCalculationCategoryOutsideHours autoCalcAtr) {
//		/*法定労働時間の算出*/
//		StatutoryWorkingTime houtei = calcStatutoryTime();
//		/*実働時間の算出*/
//		int zitudou = calcWorkTime(PremiumAtr.RegularWork);
//		/*実働時間の算出(割増時間含む)*/
//		int zitudouIncludePremium = calcWorkTime(PremiumAtr.Premium);
//		
//		int flexTime = 0;
//		if(holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday().getCalculationByActualTime().isCalclationByActualTime()
//				&& !holidayCalcMethodSet.getPremiumCalcMethodOfHoliday().getCalculationByActualTime().isCalclationByActualTime()) {
//			/*フレックス時間算出*/
//			flexTime = houtei.getForActualWorkTime().valueAsMinutes() - zitudou;
//			if(flexTime < 0) {
//				flexTime = houtei.getForWorkTimeIncludePremium().valueAsMinutes() - zitudouIncludePremium;
//				flexTime = (flexTime > 0)? 0:flexTime;
//				/*不足しているフレックス時間*/
//				int husokuZiKasanZikan = zitudouIncludePremium - zitudou;
//			}
//		}
//		else if(!holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday().getCalculationByActualTime().isCalclationByActualTime()
//				&& !holidayCalcMethodSet.getPremiumCalcMethodOfHoliday().getCalculationByActualTime().isCalclationByActualTime()) {
//			flexTime = houtei.getForActualWorkTime().valueAsMinutes() - zitudou;
//		}
//		else if(holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday().getCalculationByActualTime().isCalclationByActualTime()
//				&& holidayCalcMethodSet.getPremiumCalcMethodOfHoliday().getCalculationByActualTime().isCalclationByActualTime()) {
//			/*不足しているフレックス時間*/
//			int husokuZiKasanZikan = zitudouIncludePremium - zitudou;
//		}
//		else {
//			throw new RuntimeException("A combination that can not be selected is selected");
//		}
//		
//		if(autoCalcAtr.isCalculateEmbossing() && flexTime > 0) {
//			flexTime = 0;
//		}
//		return flexTime;
//	}
//	
//	/**
//	 * 法定労働時間から控除(フレックス用)
//	 * @return
//	 */
//	public StatutoryWorkingTime calcStatutoryTime(WorkType workType,SettingOfFlexWork flexCalcMethod,DailyCalculationPersonalInformation personalInfor) {
//		StatutoryDeductionForFlex deductionTime = calcdeductTime(workType,flexCalcMethod);
//		return new StatutoryWorkingTime( new AttendanceTime(personalInfor.getStatutoryWorkingTime().getPredetermineWorkingTime().valueAsMinutes() - deductionTime.getForActualWork().valueAsMinutes()) 
//										,new AttendanceTime(personalInfor.getStatutoryWorkingTime().getPredetermineWorkingTime().valueAsMinutes() - deductionTime.getForPremium().valueAsMinutes()));
//	}
//	/**
//	 * 控除する時間の計算
//	 */
//	public StatutoryDeductionForFlex calcdeductTime(WorkType workType,SettingOfFlexWork flexCalcMethod){
//		/*休日控除時間の計算*/
//		AttendanceTime forHolidayTime = calcHolidayDeductionTime(workType);
//		/*代休控除時間の計算*/
//		AttendanceTime forCompensatoryLeaveTime = calcSubstituteHoliday(workType);
//		
//		DeductionTime deductionTime = new DeductionTime(forHolidayTime,forCompensatoryLeaveTime);
//		
//		return new StatutoryDeductionForFlex(deductionTime.forLackCalcPredetermineDeduction(flexCalcMethod.getFlexCalcMethod())
//											,deductionTime.forPremiumCalcPredetermineDeduction(flexCalcMethod.getFlexCalcMethod()));
//	}
//}
