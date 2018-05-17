package nts.uk.ctx.at.function.dom.annualworkschedule.export;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExportData {
	HeaderData header;
	List<ExportItem> exportItems;
	List<EmployeeData> employees;
}