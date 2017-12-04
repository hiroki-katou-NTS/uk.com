package nts.uk.ctx.at.request.ac.record;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.pub.workinformation.RecordWorkInfoPub;
import nts.uk.ctx.at.record.pub.workinformation.RecordWorkInfoPubExport;
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
		RecordWorkInfoPubExport recordWorkInfoPubExport = recordWorkInfoPub.getRecordWorkInfo(employeeId, ymd);
		return new RecordWorkInfoImport(
				recordWorkInfoPubExport.getWorkTypeCode(), 
				recordWorkInfoPubExport.getWorkTimeCode(), 
				recordWorkInfoPubExport.getAttendanceStampTimeFirst(), 
				recordWorkInfoPubExport.getLeaveStampTimeFirst(), 
				recordWorkInfoPubExport.getAttendanceStampTimeSecond(), 
				recordWorkInfoPubExport.getLeaveStampTimeSecond(), 
				recordWorkInfoPubExport.getTime1(), 
				recordWorkInfoPubExport.getTime2(), 
				recordWorkInfoPubExport.getTime3(), 
				recordWorkInfoPubExport.getTime4(), 
				recordWorkInfoPubExport.getTime5());
	}
	
}
