package nts.uk.ctx.at.record.pubimp.employmentinfoterminal.infoterminal;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeAdapter;
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeDto;
import nts.uk.ctx.at.record.dom.adapter.workplace.SWkpHistRcImported;
import nts.uk.ctx.at.record.dom.adapter.workplace.SyWorkplaceAdapter;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.TimeRecordReqSettingRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.send.SendPerInfoName;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service.SendPerInfoNameService;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.SendNRDataPub;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.SendPerInfoNameExport;

@Stateless
public class SendPerInfoNamePubImpl implements SendNRDataPub<List<SendPerInfoNameExport>> {

	@Inject
	private TimeRecordReqSettingRepository timeRecordReqSettingRepository;

	@Inject
	private SyWorkplaceAdapter syWorkplaceAdapter;

	@Inject
	private StampCardRepository stampCardRepository;

	@Inject
	private EmployeeAdapter employeeAdapter;

	@Override
	public List<SendPerInfoNameExport> send(String empInfoTerCode, String contractCode) {

		RequireImpl require = new RequireImpl(timeRecordReqSettingRepository, syWorkplaceAdapter, stampCardRepository,
				employeeAdapter);

		List<SendPerInfoName> lstName = SendPerInfoNameService.send(require, new EmpInfoTerminalCode(empInfoTerCode), new ContractCode(contractCode));
		return lstName.stream().map(x -> new SendPerInfoNameExport(x.getIdNumber(), x.getPerName(),
				x.getDepartmentCode(), x.getCompanyCode(), x.getReservation(), x.getPerCode()))
				.collect(Collectors.toList());
	}

	@AllArgsConstructor
	public static class RequireImpl implements SendPerInfoNameService.Require {

		private final TimeRecordReqSettingRepository timeRecordReqSettingRepository;

		private final SyWorkplaceAdapter syWorkplaceAdapter;

		private final StampCardRepository stampCardRepository;

		private final EmployeeAdapter employeeAdapter;

		@Override
		public List<SWkpHistRcImported> findBySid(List<String> employeeIds, GeneralDate baseDate) {
			return syWorkplaceAdapter.findBySid(employeeIds, baseDate);
		}

		@Override
		public List<StampCard> getByCardNoAndContractCode(List<String> employeeIds) {
			return stampCardRepository.getLstStampCardByLstSid(employeeIds);
		}

		@Override
		public List<EmployeeDto> getByListSID(List<String> employeeIds) {
			return employeeAdapter.getByListSID(employeeIds);
		}

		@Override
		public Optional<TimeRecordReqSetting> getTimeRecordReqSetting(EmpInfoTerminalCode empInfoTerCode, ContractCode contractCode) {
			return timeRecordReqSettingRepository.getTimeRecordReqSetting(empInfoTerCode, contractCode);
		}

	}

}
