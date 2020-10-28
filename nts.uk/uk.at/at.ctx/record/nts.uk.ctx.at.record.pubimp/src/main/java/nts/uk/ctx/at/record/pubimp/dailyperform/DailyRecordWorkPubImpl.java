package nts.uk.ctx.at.record.pubimp.dailyperform;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordDto;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordWorkFinder;
import nts.uk.ctx.at.record.pub.dailyperform.DailyRecordWorkExport;
import nts.uk.ctx.at.record.pub.dailyperform.DailyRecordWorkPub;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.AttendanceItemUtil;

@Stateless
public class DailyRecordWorkPubImpl implements DailyRecordWorkPub{
	@Inject
	private DailyRecordWorkFinder fullFinder;
	
	
	public DailyRecordWorkExport getByEmployee(String employeeId, GeneralDate baseDate, List<Integer> itemIds) {
		DailyRecordWorkExport result = new DailyRecordWorkExport();
		DailyRecordDto itemDtos = this.fullFinder.find(employeeId, baseDate);
		result.setItems(AttendanceItemUtil.toItemValues(itemDtos, itemIds));
		result.setEmployeeId(employeeId);
		result.setDate(baseDate);
		return result;
	}
}
