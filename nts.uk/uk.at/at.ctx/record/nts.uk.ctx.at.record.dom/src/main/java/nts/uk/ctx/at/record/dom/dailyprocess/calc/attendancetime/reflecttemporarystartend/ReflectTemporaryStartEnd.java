package nts.uk.ctx.at.record.dom.dailyprocess.calc.attendancetime.reflecttemporarystartend;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.ProcessTimeOutput;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.breakouting.reflectgoingoutandreturn.TimeFrame;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.breakouting.reflectgoingoutandreturn.reflecttimeofday.ReflectTimeOfDay;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.reflectattdclock.AttendanceAtr;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.reflectattdclock.ReflectStampOuput;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.temporarywork.ManageWorkTemporary;
import nts.uk.ctx.at.record.dom.workrecord.temporarywork.ManageWorkTemporaryRepository;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TemporaryTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.StampReflectRangeOutput;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.TimeZoneOutput;
import nts.uk.ctx.at.shared.dom.workrule.workuse.TemporaryWorkUseManage;
import nts.uk.ctx.at.shared.dom.workrule.workuse.TemporaryWorkUseManageRepository;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkNo;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * ????????????????????????????????????
 * @author tutk
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ReflectTemporaryStartEnd {
	@Inject
	private TemporaryWorkUseManageRepository tempWorkRepo;
	
	@Inject
	private ManageWorkTemporaryRepository manageWorkTemporaryRepository;
	
	@Inject
	private ReflectTimeOfDay reflectTimeOfDay;
	
	public ReflectStampOuput reflect(String companyId, Stamp stamp,StampReflectRangeOutput stampReflectRangeOutput,IntegrationOfDaily integrationOfDaily) {
		ReflectStampOuput reflectStampOuput = ReflectStampOuput.NOT_REFLECT;
		// ??????????????????????????????????????????????????????????????????
		Optional<TemporaryWorkUseManage> optTempWorkUse = tempWorkRepo.findByCid(companyId);
		if(!optTempWorkUse.isPresent() || optTempWorkUse.get().getUseClassification() == UseAtr.NOTUSE) {
			return reflectStampOuput;
		}
		//?????????????????????????????????????????????????????????????????????????????????
		TimeWithDayAttr timeWithDayAttr = TimeWithDayAttr.convertToTimeWithDayAttr(integrationOfDaily.getYmd(),
				stamp.getStampDateTime().toDate(), stamp.getStampDateTime().clockHourMinute().v());
		//????????????????????????????????????
		ProcessTimeOutput processTimeOutput = new ProcessTimeOutput();
		processTimeOutput.setTimeOfDay(timeWithDayAttr);
		reflectStampOuput = confirmTimeStampIsReflect(stamp, stampReflectRangeOutput, processTimeOutput);
		if(reflectStampOuput == ReflectStampOuput.NOT_REFLECT) {
			return reflectStampOuput;	
		}
		//????????????????????????????????????????????????????????????
		Optional<ManageWorkTemporary> manageWorkTemporary = manageWorkTemporaryRepository.findByCID(companyId);
		if(!manageWorkTemporary.isPresent()) {
			return reflectStampOuput;
		}
		//??????????????????????????????Temporary??????????????????
		Optional<TemporaryTimeOfDailyAttd> tempTime = integrationOfDaily.getTempTime();
		List<TimeFrame> listTimeFrame = new ArrayList<>();
		if (tempTime.isPresent()) {
			listTimeFrame = tempTime.get().getTimeLeavingWorks().stream()
					.map(c -> new TimeFrame(0, // ???????????? ????? t???m l?? 0
							c.getWorkNo().v(), c.getAttendanceStamp(), c.getLeaveStamp(), null))
					.collect(Collectors.toList());
		}
		WorkInformation recordWorkInformation = integrationOfDaily.getWorkInformation().getRecordInfo();
		WorkTimeCode workTimeCode = recordWorkInformation.getWorkTimeCode();
		//?????????????????????
		reflectTimeOfDay.reflectTimeOfDay(integrationOfDaily.getEmployeeId(), integrationOfDaily.getYmd(), stamp,
				manageWorkTemporary.get().getMaxUsage().v(), listTimeFrame, AttendanceAtr.GO_OUT,workTimeCode);
		//???????????????????????????Temporary??????????????????????????????????????????????????????????????????
		List<TimeLeavingWork> timeLeavingWork = new ArrayList<>();
		for(TimeFrame tf :listTimeFrame) {
			TimeLeavingWork timeSheet = new TimeLeavingWork(new WorkNo(tf.getFrameNo()), tf.getStart(), tf.getEnd(), false, false);
			timeLeavingWork.add(timeSheet);
		}
		integrationOfDaily.setTempTime(Optional.of(new TemporaryTimeOfDailyAttd(timeLeavingWork)));
		
		return ReflectStampOuput.REFLECT;
	}
	
	/**
	 * ???????????????????????????????????? (new_2020) use ??????
	 * @param stamp
	 * @param s
	 * @param processTimeOutput
	 * @return ReflectStampOuput 
	 */
	private ReflectStampOuput confirmTimeStampIsReflect(Stamp stamp,StampReflectRangeOutput s,ProcessTimeOutput processTimeOutput) {
		TimeZoneOutput goOut = s.getTemporary();
		if (goOut.getStart().v().intValue() <= processTimeOutput.getTimeOfDay().v().intValue()
				&& goOut.getEnd().v().intValue() >= processTimeOutput.getTimeOfDay().v().intValue()) {
			return ReflectStampOuput.REFLECT;
		}
		return ReflectStampOuput.NOT_REFLECT;
	}

}
