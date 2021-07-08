package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.uk.ctx.at.record.dom.adapter.employmentinfoterminal.infoterminal.EmpDataImport;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author xuannt
 *	UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.就業情報端末.端末情報.1000人を超えるかチェックする
 *
 */
public class CheckExceededThousandPeoplesService {
	//	チェックする(require, 契約コード, 端末コード, 選択した社員IDList): 1000人を超えるかチェックするOUTPUT
	public static CheckExceededThousandPeoplesOutput checkExceed(Require require, ContractCode contractCode, EmpInfoTerminalCode empInfoTerCode, List<EmployeeId> selectedEmpID){
		CheckExceededThousandPeoplesOutput tempoOutput = new CheckExceededThousandPeoplesOutput(false, 0);
		int numberEmpOfOtherCompany = 0;
		//	1. 社員IDListを取得する(契約コード、端末コード): List<社員ID>
		 TimeRecordReqSetting timeRecordReqSetting = require.getEmpInfoListRequest(contractCode, empInfoTerCode).orElse(null);
		 if(null != timeRecordReqSetting){
			String companyId = AppContexts.user().companyId();
			// registed: loginCompany + otherCompany
			 List<EmpDataImport> empDataList= require.getEmpData(timeRecordReqSetting.getEmployeeIds());
			 List<EmployeeId> empListOfOtherCompany = empDataList.stream()
					 											 .filter(e  -> !e.getCompanyId().equals(companyId))
					 											 .map(e -> new EmployeeId(e.getEmployeeId()))
					 											 .collect(Collectors.toList());
			 if(!empListOfOtherCompany.isEmpty())
				 numberEmpOfOtherCompany = empListOfOtherCompany.size();
			 if(!selectedEmpID.isEmpty() && (numberEmpOfOtherCompany + selectedEmpID.size() > 1000)){
				 tempoOutput.setExceeded(true);
				 tempoOutput.setNumberExceeded(numberEmpOfOtherCompany + selectedEmpID.size() - 1000);
			 } 
		 }	 
		return tempoOutput;
	}
	public static interface Require {
		//	[R-1] 就業情報端末のリクエスト一覧を取得する: 就業情報端末のリクエスト一覧Repository.取得する(契約コード、端末コード)
		Optional<TimeRecordReqSetting> getEmpInfoListRequest(ContractCode contractCode, EmpInfoTerminalCode empInfoTerCode);
		//	[R-2] 社員IDListから管理情報を取得する: 社員IDListから管理情報を取得するAdapter(契約コード、端末コード)
		List<EmpDataImport> getEmpData(List<EmployeeId> empIDList); 
	}


}
