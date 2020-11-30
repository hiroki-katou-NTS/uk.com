package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.declare;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.declare.DeclareCalcClock;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.declare.DeclareCalcRange;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.declare.DeclareCalcTime;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.IntegrationOfWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimezoneNo;
import nts.uk.ctx.at.shared.dom.worktime.common.OTFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.OverTimeOfTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.SettlementOrder;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowOTTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 申告残業枠
 * @author shuichi_ishida
 */
@Getter
public class DeclareOvertimeFrame {

	/** 早出残業 */
	private Optional<OverTimeFrameNo> earlyOvertime;
	/** 早出残業深夜 */
	private Optional<OverTimeFrameNo> earlyOvertimeMn;
	/** 普通残業 */
	private Optional<OverTimeFrameNo> overtime;
	/** 普通残業深夜 */
	private Optional<OverTimeFrameNo> overtimeMn;
	
	/**
	 * コンストラクタ
	 */
	public DeclareOvertimeFrame(){
		this.earlyOvertime = Optional.empty();
		this.earlyOvertimeMn = Optional.empty();
		this.overtime = Optional.empty();
		this.overtimeMn = Optional.empty();
	}
	
	/**
	 * ファクトリー
	 * @param earlyOvertime 早出残業
	 * @param earlyOvertimeMn 早出残業深夜
	 * @param overtime 普通残業
	 * @param overtimeMn 普通残業深夜
	 * @return 申告残業枠
	 */
	public static DeclareOvertimeFrame of(
			OverTimeFrameNo earlyOvertime,
			OverTimeFrameNo earlyOvertimeMn,
			OverTimeFrameNo overtime,
			OverTimeFrameNo overtimeMn){
		
		DeclareOvertimeFrame myclass = new DeclareOvertimeFrame();
		myclass.earlyOvertime = Optional.ofNullable(earlyOvertime);
		myclass.earlyOvertimeMn = Optional.ofNullable(earlyOvertimeMn);
		myclass.overtime = Optional.ofNullable(overtime);
		myclass.overtimeMn = Optional.ofNullable(overtimeMn);
		return myclass;
	}
	
	/**
	 * ファクトリー (Java型)
	 * @param earlyOvertime 早出残業
	 * @param earlyOvertimeMn 早出残業深夜
	 * @param overtime 普通残業
	 * @param overtimeMn 普通残業深夜
	 * @return 申告残業枠
	 */
	public static DeclareOvertimeFrame createFromJavaType(
			Integer earlyOvertime,
			Integer earlyOvertimeMn,
			Integer overtime,
			Integer overtimeMn){
		
		DeclareOvertimeFrame myclass = new DeclareOvertimeFrame();
		myclass.earlyOvertime = (earlyOvertime == null ? Optional.empty()
				: Optional.ofNullable(new OverTimeFrameNo(earlyOvertime)));
		myclass.earlyOvertimeMn = (earlyOvertimeMn == null ? Optional.empty()
				: Optional.ofNullable(new OverTimeFrameNo(earlyOvertimeMn)));
		myclass.overtime = (overtime == null ? Optional.empty()
				: Optional.ofNullable(new OverTimeFrameNo(overtime)));
		myclass.overtimeMn = (overtimeMn == null ? Optional.empty()
				: Optional.ofNullable(new OverTimeFrameNo(overtimeMn)));
		return myclass;
	}
	
	/**
	 * 固定勤務の申告残業枠の設定
	 * @param itgOfWorkTime 統合就業時間帯(ref)
	 * @param calcRange 申告計算範囲
	 * @param workType 勤務種類
	 */
	public void setDeclareOvertimeFrameOfFixed(
			IntegrationOfWorkTime itgOfWorkTime,
			DeclareCalcRange calcRange,
			WorkType workType){
		
		if (!itgOfWorkTime.getFixedWorkSetting().isPresent()) return;
		FixedWorkSetting fixedWorkSet = itgOfWorkTime.getFixedWorkSetting().get();
		
		// 残業時間帯を確認する
		if (!fixedWorkSet.getFixHalfDayWorkTimezone(workType.getAttendanceHolidayAttr()).isPresent()) return;
		List<OverTimeOfTimeZoneSet> targetList = fixedWorkSet.getOverTimeOfTimeZoneSet(workType);
		if (targetList.size() <= 0) return;
		// 残業時間帯を0件にクリアする
		targetList.clear();
		
		int timezoneNo = 1;
		DeclareCalcClock calcClock = calcRange.getCalcClock();
		// 残業時間帯の追加（早出残業）
		{
			// 早出残業開始を確認する
			if (calcClock.getEarlyOtStart().isPresent()){
				// 残業時間の時間帯設定の作成（早出残業の共通設定）
				OverTimeOfTimeZoneSet addEarly = new OverTimeOfTimeZoneSet();
				if (this.earlyOvertime.isPresent()){
					addEarly.setOtFrameNo(new OTFrameNo(this.earlyOvertime.get().v()));
					addEarly.setWorkTimezoneNo(new EmTimezoneNo(timezoneNo));
					addEarly.setEarlyOTUse(true);
					addEarly.setRestraintTimeUse(false);
					addEarly.setLegalOTframeNo(OTFrameNo.getDefaultData());
					addEarly.setSettlementOrder(SettlementOrder.getDefaultData());
				}
				// 早出残業深夜開始を確認する
				if (calcClock.getEarlyOtMnStart().isPresent()){
					// 残業時間の時間帯設定を追加する（早出残業）
					if (this.earlyOvertime.isPresent()){
						int start = calcClock.getEarlyOtStart().get().valueAsMinutes();
						int end = calcClock.getEarlyOtMnStart().get().valueAsMinutes();
						if (start != end) {
							addEarly.setTimezone(new TimeZoneRounding(
									new TimeWithDayAttr(start), new TimeWithDayAttr(end),
									new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN)));
							targetList.add(addEarly);
							timezoneNo++;
						}
					}
					// 残業時間の時間帯設定を追加する（早出深夜）
					if (this.earlyOvertimeMn.isPresent() && calcRange.getWorkTimezone().isPresent()){
						OverTimeOfTimeZoneSet addEarlyMn = new OverTimeOfTimeZoneSet();
						addEarlyMn.setOtFrameNo(new OTFrameNo(this.earlyOvertimeMn.get().v()));
						addEarlyMn.setWorkTimezoneNo(new EmTimezoneNo(timezoneNo));
						addEarlyMn.setEarlyOTUse(true);
						addEarlyMn.setRestraintTimeUse(false);
						addEarlyMn.setLegalOTframeNo(OTFrameNo.getDefaultData());
						addEarlyMn.setSettlementOrder(SettlementOrder.getDefaultData());
						int start = calcClock.getEarlyOtMnStart().get().valueAsMinutes();
						int end = calcRange.getWorkTimezone().get().getStart().valueAsMinutes();
						if (start != end) {
							addEarlyMn.setTimezone(new TimeZoneRounding(
									new TimeWithDayAttr(start), new TimeWithDayAttr(end),
									new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN)));
							targetList.add(addEarlyMn);
							timezoneNo++;
						}
					}
				}
				else{
					// 残業時間の時間帯設定を追加する（早出残業）
					if (this.earlyOvertime.isPresent() && calcRange.getWorkTimezone().isPresent()){
						int start = calcClock.getEarlyOtStart().get().valueAsMinutes();
						int end = calcRange.getWorkTimezone().get().getStart().valueAsMinutes();
						if (start != end) {
							addEarly.setTimezone(new TimeZoneRounding(
									new TimeWithDayAttr(start), new TimeWithDayAttr(end),
									new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN)));
							targetList.add(addEarly);
							timezoneNo++;
						}
					}
				}
			}
		}
		// 残業時間帯の追加（普通残業）
		{
			// 普通残業終了を確認する
			if (calcClock.getOvertimeEnd().isPresent()){
				// 残業時間の時間帯設定の作成（普通残業の共通設定）
				OverTimeOfTimeZoneSet addOvertime = new OverTimeOfTimeZoneSet();
				if (this.overtime.isPresent()){
					addOvertime.setOtFrameNo(new OTFrameNo(this.overtime.get().v()));
					addOvertime.setWorkTimezoneNo(new EmTimezoneNo(timezoneNo));
					addOvertime.setEarlyOTUse(false);
					addOvertime.setRestraintTimeUse(false);
					addOvertime.setLegalOTframeNo(OTFrameNo.getDefaultData());
					addOvertime.setSettlementOrder(SettlementOrder.getDefaultData());
				}
				// 普通残業深夜開始を確認する
				if (calcClock.getOvertimeMnStart().isPresent()){
					// 残業時間の時間帯設定を追加する（普通残業）
					if (this.overtime.isPresent() && calcRange.getWorkTimezone().isPresent()){
						int start = calcRange.getWorkTimezone().get().getEnd().valueAsMinutes();
						int end = calcClock.getOvertimeMnStart().get().valueAsMinutes();
						if (start != end) {
							addOvertime.setTimezone(new TimeZoneRounding(
									new TimeWithDayAttr(start), new TimeWithDayAttr(end),
									new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN)));
							targetList.add(addOvertime);
							timezoneNo++;
						}
					}
					// 残業時間の時間帯設定を追加する（普通深夜）
					if (this.overtimeMn.isPresent()){
						OverTimeOfTimeZoneSet addOvertimeMn = new OverTimeOfTimeZoneSet();
						addOvertimeMn.setOtFrameNo(new OTFrameNo(this.overtimeMn.get().v()));
						addOvertimeMn.setWorkTimezoneNo(new EmTimezoneNo(timezoneNo));
						addOvertimeMn.setEarlyOTUse(false);
						addOvertimeMn.setRestraintTimeUse(false);
						addOvertimeMn.setLegalOTframeNo(OTFrameNo.getDefaultData());
						addOvertimeMn.setSettlementOrder(SettlementOrder.getDefaultData());
						int start = calcClock.getOvertimeMnStart().get().valueAsMinutes();
						int end = calcClock.getOvertimeEnd().get().valueAsMinutes();
						if (start != end) {
							addOvertimeMn.setTimezone(new TimeZoneRounding(
									new TimeWithDayAttr(start), new TimeWithDayAttr(end),
									new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN)));
							targetList.add(addOvertimeMn);
							timezoneNo++;
						}
					}
				}
				else{
					// 残業時間の時間帯設定を追加する（普通残業）
					if (this.overtime.isPresent() && calcRange.getWorkTimezone().isPresent()){
						int start = calcRange.getWorkTimezone().get().getEnd().valueAsMinutes();
						int end = calcClock.getOvertimeEnd().get().valueAsMinutes();
						if (start != end) {
							addOvertime.setTimezone(new TimeZoneRounding(
									new TimeWithDayAttr(start), new TimeWithDayAttr(end),
									new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN)));
							targetList.add(addOvertime);
							timezoneNo++;
						}
					}
				}
			}
		}
	}
	
	/**
	 * 流動勤務の申告残業枠の設定
	 * @param itgOfWorkTime 統合就業時間帯(ref)
	 * @param calcRange 申告計算範囲
	 */
	public void setDeclareOvertimeFrameOfFlow(
			IntegrationOfWorkTime itgOfWorkTime,
			DeclareCalcRange calcRange){
		
		if (!itgOfWorkTime.getFlowWorkSetting().isPresent()) return;
		FlowWorkSetting flowWorkSet = itgOfWorkTime.getFlowWorkSetting().get();
		
		// 初回経過時間を確認する
		List<FlowOTTimezone> targetList = flowWorkSet.getHalfDayWorkTimezoneLstOTTimezone();
		if (targetList.size() <= 0) return;
		int firstElapsed = targetList.get(0).getFlowTimeSetting().getElapsedTime().valueAsMinutes();
		// 残業時間帯を0件にクリアする
		targetList.clear();
		
		int timezoneNo = 1;
		DeclareCalcClock calcClock = calcRange.getCalcClock();
		DeclareCalcTime calcTime = calcRange.getCalcTime();
		// 流動残業時間帯の追加
		{
			// 普通残業終了を確認する
			if (calcClock.getOvertimeEnd().isPresent()){
				// 流動残業時間帯を追加する（普通残業）
				FlowOTTimezone addOvertime = new FlowOTTimezone();
				if (this.overtime.isPresent()){
					addOvertime.setOTFrameNo(new OvertimeWorkFrameNo(
							BigDecimal.valueOf(this.overtime.get().v())));
					addOvertime.setWorktimeNo(timezoneNo);
					addOvertime.setRestrictTime(false);
					addOvertime.setInLegalOTFrameNo(OvertimeWorkFrameNo.getDefaultData());
					addOvertime.setSettlementOrder(SettlementOrder.getDefaultData());
					FlowTimeSetting flowTimeSetting = new FlowTimeSetting();
					{
						flowTimeSetting.setElapsedTime(new AttendanceTime(firstElapsed));
						flowTimeSetting.setRounding(new TimeRoundingSetting(
								Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN));
					}
					addOvertime.setFlowTimeSetting(flowTimeSetting);
					if (calcTime.getOvertime().valueAsMinutes() > 0){
						targetList.add(addOvertime);
						timezoneNo++;
					}
				}
				// 普通残業深夜開始を確認する
				if (calcClock.getOvertimeMnStart().isPresent()){
					// 流動残業時間帯を追加する（普通深夜）
					if (this.overtimeMn.isPresent()){
						FlowOTTimezone addOvertimeMn = new FlowOTTimezone();
						addOvertimeMn.setOTFrameNo(new OvertimeWorkFrameNo(
								BigDecimal.valueOf(this.overtimeMn.get().v())));
						addOvertimeMn.setWorktimeNo(timezoneNo);
						addOvertimeMn.setRestrictTime(false);
						addOvertimeMn.setInLegalOTFrameNo(OvertimeWorkFrameNo.getDefaultData());
						addOvertimeMn.setSettlementOrder(SettlementOrder.getDefaultData());
						FlowTimeSetting flowTimeSetting = new FlowTimeSetting();
						{
							flowTimeSetting.setElapsedTime(new AttendanceTime(
									firstElapsed + calcTime.getOvertime().valueAsMinutes()));
							flowTimeSetting.setRounding(new TimeRoundingSetting(
									Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN));
						}
						addOvertimeMn.setFlowTimeSetting(flowTimeSetting);
						if (calcTime.getOvertimeMn().valueAsMinutes() > 0){
							targetList.add(addOvertimeMn);
							timezoneNo++;
						}
					}
				}
			}
		}
	}
	
	/**
	 * 申告設定残業枠エラーチェック
	 * @return true=エラーあり,false=エラーなし
	 */
	public boolean checkErrorOvertimeFrame(){
		
		// 残業枠を確認する
		if (this.earlyOvertime.isPresent()){	// 早出残業
			if (this.earlyOvertimeMn.isPresent()){	// 早出残業深夜
			}
			else{
				return true;
			}
		}
		else{
			if (this.earlyOvertimeMn.isPresent()){	// 早出残業深夜
				return true;
			}
			else{
			}
		}
		if (this.overtime.isPresent()){			// 普通残業
			if (this.overtimeMn.isPresent()){		// 普通残業深夜
			}
			else{
				return true;
			}
		}
		else{
			if (this.overtimeMn.isPresent()){		// 普通残業深夜
				return true;
			}
			else{
			}
		}
		return false;
	}
}
