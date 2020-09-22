package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.breakouting.reflectgoingoutandreturn;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.ProcessTimeOutput;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.breakouting.reflectgoingoutandreturn.reflecttimeofday.ReflectTimeOfDay;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.reflectattdclock.AttendanceAtr;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.reflectattdclock.ReflectStampOuput;
import nts.uk.ctx.at.record.dom.workrecord.goout.OutManage;
import nts.uk.ctx.at.record.dom.workrecord.goout.OutManageRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.StampReflectRangeOutput;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.TimeZoneOutput;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 外出・戻りを反映する (new_2020)
 * @author tutk
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ReflectGoingOutAndReturn {
	@Inject
	private OutManageRepository outManageRepository; 
	@Inject
	private ReflectTimeOfDay reflectTimeOfDay;
	
	public ReflectStampOuput reflect(Stamp stamp,StampReflectRangeOutput s,IntegrationOfDaily integrationOfDaily) {
		ReflectStampOuput reflectStampOuput = ReflectStampOuput.NOT_REFLECT;
		String companyID = AppContexts.user().companyId();
		//処理中打刻の時刻を処理中年月日に対応する時刻に変換する 
		TimeWithDayAttr timeWithDayAttr = TimeWithDayAttr.convertToTimeWithDayAttr(integrationOfDaily.getYmd(),
				stamp.getStampDateTime().toDate(), stamp.getStampDateTime().clockHourMinute().v());
		ProcessTimeOutput processTimeOutput = new ProcessTimeOutput();
		processTimeOutput.setTimeOfDay(timeWithDayAttr);
		//打刻を反映するか確認する 
		reflectStampOuput = confirmTimeStampIsReflect(stamp, s, processTimeOutput);
		if(reflectStampOuput == ReflectStampOuput.NOT_REFLECT) {
			return reflectStampOuput;
		}
		Optional<OutingTimeOfDailyAttd> outingTime = integrationOfDaily.getOutingTime();
		//時間帯から時間帯枠（Temporary）に変換する
		List<TimeFrame> listTimeFrame = new ArrayList<>();
		if (outingTime.isPresent()) {
			listTimeFrame = outingTime.get().getOutingTimeSheets().stream()
					.map(c -> new TimeFrame(0, // 反映回数 để tạm là 0
							c.getOutingFrameNo().v(), c.getGoOut(), c.getComeBack(), c.getReasonForGoOut()))
					.collect(Collectors.toList());
		}
		//外出管理を取得する
		Optional<OutManage> outManage =  outManageRepository.findByID(companyID);
		if(!outManage.isPresent()) {
			return reflectStampOuput;
		}
		WorkInformation recordWorkInformation = integrationOfDaily.getWorkInformation().getRecordInfo();
		WorkTimeCode workTimeCode = recordWorkInformation.getWorkTimeCode();
		//時間帯反映する
		reflectTimeOfDay.reflectTimeOfDay(integrationOfDaily.getEmployeeId(), integrationOfDaily.getYmd(), stamp,
				outManage.get().getMaxUsage().v(), listTimeFrame, AttendanceAtr.GO_OUT,workTimeCode);
		
		//反映済み時間帯枠（Temporary）を日別実績の外出時間帯の時間帯に上書きする
		List<OutingTimeSheet> outingTimeSheets = new ArrayList<>();
		for(TimeFrame tf :listTimeFrame) {
			OutingTimeSheet timeSheet = new OutingTimeSheet(new OutingFrameNo(tf.getFrameNo()),
					tf.getStart(), null, null, tf.getGoOutReason().isPresent() ? tf.getGoOutReason().get()  : null, tf.getEnd());
			outingTimeSheets.add(timeSheet);
		}
		integrationOfDaily.setOutingTime(Optional.ofNullable(new OutingTimeOfDailyAttd(outingTimeSheets)));		
		return ReflectStampOuput.REFLECT;
		
	}
	
	/**
	 * 打刻を反映するか確認する (new_2020) use 外出
	 * @param stamp
	 * @param s
	 * @param processTimeOutput
	 * @return ReflectStampOuput 
	 */
	private ReflectStampOuput confirmTimeStampIsReflect(Stamp stamp,StampReflectRangeOutput s,ProcessTimeOutput processTimeOutput) {
		TimeZoneOutput goOut = s.getGoOut();
		if (goOut.getStart().v().intValue() <= processTimeOutput.getTimeOfDay().v().intValue()
				&& goOut.getEnd().v().intValue() >= processTimeOutput.getTimeOfDay().v().intValue()) {
			return ReflectStampOuput.REFLECT;
		}
		return ReflectStampOuput.NOT_REFLECT;
	}
}
