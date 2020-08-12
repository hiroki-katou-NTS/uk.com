package nts.uk.ctx.at.record.pubimp.employmentinfoterminal.infoterminal;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.TimeRecordReqSettingRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service.SendTimeRecordSettingService;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.SendTimeRecordSettingExport;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.SendTimeRecordSettingExport.SettingExportBuilder;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.SendTimeRecordSettingPub;

@Stateless
public class SendTimeRecordSettingPubImpl implements SendTimeRecordSettingPub {

	@Inject
	private TimeRecordReqSettingRepository timeRecordReqSettingRepository;

	@Override
	public Optional<SendTimeRecordSettingExport> send(Integer empInfoTerCode, String contractCode) {

		RequireImpl impl = new RequireImpl(timeRecordReqSettingRepository);

		return SendTimeRecordSettingService.send(impl, new EmpInfoTerminalCode(empInfoTerCode), new ContractCode(contractCode)).map(setting -> {
			return new SendTimeRecordSettingExport(new SettingExportBuilder(setting.isRequest1(), setting.isRequest2(),
					setting.isRequest3(), setting.isRequest4(), setting.isRequest6()).createReq7(setting.isRequest7())
							.createReq8(setting.isRequest8()).createReq9(setting.isRequest9())
							.createReq10(setting.isRequest10()).createReq11(setting.isRequest11()));
		});
	}

	@AllArgsConstructor
	public static class RequireImpl implements SendTimeRecordSettingService.Require {

		private final TimeRecordReqSettingRepository timeRecordReqSettingRepository;

		@Override
		public Optional<TimeRecordReqSetting> getTimeRecordReqSetting(EmpInfoTerminalCode empInfoTerCode, ContractCode contractCode) {
			return timeRecordReqSettingRepository.getTimeRecordReqSetting(empInfoTerCode, contractCode);
		}

	}

}
