package nts.uk.ctx.at.function.dom.annualworkschedule.export;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ExportDataSource {
	List<ExportItem> exportItems;
	ExportData data;
}