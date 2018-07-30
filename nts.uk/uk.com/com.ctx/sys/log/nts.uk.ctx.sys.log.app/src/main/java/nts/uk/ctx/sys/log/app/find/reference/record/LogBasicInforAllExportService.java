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
import nts.uk.ctx.sys.log.app.find.reference.LogOutputItemDto;
import nts.uk.ctx.sys.log.dom.reference.ItemNoEnum;
import nts.uk.ctx.sys.log.dom.reference.RecordTypeEnum;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.file.csv.CSVFileData;
import nts.uk.shr.infra.file.csv.CSVReportGenerator;

@Stateless
public class LogBasicInforAllExportService extends ExportService<LogParams> {
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
	

	private List<Map<String, Object>> getDataSource(LogParams params,List<LogOutputItemDto> headers) {
		List<Map<String, Object>> dataSource = new ArrayList<>();
		List<LogBasicInfoAllDto> data = params.getListLogBasicInfoAllDto();
		for (LogBasicInfoAllDto d : data) {
			Map<String, Object> row = checkHeader(d,headers);
			dataSource.add(row);
		}
		return dataSource;
	}
	
	
	private Map<String, Object> checkHeader(LogBasicInfoAllDto logBaseDto,List<LogOutputItemDto> headers) {
		Map<String, Object> dataReturn = new HashMap<>();
		for (LogOutputItemDto a : headers) {		
			RecordTypeEnum recordTypeEnum=RecordTypeEnum.valueOf(a.getRecordType()) ;
		//int itno=a.getItemNo();
			ItemNoEnum itemNoEnum=ItemNoEnum.valueOf(a.getItemNo());
			switch (recordTypeEnum) {
			case LOGIN:
					switch (itemNoEnum) {
					case ITEM_NO_1:
						dataReturn.put(a.getItemName(), logBaseDto.getUserIdLogin());
						break;
					case ITEM_NO_2:
						dataReturn.put(a.getItemName(), logBaseDto.getUserNameLogin());
						break;	
					case ITEM_NO_3:
						dataReturn.put(a.getItemName(), logBaseDto.getEmployeeCodeLogin());
						break;
					case ITEM_NO_4:
						dataReturn.put(a.getItemName(), logBaseDto.getIpAddress());
						break;
					case ITEM_NO_5:
						dataReturn.put(a.getItemName(), logBaseDto.getPcName());
						break;
					case ITEM_NO_6:
						dataReturn.put(a.getItemName(), logBaseDto.getAccount());
						break;
					case ITEM_NO_7:
						dataReturn.put(a.getItemName(), logBaseDto.getModifyDateTime());
						break;
					case ITEM_NO_8:
						dataReturn.put(a.getItemName(), logBaseDto.getEmploymentAuthorityName());
						break;
					case ITEM_NO_9:
						dataReturn.put(a.getItemName(), logBaseDto.getSalarytAuthorityName());
						break;
					case ITEM_NO_10:
						dataReturn.put(a.getItemName(), logBaseDto.getPersonelAuthorityName());
						break;
					case ITEM_NO_11:
						dataReturn.put(a.getItemName(), logBaseDto.getOfficeHelperAuthorityName());
						break;
					case ITEM_NO_12:
						dataReturn.put(a.getItemName(), logBaseDto.getAccountAuthorityName());
						break;
					case ITEM_NO_13:
						dataReturn.put(a.getItemName(), logBaseDto.getMyNumberAuthorityName());
						break;
					case ITEM_NO_14:
						dataReturn.put(a.getItemName(), logBaseDto.getGroupCompanyAddminAuthorityName());
						break;
					case ITEM_NO_15:
						dataReturn.put(a.getItemName(), logBaseDto.getCompanyAddminAuthorityName());
						break;
					case ITEM_NO_16:
						dataReturn.put(a.getItemName(), logBaseDto.getSystemAdminAuthorityName());
						break;
					case ITEM_NO_17:
						dataReturn.put(a.getItemName(), logBaseDto.getPersonalInfoAuthorityName());
						break;
					case ITEM_NO_18:
						dataReturn.put(a.getItemName(), logBaseDto.getMenuName());
						break;
					case ITEM_NO_19:
						dataReturn.put(a.getItemName(), logBaseDto.getLoginStatus());
						break;
					case ITEM_NO_20:
						dataReturn.put(a.getItemName(), logBaseDto.getLoginMethod());
						break;
					case ITEM_NO_21:
						dataReturn.put(a.getItemName(), logBaseDto.getAccessResourceUrl());
						break;
					case ITEM_NO_22:
						dataReturn.put(a.getItemName(), logBaseDto.getNote());
						break;	
					default:
						break;
					}
				
				break;
			case START_UP:
				switch (itemNoEnum) {
				case ITEM_NO_1:
					dataReturn.put(a.getItemName(), logBaseDto.getUserIdLogin());
					break;
				case ITEM_NO_2:
					dataReturn.put(a.getItemName(), logBaseDto.getUserNameLogin());
					break;	
				case ITEM_NO_3:
					dataReturn.put(a.getItemName(), logBaseDto.getEmployeeCodeLogin());
					break;
				case ITEM_NO_4:
					dataReturn.put(a.getItemName(), logBaseDto.getIpAddress());
					break;
				case ITEM_NO_5:
					dataReturn.put(a.getItemName(), logBaseDto.getPcName());
					break;
				case ITEM_NO_6:
					dataReturn.put(a.getItemName(), logBaseDto.getAccount());
					break;
				case ITEM_NO_7:
					dataReturn.put(a.getItemName(), logBaseDto.getModifyDateTime());
					break;
				case ITEM_NO_8:
					dataReturn.put(a.getItemName(), logBaseDto.getEmploymentAuthorityName());
					break;
				case ITEM_NO_9:
					dataReturn.put(a.getItemName(), logBaseDto.getSalarytAuthorityName());
					break;
				case ITEM_NO_10:
					dataReturn.put(a.getItemName(), logBaseDto.getPersonelAuthorityName());
					break;
				case ITEM_NO_11:
					dataReturn.put(a.getItemName(), logBaseDto.getOfficeHelperAuthorityName());
					break;
				case ITEM_NO_12:
					dataReturn.put(a.getItemName(), logBaseDto.getAccountAuthorityName());
					break;
				case ITEM_NO_13:
					dataReturn.put(a.getItemName(), logBaseDto.getMyNumberAuthorityName());
					break;
				case ITEM_NO_14:
					dataReturn.put(a.getItemName(), logBaseDto.getGroupCompanyAddminAuthorityName());
					break;
				case ITEM_NO_15:
					dataReturn.put(a.getItemName(), logBaseDto.getCompanyAddminAuthorityName());
					break;
				case ITEM_NO_16:
					dataReturn.put(a.getItemName(), logBaseDto.getSystemAdminAuthorityName());
					break;
				case ITEM_NO_17:
					dataReturn.put(a.getItemName(), logBaseDto.getPersonalInfoAuthorityName());
					break;
				case ITEM_NO_18:
					dataReturn.put(a.getItemName(), logBaseDto.getNote());
					break;
				case ITEM_NO_19:
					dataReturn.put(a.getItemName(), logBaseDto.getMenuName());
					break;
				case ITEM_NO_20:
					dataReturn.put(a.getItemName(), logBaseDto.getMenuNameReSource());
					break;
				default:
					break;
				
				}
				break;
			case UPDATE_PERSION_INFO:
				switch (itemNoEnum) {
				case ITEM_NO_1:
					dataReturn.put(a.getItemName(), logBaseDto.getUserIdLogin());
					break;
				case ITEM_NO_2:
					dataReturn.put(a.getItemName(), logBaseDto.getUserNameLogin());
					break;	
				case ITEM_NO_3:
					dataReturn.put(a.getItemName(), logBaseDto.getEmployeeCodeLogin());
					break;
				case ITEM_NO_4:
					dataReturn.put(a.getItemName(), logBaseDto.getIpAddress());
					break;
				case ITEM_NO_5:
					dataReturn.put(a.getItemName(), logBaseDto.getPcName());
					break;
				case ITEM_NO_6:
					dataReturn.put(a.getItemName(), logBaseDto.getAccount());
					break;
				case ITEM_NO_7:
					dataReturn.put(a.getItemName(), logBaseDto.getModifyDateTime());
					break;
				case ITEM_NO_8:
					dataReturn.put(a.getItemName(), logBaseDto.getEmploymentAuthorityName());
					break;
				case ITEM_NO_9:
					dataReturn.put(a.getItemName(), logBaseDto.getSalarytAuthorityName());
					break;
				case ITEM_NO_10:
					dataReturn.put(a.getItemName(), logBaseDto.getPersonelAuthorityName());
					break;
				case ITEM_NO_11:
					dataReturn.put(a.getItemName(), logBaseDto.getOfficeHelperAuthorityName());
					break;
				case ITEM_NO_12:
					dataReturn.put(a.getItemName(), logBaseDto.getAccountAuthorityName());
					break;
				case ITEM_NO_13:
					dataReturn.put(a.getItemName(), logBaseDto.getMyNumberAuthorityName());
					break;
				case ITEM_NO_14:
					dataReturn.put(a.getItemName(), logBaseDto.getGroupCompanyAddminAuthorityName());
					break;
				case ITEM_NO_15:
					dataReturn.put(a.getItemName(), logBaseDto.getCompanyAddminAuthorityName());
					break;
				case ITEM_NO_16:
					dataReturn.put(a.getItemName(), logBaseDto.getSystemAdminAuthorityName());
					break;
				case ITEM_NO_17:
					dataReturn.put(a.getItemName(), logBaseDto.getPersonalInfoAuthorityName());
					break;
				case ITEM_NO_18:
					dataReturn.put(a.getItemName(), logBaseDto.getMenuName());
					break;
				case ITEM_NO_19:
					dataReturn.put(a.getItemName(), logBaseDto.getUserIdTaget());
					break;
				case ITEM_NO_20:
					dataReturn.put(a.getItemName(), logBaseDto.getUserNameTaget());
					break;
				case ITEM_NO_21:
					dataReturn.put(a.getItemName(), logBaseDto.getEmployeeCodeTaget());
					break;
				case ITEM_NO_22:
					dataReturn.put(a.getItemName(), logBaseDto.getCategoryProcess());
					break;
				case ITEM_NO_23:
					dataReturn.put(a.getItemName(), logBaseDto.getCategoryName());
					break;
				case ITEM_NO_24:
					dataReturn.put(a.getItemName(), logBaseDto.getMethodCorrection());
					break;
				case ITEM_NO_25:
					dataReturn.put(a.getItemName(), logBaseDto.getTarGetYmd());
					break;
				case ITEM_NO_26:
					dataReturn.put(a.getItemName(), logBaseDto.getTarGetYm());
					break;			
				case ITEM_NO_27:
					dataReturn.put(a.getItemName(), logBaseDto.getTarGetY());
					break;
				case ITEM_NO_28:
					dataReturn.put(a.getItemName(), logBaseDto.getKeyString());
					break;
				case ITEM_NO_29:
					dataReturn.put(a.getItemName(), logBaseDto.getItemName());
					break;
				case ITEM_NO_30:
					dataReturn.put(a.getItemName(), logBaseDto.getItemvalueBefor());
					break;
				case ITEM_NO_31:
					dataReturn.put(a.getItemName(), logBaseDto.getItemContentValueBefor());
					break;
				case ITEM_NO_32:
					dataReturn.put(a.getItemName(), logBaseDto.getItemvalueAppter());
					break;
				case ITEM_NO_33:
					dataReturn.put(a.getItemName(), logBaseDto.getItemContentValueAppter());
					break;
				case ITEM_NO_34:
					dataReturn.put(a.getItemName(), logBaseDto.getItemEditAddition());
					break;
				case ITEM_NO_35:
					dataReturn.put(a.getItemName(), logBaseDto.getTarGetYmdEditAddition());
					break;
				case ITEM_NO_36:
					dataReturn.put(a.getItemName(), logBaseDto.getNote());
					break;
				default:
					break;
				
				}
				break;
			case DATA_CORRECT:
				switch (itemNoEnum) {
				case ITEM_NO_1:
					dataReturn.put(a.getItemName(), logBaseDto.getUserIdLogin());
					break;
				case ITEM_NO_2:
					dataReturn.put(a.getItemName(), logBaseDto.getUserNameLogin());
					break;	
				case ITEM_NO_3:
					dataReturn.put(a.getItemName(), logBaseDto.getEmployeeCodeLogin());
					break;
				case ITEM_NO_4:
					dataReturn.put(a.getItemName(), logBaseDto.getIpAddress());
					break;
				case ITEM_NO_5:
					dataReturn.put(a.getItemName(), logBaseDto.getPcName());
					break;
				case ITEM_NO_6:
					dataReturn.put(a.getItemName(), logBaseDto.getAccount());
					break;
				case ITEM_NO_7:
					dataReturn.put(a.getItemName(), logBaseDto.getModifyDateTime());
					break;
				case ITEM_NO_8:
					dataReturn.put(a.getItemName(), logBaseDto.getEmploymentAuthorityName());
					break;
				case ITEM_NO_9:
					dataReturn.put(a.getItemName(), logBaseDto.getSalarytAuthorityName());
					break;
				case ITEM_NO_10:
					dataReturn.put(a.getItemName(), logBaseDto.getPersonelAuthorityName());
					break;
				case ITEM_NO_11:
					dataReturn.put(a.getItemName(), logBaseDto.getOfficeHelperAuthorityName());
					break;
				case ITEM_NO_12:
					dataReturn.put(a.getItemName(), logBaseDto.getAccountAuthorityName());
					break;
				case ITEM_NO_13:
					dataReturn.put(a.getItemName(), logBaseDto.getMyNumberAuthorityName());
					break;
				case ITEM_NO_14:
					dataReturn.put(a.getItemName(), logBaseDto.getGroupCompanyAddminAuthorityName());
					break;
				case ITEM_NO_15:
					dataReturn.put(a.getItemName(), logBaseDto.getCompanyAddminAuthorityName());
					break;
				case ITEM_NO_16:
					dataReturn.put(a.getItemName(), logBaseDto.getSystemAdminAuthorityName());
					break;
				case ITEM_NO_17:
					dataReturn.put(a.getItemName(), logBaseDto.getPersonalInfoAuthorityName());
					break;
				case ITEM_NO_18:
					dataReturn.put(a.getItemName(), logBaseDto.getMenuName());
					break;
				case ITEM_NO_19:
					dataReturn.put(a.getItemName(), logBaseDto.getUserIdTaget());
					break;
				case ITEM_NO_20:
					dataReturn.put(a.getItemName(), logBaseDto.getUserNameTaget());
					break;
				case ITEM_NO_21:
					dataReturn.put(a.getItemName(), logBaseDto.getEmployeeCodeTaget());
					break;
				case ITEM_NO_22:
					dataReturn.put(a.getItemName(), logBaseDto.getTarGetYmd());
					break;
				case ITEM_NO_23:
					dataReturn.put(a.getItemName(), logBaseDto.getTarGetYm());
					break;
				case ITEM_NO_24:
					dataReturn.put(a.getItemName(), logBaseDto.getTarGetY());
					break;
				case ITEM_NO_25:
					dataReturn.put(a.getItemName(), logBaseDto.getCatagoryCorection());
					break;
				case ITEM_NO_26:
					dataReturn.put(a.getItemName(), logBaseDto.getKeyString());
					break;
				case ITEM_NO_27:
					dataReturn.put(a.getItemName(), logBaseDto.getItemName());
					break;
				case ITEM_NO_28:
					dataReturn.put(a.getItemName(), logBaseDto.getItemvalueBefor());
					break;
				case ITEM_NO_29:
					dataReturn.put(a.getItemName(), logBaseDto.getItemvalueAppter());
					break;
				case ITEM_NO_30:
					dataReturn.put(a.getItemName(), logBaseDto.getItemContentValueBefor());
					break;
				case ITEM_NO_31:
					dataReturn.put(a.getItemName(), logBaseDto.getItemContentValueAppter());
					break;
				case ITEM_NO_32:
					dataReturn.put(a.getItemName(), logBaseDto.getNote());
					break;
				default:
					break;
				
				}
				break;

			default:
				break;
			}
			
		}
		return dataReturn;
	}
	

	@Override
	protected void handle(ExportServiceContext<LogParams> context) {
		LogParams params = context.getQuery();
		List<Map<String, Object>> dataSource = new ArrayList<>();
		List<String> headers = this.getTextHeader(params);	
		dataSource= getDataSource(params, params.getLstHeaderDto());		
		String employeeCode = AppContexts.user().employeeCode();
		CSVFileData fileData = new CSVFileData(
				PGID + "_" + GeneralDateTime.now().toString("yyyyMMddHHmmss") + "_" + employeeCode + FILE_EXTENSION, headers, dataSource);
		generator.generate(context.getGeneratorContext(), fileData);
	}
}
