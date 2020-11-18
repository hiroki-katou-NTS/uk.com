package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.overtime.ApplicationTime;
import nts.uk.ctx.at.request.dom.application.overtime.AttendanceType_Update;
import nts.uk.ctx.at.request.dom.application.overtime.ExcessState;
import nts.uk.ctx.at.request.dom.application.overtime.ExcessStateDetail;
import nts.uk.ctx.at.request.dom.application.overtime.ExcessStateMidnight;
import nts.uk.ctx.at.request.dom.application.overtime.FrameNo;
import nts.uk.ctx.at.request.dom.application.overtime.HolidayMidNightTime;
import nts.uk.ctx.at.request.dom.application.overtime.OutDateApplication;
import nts.uk.ctx.at.request.dom.application.overtime.OverStateOutput;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeShiftNight;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeAppAtr;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeApplicationSetting;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.ExcessStatusAchivementOutput;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.ExcessStatusOutput;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.AppDateContradictionAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.PrePostInitAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.OverrideSet;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeRootRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeUseSet;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.残業休出申請共通設定.残業休出申請共通設定
 * @author Doan Duy Hung
 *
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OvertimeLeaveAppCommonSet {

	/**
	 * 事前超過表示設定
	 */
	private NotUseAtr preExcessDisplaySetting;

	/**
	 * 時間外超過区分
	 */
	private Time36AgreeCheckRegister extratimeExcessAtr;

	/**
	 * 時間外表示区分
	 */
	private NotUseAtr extratimeDisplayAtr;

	/**
	 * 実績超過区分
	 */
	private AppDateContradictionAtr performanceExcessAtr;

	/**
	 * 登録時の指示時間超過チェック
	 */
	private NotUseAtr checkOvertimeInstructionRegister;

	/**
	 * 登録時の乖離時間チェック
	 */
	private NotUseAtr checkDeviationRegister;

	/**
	 * 実績超過打刻優先設定
	 */
	private OverrideSet overrideSet;

	public static OvertimeLeaveAppCommonSet create(int preExcessDisplaySetting, int extratimeExcessAtr, int extratimeDisplayAtr, int performanceExcessAtr, int checkOvertimeInstructionRegister, int checkDeviationRegister, int overrideSet) {
		return new OvertimeLeaveAppCommonSet(
				EnumAdaptor.valueOf(preExcessDisplaySetting, NotUseAtr.class),
				EnumAdaptor.valueOf(extratimeExcessAtr, Time36AgreeCheckRegister.class),
				EnumAdaptor.valueOf(extratimeDisplayAtr, NotUseAtr.class),
				EnumAdaptor.valueOf(performanceExcessAtr, AppDateContradictionAtr.class),
				EnumAdaptor.valueOf(checkOvertimeInstructionRegister, NotUseAtr.class),
				EnumAdaptor.valueOf(checkDeviationRegister, NotUseAtr.class),
				EnumAdaptor.valueOf(overrideSet, OverrideSet.class)
		);
	}
	
	// @「事前超過表示設定」をチェックする
	Boolean isPreExcessDisplaySetting() {
		return this.preExcessDisplaySetting == NotUseAtr.USE;
	}
	// @「実績超過区分」をチェックする
	Boolean isPerformanceExcessAtr() {
		return this.performanceExcessAtr != AppDateContradictionAtr.NOTCHECK;
	}
	/**
	 * Refactor5 事前申請の超過状態をチェックする
	 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.残業休出申請共通設定.アルゴリズム.事前申請の超過状態をチェックする
	 * @param overtimeAppAtr
	 * @param advanceOp
	 * @param achieveOp
	 * @return
	 */
	public ExcessStatusOutput checkExcessStatus(
			PrePostAtr prePostInitAtr,
			Optional<ApplicationTime> advanceOp,
			Optional<ApplicationTime> subsequentOp) {
		ExcessStatusOutput output =  new ExcessStatusOutput();
		OutDateApplication outDateApplication = new OutDateApplication();
		List<ExcessStateMidnight> excessStateMidnights = new ArrayList<>();
		outDateApplication.setExcessStateMidnight(excessStateMidnights);
		List<ExcessStateDetail> excessStateDetail = new ArrayList<>();
		outDateApplication.setExcessStateDetail(excessStateDetail);
		// OUTPUT「申請時間の超過状態」を初期化する
		if (subsequentOp.isPresent()) {
			ApplicationTime subsequent = subsequentOp.get();
			// INPUT．「事後の申請時間．申請時間．type = 残業時間」がある場合：
			//　申請時間の超過状態．申請時間．type = 残業時間
			//		　申請時間の超過状態．申請時間．frameNO = INPUT．「事後の申請時間．申請時間．frameNO」←条件：type = 残業時間
			//		　申請時間の超過状態．申請時間．超過状態 = 超過なし
			Optional<OvertimeApplicationSetting> normalOverTime = subsequent.getApplicationTime()
																			.stream()
																			.filter(x -> x.getAttendanceType() == AttendanceType_Update.NORMALOVERTIME)
																			.findFirst();
			if (normalOverTime.isPresent()) {
				OvertimeApplicationSetting result = normalOverTime.get();
				ExcessStateDetail exStateDetail = new ExcessStateDetail(
						result.getFrameNo(),
						result.getAttendanceType(),
						ExcessState.NO_EXCESS);
				excessStateDetail.add(exStateDetail);
			}
			// 申請時間の超過状態．申請時間．frameNO = INPUT．「事後の申請時間．申請時間．frameNO」←条件：type = 休出時間
			Optional<OvertimeApplicationSetting> breakTime = subsequent.getApplicationTime()
																	   .stream()
																	   .filter(x -> x.getAttendanceType() == AttendanceType_Update.BREAKTIME)
																	   .findFirst();
			if (breakTime.isPresent()) {
				OvertimeApplicationSetting result = breakTime.get();
				ExcessStateDetail exStateDetail = new ExcessStateDetail(
						result.getFrameNo(),
						result.getAttendanceType(),
						ExcessState.NO_EXCESS);
				excessStateDetail.add(exStateDetail);
			}
			if (subsequent.getOverTimeShiftNight().isPresent()) {
				
				OverTimeShiftNight overTimeShiftNight = subsequent.getOverTimeShiftNight().get();
				List<HolidayMidNightTime> midNightHolidayTimes = overTimeShiftNight.getMidNightHolidayTimes();
				// INPUT．「事後の申請時間．休出深夜時間．法定区分 = 法定内休出」がある場合：
				// INPUT．「事後の申請時間．休出深夜時間．法定区分 = 法定外休出」がある場合：
				// INPUT．「事後の申請時間．休出深夜時間．法定区分 = 祝日休出」がある場合：
				if (!CollectionUtil.isEmpty(midNightHolidayTimes)) {
					excessStateMidnights = midNightHolidayTimes.stream()
															   .map(x -> new ExcessStateMidnight(ExcessState.NO_EXCESS, x.getLegalClf()) )
															   .collect(Collectors.toList());
					
				}
				
			}
			if (subsequent.getFlexOverTime().isPresent()) {
				outDateApplication.setFlex(ExcessState.NO_EXCESS);				
			}
			if (subsequent.getOverTimeShiftNight().isPresent()) {
				outDateApplication.setOverTimeLate(ExcessState.NO_EXCESS);
			}
			
			
			
		}
		Boolean isAlarm = false;
		output.setIsAlarm(isAlarm);
		output.setOutDateApplication(outDateApplication);
		// 事前申請の時間超過をチェックするか
		Boolean isPrePostInitAtr = this.checkOvertimeAppAtr(prePostInitAtr);
		// 取得した内容をチェックする
		if (!isPrePostInitAtr) return output;
		// true INPUT．「事前の申請時間」があるかチェックする
		if (!advanceOp.isPresent()) return output;
		ApplicationTime advance = advanceOp.get();
		
		if (!outDateApplication.getExcessStateDetail().isEmpty()) {
			// loop 1
			outDateApplication.getExcessStateDetail().forEach(item -> {
				if (item.getType() == AttendanceType_Update.NORMALOVERTIME) {
					FrameNo frame = item.getFrame();
					Optional<OvertimeApplicationSetting> advanceResultOp = advance.getApplicationTime()
																				  .stream()
																				  .filter(x -> x.getAttendanceType() == AttendanceType_Update.NORMALOVERTIME && x.getFrameNo() == frame)
																				  .findFirst();
					Optional<OvertimeApplicationSetting> subsequentResultOp = subsequentOp.get()
																						  .getApplicationTime()
																						  .stream()
																						  .filter(x -> x.getAttendanceType() == AttendanceType_Update.NORMALOVERTIME && x.getFrameNo() == frame)
																						  .findFirst();
					Integer time1 = 0;
					Integer time2 = 0;
					if (advanceResultOp.isPresent()) {
						time1 = advanceResultOp.get().getApplicationTime().v();
					}
					if (subsequentOp.isPresent()) {
						time2 = subsequentResultOp.get().getApplicationTime().v();
					}
					Boolean isGreater = time1 >= time2;
					if (!isGreater) {
						item.setExcessState(ExcessState.EXCESS_ALARM);
					}
				}
			});
			// loop2
			outDateApplication.getExcessStateDetail().forEach(item -> {
				if (item.getType() == AttendanceType_Update.BREAKTIME) {
					FrameNo frame = item.getFrame();
					Optional<OvertimeApplicationSetting> advanceResultOp = advance.getApplicationTime().stream().filter(x -> x.getAttendanceType() == AttendanceType_Update.BREAKTIME && x.getFrameNo() == frame).findFirst();
					Optional<OvertimeApplicationSetting> subsequentResultOp = subsequentOp.get().getApplicationTime().stream().filter(x -> x.getAttendanceType() == AttendanceType_Update.BREAKTIME && x.getFrameNo() == frame).findFirst();
					Integer time1 = 0;
					Integer time2 = 0;
					if (advanceResultOp.isPresent()) {
						time1 = advanceResultOp.get().getApplicationTime().v();
					}
					if (subsequentResultOp.isPresent()) {
						time2 = subsequentResultOp.get().getApplicationTime().v();
					}
					Boolean isGreater = time1 >= time2;
					if (!isGreater) {
						item.setExcessState(ExcessState.EXCESS_ALARM);
					}
				}
			});
			// loop3
			outDateApplication.getExcessStateMidnight().forEach(item -> {
				Optional<HolidayMidNightTime> advanceResultOp = advance.getOverTimeShiftNight().get().getMidNightHolidayTimes().stream().filter(x -> x.getLegalClf() == item.getLegalCfl()).findFirst();
				Optional<HolidayMidNightTime> subsequentResultOp = subsequentOp.get().getOverTimeShiftNight().get().getMidNightHolidayTimes().stream().filter(x -> x.getLegalClf() == item.getLegalCfl()).findFirst();
				Integer time1 = 0;
				Integer time2 = 0;
				if (advanceResultOp.isPresent()) {
					time1 = advanceResultOp.get().getAttendanceTime().v();
				}
				if (subsequentResultOp.isPresent()) {
					time2 = subsequentResultOp.get().getAttendanceTime().v();
				}
				Boolean isGreater = time1 >= time2;
				if (!isGreater) {
					item.setExcessState(ExcessState.EXCESS_ALARM);
				}
			});
			// 残業深夜の超過状態をチェックする
			Optional<OverTimeShiftNight> overTimeShiftNightAdvance = advance.getOverTimeShiftNight();
			Optional<OverTimeShiftNight> overTimeShiftNightAchive = subsequentOp.get().getOverTimeShiftNight();
			Integer time1 = 0;
			Integer time2 = 0;
			if (overTimeShiftNightAdvance.isPresent()) {
				time1 = overTimeShiftNightAdvance.get().getMidNightOutSide().v();
			}
			if (overTimeShiftNightAchive.isPresent()) {
				time2 = overTimeShiftNightAchive.get().getMidNightOutSide().v();
			}
			Boolean isGreater = time1 >= time2;
			if (!isGreater) {
				outDateApplication.setOverTimeLate(ExcessState.EXCESS_ALARM);
			}
			// ﾌﾚｯｸｽの超過状態をチェックする
			Optional<AttendanceTimeOfExistMinus> attendanceTimeOfExistMinusAdvance = advance.getFlexOverTime();
			Optional<AttendanceTimeOfExistMinus> attendanceTimeOfExistMinusAchive = subsequentOp.get().getFlexOverTime();
			time1 = 0;
			time2 = 0;
			if (attendanceTimeOfExistMinusAdvance.isPresent()) {
				time1 = attendanceTimeOfExistMinusAdvance.get().v();
			}
			if (attendanceTimeOfExistMinusAchive.isPresent()) {
				time2 = attendanceTimeOfExistMinusAchive.get().v();
			}
			isGreater = time1 >= time2;
			if (!isGreater) {
				outDateApplication.setOverTimeLate(ExcessState.EXCESS_ALARM);
			}
			
		}
		
		
		return output;
	}
	/**
	 * Refactor5 実績の超過状態をチェックする
	 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.残業休出申請共通設定.アルゴリズム.実績の超過状態をチェックする
	 * @param prePostInitAtr
	 * @param advanceOp
	 * @param subsequentOp
	 * @return 
	 */
	public ExcessStatusAchivementOutput checkExcessStatusAchivement(PrePostAtr prePostInitAtr, Optional<ApplicationTime> advanceOp,
			Optional<ApplicationTime> subsequentOp) {
		ExcessStatusAchivementOutput output =  new ExcessStatusAchivementOutput();
		OutDateApplication outDateApplication = new OutDateApplication();
		List<ExcessStateMidnight> excessStateMidnights = new ArrayList<>();
		outDateApplication.setExcessStateMidnight(excessStateMidnights);
		List<ExcessStateDetail> excessStateDetail = new ArrayList<>();
		outDateApplication.setExcessStateDetail(excessStateDetail);
		// OUTPUT「申請時間の超過状態」を初期化する
		if (subsequentOp.isPresent()) {
			ApplicationTime subsequent = subsequentOp.get();
			// INPUT．「事後の申請時間．申請時間．type = 残業時間」がある場合：
			//　申請時間の超過状態．申請時間．type = 残業時間
			//		　申請時間の超過状態．申請時間．frameNO = INPUT．「事後の申請時間．申請時間．frameNO」←条件：type = 残業時間
			//		　申請時間の超過状態．申請時間．超過状態 = 超過なし
			Optional<OvertimeApplicationSetting> normalOverTime = subsequent.getApplicationTime().stream().filter(x -> x.getAttendanceType() == AttendanceType_Update.NORMALOVERTIME).findFirst();
			if (normalOverTime.isPresent()) {
				OvertimeApplicationSetting result = normalOverTime.get();
				ExcessStateDetail exStateDetail = new ExcessStateDetail(result.getFrameNo(), result.getAttendanceType(), ExcessState.NO_EXCESS);
				excessStateDetail.add(exStateDetail);
			}
			// 申請時間の超過状態．申請時間．frameNO = INPUT．「事後の申請時間．申請時間．frameNO」←条件：type = 休出時間
			Optional<OvertimeApplicationSetting> breakTime = subsequent.getApplicationTime().stream().filter(x -> x.getAttendanceType() == AttendanceType_Update.BREAKTIME).findFirst();
			if (breakTime.isPresent()) {
				OvertimeApplicationSetting result = breakTime.get();
				ExcessStateDetail exStateDetail = new ExcessStateDetail(result.getFrameNo(), result.getAttendanceType(), ExcessState.NO_EXCESS);
				excessStateDetail.add(exStateDetail);
			}
			// QA edit EAP note 
			if (subsequent.getOverTimeShiftNight().isPresent()) {
				
				OverTimeShiftNight overTimeShiftNight = subsequent.getOverTimeShiftNight().get();
				List<HolidayMidNightTime> midNightHolidayTimes = overTimeShiftNight.getMidNightHolidayTimes();
				// INPUT．「事後の申請時間．休出深夜時間．法定区分 = 法定内休出」がある場合：
				// INPUT．「事後の申請時間．休出深夜時間．法定区分 = 法定外休出」がある場合：
				// INPUT．「事後の申請時間．休出深夜時間．法定区分 = 祝日休出」がある場合：
				if (!CollectionUtil.isEmpty(midNightHolidayTimes)) {
					excessStateMidnights = midNightHolidayTimes.stream().map(x -> new ExcessStateMidnight(ExcessState.NO_EXCESS, x.getLegalClf()) ).collect(Collectors.toList());
					
				}
				
			}
			if (subsequent.getFlexOverTime().isPresent()) {
				outDateApplication.setFlex(ExcessState.NO_EXCESS);				
			}
			if (subsequent.getOverTimeShiftNight().isPresent()) {
				outDateApplication.setOverTimeLate(ExcessState.NO_EXCESS);
			}
			
			
			
		}
		
		output.setExcessState(ExcessState.NO_EXCESS);
		output.setOutDateApplication(outDateApplication);
		// 実績の時間超過をチェックするか
		Boolean isActualTime = this.checkActualTime(prePostInitAtr);
		// 取得した内容をチェックする
		if (!isActualTime) return output;
		// true INPUT．「事前の申請時間」があるかチェックする
		if (!advanceOp.isPresent()) return output;
		ApplicationTime advance = advanceOp.get();
		
		if (!outDateApplication.getExcessStateDetail().isEmpty()) {
			// loop 1
			outDateApplication.getExcessStateDetail().forEach(item -> {
				if (item.getType() == AttendanceType_Update.NORMALOVERTIME) {
					FrameNo frame = item.getFrame();
					Optional<OvertimeApplicationSetting> advanceResultOp = advance.getApplicationTime().stream().filter(x -> x.getAttendanceType() == AttendanceType_Update.NORMALOVERTIME && x.getFrameNo() == frame).findFirst();
					Optional<OvertimeApplicationSetting> subsequentResultOp = subsequentOp.get().getApplicationTime().stream().filter(x -> x.getAttendanceType() == AttendanceType_Update.NORMALOVERTIME && x.getFrameNo() == frame).findFirst();
					Integer time1 = 0;
					Integer time2 = 0;
					if (advanceResultOp.isPresent()) {
						time1 = advanceResultOp.get().getApplicationTime().v();
					}
					if (subsequentOp.isPresent()) {
						time2 = subsequentResultOp.get().getApplicationTime().v();
					}
					Boolean isGreater = time1 >= time2;
					if (!isGreater) {
						item.setExcessState(ExcessState.EXCESS_ALARM);
					}
				}
			});
			// loop2
			outDateApplication.getExcessStateDetail().forEach(item -> {
				if (item.getType() == AttendanceType_Update.BREAKTIME) {
					FrameNo frame = item.getFrame();
					Optional<OvertimeApplicationSetting> advanceResultOp = advance.getApplicationTime().stream().filter(x -> x.getAttendanceType() == AttendanceType_Update.BREAKTIME && x.getFrameNo() == frame).findFirst();
					Optional<OvertimeApplicationSetting> subsequentResultOp = subsequentOp.get().getApplicationTime().stream().filter(x -> x.getAttendanceType() == AttendanceType_Update.BREAKTIME && x.getFrameNo() == frame).findFirst();
					Integer time1 = 0;
					Integer time2 = 0;
					if (advanceResultOp.isPresent()) {
						time1 = advanceResultOp.get().getApplicationTime().v();
					}
					if (subsequentResultOp.isPresent()) {
						time2 = subsequentResultOp.get().getApplicationTime().v();
					}
					Boolean isGreater = time1 >= time2;
					if (!isGreater) {
						item.setExcessState(ExcessState.EXCESS_ALARM);
					}
				}
			});
			// loop3
			outDateApplication.getExcessStateMidnight().forEach(item -> {
				Optional<HolidayMidNightTime> advanceResultOp = advance.getOverTimeShiftNight().get().getMidNightHolidayTimes().stream().filter(x -> x.getLegalClf() == item.getLegalCfl()).findFirst();
				Optional<HolidayMidNightTime> subsequentResultOp = subsequentOp.get().getOverTimeShiftNight().get().getMidNightHolidayTimes().stream().filter(x -> x.getLegalClf() == item.getLegalCfl()).findFirst();
				Integer time1 = 0;
				Integer time2 = 0;
				if (advanceResultOp.isPresent()) {
					time1 = advanceResultOp.get().getAttendanceTime().v();
				}
				if (subsequentResultOp.isPresent()) {
					time2 = subsequentResultOp.get().getAttendanceTime().v();
				}
				Boolean isGreater = time1 >= time2;
				if (!isGreater) {
					item.setExcessState(ExcessState.EXCESS_ALARM);
				}
			});
			// 残業深夜の超過状態をチェックする
			Optional<OverTimeShiftNight> overTimeShiftNightAdvance = advance.getOverTimeShiftNight();
			Optional<OverTimeShiftNight> overTimeShiftNightAchive = subsequentOp.get().getOverTimeShiftNight();
			Integer time1 = 0;
			Integer time2 = 0;
			if (overTimeShiftNightAdvance.isPresent()) {
				time1 = overTimeShiftNightAdvance.get().getMidNightOutSide().v();
			}
			if (overTimeShiftNightAchive.isPresent()) {
				time2 = overTimeShiftNightAchive.get().getMidNightOutSide().v();
			}
			Boolean isGreater = time1 >= time2;
			if (!isGreater) {
				outDateApplication.setOverTimeLate(ExcessState.EXCESS_ALARM);
			}
			// ﾌﾚｯｸｽの超過状態をチェックする
			Optional<AttendanceTimeOfExistMinus> attendanceTimeOfExistMinusAdvance = advance.getFlexOverTime();
			Optional<AttendanceTimeOfExistMinus> attendanceTimeOfExistMinusAchive = subsequentOp.get().getFlexOverTime();
			time1 = 0;
			time2 = 0;
			if (attendanceTimeOfExistMinusAdvance.isPresent()) {
				time1 = attendanceTimeOfExistMinusAdvance.get().v();
			}
			if (attendanceTimeOfExistMinusAchive.isPresent()) {
				time2 = attendanceTimeOfExistMinusAchive.get().v();
			}
			isGreater = time1 >= time2;
			if (!isGreater) {
				outDateApplication.setOverTimeLate(ExcessState.EXCESS_ALARM);
			}
			
		}
		
		
		return output;
	}
	
	/**
	 * Refactor5 実績の時間超過をチェックするか
	 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.残業休出申請共通設定.アルゴリズム.実績の時間超過をチェックするか
	 * @param prePostInitAtr
	 * @return
	 */
	private Boolean checkActualTime(PrePostAtr prePostInitAtr) {
		// INPUT．「事前事後区分」をチェックする
		if (prePostInitAtr == PrePostAtr.PREDICT) return false;
		return this.isPerformanceExcessAtr();
	}
	/**
	 * Refactor5 事前申請の時間超過をチェックするか
	 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.残業休出申請共通設定.アルゴリズム.事前申請の時間超過をチェックするか
	 * @param overtimeAppAtr
	 * @return
	 */
	public Boolean checkOvertimeAppAtr(PrePostAtr prePostInitAtr) {
		// INPUT．「事前事後区分」をチェックする
		if (prePostInitAtr == PrePostAtr.PREDICT) return false;
		// @「事前超過表示設定」をチェックする
		return this.isPreExcessDisplaySetting();
	}
	/**
	 * Refactor5 事前申請・実績の時間超過をチェックする
	 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.残業休出申請共通設定.アルゴリズム.事前申請・実績の時間超過をチェックする
	 * @param prePostInitAtr
	 * @param advanceOp
	 * @param subsequentOp
	 * @param achiveOp
	 * @return
	 */
	public OverStateOutput checkPreApplication(
			PrePostAtr prePostInitAtr,
			Optional<ApplicationTime> advanceOp,
			Optional<ApplicationTime> subsequentOp,
			Optional<ApplicationTime> achiveOp
			) {
		OverStateOutput output = new OverStateOutput();
		// 事前申請の超過状態をチェックする
		ExcessStatusOutput excessStatusOutput = this.checkExcessStatus(
				prePostInitAtr,
				advanceOp,
				subsequentOp);
		// 取得した内容をOUTPUT「事前申請・実績の超過状態」にセットする
		output.setIsExistApp(excessStatusOutput.getIsAlarm());
		output.setAdvanceExcess(excessStatusOutput.getOutDateApplication());
		// 実績の超過状態をチェックする
		ExcessStatusAchivementOutput excessStatusAchivementOutput = this.checkExcessStatusAchivement(
				prePostInitAtr,
				subsequentOp,
				achiveOp);
		// 取得した内容をOUTPUT「事前申請・実績の超過状態」にセットする
		output.setAchivementStatus(excessStatusAchivementOutput.getExcessState());
		output.setAchivementExcess(excessStatusAchivementOutput.getOutDateApplication());
		return output;
	}
	
	
	

}
