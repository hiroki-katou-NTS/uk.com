package nts.uk.file.at.app.export.statement.stamp;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;

@Stateless
public class OutputListOfStampExportService extends ExportService<OutputConditionListOfStampQuery> {

	@Inject
	private OutputConditionListOfStampGenerator generator;

	@Override
	protected void handle(ExportServiceContext<OutputConditionListOfStampQuery> context) {
		// OutputConditionListOfStampQuery query = context.getQuery();
		OutputConditionListOfStampQuery query = new OutputConditionListOfStampQuery();
		StampHeader header = new StampHeader();
		header.setCompanyName("company1");
		header.setDatePeriodHead("20/12/2020");
		header.setTitle("title");
		query.setHeader(header);
		List<EmployeeInfor> employeeList = new ArrayList<>();
		for (Integer j = 0; j < 2; j++) {
			List<StampList> stampLists = new ArrayList<>();
			for (Integer i = 0; i < 40; i++) {
				StampList stampList = new StampList();
				stampList.setClassification(i.toString());
				stampList.setDate(i.toString());
				stampList.setInsLocation(i.toString());
				stampList.setLocationInfor(i.toString());
				stampList.setMean(i.toString());
				stampList.setMethod(i.toString());
				stampList.setMean(i.toString());
				stampList.setNightTime(i.toString());
				stampLists.add(stampList);
			}
			EmployeeInfor employeeInfor = new EmployeeInfor();
			employeeInfor.setWorkplace("Workplace");
			employeeInfor.setCardNo(j.toString());
			employeeInfor.setEmployee(j.toString());
			employeeInfor.setStampList(stampLists);
			employeeList.add(employeeInfor);
		}
		query.setEmployeeList(employeeList);
		this.generator.generate(context.getGeneratorContext(), query);

	}
}
