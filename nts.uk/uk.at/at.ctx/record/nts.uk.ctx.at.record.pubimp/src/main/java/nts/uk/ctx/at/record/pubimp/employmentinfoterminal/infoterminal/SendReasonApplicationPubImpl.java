package nts.uk.ctx.at.record.pubimp.employmentinfoterminal.infoterminal;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.record.dom.adapter.application.setting.ApplicationReasonRc;
import nts.uk.ctx.at.record.dom.adapter.application.setting.ApplicationReasonRcAdapter;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.TimeRecordReqSettingRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service.SendReasonApplicationService;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.SendNRDataPub;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.SendReasonApplicationExport;

@Stateless
public class SendReasonApplicationPubImpl implements SendNRDataPub<List<SendReasonApplicationExport>> {

	@Inject
	private TimeRecordReqSettingRepository timeRecordReqSettingRepository;

	@Inject
	private ApplicationReasonRcAdapter applicationReasonRcAdapter;

	@Override
	public List<SendReasonApplicationExport> send(String empInfoTerCode, String contractCode) {

		RequireImpl requireImpl = new RequireImpl(timeRecordReqSettingRepository, applicationReasonRcAdapter);

		return SendReasonApplicationService.send(requireImpl, new EmpInfoTerminalCode(empInfoTerCode), new ContractCode(contractCode)).stream()
				.map(x -> {
					return new SendReasonApplicationExport(x.getAppReasonNo(), x.getAppReasonName());
				}).collect(Collectors.toList());
	}

	@AllArgsConstructor
	public static class RequireImpl implements SendReasonApplicationService.Require {

		private final TimeRecordReqSettingRepository timeRecordReqSettingRepository;

		private final ApplicationReasonRcAdapter applicationReasonRcAdapter;

		@Override
		public List<ApplicationReasonRc> getReasonByAppType(String companyId, List<Integer> lstAppType) {
			return applicationReasonRcAdapter.getReasonByAppType(companyId, lstAppType);
		}

		@Override
		public Optional<TimeRecordReqSetting> getTimeRecordReqSetting(EmpInfoTerminalCode empInfoTerCode, ContractCode contractCode) {

			return timeRecordReqSettingRepository.getTimeRecordReqSetting(empInfoTerCode, contractCode);
		}

	}

}
