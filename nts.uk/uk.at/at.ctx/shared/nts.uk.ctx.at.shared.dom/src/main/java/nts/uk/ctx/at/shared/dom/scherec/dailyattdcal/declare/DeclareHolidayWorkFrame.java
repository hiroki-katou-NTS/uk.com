package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.declare;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.declare.DeclareCalcClock;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.declare.DeclareCalcRange;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.declare.DeclareCalcTime;
import nts.uk.ctx.at.shared.dom.worktime.IntegrationOfWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.common.BreakFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkHolidayTimeZone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 申告休出枠
 * @author shuichi_ishida
 */
@Getter
public class DeclareHolidayWorkFrame {

	/** 休出 */
	private Optional<HdwkFrameEachHdAtr> holidayWork;
	/** 休出深夜 */
	private Optional<HdwkFrameEachHdAtr> holidayWorkMn;
	
	/**
	 * コンストラクタ
	 */
	public DeclareHolidayWorkFrame(){
		this.holidayWork = Optional.empty();
		this.holidayWorkMn = Optional.empty();
	}
	
	/**
	 * ファクトリー
	 * @param holidayWork 休出
	 * @param holidayWorkMn 休出深夜
	 * @return 申告休出枠
	 */
	public static DeclareHolidayWorkFrame of(
			HdwkFrameEachHdAtr holidayWork,
			HdwkFrameEachHdAtr holidayWorkMn){

		DeclareHolidayWorkFrame myclass = new DeclareHolidayWorkFrame();
		myclass.holidayWork = Optional.ofNullable(holidayWork);
		myclass.holidayWorkMn = Optional.ofNullable(holidayWorkMn);
		return myclass;
	}
	
	/**
	 * ファクトリー (Java型)
	 * @param holidayWork 休出
	 * @param holidayWorkMn 休出深夜
	 * @return 申告休出枠
	 */
	public static DeclareHolidayWorkFrame createFromJavaType(
			HdwkFrameEachHdAtr holidayWork,
			HdwkFrameEachHdAtr holidayWorkMn){
		
		DeclareHolidayWorkFrame myclass = new DeclareHolidayWorkFrame();
		myclass.holidayWork = Optional.ofNullable(holidayWork);
		myclass.holidayWorkMn = Optional.ofNullable(holidayWorkMn);
		return myclass;
	}
	
	/**
	 * 固定勤務の申告休出枠の設定
	 * @param itgOfWorkTime 統合就業時間帯(ref)
	 * @param calcRange 申告計算範囲
	 */
	public void setDeclareHolidayWorkFrameOfFixed(
			IntegrationOfWorkTime itgOfWorkTime,
			DeclareCalcRange calcRange){
		
		if (!itgOfWorkTime.getFixedWorkSetting().isPresent()) return;
		FixedWorkSetting fixedWorkSet = itgOfWorkTime.getFixedWorkSetting().get();
		
		// 勤務時間帯を確認する
		List<HDWorkTimeSheetSetting> targetList = fixedWorkSet.getOffdayWorkTimezone().getLstWorkTimezone();
		if (targetList.size() <= 0) return;
		// 勤務時間帯を0件にクリアする
		targetList.clear();
		
		int timezoneNo = 1;
		DeclareCalcClock calcClock = calcRange.getCalcClock();
		// 休出時間帯の追加
		{
			// 休出終了を確認する
			if (calcClock.getHolidayWorkEnd().isPresent()){
				// 休出時間の時間帯設定の作成（休出の共通設定）
				HDWorkTimeSheetSetting addHolidayWork = new HDWorkTimeSheetSetting();
				if (this.holidayWork.isPresent()){
					HdwkFrameEachHdAtr eachHdAtr = this.holidayWork.get();
					addHolidayWork.setWorkTimeNo(timezoneNo);
					addHolidayWork.setInLegalBreakFrameNo(new BreakFrameNo(
							BigDecimal.valueOf(eachHdAtr.getStatutory().v())));
					addHolidayWork.setOutLegalBreakFrameNo(new BreakFrameNo(
							BigDecimal.valueOf(eachHdAtr.getNotStatutory().v())));
					addHolidayWork.setOutLegalPubHDFrameNo(new BreakFrameNo(
							BigDecimal.valueOf(eachHdAtr.getNotStatHoliday().v())));
					addHolidayWork.setLegalHolidayConstraintTime(false);
					addHolidayWork.setNonStatutoryDayoffConstraintTime(false);
					addHolidayWork.setNonStatutoryHolidayConstraintTime(false);
				}
				// 休出深夜開始を確認する
				if (calcClock.getHolidayWorkMnStart().isPresent()){
					// 休出時間の時間帯設定を追加する（休出）
					if (this.holidayWork.isPresent() && calcRange.getWorkTimezone().isPresent()){
						int start = calcRange.getWorkTimezone().get().getStart().valueAsMinutes();
						int end = calcClock.getHolidayWorkMnStart().get().valueAsMinutes();
						if (start != end) {
							addHolidayWork.setTimezone(new TimeZoneRounding(
									new TimeWithDayAttr(start), new TimeWithDayAttr(end),
									new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN)));
							targetList.add(addHolidayWork);
							timezoneNo++;
						}
					}
					// 休出時間の時間帯設定を追加する（休出深夜）
					if (this.holidayWorkMn.isPresent()){
						HDWorkTimeSheetSetting addHolidayWorkMn = new HDWorkTimeSheetSetting();
						HdwkFrameEachHdAtr eachHdAtr = this.holidayWorkMn.get();
						addHolidayWorkMn.setWorkTimeNo(timezoneNo);
						addHolidayWorkMn.setInLegalBreakFrameNo(new BreakFrameNo(
								BigDecimal.valueOf(eachHdAtr.getStatutory().v())));
						addHolidayWorkMn.setOutLegalBreakFrameNo(new BreakFrameNo(
								BigDecimal.valueOf(eachHdAtr.getNotStatutory().v())));
						addHolidayWorkMn.setOutLegalPubHDFrameNo(new BreakFrameNo(
								BigDecimal.valueOf(eachHdAtr.getNotStatHoliday().v())));
						addHolidayWorkMn.setLegalHolidayConstraintTime(false);
						addHolidayWorkMn.setNonStatutoryDayoffConstraintTime(false);
						addHolidayWorkMn.setNonStatutoryHolidayConstraintTime(false);
						int start = calcClock.getHolidayWorkMnStart().get().valueAsMinutes();
						int end = calcClock.getHolidayWorkEnd().get().valueAsMinutes();
						if (start != end) {
							addHolidayWorkMn.setTimezone(new TimeZoneRounding(
									new TimeWithDayAttr(start), new TimeWithDayAttr(end),
									new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN)));
							targetList.add(addHolidayWorkMn);
							timezoneNo++;
						}
					}
				}
				else{
					// 休出時間の時間帯設定を追加する（休出）
					if (this.holidayWork.isPresent() && calcRange.getWorkTimezone().isPresent()){
						int start = calcRange.getWorkTimezone().get().getStart().valueAsMinutes();
						int end = calcClock.getHolidayWorkEnd().get().valueAsMinutes();
						if (start != end) {
							addHolidayWork.setTimezone(new TimeZoneRounding(
									new TimeWithDayAttr(start), new TimeWithDayAttr(end),
									new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN)));
							targetList.add(addHolidayWork);
							timezoneNo++;
						}
					}
				}
			}
		}
	}
	
	/**
	 * 流動勤務の申告休出枠の設定
	 * @param itgOfWorkTime 統合就業時間帯(ref)
	 * @param calcRange 申告計算範囲
	 */
	public void setDeclareHolidayWorkFrameOfFlow(
			IntegrationOfWorkTime itgOfWorkTime,
			DeclareCalcRange calcRange){
		
		if (!itgOfWorkTime.getFlowWorkSetting().isPresent()) return;
		FlowWorkSetting flowWorkSet = itgOfWorkTime.getFlowWorkSetting().get();
		
		// 初回経過時間を確認する
		List<FlowWorkHolidayTimeZone> targetList = flowWorkSet.getOffdayWorkTimezoneLstWorkTimezone();
		if (targetList.size() <= 0) return;
		int firstElapsed = targetList.get(0).getFlowTimeSetting().getElapsedTime().valueAsMinutes();
		// 残業時間帯を0件にクリアする
		targetList.clear();
		
		int timezoneNo = 1;
		DeclareCalcClock calcClock = calcRange.getCalcClock();
		DeclareCalcTime calcTime = calcRange.getCalcTime();
		// 流動残業時間帯の追加
		{
			// 休出終了を確認する
			if (calcClock.getHolidayWorkEnd().isPresent()){
				// 流動休出時間帯の作成（普通残業）
				FlowWorkHolidayTimeZone addHolidayWork = new FlowWorkHolidayTimeZone();
				// 流動休出時間帯を追加する（休出）
				if (this.holidayWork.isPresent()){
					HdwkFrameEachHdAtr eachHdAtr = this.holidayWork.get();
					addHolidayWork.setWorktimeNo(timezoneNo);
					addHolidayWork.setInLegalBreakFrameNo(new BreakFrameNo(
							BigDecimal.valueOf(eachHdAtr.getStatutory().v())));
					addHolidayWork.setOutLegalBreakFrameNo(new BreakFrameNo(
							BigDecimal.valueOf(eachHdAtr.getNotStatutory().v())));
					addHolidayWork.setOutLegalPubHolFrameNo(new BreakFrameNo(
							BigDecimal.valueOf(eachHdAtr.getNotStatHoliday().v())));
					addHolidayWork.setUseInLegalBreakRestrictTime(false);
					addHolidayWork.setUseOutLegalBreakRestrictTime(false);
					addHolidayWork.setUseOutLegalPubHolRestrictTime(false);
					FlowTimeSetting flowTimeSetting = new FlowTimeSetting();
					{
						flowTimeSetting.setElapsedTime(new AttendanceTime(firstElapsed));
						flowTimeSetting.setRounding(new TimeRoundingSetting(
								Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN));
					}
					addHolidayWork.setFlowTimeSetting(flowTimeSetting);
					if (calcTime.getHolidayWork().valueAsMinutes() > 0){
						targetList.add(addHolidayWork);
						timezoneNo++;
					}
				}
				// 休出深夜開始を確認する
				if (calcClock.getHolidayWorkMnStart().isPresent()){
					// 流動休出時間帯を追加する（休出深夜）
					if (this.holidayWorkMn.isPresent()){
						FlowWorkHolidayTimeZone addHolidayWorkMn = new FlowWorkHolidayTimeZone();
						HdwkFrameEachHdAtr eachHdAtr = this.holidayWorkMn.get();
						addHolidayWorkMn.setWorktimeNo(timezoneNo);
						addHolidayWorkMn.setInLegalBreakFrameNo(new BreakFrameNo(
								BigDecimal.valueOf(eachHdAtr.getStatutory().v())));
						addHolidayWorkMn.setOutLegalBreakFrameNo(new BreakFrameNo(
								BigDecimal.valueOf(eachHdAtr.getNotStatutory().v())));
						addHolidayWorkMn.setOutLegalPubHolFrameNo(new BreakFrameNo(
								BigDecimal.valueOf(eachHdAtr.getNotStatHoliday().v())));
						addHolidayWorkMn.setUseInLegalBreakRestrictTime(false);
						addHolidayWorkMn.setUseOutLegalBreakRestrictTime(false);
						addHolidayWorkMn.setUseOutLegalPubHolRestrictTime(false);
						FlowTimeSetting flowTimeSetting = new FlowTimeSetting();
						{
							flowTimeSetting.setElapsedTime(new AttendanceTime(
									firstElapsed + calcTime.getHolidayWork().valueAsMinutes()));
							flowTimeSetting.setRounding(new TimeRoundingSetting(
									Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN));
						}
						addHolidayWorkMn.setFlowTimeSetting(flowTimeSetting);
						if (calcTime.getHolidayWorkMn().valueAsMinutes() > 0){
							targetList.add(addHolidayWorkMn);
							timezoneNo++;
						}
					}
				}
			}
		}
	}
	
	/**
	 * 申告設定休出枠エラーチェック
	 * @return true=エラーあり,false=エラーなし
	 */
	public boolean checkErrorHolidayWorkFrame(){
		
		// 休出枠を確認する
		if (this.holidayWork.isPresent()){		// 休出 
			if (this.holidayWorkMn.isPresent()){	// 休出深夜
			}
			else{
				return true;
			}
		}
		else{
			if (this.holidayWorkMn.isPresent()){	// 休出深夜
				return true;
			}
			else{
			}
		}
		return false;
	}
}
