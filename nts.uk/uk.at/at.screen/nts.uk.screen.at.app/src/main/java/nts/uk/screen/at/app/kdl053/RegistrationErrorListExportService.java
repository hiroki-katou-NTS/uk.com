package nts.uk.screen.at.app.kdl053;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
 * @author quytb
 *
 */

@Stateless
public class RegistrationErrorListExportService extends ExportService<List<RegistrationErrorListDto>>{
	
	/** The generator. */
    @Inject
    private CSVReportGenerator generator;
    
	private static final List<String> LST_NAME_ID_HEADER = Arrays.asList("KDL053_5","KDL053_6","KDL053_7", "KDL053_8");

	@Override
	protected void handle(ExportServiceContext<List<RegistrationErrorListDto>> context) {
		List<RegistrationErrorListDto> errorListDtos = context.getQuery();
		if(errorListDtos == null ) {
			return;
		}
		List<String> header = this.getTextHeader();
		String dateTime = GeneralDateTime.now().toString().replaceAll("[/:\\s]", "");
		String fileName = TextResource.localize("KDL053_1") + "_" + dateTime + ".csv";
		List<Map<String, Object>> dataSource = errorListDtos.stream()
				.map(errorLine -> {
					Map<String, Object> row = new HashMap<>();
					row.put(header.get(0), errorLine.getEmployeeCdName());
					row.put(header.get(1), errorLine.getDateCsv());
					row.put(header.get(2), errorLine.getErrName());
					row.put(header.get(3), errorLine.getErrorMessage());
					return row;
				}).collect(Collectors.toList());
		
		CSVFileData dataExport = new CSVFileData(fileName, header, dataSource);		
		this.generator.generate(context.getGeneratorContext(), dataExport);	
	}
	
	private List<String> getTextHeader() {
		List<String> lstHeader = new ArrayList<>();
		for (String nameId : LST_NAME_ID_HEADER) {
			lstHeader.add(TextResource.localize(nameId));
		}
		return lstHeader;
	}
}
