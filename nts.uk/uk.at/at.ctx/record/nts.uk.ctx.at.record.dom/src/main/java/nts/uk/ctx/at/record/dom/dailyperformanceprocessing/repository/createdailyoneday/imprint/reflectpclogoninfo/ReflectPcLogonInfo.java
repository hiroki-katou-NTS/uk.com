package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.imprint.reflectpclogoninfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.imprint.reflectframe.ReflectFrameEntranceAndExit;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.imprint.reflectondomain.ReflectionInformation;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.EngravingMethod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.ReasonTimeChange;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkTimeInformation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.entranceandexit.LogOnInfo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.entranceandexit.PCLogOnInfoOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.entranceandexit.PCLogOnNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.StampReflectRangeOutput;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * PCログオン情報反映する
 * 
 * @author tutk
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ReflectPcLogonInfo {
	@Inject
	private ReflectFrameEntranceAndExit reflectFrameEntranceAndExit;
	
	public void reflect(Stamp stamp, StampReflectRangeOutput stampReflectRangeOutput,
			IntegrationOfDaily integrationOfDaily) {
		// 日別実績のPCログオン情報のログオン情報を・反映情報（Temporary）に変換する
		// 日別実績の入退門の入退門を・反映情報（Temporary）に変換する
		List<ReflectionInformation> listReflectionInformation = new ArrayList<>();
		Optional<PCLogOnInfoOfDailyAttd> pCLogOnInfoOfDailyAttd = integrationOfDaily.getPcLogOnInfo();
		if (pCLogOnInfoOfDailyAttd.isPresent()) {
			listReflectionInformation = pCLogOnInfoOfDailyAttd.get().getLogOnInfo().stream().map(
					c -> new ReflectionInformation(
							c.getWorkNo().v(), 
							convertToTimeWithDayAttr(c.getLogOn()),
							convertToTimeWithDayAttr(c.getLogOff())
							))
					.collect(Collectors.toList());
		}
		// 枠反映する
		listReflectionInformation = reflectFrameEntranceAndExit.reflect(listReflectionInformation, stamp, stampReflectRangeOutput, integrationOfDaily);
		
		// 反映済みの反映情報（Temporary）を日別実績のPCログオン情報にコピーする
		List<LogOnInfo> logOnInfo = new ArrayList<>();
		for(ReflectionInformation  reflectionInfor : listReflectionInformation) {
			logOnInfo.add(new LogOnInfo(reflectionInfor.getFrameNo(),
					reflectionInfor.getEnd().isPresent()?reflectionInfor.getEnd().get().getTimeDay().getTimeWithDay(): Optional.empty(),
					reflectionInfor.getStart().isPresent()? reflectionInfor.getStart().get().getTimeDay().getTimeWithDay(): Optional.empty()));
		}
		integrationOfDaily.setPcLogOnInfo(Optional.of(new PCLogOnInfoOfDailyAttd(logOnInfo)));
	}

	private Optional<WorkStamp> convertToTimeWithDayAttr(Optional<TimeWithDayAttr> opt) {
		if (!opt.isPresent()) {
			return Optional.empty();
		}
		WorkTimeInformation timeDay = new WorkTimeInformation(
				new ReasonTimeChange(TimeChangeMeans.REAL_STAMP, EngravingMethod.TIME_RECORD_ID_INPUT), opt.get());
		WorkStamp data = new WorkStamp(opt.get(), timeDay, Optional.empty());
		return Optional.of(data);
	}

}
