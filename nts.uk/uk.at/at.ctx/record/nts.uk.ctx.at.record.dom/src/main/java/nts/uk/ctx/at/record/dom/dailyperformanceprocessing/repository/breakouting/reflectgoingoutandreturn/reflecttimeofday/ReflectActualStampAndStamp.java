package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.breakouting.reflectgoingoutandreturn.reflecttimeofday;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.breakouting.reflectgoingoutandreturn.TimeFrame;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ChangeClockArt;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.EngravingMethod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.ReasonTimeChange;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkTimeInformation;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.getcommonset.GetCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.common.FontRearSection;
import nts.uk.ctx.at.shared.dom.worktime.common.InstantRounding;
import nts.uk.ctx.at.shared.dom.worktime.common.RoundingSet;
import nts.uk.ctx.at.shared.dom.worktime.common.Superiority;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneStampSet;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 実打刻と打刻反映する (new_2020)
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.日別実績作成処理.作成処理.アルゴリズム.一日の日別実績の作成処理（New）.打刻反映.外出・臨時反映する.時間帯反映する.実打刻と打刻反映する.実打刻と打刻反映する
 * @author tutk
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ReflectActualStampAndStamp {
	
//	@Inject
//	private GetCommonSet getCommonSet;
	
	@Inject
	private RecordDomRequireService requireService;
	
	/**
	 * 
	 * @param timeActualStamp 反映先勤怠打刻(実打刻付き)
	 * @param stamp 打刻
	 * @param isReflectTimeStamp 普通打刻反映するか
	 * @param timeFrame 反映先時間帯枠（Temporary）
	 * @param ymd  処理中の年月日
	 * @param workTimeCode 処理中の年月日の就業時間帯コード
	 */
	public TimeActualStamp reflect(TimeActualStamp timeActualStamp,Stamp stamp,boolean isReflectTimeStamp,TimeFrame timeFrame,GeneralDate ymd,WorkTimeCode workTimeCode) {
		//if not (False←普通打刻反映するか＝False　AND　勤怠打刻(実打刻付き)．実打刻に値が入っている)
		if(!(!isReflectTimeStamp && (timeActualStamp.getActualStamp().isPresent() 
										&& timeActualStamp.getActualStamp().get().getAfterRoundingTime()!=null 
										&& timeActualStamp.getActualStamp().get().getTimeDay().getTimeWithDay().isPresent() ))) {
			
			//日区分付き時刻を求める
			TimeWithDayAttr timeWithDayAttr = TimeWithDayAttr.convertToTimeWithDayAttr(ymd,
					stamp.getStampDateTime().toDate(), stamp.getStampDateTime().clockHourMinute().v());
			//時刻を丸める
			TimeWithDayAttr afterRouding = timeWithDayAttr;
			if(workTimeCode !=null ) {
				afterRouding = this.roundStamp(workTimeCode.v(), timeWithDayAttr,stamp.getType().getChangeClockArt());
			}
			//実打刻コピーする
			TimeActualStamp timeStampCopy = new TimeActualStamp(timeActualStamp.getActualStamp(), timeActualStamp.getStamp(), timeActualStamp.getNumberOfReflectionStamp(), timeActualStamp.getOvertimeDeclaration(), timeActualStamp.getTimeVacation());
			//打刻方法を打刻元情報に変換する
			WorkTimeInformation timeDay = new WorkTimeInformation(new ReasonTimeChange(TimeChangeMeans.REAL_STAMP, EngravingMethod.TIME_RECORD_ID_INPUT), timeWithDayAttr);
			WorkStamp workStamp = new WorkStamp(afterRouding, timeDay, stamp.getRefActualResults().getWorkLocationCD());
			timeStampCopy.setActualStamp(Optional.of(workStamp));
			//時間帯枠（Temporary）。理由←外出理由
			timeFrame.setGoOutReason(stamp.getType().getGoOutArt());
			//普通打刻反映＝True
			if(isReflectTimeStamp) {
				timeStampCopy.setStamp(timeStampCopy.getActualStamp());
			}
			timeActualStamp = timeStampCopy;
		}
		int number = timeActualStamp.getNumberOfReflectionStamp()!=null?timeActualStamp.getNumberOfReflectionStamp()+1:1;
		//打刻反映回数を＋１する
		timeFrame.setNumberOfReflections(number);
		timeActualStamp.setNumberOfReflectionStamp(number);
		//反映済み区分　←　true
		stamp.setReflectedCategory(true);
		
		return timeActualStamp;
	}
	
	/**
	 * 外出・休憩時刻を丸める (new_2020)
	 * @param workTimeCode
	 * @param workStamp
	 * @param attendanceAtr
	 * @param actualStampAtr
	 */
	public TimeWithDayAttr roundStamp(String workTimeCode, TimeWithDayAttr timeWithDayAttr, ChangeClockArt changeClockArt) {
		String companyId = AppContexts.user().companyId();
		// ドメインモデル「丸め設定」を取得する (Lấy 「丸め設定」)
		RoundingSet roudingTime = workTimeCode != null
				? this.getRoudingTime(companyId, workTimeCode,
						changeClockArt == ChangeClockArt.GO_OUT ? Superiority.GO_OUT : Superiority.TURN_BACK)
				: null;

		InstantRounding instantRounding = null;
		if (roudingTime != null) {
			instantRounding = new InstantRounding(roudingTime.getRoundingSet().getFontRearSection(),
					roudingTime.getRoundingSet().getRoundingTimeUnit());
		}
		// 勤怠打刻．時刻を丸める (Làm tròn 勤怠打刻．時刻 )
		if (instantRounding != null && timeWithDayAttr !=null) {
			// block thời gian theo e num ( 1,5,6,10,15,20,30,60)
			int blockTime = new Integer(instantRounding.getRoundingTimeUnit().description).intValue();
			// tổng thời gian tuyền vào
			int numberMinuteTimeOfDay = timeWithDayAttr.v().intValue();
			// thời gian dư sau khi chia dư cho block time
			int modTimeOfDay = numberMinuteTimeOfDay % blockTime;
			// thoi gian thay doi sau khi lam tron
			int timeChange = 0;
			// làm tròn lên hay xuống
			boolean isBefore = instantRounding.getFontRearSection() == FontRearSection.BEFORE;
			if (isBefore) {
				timeChange = (modTimeOfDay == 0) ? numberMinuteTimeOfDay : numberMinuteTimeOfDay - modTimeOfDay;
			} else {
				timeChange = (modTimeOfDay == 0) ? numberMinuteTimeOfDay
						: numberMinuteTimeOfDay - modTimeOfDay + blockTime;
			}
			return new TimeWithDayAttr(timeChange);
		} // end : nếu time khác giá trị default
		return timeWithDayAttr;
	}
	
	private RoundingSet getRoudingTime(String companyId, String workTimeCode, Superiority superiority) {
		Optional<WorkTimezoneCommonSet> workTimezoneCommonSet = GetCommonSet.workTimezoneCommonSet(
				requireService.createRequire(), companyId, workTimeCode);
		if (workTimezoneCommonSet.isPresent()) {
			WorkTimezoneStampSet stampSet = workTimezoneCommonSet.get().getStampSet();
			return stampSet.getRoundingSets().stream().filter(item -> item.getSection() == superiority).findFirst().isPresent() ?
					stampSet.getRoundingSets().stream().filter(item -> item.getSection() == superiority).findFirst().get() : null;
		}
		return null;
	}

}
