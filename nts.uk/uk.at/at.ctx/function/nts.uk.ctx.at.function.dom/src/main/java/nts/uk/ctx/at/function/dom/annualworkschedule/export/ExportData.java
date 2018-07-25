package nts.uk.ctx.at.function.dom.annualworkschedule.export;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.annualworkschedule.enums.PageBreakIndicator;

@Getter
@Setter
public class ExportData {
	HeaderData header;
	List<ExportItem> exportItems;
	List<String> employeeIds;
	/**
	 * employeeId, EmployeeData
	 */
	Map<String, EmployeeData> employees;
	/**
	 * 36協定時間を超過した月数を出力する
	 */
	private boolean outNumExceedTime36Agr;
	PageBreakIndicator pageBreak;
	/**
	 * 対象の社員IDをエラーリスト
	 */
	List<String> employeeIdsError;
	List<String> employeeError;

	public boolean hasDataItemOutput() {
		if (this.employeeIds.isEmpty() || this.employees == null || this.employees.isEmpty()) {
			return false;
		}
		return true;
	}

	public void storeEmployeeError() {
		this.employeeIdsError = new ArrayList<>();
		this.employeeError = new ArrayList<>();
		for (Map.Entry<String, EmployeeData> emp : this.employees.entrySet()) {
			if (!emp.getValue().hasDataItem()) {
				this.employeeIdsError.add(emp.getKey());
				EmployeeInfo employeeInfo = emp.getValue().getEmployeeInfo();
				this.employeeError.add(employeeInfo.getEmployeeCode() + "　 " + employeeInfo.getEmployeeName());
			}
		}
	}
}