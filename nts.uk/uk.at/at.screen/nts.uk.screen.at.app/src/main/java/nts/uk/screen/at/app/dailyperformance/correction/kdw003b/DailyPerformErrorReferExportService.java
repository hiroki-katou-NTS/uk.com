package nts.uk.screen.at.app.dailyperformance.correction.kdw003b;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.csv.CSVFileData;
import nts.uk.shr.infra.file.csv.CSVReportGenerator;

/**
 * 
 * @author sonnh1
 *
 */
@Stateless
public class DailyPerformErrorReferExportService extends ExportService<List<DailyPerformErrorReferExportDto>> {

	@Inject
	private CSVReportGenerator generator;

	private static final List<String> LST_NAME_ID_HEADER = Arrays.asList("社員コード", "社員名", "エラー発生日付", "コード", "メッセージ",
			"対象項目", "提出済申請");

	private static final String PGID = "KDW003";

	private static final String FILE_EXTENSION = ".csv";

	@Override
	protected void handle(ExportServiceContext<List<DailyPerformErrorReferExportDto>> context) {
		List<DailyPerformErrorReferExportDto> data = context.getQuery();
		String dateTime = GeneralDateTime.now().toString().replaceAll("[/:\\s]", "");;
		List<String> header = this.getTextHeader();
		List<Map<String, Object>> dataSource = new ArrayList<>();
		for (DailyPerformErrorReferExportDto d : data) {
			Map<String, Object> row = new HashMap<>();
			row.put(header.get(0), d.getEmployeeCode());
			row.put(header.get(1), d.getEmployeeName());
			row.put(header.get(2), d.getDate());
			row.put(header.get(3), d.getErrorCode());
			row.put(header.get(4), d.getMessage());
			row.put(header.get(5), d.getItemName());
			row.put(header.get(6), d.getSubmitedName());
			dataSource.add(row);
		}
		CSVFileData fileData = new CSVFileData(
				PGID + "_ERAL" + "_" + dateTime + FILE_EXTENSION, header, dataSource);
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
