package nts.uk.ctx.at.function.ac.dailyperform;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.dailyperform.DailyRecordWorkAdapter;
import nts.uk.ctx.at.function.dom.adapter.dailyperform.DailyRecordWorkImport;
import nts.uk.ctx.at.record.pub.dailyperform.DailyRecordWorkExport;
import nts.uk.ctx.at.record.pub.dailyperform.DailyRecordWorkPub;

@Stateless
public class DailyRecordWorkAcAdapter implements DailyRecordWorkAdapter{
	
	@Inject
	private DailyRecordWorkPub pub; 
	/* param
	 * 社員ID　　employeeId
	 * 基準日　baseDate
	 * 勤怠項目ID baseDate
	 * 
	 * return 勤怠項目ID , 勤怠項目価値
	 */
	@Override
	public DailyRecordWorkImport getByEmployeeList(String employeeId, GeneralDate baseDate, List<Integer> itemIds) {
		DailyRecordWorkImport dailyRecordWorkImport = new DailyRecordWorkImport();
		DailyRecordWorkExport export = pub.getByEmployee(employeeId, baseDate, itemIds);
		if(export==null) throw new  RuntimeException("List<AttendanceID, AttendanceName> not found!");
		
		dailyRecordWorkImport.setEmployeeId(employeeId);
		dailyRecordWorkImport.setDate(baseDate);
		dailyRecordWorkImport.setItems(export.getItems());
		dailyRecordWorkImport.setFormatter(export.getFormatter());
		return dailyRecordWorkImport;
	}

}
