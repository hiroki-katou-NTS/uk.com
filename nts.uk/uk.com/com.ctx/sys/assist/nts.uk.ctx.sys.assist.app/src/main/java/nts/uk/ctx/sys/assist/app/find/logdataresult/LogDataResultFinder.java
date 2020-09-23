package nts.uk.ctx.sys.assist.app.find.logdataresult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.sys.assist.app.find.params.LogDataParams;
import nts.uk.ctx.sys.assist.app.find.resultofdeletion.ResultOfDeletionDto;
import nts.uk.ctx.sys.assist.app.find.resultofdeletion.ResultOfDeletionFinder;
import nts.uk.ctx.sys.assist.app.find.resultofrestoration.ResultOfRestorationDto;
import nts.uk.ctx.sys.assist.app.find.resultofrestoration.ResultOfRestorationFinder;
import nts.uk.ctx.sys.assist.app.find.resultofsaving.ResultOfSavingDto;
import nts.uk.ctx.sys.assist.app.find.resultofsaving.ResultOfSavingFinder;
import nts.uk.ctx.sys.assist.dom.reference.record.EmpBasicInfoAdapter;
import nts.uk.ctx.sys.assist.dom.reference.record.EmpBasicInfoImport;
import nts.uk.ctx.sys.assist.dom.storage.ResultLogSaving;

@Stateless
public class LogDataResultFinder {
	
	@Inject
	private ResultOfSavingFinder resultOfSavingFinder;
	
	@Inject
	private ResultOfRestorationFinder resultOfRestorationFinder;
	
	@Inject
	private ResultOfDeletionFinder resultOfDeletionFinder;
	
	@Inject
	private EmpBasicInfoAdapter personEmpBasicInfoAdapter;
	
	
	public List<LogDataResultDto> getLogDataResult(LogDataParams logDataParams){
		int recordType = logDataParams.getRecordType();

		 List<LogDataResultDto> logDataResults = new ArrayList<LogDataResultDto>();
		if(recordType == 9) {
			//step データ保存の保存結果を取得
			List<ResultOfSavingDto> resultOfSavings = resultOfSavingFinder.getResultOfSaving(logDataParams);
			for( ResultOfSavingDto resultOfSaving : resultOfSavings) {
				
					//step 社員ID(List)から個人社員基本情報を取得
					String employeeCode = "";
					String employeeName = "";
					List<EmpBasicInfoImport>  personEmpBasicInfos = personEmpBasicInfoAdapter.getEmployeeCodeByEmpId(resultOfSaving.getPractitioner());
					if (!CollectionUtil.isEmpty(personEmpBasicInfos)) {
						EmpBasicInfoImport personEmpBasicInfo = personEmpBasicInfos.get(0);
						employeeCode = personEmpBasicInfo.getEmployeeCode();
						employeeName= personEmpBasicInfo.getBusinessName();
					}
					 String ipAddress = resultOfSaving.getLoginInfo().getIpAddress();
					 String pcName = resultOfSaving.getLoginInfo().getPcName();
					 String account = resultOfSaving.getLoginInfo().getAccount();
					 GeneralDateTime startDateTime = resultOfSaving.getSaveStartDatetime();
					 GeneralDateTime endDateTime = resultOfSaving.getSaveEndDatetime();
					 int form = resultOfSaving.getSaveForm();
					 String name = resultOfSaving.getSaveName();
					 String fileId = resultOfSaving.getFileId();
					 String fileName = resultOfSaving.getSaveFileName();
					 long fileSize = resultOfSaving.getFileSize();
					 Integer status = resultOfSaving.getSaveStatus();
					 Integer targetNumberPeople = resultOfSaving.getTargetNumberPeople();
					 String setCode = resultOfSaving.getSaveSetCode();
					 int isDeletedFilesFlg = -1;
					 List<LogResultDto> logResult = new ArrayList<LogResultDto>();
					 for(ResultLogSaving resultLogSaving :resultOfSaving.getListResultLogSavings()) {
						 
						//step 社員ID(List)から個人社員基本情報を取得
						 String errorEmployeeId = "";
							List<EmpBasicInfoImport>  listpersonEmpBasicInfo = personEmpBasicInfoAdapter.getEmployeeCodeByEmpId(resultLogSaving.getErrorEmployeeId());
							if (!CollectionUtil.isEmpty(listpersonEmpBasicInfo)) {
								EmpBasicInfoImport personEmpBasicInfo = listpersonEmpBasicInfo.get(0);
								errorEmployeeId = personEmpBasicInfo.getEmployeeCode();
							}
						 int logNumber = resultLogSaving.getLogNumber();
						 String processingContent = resultLogSaving.getLogContent().v();
						 String errorContent = resultLogSaving.getErrorContent().v();
						 String contentSql = null;
						 GeneralDate errorDate = resultLogSaving.getErrorDate();
						 logResult.add(new LogResultDto(logNumber,processingContent,errorContent,contentSql,errorDate,errorEmployeeId));
					 }
					
					 LogDataResultDto logDataResult =  
							 new LogDataResultDto(
									 	ipAddress,pcName, account,
										employeeCode,employeeName,
										startDateTime, endDateTime,
										form, name,fileId,fileName,
										fileSize,status,targetNumberPeople,
										setCode,isDeletedFilesFlg,logResult
									);
				//step F：各種記録の絞り込み処理
				if(filterLogResultOfSaving(logDataResult,logDataParams.getListCondition())) {
					//step 「データ保存・復旧・削除の操作ログ」を作る
					 logDataResults.add(logDataResult);
				}
			}
			//step 作った「データ保存・復旧・削除の操作ログ」を返す
			return logDataResults;
		} else if(recordType == 10) {
			//step データ復旧の結果を取得
			List<ResultOfRestorationDto> resultOfRestorations = resultOfRestorationFinder.getResultOfRestoration(logDataParams);
		} else if(recordType == 11) {
			//step データ削除の保存結果を取得
			List<ResultOfDeletionDto> getResultOfDeletion = resultOfDeletionFinder.getResultOfDeletion(logDataParams);
		}
		return null;
	}
	
	
    private boolean filterLogResultOfSaving(LogDataResultDto logDataResult, List<Condition> listCondition) {

        if (this.filterLogByItemNo(logDataResult.getIpAddress(), 1, listCondition)) {
            return false;
        };
        if (this.filterLogByItemNo(logDataResult.getPcName(), 2, listCondition)) {
            return false;
        };
        if (this.filterLogByItemNo(logDataResult.getAccount(), 3, listCondition)) {
            return false;
        };
        if (this.filterLogByItemNo(logDataResult.getEmployeeCode(), 4, listCondition)) {
            return false;
        };
        if (this.filterLogByItemNo(logDataResult.getEmployeeName(), 5, listCondition)) {
            return false;
        };
        if (this.filterLogByItemNo(String.valueOf(logDataResult.getStartDateTime()), 6, listCondition)) {
            return false;
        };
        if (this.filterLogByItemNo(String.valueOf(logDataResult.getForm()), 7, listCondition)) {
            return false;
        };
        if (this.filterLogByItemNo(logDataResult.getName(), 8, listCondition)) {
            return false;
        };
        if (this.filterLogByItemNo(logDataResult.getFileId(), 9, listCondition)) {
            return false;
        };
        if (this.filterLogByItemNo(String.valueOf(logDataResult.getFileSize()), 10, listCondition)) {
            return false;
        };
        if (this.filterLogByItemNo(String.valueOf(logDataResult.getStatus()), 11, listCondition)) {
            return false;
        };
        if (this.filterLogByItemNo(String.valueOf(logDataResult.getTargetNumberPeople()), 12, listCondition)) {
            return false;
        };
        if (this.filterLogByItemNo(logDataResult.getSetCode(), 13, listCondition)) {
            return false;
        };
        if (this.filterLogByItemNo(logDataResult.getFileName(), 14, listCondition)) {
            return false;
        };
        if (this.filterLogByItemNo(String.valueOf(logDataResult.getEndDateTime()), 15, listCondition)) {
            return false;
        };
        for(LogResultDto logResultDto : logDataResult.getLogResult()) {
        	
        	if (this.filterLogByItemNo(logResultDto.getProcessingContent(), 16, listCondition)) {
                return false;
            };
            if (this.filterLogByItemNo(logResultDto.getErrorContent(), 17, listCondition)) {
                return false;
            };
            if (this.filterLogByItemNo(String.valueOf(logResultDto.getErrorDate()), 18, listCondition)) {
                return false;
            };
            if (this.filterLogByItemNo(logResultDto.getErrorEmployeeId(), 19, listCondition)) {
                return false;
            };
        }
        return true;
    }
    
    
    private boolean filterLogByItemNo(String content,int  itemNo, List<Condition> listCondition) {
    	List<Condition> conditionArray = listCondition.stream().filter(condition -> condition.itemNo == itemNo).collect(Collectors.toList());
        if (conditionArray.size() == 0) {
            return true;
        }
        if (content == null || content.equals("")) {
            return false;
        }
        for (Condition condition : conditionArray) {
        	//EQUAL
            if (condition.symbol == 0) { 
                return (content == condition.condition);
                
            //DIFFERENT
            } else if (condition.symbol == 1) { 
                return (content != condition.condition);
                
            //INCLUDE
            } else if (condition.symbol == 2) { 
                return content.equals(condition.condition);
            }
        }
		return false;
    }
	

}
