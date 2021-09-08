package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service;

import java.util.Collections;
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
 *	UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.就業情報端末.端末情報.ログイン会社の社員IDListを取得する
 *
 */
public class GetEmpIDListOfLoginCompanyService {
	
	//	取得する (require: Require, 契約コード: 契約コード, 端末コード: 就業情報端末コード): List<社員ID>
	 public static List<EmployeeId> getEmpList(Require require, ContractCode contractCode, EmpInfoTerminalCode empInfoTerCode){
		List<EmployeeId> empIDList = Collections.emptyList();
		 //	1. *get(契約コード、コード): Optional<就業情報端末のリクエスト一覧>
		 TimeRecordReqSetting timeRecordReqSetting = require.getEmpInfoListRequest(contractCode, empInfoTerCode).orElse(null);
		 if(null == timeRecordReqSetting)
			 return empIDList;
		 //	2. [データがある] 社員IDを取得する(就業情報端末のリクエスト一覧.社員ID（List）): List<社員データ> 
		 List<EmployeeId> employeeIds = timeRecordReqSetting.getEmployeeIds();
		 //	String companyId = timeRecordReqSetting.getCompanyId().v();
		 String companyId = AppContexts.user().companyId();
		 if(employeeIds.isEmpty())
			 return empIDList;
		 List<EmpDataImport> empDataList= require.getEmpData(employeeIds);
		 empIDList = empDataList.stream()
				 				.filter(e -> e.getCompanyId().equals(companyId))
				 				.map(e -> new EmployeeId(e.getEmployeeId()))
				 				.collect(Collectors.toList());
		return empIDList;
	}
	
	
	public static interface Require {
		//	[R-1] 就業情報端末のリクエスト一覧を取得する: 就業情報端末のリクエスト一覧Repository.取得する(契約コード、端末コード)	
		Optional<TimeRecordReqSetting> getEmpInfoListRequest(ContractCode contractCode, EmpInfoTerminalCode empInfoTerCode);
		//	[R-2] 社員IDListから管理情報を取得する: 社員IDListから管理情報を取得するAdapter(契約コード、端末コード)
		List<EmpDataImport> getEmpData(List<EmployeeId> empIDList); 
	}

}
