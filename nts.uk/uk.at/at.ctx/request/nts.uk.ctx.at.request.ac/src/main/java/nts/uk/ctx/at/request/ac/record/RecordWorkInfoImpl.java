package nts.uk.ctx.at.request.ac.record;

import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.pub.workinformation.RecordWorkInfoPub;
import nts.uk.ctx.at.record.pub.workinformation.RecordWorkInfoPubExport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.CommonTimeSheetImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.RecordWorkInfoAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.RecordWorkInfoImport;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class RecordWorkInfoImpl implements RecordWorkInfoAdapter {
	
	@Inject
	private RecordWorkInfoPub recordWorkInfoPub;
	
	@Override
	public RecordWorkInfoImport getRecordWorkInfo(String employeeId, GeneralDate ymd) {
		RecordWorkInfoPubExport recordWorkInfo = recordWorkInfoPub.getRecordWorkInfo(employeeId, ymd);
		return new RecordWorkInfoImport(
				recordWorkInfo.getWorkTypeCode(), 
				recordWorkInfo.getWorkTimeCode(), 
				recordWorkInfo.getAttendanceStampTimeFirst(), 
				recordWorkInfo.getLeaveStampTimeFirst(), 
				recordWorkInfo.getAttendanceStampTimeSecond(), 
				recordWorkInfo.getLeaveStampTimeSecond(), 
				recordWorkInfo.getLateTime1(), 
				recordWorkInfo.getLeaveEarlyTime1(), 
				recordWorkInfo.getLateTime2(), 
				recordWorkInfo.getLeaveEarlyTime2(), 
				recordWorkInfo.getChildCareTime(), 
				recordWorkInfo.getOutingTimePrivate(), 
				recordWorkInfo.getOutingTimeCombine(), 
				recordWorkInfo.getFlexTime(), 
				recordWorkInfo.getOvertimes().stream().map(x -> new CommonTimeSheetImport(x.getNo(), x.getTime(), x.getTranferTime())).collect(Collectors.toList()),
				recordWorkInfo.getHolidayWorks().stream().map(x -> new CommonTimeSheetImport(x.getNo(), x.getTime(), x.getTranferTime())).collect(Collectors.toList()), 
				recordWorkInfo.getMidnightTime());
	}
	
}
