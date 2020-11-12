package nts.uk.ctx.sys.assist.app.find.logdataresult.exportcsv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.assist.app.find.logdataresult.LogDataResultDto;
import nts.uk.ctx.sys.assist.app.find.logdataresult.LogDataResultFinder;
import nts.uk.ctx.sys.assist.app.find.logdataresult.LogResultDto;
import nts.uk.ctx.sys.assist.app.find.params.LogDataParams;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.file.csv.CSVFileData;
import nts.uk.shr.infra.file.csv.CSVReportGenerator;

@Stateless
public class LogDataResultExportService extends ExportService<LogDataParamsExport> {
	@Inject
	private CSVReportGenerator generator;
	
	@Inject
	LogDataResultFinder logDataResultFinder;

	private static final String FILE_EXTENSION = ".csv";
	private static final String PGID = "CLI003";
	private List<String> listHeader = new ArrayList<>();

	private List<Map<String, Object>> getLogDataStorage(List<LogDataResultDto> logParams) {
		List<Map<String, Object>> dataSource = new ArrayList<>();
		for (LogDataResultDto data : logParams) {
			if(data.getLogResult().isEmpty()) {
				Map<String, Object> row = new HashMap<>();
				//parent
				row.put(listHeader.get(0), data.getEmployeeCode());
				row.put(listHeader.get(1), data.getEmployeeName());
				row.put(listHeader.get(2), data.getStartDateTime());
				row.put(listHeader.get(3), data.getSetCode());
				row.put(listHeader.get(4), data.getEndDateTime());
				dataSource.add(row);
			} else {
				for( LogResultDto logRs : data.getLogResult()) {
					Map<String, Object> row = new HashMap<>();
					//parent
					row.put(listHeader.get(0), data.getEmployeeCode());
					row.put(listHeader.get(1), data.getEmployeeName());
					row.put(listHeader.get(2), data.getStartDateTime());
					row.put(listHeader.get(3), data.getSetCode());
					row.put(listHeader.get(4), data.getEndDateTime());
					//sub
					row.put(listHeader.get(5), logRs.getProcessingContent());
					row.put(listHeader.get(6), logRs.getErrorContent());
					row.put(listHeader.get(7), logRs.getErrorDate());
					row.put(listHeader.get(8), logRs.getErrorEmployeeId());
					dataSource.add(row);
				}
			}
		}
		return dataSource;
	}
	
	private List<Map<String, Object>> getDataRecovery(List<LogDataResultDto> logParams) {
		List<Map<String, Object>> dataSource = new ArrayList<>();
		for (LogDataResultDto data : logParams) {
			for( LogResultDto logRs : data.getLogResult()) {
				Map<String, Object> row = new HashMap<>();
				//parent
				row.put(listHeader.get(0), data.getEmployeeCode());
				row.put(listHeader.get(1), data.getEmployeeName());
				row.put(listHeader.get(2), data.getStartDateTime());
				row.put(listHeader.get(3), data.getSetCode());
				row.put(listHeader.get(4), data.getEndDateTime());
				//sub
				row.put(listHeader.get(5), logRs.getProcessingContent());
				row.put(listHeader.get(6), logRs.getErrorContent());
				row.put(listHeader.get(7), logRs.getContentSql());
				row.put(listHeader.get(8), logRs.getErrorDate());
				row.put(listHeader.get(9), logRs.getErrorEmployeeId());
				dataSource.add(row);
			}
		}
		return dataSource;
	}
	
	private List<Map<String, Object>> getDataDeletion(List<LogDataResultDto> logParams) {
		List<Map<String, Object>> dataSource = new ArrayList<>();
		for (LogDataResultDto data : logParams) {
			for( LogResultDto logRs : data.getLogResult()) {
				Map<String, Object> row = new HashMap<>();
				//parent
				row.put(listHeader.get(0), data.getEmployeeCode());
				row.put(listHeader.get(1), data.getEmployeeName());
				row.put(listHeader.get(2), data.getStartDateTime());
				row.put(listHeader.get(3), data.getSetCode());
				row.put(listHeader.get(4), data.getEndDateTime());
				//sub
				row.put(listHeader.get(5), logRs.getProcessingContent());
				row.put(listHeader.get(6), logRs.getErrorContent());
				row.put(listHeader.get(7), logRs.getErrorDate());
				row.put(listHeader.get(8), logRs.getErrorEmployeeId());
				dataSource.add(row);
			}
		}
		return dataSource;
	}
	
	@Override
	protected void handle(ExportServiceContext<LogDataParamsExport> context) {
		LogDataParamsExport params = context.getQuery();
		listHeader.addAll(params.getLstHeaderDto());
		listHeader.addAll(params.getLstSubHeaderDto());
		LogDataParams param = LogDataParams.builder()
				.systemType(params.getSystemType())
				.recordType(params.getRecordType())
				.startDateOperator(params.getStartDateOperator())
				.endDateOperator(params.getEndDateOperator())
				.listOperatorEmployeeId(params.getListOperatorEmployeeId())
				.listCondition(params.getListCondition())
				.build();
		List<LogDataResultDto> logParams = this.logDataResultFinder.getLogDataResult(param);
		List<Map<String, Object>> dataSource = new ArrayList<>();
		Integer recordType = params.getRecordType();
		switch (recordType) {
		case 9:
			dataSource = getLogDataStorage(logParams);
			break;
		case 10:
			dataSource = getDataRecovery(logParams);
			break;
		case 11:
			dataSource = getDataDeletion(logParams);
			break;
		default:
			break;
		}
		String employeeCode = AppContexts.user().employeeCode();
		CSVFileData fileData = new CSVFileData(
				PGID + "_" + GeneralDateTime.now().toString("yyyyMMddHHmmss") + "_" + employeeCode + FILE_EXTENSION, listHeader, dataSource);
		generator.generate(context.getGeneratorContext(), fileData);
	}
}
