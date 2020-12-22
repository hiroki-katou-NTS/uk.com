package nts.uk.ctx.sys.assist.app.find.logdataresult.exportcsv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import nts.uk.shr.com.i18n.TextResource;
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

	private List<Map<String, Object>> getLogDataStorage(List<String> listHeader, List<String> listHeaderSelected, List<LogDataResultDto> logParams) {
		List<Map<String, Object>> dataSource = new ArrayList<>();
		for (LogDataResultDto data : logParams) {
			if(data.getLogResult().isEmpty()) {
				Map<String, Object> row = new HashMap<>();
				//parent
				row.put(listHeader.get(0), data.getIpAddress());
				row.put(listHeader.get(1), data.getPcName());
				row.put(listHeader.get(2), data.getAccount());
				row.put(listHeader.get(3), data.getEmployeeCode());
				row.put(listHeader.get(4), data.getEmployeeName());
				row.put(listHeader.get(5), data.getStartDateTime());
				row.put(listHeader.get(6), this.getFormName(data.getForm()));
				row.put(listHeader.get(7), data.getName());
				row.put(listHeader.get(8), data.getFileId());
				row.put(listHeader.get(9), data.getFileSize());  
                row.put(listHeader.get(10), this.getStatusName(data.getStatus()));
				row.put(listHeader.get(11), data.getTargetNumberPeople());
				row.put(listHeader.get(12), data.getSetCode());
				row.put(listHeader.get(13), data.getFileName());
				row.put(listHeader.get(14), data.getEndDateTime());
				//sub
				row.put(listHeader.get(15), "");
				row.put(listHeader.get(16), "");
				row.put(listHeader.get(17), "");
				row.put(listHeader.get(18), "");
				dataSource.add(this.filterMap(row, listHeaderSelected));
			} else {
				for( LogResultDto logRs : data.getLogResult()) {
					Map<String, Object> row = new HashMap<>();
					//parent
					row.put(listHeader.get(0), data.getIpAddress());
					row.put(listHeader.get(1), data.getPcName());
					row.put(listHeader.get(2), data.getAccount());
					row.put(listHeader.get(3), data.getEmployeeCode());
					row.put(listHeader.get(4), data.getEmployeeName());
					row.put(listHeader.get(5), data.getStartDateTime());
					row.put(listHeader.get(6), this.getFormName(data.getForm()));
					row.put(listHeader.get(7), data.getName());
					row.put(listHeader.get(8), data.getFileId());
					row.put(listHeader.get(9), data.getFileSize());  
	                row.put(listHeader.get(10), this.getStatusName(data.getStatus()));
					row.put(listHeader.get(11), data.getTargetNumberPeople());
					row.put(listHeader.get(12), data.getSetCode());
					row.put(listHeader.get(13), data.getFileName());
					row.put(listHeader.get(14), data.getEndDateTime());
					//sub
					row.put(listHeader.get(15), logRs.getProcessingContent());
					row.put(listHeader.get(16), logRs.getErrorContent());
					row.put(listHeader.get(17), logRs.getErrorDate());
					row.put(listHeader.get(18), logRs.getErrorEmployeeId());
					dataSource.add(this.filterMap(row, listHeaderSelected));
				}
			}
		}
		return dataSource;
	}
	
	private List<Map<String, Object>> getDataRecovery(List<String> listHeader, List<String> listHeaderSelected, List<LogDataResultDto> logParams) {
		List<Map<String, Object>> dataSource = new ArrayList<>();
		for (LogDataResultDto data : logParams) {
			if(data.getLogResult().isEmpty()) {
				Map<String, Object> row = new HashMap<>();
				//parent
				row.put(listHeader.get(0), data.getIpAddress());
				row.put(listHeader.get(1), data.getPcName());
				row.put(listHeader.get(2), data.getAccount());
				row.put(listHeader.get(3), data.getEmployeeCode());
				row.put(listHeader.get(4), data.getEmployeeName());
				row.put(listHeader.get(5), data.getStartDateTime());
				row.put(listHeader.get(6), this.getFormName(data.getForm()));
				row.put(listHeader.get(7), data.getName());
				row.put(listHeader.get(8), data.getEndDateTime());
				row.put(listHeader.get(9), data.getSetCode());
				//sub
				row.put(listHeader.get(10), "");
				row.put(listHeader.get(11), "");
				row.put(listHeader.get(12), "");
				row.put(listHeader.get(13), "");
				row.put(listHeader.get(14), "");
				dataSource.add(this.filterMap(row, listHeaderSelected));
			} else {
				for( LogResultDto logRs : data.getLogResult()) {
					Map<String, Object> row = new HashMap<>();
					//parent
					row.put(listHeader.get(0), data.getIpAddress());
					row.put(listHeader.get(1), data.getPcName());
					row.put(listHeader.get(2), data.getAccount());
					row.put(listHeader.get(3), data.getEmployeeCode());
					row.put(listHeader.get(4), data.getEmployeeName());
					row.put(listHeader.get(5), data.getStartDateTime());
					row.put(listHeader.get(6), this.getFormName(data.getForm()));
					row.put(listHeader.get(7), data.getName());
					row.put(listHeader.get(8), data.getEndDateTime());
					row.put(listHeader.get(9), data.getSetCode());
					//sub
					row.put(listHeader.get(10), logRs.getProcessingContent());
					row.put(listHeader.get(11), logRs.getErrorContent());
					row.put(listHeader.get(12), logRs.getContentSql());
					row.put(listHeader.get(13), logRs.getErrorDate());
					row.put(listHeader.get(14), logRs.getErrorEmployeeId());
					dataSource.add(this.filterMap(row, listHeaderSelected));
				}
			}
		}
		return dataSource;
	}
	
	private List<Map<String, Object>> getDataDeletion(List<String> listHeader, List<String> listHeaderSelected, List<LogDataResultDto> logParams) {
		List<Map<String, Object>> dataSource = new ArrayList<>();
		for (LogDataResultDto data : logParams) {
			if(data.getLogResult().isEmpty()) {
				Map<String, Object> row = new HashMap<>();
				//parent
				row.put(listHeader.get(0), data.getIpAddress());
				row.put(listHeader.get(1), data.getPcName());
				row.put(listHeader.get(2), data.getAccount());
				row.put(listHeader.get(3), data.getEmployeeCode());
				row.put(listHeader.get(4), data.getEmployeeName());
				row.put(listHeader.get(5), data.getStartDateTime());
				row.put(listHeader.get(6), this.getFormName(data.getForm()));
				row.put(listHeader.get(7), this.getStatusName(data.getStatus()));
				row.put(listHeader.get(8), data.getTargetNumberPeople());
				row.put(listHeader.get(9), this.getDelFlgName(data.getIsDeletedFilesFlg()));
				row.put(listHeader.get(10), data.getFileSize());
				row.put(listHeader.get(11), data.getFileName());
				row.put(listHeader.get(12), data.getEndDateTime());
				row.put(listHeader.get(13), data.getSetCode());
				//sub
				row.put(listHeader.get(14), "");
				row.put(listHeader.get(15), "");
				row.put(listHeader.get(16), "");
				row.put(listHeader.get(17), "");
				dataSource.add(this.filterMap(row, listHeaderSelected));
			} else {
				for( LogResultDto logRs : data.getLogResult()) {
					Map<String, Object> row = new HashMap<>();
					//parent
					row.put(listHeader.get(0), data.getIpAddress());
					row.put(listHeader.get(1), data.getPcName());
					row.put(listHeader.get(2), data.getAccount());
					row.put(listHeader.get(3), data.getEmployeeCode());
					row.put(listHeader.get(4), data.getEmployeeName());
					row.put(listHeader.get(5), data.getStartDateTime());
					row.put(listHeader.get(6), this.getFormName(data.getForm()));
					row.put(listHeader.get(7), this.getStatusName(data.getStatus()));
					row.put(listHeader.get(8), data.getTargetNumberPeople());
					row.put(listHeader.get(9), this.getDelFlgName(data.getIsDeletedFilesFlg()));
					row.put(listHeader.get(10), data.getFileSize());
					row.put(listHeader.get(11), data.getFileName());
					row.put(listHeader.get(12), data.getEndDateTime());
					row.put(listHeader.get(13), data.getSetCode());
					//sub
					row.put(listHeader.get(14), logRs.getProcessingContent());
					row.put(listHeader.get(15), logRs.getErrorContent());
					row.put(listHeader.get(16), logRs.getErrorDate());
					row.put(listHeader.get(17), logRs.getErrorEmployeeId());
					dataSource.add(this.filterMap(row, listHeaderSelected));
				}
			}
		}
		return dataSource;
	}
	
	private String getFormName(int form) {
		return form == 0 ? TextResource.localize("Enum_StorageForm_MANUAL") : TextResource.localize("Enum_StorageForm_AUTOMATIC");
	}
	
	private String getDelFlgName(int isDelFlg) {
		return isDelFlg == 0 ? TextResource.localize("CLI003_93") : TextResource.localize("CLI003_94");
	}
	
	private String getStatusName(int status) {
		switch (status) {
        case 0:
            return TextResource.localize("Enum_SaveStatus_SUCCESS");
        case 1:
            return TextResource.localize("Enum_SaveStatus_INTERRUPTION");
        case 2:
            return TextResource.localize("Enum_SaveStatus_FAILURE");
        default:
            return "";
		}
	}
	
	private Map<String, Object> filterMap(Map<String, Object> map, List<String> selectedHeader) {
		return map.entrySet().stream() 
			          .filter(m -> selectedHeader.stream().anyMatch(item -> item.equals(m.getKey())) ) 
			          .collect(Collectors.toMap(m -> m.getKey(), m -> m.getValue() == null ? "" : m.getValue()));  
	}
	
	@Override
	protected void handle(ExportServiceContext<LogDataParamsExport> context) {
		LogDataParamsExport params = context.getQuery();
		List<String> listHeader = new ArrayList<>();
		List<String> listHeaderAll = new ArrayList<>();
		listHeaderAll.addAll(params.getLstSelectedItemHeader());
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
			dataSource = getLogDataStorage(listHeaderAll, listHeader, logParams);
			break;
		case 10:
			dataSource = getDataRecovery(listHeaderAll, listHeader, logParams);
			break;
		case 11:
			dataSource = getDataDeletion(listHeaderAll, listHeader, logParams);
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
