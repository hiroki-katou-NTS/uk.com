package nts.uk.ctx.sys.log.app.finder.datacorrectionlog.exportcsv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.log.app.finder.datacorrectionlog.DataCorrectionLogParams;
import nts.uk.ctx.sys.log.dom.datacorrectionlog.DataCorrectionLogRepository;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.csv.CSVFileData;
import nts.uk.shr.infra.file.csv.CSVReportGenerator;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class DataCorrectionLogExportService  extends ExportService<DataCorrectionLogParams> {

	@Inject
	private CSVReportGenerator generator;
	
	@Inject
	private DataCorrectionLogRepository repo;
	
	private static final List<String> LST_NAME_ID_HEADER = Arrays.asList("CDL027_4", "CDL027_7", "CDL027_8",
			"CDL027_9", "CDL027_11", "CDL027_12", "CDL027_13", "CDL027_14");

	private static final String FILE_EXTENSION = ".csv";
	
	private List<String> getTextHeader() {
		List<String> lstHeader = new ArrayList<>();
		for (String nameId : LST_NAME_ID_HEADER) {
			lstHeader.add(TextResource.localize(nameId));
		}
		return lstHeader;
	}

	@Override
	protected void handle(ExportServiceContext<DataCorrectionLogParams> context) {
		DataCorrectionLogParams params = context.getQuery();
		int dispFormat = params.getDisplayFormat();
		List<String> header = this.getTextHeader();
		List<Map<String, Object>> dataSource = new ArrayList<>();
//		for (DataCorrectionLogDto d : data) {
//			Map<String, Object> row = new HashMap<>();
//			
//			dataSource.add(row);
//		}
		CSVFileData fileData = new CSVFileData(
				"PGID" + "_" + GeneralDateTime.now().toString("yyyyMMddHHmmss") + FILE_EXTENSION, header, dataSource);
		generator.generate(context.getGeneratorContext(), fileData);
	}

}
