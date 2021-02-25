package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon;

import java.util.ArrayList;
import java.util.Collections;
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
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeRootRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeUseSet;
import nts.uk.ctx.at.shared.dom.scherec.event.PerformanceAtr;
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
			PrePostInitAtr prePostInitAtr,
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
			List<OvertimeApplicationSetting> normalOverTime = subsequent.getApplicationTime()
																			.stream()
																			.filter(x -> x.getAttendanceType() == AttendanceType_Update.NORMALOVERTIME)
																			.collect(Collectors.toList());
			
			normalOverTime.stream()
						  .forEach(x -> {
							  OvertimeApplicationSetting result = x;
								ExcessStateDetail exStateDetail = new ExcessStateDetail(
										result.getFrameNo(),
										result.getAttendanceType(),
										ExcessState.NO_EXCESS);
								excessStateDetail.add(exStateDetail);
						  });
			// 申請時間の超過状態．申請時間．frameNO = INPUT．「事後の申請時間．申請時間．frameNO」←条件：type = 休出時間
			List<OvertimeApplicationSetting> breakTime = subsequent.getApplicationTime()
																	   .stream()
																	   .filter(x -> x.getAttendanceType() == AttendanceType_Update.BREAKTIME)
																	   .collect(Collectors.toList());
			breakTime.stream()
					 .forEach(x -> {
						 OvertimeApplicationSetting result = x;
							ExcessStateDetail exStateDetail = new ExcessStateDetail(
									result.getFrameNo(),
									result.getAttendanceType(),
									ExcessState.NO_EXCESS);
							excessStateDetail.add(exStateDetail);
					 });
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
					outDateApplication.setExcessStateMidnight(excessStateMidnights);
					
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
		if (!advanceOp.isPresent()) {
			output.setIsAlarm(true);
			if (!CollectionUtil.isEmpty(outDateApplication.getExcessStateDetail())) {
				// loop 1
				outDateApplication.getExcessStateDetail().forEach(item -> {
					if (item.getType() == AttendanceType_Update.NORMALOVERTIME) {
						FrameNo frame = item.getFrame();
						Optional<OvertimeApplicationSetting> subsequentResultOp = subsequentOp.get()
																							  .getApplicationTime()
																							  .stream()
																							  .filter(x -> x.getAttendanceType() == AttendanceType_Update.NORMALOVERTIME && x.getFrameNo().v() == frame.v())
																							  .findFirst();
						Integer	time2 = subsequentResultOp.flatMap(x -> Optional.ofNullable(x.getApplicationTime())).map(x -> x.v()).orElse(0);
						if (time2 > 0) {
							item.setExcessState(ExcessState.EXCESS_ALARM);
						}
					}
				});
				// loop2
				outDateApplication.getExcessStateDetail().forEach(item -> {
					if (item.getType() == AttendanceType_Update.BREAKTIME) {
						FrameNo frame = item.getFrame();
						Optional<OvertimeApplicationSetting> subsequentResultOp = subsequentOp.get().getApplicationTime().stream().filter(x -> x.getAttendanceType() == AttendanceType_Update.BREAKTIME && x.getFrameNo().v() == frame.v()).findFirst();
						Integer	time2 = subsequentResultOp.flatMap(x -> Optional.ofNullable(x.getApplicationTime())).map(x -> x.v()).orElse(0);
						
						if (time2 > 0) {
							item.setExcessState(ExcessState.EXCESS_ALARM);
						}
					}
				});
				
				
			}
			// loop3
			outDateApplication.getExcessStateMidnight().forEach(item -> {
				Optional<HolidayMidNightTime> subsequentResultOp = subsequentOp.get()
						.getOverTimeShiftNight()
						.map(x -> x.getMidNightHolidayTimes())
						.orElse(Collections.emptyList())
						.stream()
						.filter(x -> x.getLegalClf() == item.getLegalCfl())
						.findFirst();
				Integer time2 = subsequentResultOp.flatMap(x -> Optional.ofNullable(x.getAttendanceTime())).map(x -> x.v()).orElse(0);
				if (time2 > 0) {
					item.setExcessState(ExcessState.EXCESS_ALARM);
				}
			});
			// 残業深夜の超過状態をチェックする
			Optional<OverTimeShiftNight> overTimeShiftNightAchive = subsequentOp.get().getOverTimeShiftNight();
			Integer time2 = overTimeShiftNightAchive.flatMap(x -> Optional.ofNullable(x.getOverTimeMidNight())).map(x -> x.v()).orElse(0);
			if (time2 > 0) {
				outDateApplication.setOverTimeLate(ExcessState.EXCESS_ALARM);
			}
			// ﾌﾚｯｸｽの超過状態をチェックする
			Optional<AttendanceTimeOfExistMinus> attendanceTimeOfExistMinusAchive = subsequentOp.get().getFlexOverTime();
			time2 = attendanceTimeOfExistMinusAchive.map(x -> x.v()).orElse(0);
			if (time2 > 0) {
				outDateApplication.setFlex(ExcessState.EXCESS_ALARM);
			}
			
			
			return output;
		}
		ApplicationTime advance = advanceOp.get();
		
		if (!CollectionUtil.isEmpty(outDateApplication.getExcessStateDetail())) {
			// loop 1
			outDateApplication.getExcessStateDetail().forEach(item -> {
				if (item.getType() == AttendanceType_Update.NORMALOVERTIME) {
					FrameNo frame = item.getFrame();
					Optional<OvertimeApplicationSetting> advanceResultOp = advance.getApplicationTime()
																				  .stream()
																				  .filter(x -> x.getAttendanceType() == AttendanceType_Update.NORMALOVERTIME && x.getFrameNo().v() == frame.v())
																				  .findFirst();
					Optional<OvertimeApplicationSetting> subsequentResultOp = subsequentOp.get()
																						  .getApplicationTime()
																						  .stream()
																						  .filter(x -> x.getAttendanceType() == AttendanceType_Update.NORMALOVERTIME && x.getFrameNo().v() == frame.v())
																						  .findFirst();
					Integer	time1 = advanceResultOp.flatMap(x -> Optional.ofNullable(x.getApplicationTime())).map(x -> x.v()).orElse(null);					
					Integer	time2 = subsequentResultOp.flatMap(x -> Optional.ofNullable(x.getApplicationTime())).map(x -> x.v()).orElse(null);
					if (isGreater(time1, time2)) {
						item.setExcessState(ExcessState.EXCESS_ALARM);
					}
				}
			});
			// loop2
			outDateApplication.getExcessStateDetail().forEach(item -> {
				if (item.getType() == AttendanceType_Update.BREAKTIME) {
					FrameNo frame = item.getFrame();
					Optional<OvertimeApplicationSetting> advanceResultOp = advance.getApplicationTime().stream().filter(x -> x.getAttendanceType() == AttendanceType_Update.BREAKTIME && x.getFrameNo().v() == frame.v()).findFirst();
					Optional<OvertimeApplicationSetting> subsequentResultOp = subsequentOp.get().getApplicationTime().stream().filter(x -> x.getAttendanceType() == AttendanceType_Update.BREAKTIME && x.getFrameNo().v() == frame.v()).findFirst();
					Integer	time1 = advanceResultOp.flatMap(x -> Optional.ofNullable(x.getApplicationTime())).map(x -> x.v()).orElse(null);
					Integer	time2 = subsequentResultOp.flatMap(x -> Optional.ofNullable(x.getApplicationTime())).map(x -> x.v()).orElse(null);
					
					if (isGreater(time1, time2)) {
						item.setExcessState(ExcessState.EXCESS_ALARM);
					}
				}
			});
			
			
		}
		// loop3
		outDateApplication.getExcessStateMidnight().forEach(item -> {
			Optional<HolidayMidNightTime> advanceResultOp = advance
					.getOverTimeShiftNight()
					.flatMap(x -> Optional.ofNullable(x.getMidNightHolidayTimes()))
					.orElse(Collections.emptyList())
					.stream()
					.filter(x -> x.getLegalClf() == item.getLegalCfl())
					.findFirst();
			Optional<HolidayMidNightTime> subsequentResultOp = subsequentOp.get()
					.getOverTimeShiftNight()
					.flatMap(x -> Optional.ofNullable(x.getMidNightHolidayTimes()))
					.orElse(Collections.emptyList())
					.stream()
					.filter(x -> x.getLegalClf() == item.getLegalCfl())
					.findFirst();
			Integer time1 = advanceResultOp.flatMap(x -> Optional.ofNullable(x.getAttendanceTime())).map(x -> x.v()).orElse(null);
			Integer time2 = subsequentResultOp.flatMap(x -> Optional.ofNullable(x.getAttendanceTime())).map(x -> x.v()).orElse(null);
			if (isGreater(time1, time2)) {
				item.setExcessState(ExcessState.EXCESS_ALARM);
			}
		});
		// 残業深夜の超過状態をチェックする
		Optional<OverTimeShiftNight> overTimeShiftNightAdvance = advance.getOverTimeShiftNight();
		Optional<OverTimeShiftNight> overTimeShiftNightAchive = subsequentOp.get().getOverTimeShiftNight();
		Integer time1 = overTimeShiftNightAdvance.flatMap(x -> Optional.ofNullable(x.getOverTimeMidNight())).map(x -> x.v()).orElse(null);
		Integer time2 = overTimeShiftNightAchive.flatMap(x -> Optional.ofNullable(x.getOverTimeMidNight())).map(x -> x.v()).orElse(null);
		if (isGreater(time1, time2)) {
			outDateApplication.setOverTimeLate(ExcessState.EXCESS_ALARM);
		}
		// ﾌﾚｯｸｽの超過状態をチェックする
		Optional<AttendanceTimeOfExistMinus> attendanceTimeOfExistMinusAdvance = advance.getFlexOverTime();
		Optional<AttendanceTimeOfExistMinus> attendanceTimeOfExistMinusAchive = subsequentOp.get().getFlexOverTime();
		time1 = attendanceTimeOfExistMinusAdvance.map(x -> x.v()).orElse(null);
		time2 = attendanceTimeOfExistMinusAchive.map(x -> x.v()).orElse(null);
		if (isGreater(time1, time2)) {
			outDateApplication.setFlex(ExcessState.EXCESS_ALARM);
		}

		
		return output;
	}
	
	public Boolean isGreater(Integer time1, Integer time2) {
		if (time1 == null ) time1 = 0;
		if (time2 == null ) time2 = 0;
		if (time1 < time2) {
			return true;
		}
		
		return false;
	}
	/**
	 * Refactor5 実績の超過状態をチェックする
	 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.残業休出申請共通設定.アルゴリズム.実績の超過状態をチェックする
	 * @param prePostInitAtr
	 * @param advanceOp
	 * @param subsequentOp
	 * @return 
	 */
	public ExcessStatusAchivementOutput checkExcessStatusAchivement(
			PrePostInitAtr prePostInitAtr,
			Optional<ApplicationTime> archivementOp,
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
			List<OvertimeApplicationSetting> normalOverTime = subsequent.getApplicationTime()
					.stream().filter(x -> x.getAttendanceType() == AttendanceType_Update.NORMALOVERTIME)
					.collect(Collectors.toList());
			normalOverTime.stream()
						  .forEach(x -> {
							  OvertimeApplicationSetting result = x;
							  ExcessStateDetail exStateDetail = new ExcessStateDetail(result.getFrameNo(), result.getAttendanceType(), ExcessState.NO_EXCESS);
							  excessStateDetail.add(exStateDetail);							  
						  });
			// 申請時間の超過状態．申請時間．frameNO = INPUT．「事後の申請時間．申請時間．frameNO」←条件：type = 休出時間
			List<OvertimeApplicationSetting> breakTime = subsequent
					.getApplicationTime().stream()
					.filter(x -> x.getAttendanceType() == AttendanceType_Update.BREAKTIME)
					.collect(Collectors.toList());
			breakTime.stream()
					 .forEach(x -> {
						 OvertimeApplicationSetting result = x;
						 ExcessStateDetail exStateDetail = new ExcessStateDetail(result.getFrameNo(), result.getAttendanceType(), ExcessState.NO_EXCESS);
						 excessStateDetail.add(exStateDetail);						 
					 });
			// QA edit EAP note 
			if (subsequent.getOverTimeShiftNight().isPresent()) {
				
				OverTimeShiftNight overTimeShiftNight = subsequent.getOverTimeShiftNight().get();
				List<HolidayMidNightTime> midNightHolidayTimes = overTimeShiftNight.getMidNightHolidayTimes();
				// INPUT．「事後の申請時間．休出深夜時間．法定区分 = 法定内休出」がある場合：
				// INPUT．「事後の申請時間．休出深夜時間．法定区分 = 法定外休出」がある場合：
				// INPUT．「事後の申請時間．休出深夜時間．法定区分 = 祝日休出」がある場合：
				if (!CollectionUtil.isEmpty(midNightHolidayTimes)) {
					excessStateMidnights = midNightHolidayTimes.stream().map(x -> new ExcessStateMidnight(ExcessState.NO_EXCESS, x.getLegalClf()) ).collect(Collectors.toList());
					outDateApplication.setExcessStateMidnight(excessStateMidnights);
					
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
		if (!archivementOp.isPresent()) {
			if (this.performanceExcessAtr == AppDateContradictionAtr.CHECKNOTREGISTER) {
				output.setExcessState(ExcessState.EXCESS_ERROR);				
			}
			if ((this.performanceExcessAtr == AppDateContradictionAtr.CHECKREGISTER)) {
				output.setExcessState(ExcessState.EXCESS_ALARM);	
			}
			if (!CollectionUtil.isEmpty(outDateApplication.getExcessStateDetail())) {
				// loop 1
				outDateApplication.getExcessStateDetail().forEach(item -> {
					if (item.getType() == AttendanceType_Update.NORMALOVERTIME) {
						FrameNo frame = item.getFrame();
						Optional<OvertimeApplicationSetting> subsequentResultOp = subsequentOp.get().getApplicationTime().stream().filter(x -> x.getAttendanceType() == AttendanceType_Update.NORMALOVERTIME && x.getFrameNo().v() == frame.v()).findFirst();
						Integer time2 = subsequentResultOp.flatMap(x -> Optional.ofNullable(x.getApplicationTime())).map(x -> x.v()).orElse(0);				
						if (time2 > 0) {
							item.setExcessState(this.setStateColorForArchivement());
						}
					}
				});
				// loop2
				outDateApplication.getExcessStateDetail().forEach(item -> {
					if (item.getType() == AttendanceType_Update.BREAKTIME) {
						FrameNo frame = item.getFrame();
						Optional<OvertimeApplicationSetting> subsequentResultOp = subsequentOp.get().getApplicationTime().stream().filter(x -> x.getAttendanceType() == AttendanceType_Update.BREAKTIME && x.getFrameNo().v() == frame.v()).findFirst();
						Integer time2 = subsequentResultOp.flatMap(x -> Optional.ofNullable(x.getApplicationTime())).map(x -> x.v()).orElse(0);				
						if (time2 > 0) {
							item.setExcessState(this.setStateColorForArchivement());
						}
					}
				});
				
				
			}
			// loop3
			outDateApplication.getExcessStateMidnight().forEach(item -> {
				Optional<HolidayMidNightTime> subsequentResultOp = subsequentOp.get().getOverTimeShiftNight().get().getMidNightHolidayTimes().stream().filter(x -> x.getLegalClf() == item.getLegalCfl()).findFirst();
				Integer time2 = subsequentResultOp.flatMap(x -> Optional.ofNullable(x.getAttendanceTime())).map(x -> x.v()).orElse(0);
				if (time2 > 0) {
					item.setExcessState(this.setStateColorForArchivement());
				}
			});
			// 残業深夜の超過状態をチェックする
			Optional<OverTimeShiftNight> overTimeShiftNightSubsequent = subsequentOp.get().getOverTimeShiftNight();
			Integer time2 = overTimeShiftNightSubsequent.flatMap(x -> Optional.ofNullable(x.getOverTimeMidNight())).map(x -> x.v()).orElse(0);
			
			if (time2 > 0) {
				outDateApplication.setOverTimeLate(this.setStateColorForArchivement());
			}
			// ﾌﾚｯｸｽの超過状態をチェックする
			Optional<AttendanceTimeOfExistMinus> attendanceTimeOfExistMinusSubsequent = subsequentOp.get().getFlexOverTime();
			time2 = attendanceTimeOfExistMinusSubsequent.map(x -> x.v()).orElse(0);
			if (time2 > 0) {
				outDateApplication.setFlex(this.setStateColorForArchivement());
			}
			return output;
		}
		ApplicationTime archivement = archivementOp.get();
		
		if (!CollectionUtil.isEmpty(outDateApplication.getExcessStateDetail())) {
			// loop 1
			outDateApplication.getExcessStateDetail().forEach(item -> {
				if (item.getType() == AttendanceType_Update.NORMALOVERTIME) {
					FrameNo frame = item.getFrame();
					Optional<OvertimeApplicationSetting> archivementResultOp = archivement.getApplicationTime().stream().filter(x -> x.getAttendanceType() == AttendanceType_Update.NORMALOVERTIME && x.getFrameNo().v() == frame.v()).findFirst();
					Optional<OvertimeApplicationSetting> subsequentResultOp = subsequentOp.get().getApplicationTime().stream().filter(x -> x.getAttendanceType() == AttendanceType_Update.NORMALOVERTIME && x.getFrameNo().v() == frame.v()).findFirst();
					Integer time1 = archivementResultOp.flatMap(x -> Optional.ofNullable(x.getApplicationTime())).map(x -> x.v()).orElse(null);
					Integer time2 = subsequentResultOp.flatMap(x -> Optional.ofNullable(x.getApplicationTime())).map(x -> x.v()).orElse(null);				
					if (isGreater(time1, time2)) {
						item.setExcessState(this.setStateColorForArchivement());
					}
				}
			});
			// loop2
			outDateApplication.getExcessStateDetail().forEach(item -> {
				if (item.getType() == AttendanceType_Update.BREAKTIME) {
					FrameNo frame = item.getFrame();
					Optional<OvertimeApplicationSetting> archivementResultOp = archivement.getApplicationTime().stream().filter(x -> x.getAttendanceType() == AttendanceType_Update.BREAKTIME && x.getFrameNo().v() == frame.v()).findFirst();
					Optional<OvertimeApplicationSetting> subsequentResultOp = subsequentOp.get().getApplicationTime().stream().filter(x -> x.getAttendanceType() == AttendanceType_Update.BREAKTIME && x.getFrameNo().v() == frame.v()).findFirst();
					Integer time1 = archivementResultOp.flatMap(x -> Optional.ofNullable(x.getApplicationTime())).map(x -> x.v()).orElse(null);
					Integer time2 = subsequentResultOp.flatMap(x -> Optional.ofNullable(x.getApplicationTime())).map(x -> x.v()).orElse(null);				
					if (isGreater(time1, time2)) {
						item.setExcessState(this.setStateColorForArchivement());
					}
				}
			});
			
			
		}
		// loop3
		outDateApplication.getExcessStateMidnight().forEach(item -> {
			Optional<HolidayMidNightTime> archivementResultOp = archivement.getOverTimeShiftNight()
					.map(x -> x.getMidNightHolidayTimes())
					.orElse(Collections.emptyList())
					.stream()
					.filter(x -> x.getLegalClf() == item.getLegalCfl())
					.findFirst();
			Optional<HolidayMidNightTime> subsequentResultOp = subsequentOp.get()
					.getOverTimeShiftNight()
					.map(x -> x.getMidNightHolidayTimes())
					.orElse(Collections.emptyList())
					.stream()
					.filter(x -> x.getLegalClf() == item.getLegalCfl())
					.findFirst();
			Integer time1 = archivementResultOp.flatMap(x -> Optional.ofNullable(x.getAttendanceTime())).map(x -> x.v()).orElse(null);
			Integer time2 = subsequentResultOp.flatMap(x -> Optional.ofNullable(x.getAttendanceTime())).map(x -> x.v()).orElse(null);
			if (isGreater(time1, time2)) {
				item.setExcessState(this.setStateColorForArchivement());
			}
		});
		// 残業深夜の超過状態をチェックする
		Optional<OverTimeShiftNight> overTimeShiftNightArchivement = archivement.getOverTimeShiftNight();
		Optional<OverTimeShiftNight> overTimeShiftNightSubsequent = subsequentOp.get().getOverTimeShiftNight();
		Integer time1 = overTimeShiftNightArchivement.flatMap(x -> Optional.ofNullable(x.getOverTimeMidNight())).map(x -> x.v()).orElse(null);
		Integer time2 = overTimeShiftNightSubsequent.flatMap(x -> Optional.ofNullable(x.getOverTimeMidNight())).map(x -> x.v()).orElse(null);
		
		if (isGreater(time1, time2)) {
			outDateApplication.setOverTimeLate(this.setStateColorForArchivement());
		}
		// ﾌﾚｯｸｽの超過状態をチェックする
		Optional<AttendanceTimeOfExistMinus> attendanceTimeOfExistMinusArchivement = archivement.getFlexOverTime();
		Optional<AttendanceTimeOfExistMinus> attendanceTimeOfExistMinusSubsequent = subsequentOp.get().getFlexOverTime();
		time1 = attendanceTimeOfExistMinusArchivement.map(x -> x.v()).orElse(null);
		time2 = attendanceTimeOfExistMinusSubsequent.map(x -> x.v()).orElse(null);
		if (isGreater(time1, time2)) {
			outDateApplication.setFlex(this.setStateColorForArchivement());
		}
		
		
		return output;
	}
	
	public ExcessState setStateColorForArchivement() {
		if (this.performanceExcessAtr == AppDateContradictionAtr.CHECKREGISTER) {
			return ExcessState.EXCESS_ALARM;
		} else if (this.performanceExcessAtr == AppDateContradictionAtr.CHECKNOTREGISTER) {
			return ExcessState.EXCESS_ERROR;
		} else {
			return ExcessState.NO_EXCESS;
		}
	}
	
	/**
	 * Refactor5 実績の時間超過をチェックするか
	 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.残業休出申請共通設定.アルゴリズム.実績の時間超過をチェックするか
	 * @param prePostInitAtr
	 * @return
	 */
	private Boolean checkActualTime(PrePostInitAtr prePostInitAtr) {
		// INPUT．「事前事後区分」をチェックする
		if (prePostInitAtr == PrePostInitAtr.PREDICT) return false;
		return this.isPerformanceExcessAtr();
	}
	/**
	 * Refactor5 事前申請の時間超過をチェックするか
	 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.残業休出申請共通設定.アルゴリズム.事前申請の時間超過をチェックするか
	 * @param overtimeAppAtr
	 * @return
	 */
	public Boolean checkOvertimeAppAtr(PrePostInitAtr prePostInitAtr) {
		// INPUT．「事前事後区分」をチェックする
		if (prePostInitAtr == PrePostInitAtr.PREDICT) return false;
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
			PrePostInitAtr prePostInitAtr,
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
				achiveOp,
				subsequentOp);
		// 取得した内容をOUTPUT「事前申請・実績の超過状態」にセットする
		output.setAchivementStatus(excessStatusAchivementOutput.getExcessState());
		output.setAchivementExcess(excessStatusAchivementOutput.getOutDateApplication());
		return output;
	}
	
	
	

}
