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
	@Override
	public DailyRecordWorkImport getByEmployeeList(String employeeId, GeneralDate baseDate, List<Integer> itemIds) {
		DailyRecordWorkImport dailyRecordWorkImport = new DailyRecordWorkImport();
		DailyRecordWorkExport export = pub.getByEmployee(employeeId, baseDate, itemIds);
		dailyRecordWorkImport.setEmployeeId(employeeId);
		dailyRecordWorkImport.setDate(baseDate);
		dailyRecordWorkImport.setItems(export.getItems());
		dailyRecordWorkImport.setFormatter(export.getFormatter());
		return dailyRecordWorkImport;
	}

}
