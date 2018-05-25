package nts.uk.ctx.at.function.dom.annualworkschedule.export;

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
}