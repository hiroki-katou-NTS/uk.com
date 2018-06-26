package nts.uk.ctx.at.record.app.find.resultsperiod.optionalaggregationperiod.exportcsv;

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
import nts.uk.ctx.at.record.app.find.resultsperiod.optionalaggregationperiod.AggrPeriodErrorInfoDto;
import nts.uk.ctx.at.record.app.find.resultsperiod.optionalaggregationperiod.AggrPeriodErrorInfoFinder;
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
public class AggrPeriodErrorInfoExportService extends ExportService<AggrPeriodErrorQuery> {

	@Inject
	private CSVReportGenerator generator;

	@Inject
	private AggrPeriodErrorInfoFinder errorFinder;

	private static final List<String> LST_NAME_ID_HEADER = Arrays.asList("KFP001_39", "KFP001_40", "KFP001_41",
			"KFP001_42", "KFP001_43");

	private static final String PGID = "KFP001";

	private static final String FILE_EXTENSION = ".csv";

	@Override
	protected void handle(ExportServiceContext<AggrPeriodErrorQuery> context) {
		List<AggrPeriodErrorInfoDto> data = errorFinder.findAll(context.getQuery().getAggrPeriodId());
		String executionDt = GeneralDateTime.now().toString().replaceAll("[/:\\s]", "");
		List<String> header = this.getTextHeader();
		List<Map<String, Object>> dataSource = new ArrayList<>();
		for (AggrPeriodErrorInfoDto d : data) {
			Map<String, Object> row = new HashMap<>();
			row.put(header.get(0), data.indexOf(d) + 1);
			row.put(header.get(1), d.getEmployeeCode());
			row.put(header.get(2), d.getEmployeeName());
			row.put(header.get(3), d.getProcDate());
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
