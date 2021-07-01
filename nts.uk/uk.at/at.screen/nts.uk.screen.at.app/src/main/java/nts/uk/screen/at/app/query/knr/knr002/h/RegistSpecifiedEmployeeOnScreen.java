package nts.uk.screen.at.app.query.knr.knr002.h;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.error.BusinessException;
import nts.uk.ctx.at.record.app.command.knr.knr002.h.MakeSelectedEmployeesCommand;
import nts.uk.ctx.at.record.app.command.knr.knr002.h.MakeSelectedEmployeesCommandHandler;
import nts.uk.ctx.at.record.dom.adapter.employmentinfoterminal.infoterminal.EmpDataImport;
import nts.uk.ctx.at.record.dom.adapter.employmentinfoterminal.infoterminal.GetMngInfoFromEmpIDListAdapter;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.TimeRecordReqSettingRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service.CheckExceededThousandPeoplesOutput;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service.CheckExceededThousandPeoplesService;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service.GetEmpIDListOfLoginCompanyService;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author xuannt
 *	UKDesign.UniversalK.就業.KNR_就業情報端末.KNR002_就業情報端末の監視.Ｈ：個人情報選択ダイアログ.メニュー別OCD.Ｈ：選択した社員を送信データとする
 *
 */
@Stateless
public class RegistSpecifiedEmployeeOnScreen {
	@Inject
	private GetMngInfoFromEmpIDListAdapter getMngInfoFromEmpIDListAdapter;
	@Inject
	private TimeRecordReqSettingRepository timeRecordReqSettingRepository;
	@Inject
	private MakeSelectedEmployeesCommandHandler makeSelectedEmployeesCommandHandler;
	
	
	public void registSpecifiedEmployeeOnScreen(String terCode, List<String> empList) {
		// 1. チェックする(Require, 契約コード, 就業情報端末コード, 社員ID): 1000人を超えるかチェックするOUTPUT
		if(empList.isEmpty())
			return;
		ContractCode contractCode = new ContractCode(AppContexts.user().contractCode());
		EmpInfoTerminalCode empInforTerCode = new EmpInfoTerminalCode(terCode);
		List<EmployeeId> selectedEmpList = empList.stream().map(e -> new EmployeeId(e))
															.collect(Collectors.toList());
				
		CheckExceededThousandPeoplesOutput tempo = null;
		RequireEmpList requireEmpList = new RequireEmpList(getMngInfoFromEmpIDListAdapter, timeRecordReqSettingRepository);
		RequireCheckExceeded requireCheckExceeded = new RequireCheckExceeded(getMngInfoFromEmpIDListAdapter, timeRecordReqSettingRepository);
		tempo = CheckExceededThousandPeoplesService.checkExceed(requireCheckExceeded, contractCode, empInforTerCode, selectedEmpList);
		//	2. 「超えるか」＝true
		if(tempo.isExceeded())
			throw new BusinessException("Msg_2149", new Integer(tempo.getNumberExceeded()).toString());
		//	3. 取得する(require, 契約コード, 端末コード): List<社員ID>
		List<EmployeeId> loginCompanyEmployeesID = GetEmpIDListOfLoginCompanyService.getEmpList(requireEmpList, contractCode, empInforTerCode);
		//	4. <call>(契約コード、端末コード、ログイン会社の社員ID(List)、画面で指定した社員ID(List))
		this.makeSelectedEmployeesCommandHandler.handle(new MakeSelectedEmployeesCommand(empInforTerCode, loginCompanyEmployeesID, selectedEmpList));
	}
	
	@AllArgsConstructor
	private static class RequireEmpList implements GetEmpIDListOfLoginCompanyService.Require {
		
		private GetMngInfoFromEmpIDListAdapter getMngInfoFromEmpIDListAdapter;
		private TimeRecordReqSettingRepository timeRecordReqSettingRepository;
		
		@Override
		public Optional<TimeRecordReqSetting> getEmpInfoListRequest(ContractCode contractCode,
				EmpInfoTerminalCode empInfoTerCode) {
			return timeRecordReqSettingRepository.getTimeRecordEmployee(empInfoTerCode, contractCode);
		}

		@Override
		public List<EmpDataImport> getEmpData(List<EmployeeId> empIDList) {
			return getMngInfoFromEmpIDListAdapter.getEmpData(empIDList.stream().map(e -> e.v()).collect(Collectors.toList()));
		}
	}
	
	@AllArgsConstructor
	private static class RequireCheckExceeded implements CheckExceededThousandPeoplesService.Require {
		
		private GetMngInfoFromEmpIDListAdapter getMngInfoFromEmpIDListAdapter;
		private TimeRecordReqSettingRepository timeRecordReqSettingRepository;
		@Override
		public Optional<TimeRecordReqSetting> getEmpInfoListRequest(ContractCode contractCode,
				EmpInfoTerminalCode empInfoTerCode) {
			return timeRecordReqSettingRepository.getTimeRecordEmployee(empInfoTerCode, contractCode);
		}
		@Override
		public List<EmpDataImport> getEmpData(List<EmployeeId> empIDList) {
			return getMngInfoFromEmpIDListAdapter.getEmpData(empIDList.stream().map(e -> e.v()).collect(Collectors.toList()));
		}	
	}

}
