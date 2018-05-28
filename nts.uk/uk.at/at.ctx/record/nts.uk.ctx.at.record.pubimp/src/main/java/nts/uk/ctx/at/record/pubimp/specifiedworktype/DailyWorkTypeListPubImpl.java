package nts.uk.ctx.at.record.pubimp.specifiedworktype;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workinformation.service.specifiedworktype.DailyWorkTypeList;
import nts.uk.ctx.at.record.dom.workinformation.service.specifiedworktype.SpecifiedWorkTypeService;
import nts.uk.ctx.at.record.pub.specifiedworktype.DailyWorkTypeListExport;
import nts.uk.ctx.at.record.pub.specifiedworktype.DailyWorkTypeListPub;
import nts.uk.ctx.at.record.pub.specifiedworktype.NumberOfWorkTypeUsedExport;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

@Stateless
public class DailyWorkTypeListPubImpl implements DailyWorkTypeListPub {

	@Inject
	private SpecifiedWorkTypeService specifiedWorkTypeService;

	@Override
	public DailyWorkTypeListExport getDailyWorkTypeUsed(String employeeId, List<String> workTypeList,
			GeneralDate startDate, GeneralDate endDate) {

		List<WorkTypeCode> list = workTypeList.stream().map(item -> new WorkTypeCode(item))
				.collect(Collectors.toList());

		// get data from common process
		DailyWorkTypeList dailyWorkTypeList = this.specifiedWorkTypeService.getNumberOfSpecifiedWorkType(employeeId,
				list, startDate, endDate);

		// convert data from domain -> export data
		DailyWorkTypeListExport dailyWorkTypeListExport = new DailyWorkTypeListExport();
		dailyWorkTypeListExport.setEmployeeId(dailyWorkTypeList.getEmployeeId());

		List<NumberOfWorkTypeUsedExport> numberOfWorkTypeUsedExports = dailyWorkTypeList.getNumberOfWorkTypeUsedList()
				.stream().map(item -> {
					NumberOfWorkTypeUsedExport export = new NumberOfWorkTypeUsedExport();
					export.setWorkTypeCode(item.getWorkTypeCode().v());
					export.setAttendanceDaysMonth(item.getAttendanceDaysMonth().v());
					return export;
				}).collect(Collectors.toList());
		dailyWorkTypeListExport.setNumberOfWorkTypeUsedExports(numberOfWorkTypeUsedExports);

		return dailyWorkTypeListExport;
	}

}
