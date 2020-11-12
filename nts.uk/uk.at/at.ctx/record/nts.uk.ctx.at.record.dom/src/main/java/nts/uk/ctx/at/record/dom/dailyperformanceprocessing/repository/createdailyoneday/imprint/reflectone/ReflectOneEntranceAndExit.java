package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.imprint.reflectone;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.imprint.reflectondomain.ReflectOnDomain;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.reflectattdclock.ReflectAttendanceClock;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.reflectattdclock.ReflectStampOuput;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.EngravingMethod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.ReasonTimeChange;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkTimeInformation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 片方反映する (new_2020) for : 入退門反映する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.日別実績作成処理.作成処理.アルゴリズム.一日の日別実績の作成処理（New）.打刻反映.入退門・PC反映する.片方反映する.片方反映する
 * @author tutk
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ReflectOneEntranceAndExit {
	@Inject
	private ReflectAttendanceClock reflectAttendanceClock;
	
	@Inject
	private ReflectOnDomain reflectOnDomain;
	/**
	 * 
	 * @param workStamp
	 * @param stamp
	 * @param ymd  処理中の年月日
	 */
	public Optional<WorkStamp> reflect(Optional<WorkStamp> workStamp,Stamp stamp,GeneralDate ymd) {
		String cid = AppContexts.user().companyId();
		//データがあるかどうか確認する
		if(!workStamp.isPresent()) {
			//勤怠打刻を生成する
			WorkTimeInformation timeDayNew = new WorkTimeInformation(new ReasonTimeChange(TimeChangeMeans.REAL_STAMP, Optional.of(EngravingMethod.TIME_RECORD_ID_INPUT)), null);
			workStamp = Optional.of(new WorkStamp(timeDayNew,Optional.empty())); //丸め後の時刻 để tạm là 0
			//ドメインに反映する
			return Optional.of(reflectOnDomain.reflect(workStamp.get(), stamp, ymd));
		}else {
			ReasonTimeChange reasonTimeChangeNew = new ReasonTimeChange(TimeChangeMeans.REAL_STAMP,Optional.of(EngravingMethod.TIME_RECORD_ID_INPUT));
			//時刻を変更してもいいか判断する
			boolean check = reflectAttendanceClock.isCanChangeTime(cid, workStamp, reasonTimeChangeNew);
			if(check) {
				//ドメインに反映する
				return Optional.of(reflectOnDomain.reflect(workStamp.get(), stamp, ymd));
			}
		}
		return workStamp;
	}
}
