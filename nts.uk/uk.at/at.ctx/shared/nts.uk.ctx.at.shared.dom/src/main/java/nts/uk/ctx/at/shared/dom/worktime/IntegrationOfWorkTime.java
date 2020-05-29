package nts.uk.ctx.at.shared.dom.worktime;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.CommonRestSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedRestCalculateMethod;
import nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.OverTimeOfTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.CoreTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestSettingDetail;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeForm;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceHolidayAttr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * 統合就業時間帯
 * @author daiki_ichioka
 *
 */
@Getter
@Setter
public class IntegrationOfWorkTime {

	/** 就業時間帯コード */
	private WorkTimeCode code;
	
	/** 就業時間帯の設定 */
	private WorkTimeSetting workTimeSetting;
	
	/** 固定勤務設定 */
	private Optional<FixedWorkSetting> fixedWorkSetting;
	
	/** フレックス勤務設定 */
	private Optional<FlexWorkSetting> flexWorkSetting;
	
	/** 流動勤務設定 */
	private Optional<FlowWorkSetting> flowWorkSetting;
	
	
	/**
	 * コンストラクタ（通常勤務）
	 * 
	 * @param workTimeCode 就業時間帯コード
	 * @param workTimeSetting 就業時間帯の設定
	 * @param fixedWorkSetting 固定勤務設定
	 */
	public IntegrationOfWorkTime(WorkTimeCode workTimeCode, WorkTimeSetting workTimeSetting, FixedWorkSetting fixedWorkSetting) {
		this.code = workTimeCode;
		this.workTimeSetting = workTimeSetting;
		this.fixedWorkSetting = Optional.of(fixedWorkSetting);
		this.flexWorkSetting = Optional.empty();
		this.flowWorkSetting = Optional.empty();
	}
	
	/**
	 * コンストラクタ（フレックス勤務）
	 * 
	 * @param workTimeCode 就業時間帯コード
	 * @param workTimeSetting 就業時間帯の設定
	 * @param fixedWorkSetting フレックス勤務設定
	 */
	public IntegrationOfWorkTime(WorkTimeCode workTimeCode, WorkTimeSetting workTimeSetting, FlexWorkSetting flexWorkSetting) {
		this.code = workTimeCode;
		this.workTimeSetting = workTimeSetting;
		this.fixedWorkSetting = Optional.empty();
		this.flexWorkSetting = Optional.of(flexWorkSetting);
		this.flowWorkSetting = Optional.empty();
	}
	
	/**
	 * コンストラクタ（流動勤務）
	 * 
	 * @param workTimeCode 就業時間帯コード
	 * @param workTimeSetting 就業時間帯の設定
	 * @param fixedWorkSetting 流動勤務設定
	 */
	public IntegrationOfWorkTime(WorkTimeCode workTimeCode, WorkTimeSetting workTimeSetting, FlowWorkSetting flowWorkSetting) {
		this.code = workTimeCode;
		this.workTimeSetting = workTimeSetting;
		this.fixedWorkSetting = Optional.empty();
		this.flexWorkSetting = Optional.empty();
		this.flowWorkSetting = Optional.of(flowWorkSetting);
	}
	
	
	/**
	 * 就業時間帯の共通設定を取得する
	 * 
	 * @return 就業時間帯の共通設定
	 */
	public WorkTimezoneCommonSet getCommonSetting() {
		switch(this.workTimeSetting.getWorkTimeDivision().getWorkTimeForm()) {
			case FIXED:				return this.fixedWorkSetting.get().getCommonSetting();
			case FLEX:				return this.flexWorkSetting.get().getCommonSetting();
			case FLOW:				return this.flowWorkSetting.get().getCommonSetting();
			case TIMEDIFFERENCE:	throw new RuntimeException("Unimplemented");/*時差勤務はまだ実装しない。2020/5/19 渡邉*/
			default:				throw new RuntimeException("Non-conformity No Work");
		}
	}
	
	/**
	 * 残業時間の時間帯設定を取得する
	 * 
	 * @param workType 勤務種類
	 * @return 残業時間の時間帯設定
	 */
	public List<OverTimeOfTimeZoneSet> getOverTimeOfTimeZoneSetList(WorkType workType) {
		if(this.workTimeSetting.getWorkTimeDivision().isFlex())
			return this.flexWorkSetting.get().getOverTimeOfTimeZoneSet(workType);
		
		switch(this.workTimeSetting.getWorkTimeDivision().getWorkTimeMethodSet()) {
			case FIXED_WORK:
				return this.fixedWorkSetting.get().getOverTimeOfTimeZoneSet(workType);
			case DIFFTIME_WORK:
				throw new RuntimeException("Unimplemented");/*時差勤務はまだ実装しない。2020/5/19 渡邉*/
			case FLOW_WORK:
				throw new RuntimeException("Non-conformity FLOW_WORK");
			default:
				throw new RuntimeException("Non-conformity No Work");
		}
	}
	
	/**
	 * 就業時間の時間帯設定
	 * 
	 * @param workType 勤務種類
	 * @return 就業時間の時間帯設定
	 */
	public List<EmTimeZoneSet> getEmTimeZoneSetList(WorkType workType) {
		if(this.workTimeSetting.getWorkTimeDivision().isFlex())
			return this.flexWorkSetting.get().getEmTimeZoneSet(workType);
		
		switch(this.workTimeSetting.getWorkTimeDivision().getWorkTimeMethodSet()) {
			case FIXED_WORK:
				return this.fixedWorkSetting.get().getEmTimeZoneSet(workType);
			case DIFFTIME_WORK:
				throw new RuntimeException("Unimplemented");/*時差勤務はまだ実装しない。2020/5/19 渡邉*/
			case FLOW_WORK:
				throw new RuntimeException("Non-conformity FLOW_WORK");
			default:
				throw new RuntimeException("Non-conformity No Work");
		}
	}
	
	public List<HDWorkTimeSheetSetting> getHDWorkTimeSheetSettingList() {
		if(this.workTimeSetting.getWorkTimeDivision().isFlex())
			return this.flexWorkSetting.get().getOffdayWorkTime().getLstWorkTimezone();
		
		switch(this.workTimeSetting.getWorkTimeDivision().getWorkTimeMethodSet()) {
			case FIXED_WORK:
				return this.fixedWorkSetting.get().getOffdayWorkTimezone().getLstWorkTimezone();
			case DIFFTIME_WORK:
				throw new RuntimeException("Unimplemented");/*時差勤務はまだ実装しない。2020/5/19 渡邉*/
			case FLOW_WORK:
				throw new RuntimeException("Non-conformity FLOW_WORK");
			default:
				throw new RuntimeException("Non-conformity No Work");
		}
	}
	
	public boolean isLegalInternalTime() {
		if(this.workTimeSetting.getWorkTimeDivision().isFlex())
			return false;
		
		switch(this.workTimeSetting.getWorkTimeDivision().getWorkTimeMethodSet()) {
			case FIXED_WORK:
				return this.fixedWorkSetting.get().getLegalOTSetting().isLegal();
			case DIFFTIME_WORK:
				throw new RuntimeException("Unimplemented");/*時差勤務はまだ実装しない。2020/5/19 渡邉*/
			case FLOW_WORK:
				return false;
			default:
				throw new RuntimeException("Non-conformity No Work");
		}
	}
	
	public Optional<CoreTimeSetting> getCoreTimeSetting() {
		if(this.workTimeSetting.getWorkTimeDivision().isFlex())
			return Optional.of(this.flexWorkSetting.get().getCoreTimeSetting());
		
		switch(this.workTimeSetting.getWorkTimeDivision().getWorkTimeMethodSet()) {
			case FIXED_WORK:
				return Optional.empty();
			case DIFFTIME_WORK:
				throw new RuntimeException("Unimplemented");/*時差勤務はまだ実装しない。2020/5/19 渡邉*/
			case FLOW_WORK:
				return Optional.empty();
			default:
				throw new RuntimeException("Non-conformity No Work");
		}
	}
	
	
	
	/**
	 * 流動勤務の休憩設定を取得する
	 * フレックス勤務、流動勤務以外はRunTimeException
	 * 
	 * @return
	 */
	public FlowWorkRestSetting getFlowWorkRestSetting() {
		if(this.workTimeSetting.getWorkTimeDivision().isFlex())
			return this.flexWorkSetting.get().getRestSetting();
		
		switch(this.workTimeSetting.getWorkTimeDivision().getWorkTimeMethodSet()) {
			case FIXED_WORK:
				throw new RuntimeException("Non-conformity FIXED_WORK");
			case DIFFTIME_WORK:
				throw new RuntimeException("Unimplemented");/*時差勤務はまだ実装しない。2020/5/19 渡邉*/
			case FLOW_WORK:
				return this.flowWorkSetting.get().getRestSetting();
			default:
				throw new RuntimeException("Non-conformity No Work");
		}
	}
	
	/**
	 * 流動勤務の休憩設定詳細を取得する
	 * 通常勤務の場合はOptional.empty()を返す為、要注意
	 * 
	 * @return
	 */
	public Optional<FlowWorkRestSettingDetail> getFlowWorkRestSettingDetail() {
		if(this.workTimeSetting.getWorkTimeDivision().isFlex())
			return Optional.of(this.flexWorkSetting.get().getRestSetting().getFlowRestSetting());
		
		switch(this.workTimeSetting.getWorkTimeDivision().getWorkTimeMethodSet()) {
			case FIXED_WORK:
				return Optional.empty();
			case DIFFTIME_WORK:
				throw new RuntimeException("Unimplemented");/*時差勤務はまだ実装しない。2020/5/19 渡邉*/
			case FLOW_WORK:
				return Optional.of(this.flowWorkSetting.get().getRestSetting().getFlowRestSetting());
			default:
				throw new RuntimeException("Non-conformity No Work");
		}
	}
	
	/**
	 * 流動勤務の休憩時間帯を取得する
	 * 通常勤務の場合はOptional.empty()を返す為、要注意
	 * 
	 * @param workType 勤務種類
	 * @return 流動勤務の休憩時間帯
	 */
	public Optional<FlowWorkRestTimezone> getFlowWorkRestTimezone(WorkType workType) {
		if(this.workTimeSetting.getWorkTimeDivision().isFlex())
			return Optional.of(this.flexWorkSetting.get().getFlowWorkRestTimezone(workType));
		
		switch(this.workTimeSetting.getWorkTimeDivision().getWorkTimeMethodSet()) {
			case FIXED_WORK:
				return Optional.empty();
			case DIFFTIME_WORK:
				throw new RuntimeException("Unimplemented");/*時差勤務はまだ実装しない。2020/5/19 渡邉*/
			case FLOW_WORK:
				return Optional.of(this.flowWorkSetting.get().getFlowWorkRestTimezone(workType));
			default:
				throw new RuntimeException("Non-conformity No Work");
		}
	}
	
	/**
	 * 固定休憩の計算方法を取得する
	 * フレックス勤務、流動勤務の場合はOptional.empty()を返す為、要注意
	 * 
	 * @param workType
	 * @return
	 */
	public Optional<FixedRestCalculateMethod> getFixedRestCalculateMethod() {
		
		if(this.workTimeSetting.getWorkTimeDivision().isFlex())
			return Optional.empty();
		
		switch(this.workTimeSetting.getWorkTimeDivision().getWorkTimeMethodSet()) {
			case FIXED_WORK:
				return Optional.of(this.fixedWorkSetting.get().getFixedWorkRestSetting().getCalculateMethod());
			case DIFFTIME_WORK:
				throw new RuntimeException("Unimplemented");/*時差勤務はまだ実装しない。2020/5/19 渡邉*/
			case FLOW_WORK:
				return Optional.empty();
			default:
				throw new RuntimeException("Non-conformity No Work");
		}
	}
	
	/**
	 * 
	 * 
	 * @return 
	 */
	public CommonRestSetting getCommonRestSetting() {
		if(this.workTimeSetting.getWorkTimeDivision().isFlex())
			return this.flexWorkSetting.get().getRestSetting().getCommonRestSetting();
		
		switch(this.workTimeSetting.getWorkTimeDivision().getWorkTimeMethodSet()) {
			case FIXED_WORK:
				return this.fixedWorkSetting.get().getFixedWorkRestSetting().getCommonRestSet();
			case DIFFTIME_WORK:
				throw new RuntimeException("Unimplemented");/*時差勤務はまだ実装しない。2020/5/19 渡邉*/
			case FLOW_WORK:
				return this.flowWorkSetting.get().getRestSetting().getCommonRestSetting();
			default:
				throw new RuntimeException("Non-conformity No Work");
		}
	}
	
	/*
	 * 大塚カスタマイズ専用　遅刻早退の時間帯処理にて、出勤/退勤を変更したときに
	 * 休憩時間帯が消えてしまうので、就業時間帯から取得した休憩時間帯で計算できるように
	 * マスタから休憩時間帯を取得する。
	 * （流動休憩の事は考慮していないので、大塚カスタマイズ以外では使用禁止）
	 * */
	public List<DeductionTime> getBreakTimeList(WorkType worktype){
		if(this.workTimeSetting.getWorkTimeDivision().isFlex()) {
			if(worktype.isWeekDayAttendance())
				return null;
			if(worktype.getDailyWork().getOneDay().isHolidayWork())
				return this.flexWorkSetting.get().getOffdayWorkTime().getRestTimezone().getFixedRestTimezone().getTimezones();
		}
		return Collections.emptyList();
	}

}
