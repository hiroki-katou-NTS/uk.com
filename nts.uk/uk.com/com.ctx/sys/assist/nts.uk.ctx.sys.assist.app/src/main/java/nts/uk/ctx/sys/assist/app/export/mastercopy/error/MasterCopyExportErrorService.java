package nts.uk.ctx.sys.assist.app.export.mastercopy.error;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import nts.uk.ctx.sys.assist.app.command.mastercopy.ErrorContentDto;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.csv.CSVFileData;
import nts.uk.shr.infra.file.csv.CSVReportGenerator;

// TODO: Auto-generated Javadoc
/**
 * The Class MasterCopyExportErrorService.
 */
@Stateless
public class MasterCopyExportErrorService extends ExportService<List<ErrorContentDto>> {

	/** The generator. */
	@Inject
	private CSVReportGenerator generator;

	/** The Constant LST_NAME_ID_HEADER. */
	private static final List<String> LST_NAME_ID_HEADER = Arrays.asList("CMM001_60", "CMM001_61", "CMM001_62");

	/** The Constant PREFIX_NAME. */
	private static final String PREFIX_NAME = "CMM001";

	/** The Constant FORMATTER_STR. */
	private static final String FORMATTER_STR = "yyyyMMddHHmmss";

	/** The Constant EXTENSION_FILE. */
	private static final String EXTENSION_FILE = ".csv";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.file.export.ExportService#handle(nts.arc.layer.app.file
	 * .export.ExportServiceContext)
	 */
	@Override
	protected void handle(ExportServiceContext<List<ErrorContentDto>> context) {

		List<ErrorContentDto> lstError = context.getQuery();
		List<String> header = this.getTextHeader();

		List<Map<String, Object>> dataSource = lstError.stream().map(errorLine -> {
			Map<String, Object> map = new HashMap<>();
			map.put(header.get(0), errorLine.getSystemType());
			map.put(header.get(1), errorLine.getCategoryName());
			map.put(header.get(2), errorLine.getMessage());
			return map;
		}).collect(Collectors.toList());

		CSVFileData dataExport = new CSVFileData(this.getFileName(), header, dataSource);
		// generate file
		this.generator.generate(context.getGeneratorContext(), dataExport);
	}

	/**
	 * Find header.
	 *
	 * @return the list
	 */
	/**
	 * @return
	 */
	private List<String> getTextHeader() {
		List<String> lstHeader = new ArrayList<>();
		for (String nameId : LST_NAME_ID_HEADER) {
			lstHeader.add(TextResource.localize(nameId));
		}
		return lstHeader;
	}

	/**
	 * Gets the file name.
	 *
	 * @return the file name
	 */
	private String getFileName() {
		// Format CMM001_Processing year, month, day, hour, minute, second_Employee code. Csv
		String emplCode = AppContexts.user().employeeCode();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(FORMATTER_STR);
		LocalDateTime now = LocalDateTime.now();
		return String.format("%s_%s_%s%s", PREFIX_NAME, dtf.format(now), emplCode, EXTENSION_FILE);
	}
}
