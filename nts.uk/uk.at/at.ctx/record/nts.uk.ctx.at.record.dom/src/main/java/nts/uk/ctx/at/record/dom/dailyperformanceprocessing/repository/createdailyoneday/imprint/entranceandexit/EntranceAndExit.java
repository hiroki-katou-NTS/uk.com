package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.imprint.entranceandexit;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.imprint.reflectframe.ReflectFrameEntranceAndExit;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.imprint.reflectondomain.ReflectionInformation;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.reflectattdclock.ReflectStampOuput;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.entranceandexit.AttendanceLeavingGate;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.entranceandexit.AttendanceLeavingGateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.dailyattdcal.workinfo.timereflectfromworkinfo.StampReflectRangeOutput;

/**
 * 入退門反映する
 * @author tutk
 *
 */
@Stateless
public class EntranceAndExit {
	@Inject
	private ReflectFrameEntranceAndExit reflectFrameEntranceAndExit;
	
	public ReflectStampOuput entranceAndExit(Stamp stamp,StampReflectRangeOutput stampReflectRangeOutput,IntegrationOfDaily integrationOfDaily) {
		ReflectStampOuput reflectStampOuput = ReflectStampOuput.NOT_REFLECT;
		//日別実績の入退門の入退門を・反映情報（Temporary）に変換する
		List<ReflectionInformation> listReflectionInformation = new ArrayList<>();
		Optional<AttendanceLeavingGateOfDailyAttd> attendanceLeavingGate = integrationOfDaily.getAttendanceLeavingGate();
		if (attendanceLeavingGate.isPresent()) {
			listReflectionInformation = attendanceLeavingGate.get().getAttendanceLeavingGates().stream()
					.map(c -> new ReflectionInformation(c.getWorkNo().v(), c.getAttendance(), c.getLeaving()))
					.collect(Collectors.toList());
		}
		//枠反映する
		reflectStampOuput = reflectFrameEntranceAndExit.reflect(listReflectionInformation, stamp, stampReflectRangeOutput, integrationOfDaily);
		
		//反映済みの反映情報（Temporary）を日別実績の入退門にコピーする
		if (attendanceLeavingGate.isPresent()) {
			for(AttendanceLeavingGate attdLeavingGate :attendanceLeavingGate.get().getAttendanceLeavingGates()) {
				for(ReflectionInformation ri :listReflectionInformation) {
					if(attdLeavingGate.getWorkNo().v().intValue() == ri.getFrameNo()) {
						attdLeavingGate.setAttendance(ri.getStart());
						attdLeavingGate.setLeaving(ri.getEnd());
						break;
					}
				}
			}
		}
		return reflectStampOuput;
		
	}
}
