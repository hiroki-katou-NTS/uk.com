package nts.uk.ctx.at.record.app.find.monthlyclosureupdate.exportcsv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.ctx.at.record.app.find.monthlyclosureupdate.MonthlyClosureErrorInforDto;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdateErrorAlarmAtr;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.csv.CSVFileData;
import nts.uk.shr.infra.file.csv.CSVReportGenerator;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class MonthlyClosureUpdateLogExportService extends ExportService<MontlyClosureUpdateExportDto> {

	@Inject
	private CSVReportGenerator generator;

	private static final List<String> LST_NAME_ID_HEADER = Arrays.asList("KMW006_13", "KMW006_16", "KMW006_17",
			"KMW006_39", "KMW006_40");

	private static final String PGID = "KMW006";

	private static final String FILE_EXTENSION = ".csv";

	@Override
	protected void handle(ExportServiceContext<MontlyClosureUpdateExportDto> context) {
		List<MonthlyClosureErrorInforDto> data = context.getQuery().getData();
		String executionDt = context.getQuery().getExecutionDt().replaceAll("[/:\\s]", "");
		List<String> header = this.getTextHeader();
		List<Map<String, Object>> dataSource = new ArrayList<>();
		for (MonthlyClosureErrorInforDto d : data) {
			Map<String, Object> row = new HashMap<>();
			row.put(header.get(0), data.indexOf(d) + 1);
			row.put(header.get(1), d.getEmployeeCode());
			row.put(header.get(2), d.getEmployeeName());
			row.put(header.get(3),
					d.getAtr() == MonthlyClosureUpdateErrorAlarmAtr.ALARM.value
							? TextResource.localize("Enum_MonthlyClosureUpdate_Alarm")
							: TextResource.localize("Enum_MonthlyClosureUpdate_Error"));
			row.put(header.get(4), d.getErrorMessage());
			dataSource.add(row);
		}
		CSVFileData fileData = new CSVFileData(
				PGID + "_" + executionDt + "_" + AppContexts.user().companyCode() + FILE_EXTENSION, header, dataSource);
		generator.generate(context.getGeneratorContext(), fileData);
	}

	private List<String> getTextHeader() {
		List<String> lstHeader = new ArrayList<>();
		for (String nameId : LST_NAME_ID_HEADER) {
			lstHeader.add(TextResource.localize(nameId));
		}
		return lstHeader;
	}

}
