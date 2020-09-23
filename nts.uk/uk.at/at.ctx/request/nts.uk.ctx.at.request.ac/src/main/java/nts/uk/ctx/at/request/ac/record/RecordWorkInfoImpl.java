package nts.uk.ctx.at.request.ac.record;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.pub.workinformation.RecordWorkInfoPub;
import nts.uk.ctx.at.record.pub.workinformation.RecordWorkInfoPubExport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.RecordWorkInfoAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.RecordWorkInfoImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.RecordWorkInfoImport_Old;
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
	public RecordWorkInfoImport_Old getRecordWorkInfo(String employeeId, GeneralDate ymd) {
//		RecordWorkInfoPubExport_Old recordWorkInfo = recordWorkInfoPub.getRecordWorkInfo(employeeId, ymd);
//		return new RecordWorkInfoImport(
//				recordWorkInfo.getWorkTypeCode(), 
//				recordWorkInfo.getWorkTimeCode(), 
//				recordWorkInfo.getAttendanceStampTimeFirst(), 
//				recordWorkInfo.getLeaveStampTimeFirst(), 
//				recordWorkInfo.getAttendanceStampTimeSecond(), 
//				recordWorkInfo.getLeaveStampTimeSecond(), 
//				recordWorkInfo.getLateTime1(), 
//				recordWorkInfo.getLeaveEarlyTime1(), 
//				recordWorkInfo.getLateTime2(), 
//				recordWorkInfo.getLeaveEarlyTime2(), 
//				recordWorkInfo.getChildCareTime(), 
//				recordWorkInfo.getOutingTimePrivate(), 
//				recordWorkInfo.getOutingTimeCombine(), 
//				recordWorkInfo.getFlexTime(), 
//				recordWorkInfo.getOvertimes().stream().map(x -> new CommonTimeSheetImport(x.getNo(), x.getTime(), x.getTranferTime())).collect(Collectors.toList()),
//				recordWorkInfo.getHolidayWorks().stream().map(x -> new CommonTimeSheetImport(x.getNo(), x.getTime(), x.getTranferTime())).collect(Collectors.toList()), 
//				recordWorkInfo.getMidnightTime());
		return null;
	}
	
	@Override
	public RecordWorkInfoImport getRecordWorkInfoRefactor(String employeeId, GeneralDate ymd) {
		RecordWorkInfoPubExport_New recordWorkInfoPubExport = recordWorkInfoPub.getRecordWorkInfo_New(employeeId, ymd);
		return fromExport(recordWorkInfoPubExport);
	}
	
	private RecordWorkInfoImport fromExport(RecordWorkInfoPubExport_New recordWorkInfoPubExport) {
		return new RecordWorkInfoImport(
				recordWorkInfoPubExport.getEmployeeId(), 
				recordWorkInfoPubExport.getDate(), 
				recordWorkInfoPubExport.getWorkTypeCode(), 
				recordWorkInfoPubExport.getWorkTimeCode(), 
				recordWorkInfoPubExport.getStartTime1(), 
				recordWorkInfoPubExport.getEndTime1(), 
				recordWorkInfoPubExport.getStartTime2(), 
				recordWorkInfoPubExport.getEndTime2(), 
				recordWorkInfoPubExport.getLateTime1(), 
				recordWorkInfoPubExport.getEarlyLeaveTime1(), 
				recordWorkInfoPubExport.getLateTime2(), 
				recordWorkInfoPubExport.getEarlyLeaveTime2(), 
				recordWorkInfoPubExport.getOutTime1(), 
				recordWorkInfoPubExport.getOutTime2(), 
				recordWorkInfoPubExport.getTotalTime(), 
				recordWorkInfoPubExport.getCalculateFlex(), 
				recordWorkInfoPubExport.getOverTimeLst(), 
				recordWorkInfoPubExport.getCalculateTransferOverTimeLst(), 
				recordWorkInfoPubExport.getCalculateHolidayLst(), 
				recordWorkInfoPubExport.getCalculateTransferLst(), 
				recordWorkInfoPubExport.getScheduledAttendence1(), 
				recordWorkInfoPubExport.getScheduledDeparture1(), 
				recordWorkInfoPubExport.getScheduledAttendence2(), 
				recordWorkInfoPubExport.getScheduledDeparture2(), 
				recordWorkInfoPubExport.getTimeLeavingWorks(), 
				recordWorkInfoPubExport.getOutHoursLst(), 
				recordWorkInfoPubExport.getShortWorkingTimeSheets(), 
				recordWorkInfoPubExport.getBreakTimeSheets(), 
				recordWorkInfoPubExport.getOverTimeMidnight(), 
				recordWorkInfoPubExport.getMidnightOnHoliday(), 
				recordWorkInfoPubExport.getOutOfMidnight(), 
				recordWorkInfoPubExport.getMidnightPublicHoliday(),
				recordWorkInfoPubExport.getChildCareShortWorkingTimeList(),
				recordWorkInfoPubExport.getCareShortWorkingTimeList());
	}
	
}
