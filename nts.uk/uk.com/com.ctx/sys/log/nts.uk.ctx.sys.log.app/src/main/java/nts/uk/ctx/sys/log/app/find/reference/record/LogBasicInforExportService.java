package nts.uk.ctx.sys.log.app.find.reference.record;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.sys.log.app.find.reference.LogOutputItemDto;
import nts.uk.ctx.sys.log.dom.reference.RecordTypeEnum;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.security.audittrail.correction.content.TargetDataType;
import nts.uk.shr.infra.file.csv.CSVFileData;
import nts.uk.shr.infra.file.csv.CSVReportGenerator;

@Stateless
public class LogBasicInforExportService extends ExportService<LogParams> {
	@Inject
	private CSVReportGenerator generator;

	private static final String FILE_EXTENSION = ".csv";
	private static final String PRIMAKY = "primarykey";
	private static final String PGID = "CLI003";

	private List<String> getTextHeader(LogParams params) {
		List<String> lstHeader = new ArrayList<>();
		List<LogOutputItemDto> lstOutputItemDto = params.getLstHeaderDto();
		for (LogOutputItemDto logOutputItemDto : lstOutputItemDto) {
			if(!PRIMAKY.equals(logOutputItemDto.getItemName())){
				lstHeader.add(logOutputItemDto.getItemName());
			}
		}
		return lstHeader;
	}
	private List<String> getTextSubHeader(LogParams params) {
		List<String> lstHeader = new ArrayList<>();
		List<LogOutputItemDto> lstOutputItemDto = params.getLstSupHeaderDto();
		for (LogOutputItemDto logOutputItemDto : lstOutputItemDto) {
			lstHeader.add(logOutputItemDto.getItemName());
		}
		return lstHeader;
	}
	private List<String> getTextSubHeaderPersion(LogBasicInfoDto logBasicInfoDto) {
		List<String> lstHeader = new ArrayList<>();
		List<LogOutputItemDto> lstOutputItemDto = logBasicInfoDto.getLstLogOutputItemDto();
		for (LogOutputItemDto logOutputItemDto : lstOutputItemDto) {
			lstHeader.add(logOutputItemDto.getItemName());
		}
		return lstHeader;
	}

	private List<Map<String, Object>> getLogRecordData(LogParams params,List<String> headers) {
		List<Map<String, Object>> dataSource = new ArrayList<>();
		List<LogBasicInfoDto> data = params.getLstLogBasicInfoDto();
		for (LogBasicInfoDto d : data) {
			Map<String, Object> row = new HashMap<>();
			row.put(headers.get(0), d.getEmployeeCodeLogin());
			row.put(headers.get(1), d.getUserNameLogin());
			row.put(headers.get(2), d.getModifyDateTime());
			row.put(headers.get(3), d.getMethodName());
			row.put(headers.get(4), d.getLoginStatus());
			row.put(headers.get(5), d.getNote());
			dataSource.add(row);
		}
		return dataSource;
	}
	private List<Map<String, Object>> getStartUpData(LogParams params,List<String> headers) {
		List<Map<String, Object>> dataSource = new ArrayList<>();
		List<LogBasicInfoDto> data = params.getLstLogBasicInfoDto();
		for (LogBasicInfoDto d : data) {
			Map<String, Object> row = new HashMap<>();
			row.put(headers.get(0), d.getEmployeeCodeLogin());
			row.put(headers.get(1), d.getUserNameLogin());
			row.put(headers.get(2), d.getModifyDateTime());
			row.put(headers.get(3), d.getMenuName());
			row.put(headers.get(4), d.getNote());
			dataSource.add(row);
		}
		return dataSource;
	}
	
	private List<Map<String, Object>> getDataCorrectLog(LogParams params,List<String> headers,List<String> supHeaders) {
		List<Map<String, Object>> dataSource = new ArrayList<>();
		List<LogBasicInfoDto> data = params.getLstLogBasicInfoDto();
		for (LogBasicInfoDto d : data) {
			Map<String, Object> row = new HashMap<>();
			row.put(headers.get(0), d.getEmployeeCodeTaget());
			row.put(headers.get(1), d.getUserNameTaget());
			row.put(headers.get(2), d.getEmployeeCodeLogin());
			row.put(headers.get(3), d.getUserNameLogin());
			row.put(headers.get(4), d.getModifyDateTime());
			dataSource.add(row);
			// check list child and generate row child
			List<LogDataCorrectRecordRefeDto> lstDataCorrect = d.getLstLogDataCorrectRecordRefeDto();
			
			if (!CollectionUtil.isEmpty(lstDataCorrect)) {
			
				int count = 0;
				for (LogDataCorrectRecordRefeDto dataCorrectLog : lstDataCorrect) {
					Map<String, Object> rowSub ;
					if (count == 0) {
						rowSub = new HashMap<>();
						if (dataCorrectLog.getTargetDataType() == TargetDataType.SCHEDULE.value
								|| dataCorrectLog.getTargetDataType() == TargetDataType.DAILY_RECORD.value) {
							rowSub.put(headers.get(0), supHeaders.get(0));
						}
						if (dataCorrectLog.getTargetDataType() == TargetDataType.MONTHLY_RECORD.value
								|| dataCorrectLog.getTargetDataType() == TargetDataType.ANY_PERIOD_SUMMARY.value
								|| dataCorrectLog.getTargetDataType() == TargetDataType.SALARY_DETAIL.value
								|| dataCorrectLog.getTargetDataType() == TargetDataType.BONUS_DETAIL.value) {
							rowSub.put(headers.get(0), supHeaders.get(1));
						}
						if (dataCorrectLog.getTargetDataType() == TargetDataType.YEAR_END_ADJUSTMENT.value
								|| dataCorrectLog.getTargetDataType() == TargetDataType.MONTHLY_CALCULATION.value
								|| dataCorrectLog.getTargetDataType() == TargetDataType.RISING_SALARY_BACK.value) {
							rowSub.put(headers.get(0), supHeaders.get(2));
						}

						rowSub.put(headers.get(1), supHeaders.get(3));
						rowSub.put(headers.get(2), supHeaders.get(4));
						rowSub.put(headers.get(3), supHeaders.get(5));
						rowSub.put(headers.get(4), supHeaders.get(6));
						dataSource.add(rowSub);
						count++;
					}
					rowSub = new HashMap<>();
					rowSub.put(headers.get(0), dataCorrectLog.getTargetDate());
					rowSub.put(headers.get(1), dataCorrectLog.getItemName());
					rowSub.put(headers.get(2), dataCorrectLog.getValueBefore());
					rowSub.put(headers.get(3), dataCorrectLog.getValueAfter());
					rowSub.put(headers.get(4), dataCorrectLog.getCorrectionAttr());
					dataSource.add(rowSub);
				}
			}
		}
		return dataSource;
	}
	
	private List<Map<String, Object>> getPersionCorrectLog(LogParams params,List<String> headers) {
		List<Map<String, Object>> dataSource = new ArrayList<>();
		List<LogBasicInfoDto> data = params.getLstLogBasicInfoDto();
		for (LogBasicInfoDto d : data) {
			Map<String, Object> row = new HashMap<>();
			row.put(headers.get(0), d.getEmployeeCodeTaget());
			row.put(headers.get(1), d.getUserNameTaget());
			row.put(headers.get(2), d.getProcessAttr());
			row.put(headers.get(3), d.getEmployeeCodeLogin());
			row.put(headers.get(4), d.getUserNameLogin());
			row.put(headers.get(5), d.getModifyDateTime());
			row.put(headers.get(6), d.getNote());
			dataSource.add(row);
			// check list child and generate row child
			List<LogPerCateCorrectRecordDto> lstPersionCorrect = d.getLstLogPerCateCorrectRecordDto();
			
			if (!CollectionUtil.isEmpty(lstPersionCorrect)) {
				List<String> subHeadersTemp = this.getTextSubHeaderPersion(d);
				int count = 0;
				for (LogPerCateCorrectRecordDto persionCorrectLog : lstPersionCorrect) {
					Map<String, Object> rowSub ;
					if (count == 0) {
						rowSub = new HashMap<>();
						rowSub.put(headers.get(0), subHeadersTemp.get(0));
						rowSub.put(headers.get(1), subHeadersTemp.get(1));
						rowSub.put(headers.get(2), subHeadersTemp.get(2));
						rowSub.put(headers.get(3), subHeadersTemp.get(3));
						rowSub.put(headers.get(4), subHeadersTemp.get(4));
						rowSub.put(headers.get(5), subHeadersTemp.get(5));
						rowSub.put(headers.get(6), "");
						dataSource.add(rowSub);
						count++;
					}
					rowSub = new HashMap<>();
					rowSub.put(headers.get(0), persionCorrectLog.getCategoryName());
					rowSub.put(headers.get(1), persionCorrectLog.getTargetDate() !=null ? persionCorrectLog.getTargetDate().toString("yyyy/MM/dd") : "");
					rowSub.put(headers.get(2), persionCorrectLog.getItemName());
					rowSub.put(headers.get(3), persionCorrectLog.getInfoOperateAttr());
					rowSub.put(headers.get(4), persionCorrectLog.getValueBefore());
					rowSub.put(headers.get(5), persionCorrectLog.getValueAfter());
					rowSub.put(headers.get(6), "");
					dataSource.add(rowSub);
				}
			}
		}
		return dataSource;
	}


	@Override
	protected void handle(ExportServiceContext<LogParams> context) {
		LogParams params = context.getQuery();

		List<String> headers = this.getTextHeader(params);
		RecordTypeEnum recordTypeEnum = RecordTypeEnum.valueOf(params.getRecordType());
		List<Map<String, Object>> dataSource = new ArrayList<>();
		switch (recordTypeEnum) {
		case LOGIN:
			dataSource = getLogRecordData(params,headers);
			break;
		case START_UP:
			dataSource = getStartUpData(params,headers);
			break;
		case UPDATE_PERSION_INFO:
			dataSource = getPersionCorrectLog(params,headers);
			break;
		case DATA_CORRECT:
			List<String> subHeaders = this.getTextSubHeader(params);
			dataSource = getDataCorrectLog(params,headers,subHeaders);
			break;

		default:
			break;
		}
		String employeeCode = AppContexts.user().employeeCode();
		CSVFileData fileData = new CSVFileData(
				PGID + "_" + GeneralDateTime.now().toString("yyyyMMddHHmmss") + "_" + employeeCode + FILE_EXTENSION, headers, dataSource);
		generator.generate(context.getGeneratorContext(), fileData);
	}
}
