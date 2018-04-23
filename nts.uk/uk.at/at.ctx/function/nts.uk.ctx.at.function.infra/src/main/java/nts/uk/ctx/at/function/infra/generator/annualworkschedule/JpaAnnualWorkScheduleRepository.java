package nts.uk.ctx.at.function.infra.generator.annualworkschedule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.at.function.dom.annualworkschedule.export.AnnualWorkScheduleData;
import nts.uk.ctx.at.function.dom.annualworkschedule.export.AnnualWorkScheduleRepository;
import nts.uk.ctx.at.function.dom.annualworkschedule.export.EmployeeData;
import nts.uk.ctx.at.function.dom.annualworkschedule.export.ExportData;
import nts.uk.ctx.at.function.dom.annualworkschedule.export.ExportItem;
import nts.uk.ctx.at.function.dom.annualworkschedule.export.HeaderData;

@Stateless
public class JpaAnnualWorkScheduleRepository implements AnnualWorkScheduleRepository {

	/**
	 * TODO linh.nd
	 */
	@Override
	public ExportData getData() {
		ExportData data = new ExportData();
		HeaderData header = new HeaderData();
		header.setTitle("Hello World!");
		data.setHeader(header);
		List<EmployeeData> employees = new ArrayList<>();
		List<String> workplace1 = Arrays.asList("Workplace1");
		List<String> workplace2 = Arrays.asList("Workplace2");
		List<String> workplace3 = Arrays.asList("Workplace3");
		EmployeeData ed = new EmployeeData();
		EmployeeData ed1 = new EmployeeData();
		EmployeeData ed2 = new EmployeeData();
		EmployeeData ed3 = new EmployeeData();
		EmployeeData ed4 = new EmployeeData();
		ed.setEmployeeInfo(workplace1);
		ed2.setEmployeeInfo(workplace1);
		ed3.setEmployeeInfo(workplace1);
		ed4.setEmployeeInfo(workplace1);
		ed1.setEmployeeInfo(workplace1);
		employees.addAll(Arrays.asList(ed, ed1, ed2, ed3, ed4));
		EmployeeData ed5 = new EmployeeData();
		EmployeeData ed6 = new EmployeeData();
		EmployeeData ed7 = new EmployeeData();
		EmployeeData ed8 = new EmployeeData();
		ed5.setEmployeeInfo(workplace2);
		ed6.setEmployeeInfo(workplace2);
		ed7.setEmployeeInfo(workplace2);
		ed8.setEmployeeInfo(workplace2);
		employees.addAll(Arrays.asList(ed5, ed6, ed7, ed8));
		EmployeeData ed9 = new EmployeeData();
		EmployeeData ed10 = new EmployeeData();
		EmployeeData ed11 = new EmployeeData();
		ed9.setEmployeeInfo(workplace3);
		ed10.setEmployeeInfo(workplace3);
		ed11.setEmployeeInfo(workplace3);
		employees.addAll(Arrays.asList(ed9, ed10, ed11));

		data.setEmployees(employees);
		return data;
	}

	/**
	 * TODO get from DB
	 * B4_2 出力項目
	 */
	public List<ExportItem> geExportItems() {
		List<ExportItem> items = new ArrayList<>();
		items.add(new ExportItem("36協定時間"));
		items.add(new ExportItem("早出残業"));
		items.add(new ExportItem("残業"));
		items.add(new ExportItem("遅刻回数"));
		items.add(new ExportItem("年休日数"));
		return items;
	}
}
