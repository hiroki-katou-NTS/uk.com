package nts.uk.ctx.at.function.dom.annualworkschedule.export;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.annualworkschedule.enums.PageBreakIndicator;

@Getter
@Setter
public class ExportData {
	HeaderData header;
	List<ExportItem> exportItems;
	List<EmployeeData> employees;
	PageBreakIndicator pageBreak;
}