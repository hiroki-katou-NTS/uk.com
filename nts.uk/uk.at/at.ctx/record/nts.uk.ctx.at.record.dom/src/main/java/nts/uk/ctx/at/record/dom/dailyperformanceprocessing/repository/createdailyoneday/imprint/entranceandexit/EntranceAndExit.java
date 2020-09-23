package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.imprint.entranceandexit;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.imprint.reflectframe.ReflectFrameEntranceAndExit;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.imprint.reflectondomain.ReflectionInformation;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.reflectattdclock.ReflectStampOuput;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.entranceandexit.AttendanceLeavingGate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.entranceandexit.AttendanceLeavingGateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.entranceandexit.LogOnInfo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.entranceandexit.PCLogOnInfoOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.StampReflectRangeOutput;

/**
 * 入退門反映する
 * @author tutk
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
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
		listReflectionInformation = reflectFrameEntranceAndExit.reflect(listReflectionInformation, stamp, stampReflectRangeOutput, integrationOfDaily);
		
		//反映済みの反映情報（Temporary）を日別実績の入退門にコピーする
		List<AttendanceLeavingGate> attendanceLeavingGates = new ArrayList<>();
		for(ReflectionInformation  reflectionInfor : listReflectionInformation) {
			attendanceLeavingGates.add(new AttendanceLeavingGate(new WorkNo(reflectionInfor.getFrameNo()),
					reflectionInfor.getStart().isPresent()?reflectionInfor.getStart().get():null,
					reflectionInfor.getEnd().isPresent()?reflectionInfor.getEnd().get():null));
		}
		integrationOfDaily.setAttendanceLeavingGate(Optional.of(new AttendanceLeavingGateOfDailyAttd(attendanceLeavingGates)));
		
		return reflectStampOuput;
		
	}
}
