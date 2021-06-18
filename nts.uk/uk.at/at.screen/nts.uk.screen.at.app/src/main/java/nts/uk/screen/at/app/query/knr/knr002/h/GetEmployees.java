package nts.uk.screen.at.app.query.knr.knr002.h;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.employmentinfoterminal.infoterminal.EmpDataImport;
import nts.uk.ctx.at.record.dom.adapter.employmentinfoterminal.infoterminal.GetMngInfoFromEmpIDListAdapter;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.TimeRecordReqSettingRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service.GetEmpIDListOfLoginCompanyService;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.query.model.employee.EmployeeInformation;
import nts.uk.query.model.employee.EmployeeInformationQuery;
import nts.uk.query.model.employee.EmployeeInformationRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * UKDesign.UniversalK.就業.KNR_就業情報端末.KNR002_就業情報端末の監視.Ｈ：個人情報選択ダイアログ.メニュー別OCD.Ｈ：送信予定の社員を取得する
 * 
 * @author xuannt
 *
 */
@Stateless
public class GetEmployees {
	// 社員の情報を取得する -> List<社員情報>
	@Inject
	private EmployeeInformationRepository empInfoRepo;
	@Inject
	private GetMngInfoFromEmpIDListAdapter getMngInfoFromEmpIDListAdapter;
	@Inject
	private TimeRecordReqSettingRepository timeRecordReqSettingRepository;

	/**
	 * @param empInforTerCode
	 * @return
	 */
	public List<GetEmployeesDto> getEmployees(String empInforTerCode) {
		ContractCode contractCode = new ContractCode(AppContexts.user().contractCode());
		// #115008
		// 1. 取得する(Require, 契約コード, 就業情報端末コード): List<社員ID>
		RequireEmpList requireEmpList = new RequireEmpList(getMngInfoFromEmpIDListAdapter, timeRecordReqSettingRepository);
		List<EmployeeId> empIdList = GetEmpIDListOfLoginCompanyService.getEmpList(requireEmpList, contractCode,
				new EmpInfoTerminalCode(empInforTerCode));
		// 2. <call>(社員ID<List>): 社員情報<List>
		EmployeeInformationQuery param = EmployeeInformationQuery.builder()
				.employeeIds(empIdList.stream().map(e -> e.v()).collect(Collectors.toList()))
				.referenceDate(GeneralDate.today()).toGetWorkplace(true).toGetDepartment(false).toGetPosition(false)
				.toGetEmployment(false).toGetClassification(false).toGetEmploymentCls(false).build();

		List<EmployeeInformation> listEmpInfo = empInfoRepo.find(param);

		return listEmpInfo.stream().map(e -> {
			GetEmployeesDto dto = new GetEmployeesDto();
			dto.setEmployeeId(e.getEmployeeId());
			dto.setEmployeeCode(e.getEmployeeCode());
			dto.setBusinessName(e.getBusinessName());
			dto.setBusinessNameKana(e.getBusinessNameKana());
			dto.setWorkplaceName(e.getWorkplace().isPresent() ? e.getWorkplace().get().getWorkplaceName() : "");
			return dto;
		}).collect(Collectors.toList());
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

}
