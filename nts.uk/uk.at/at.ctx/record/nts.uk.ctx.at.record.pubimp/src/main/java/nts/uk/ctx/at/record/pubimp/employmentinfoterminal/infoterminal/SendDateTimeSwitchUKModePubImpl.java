package nts.uk.ctx.at.record.pubimp.employmentinfoterminal.infoterminal;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.TimeRecordReqSettingRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service.SendDateTimeSwitchUKModeService;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.DateTimeSwitchUKModeExport;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.SendDateTimeSwitchUKModePub;

/**
 * @author thanh_nx
 *
 */
@Stateless
public class SendDateTimeSwitchUKModePubImpl implements SendDateTimeSwitchUKModePub {

	@Inject
	private TimeRecordReqSettingRepository timeRecordReqSettingRepository;

	@Override
	public Optional<DateTimeSwitchUKModeExport> process(String empInfoTerCode, String contractCode) {
		RequireImpl impl = new RequireImpl(timeRecordReqSettingRepository);
		return SendDateTimeSwitchUKModeService
				.process(impl, new EmpInfoTerminalCode(empInfoTerCode), new ContractCode(contractCode))
				.map(x -> new DateTimeSwitchUKModeExport(x.getYear(), x.getMonth(), x.getDay(), x.getHour(),
						x.getMinute(), x.getSecond(), x.getWeek()));
	}

	@AllArgsConstructor
	public class RequireImpl implements SendDateTimeSwitchUKModeService.Require {

		private final TimeRecordReqSettingRepository timeRecordReqSettingRepository;

		@Override
		public Optional<TimeRecordReqSetting> getTimeRecordReqSetting(EmpInfoTerminalCode empInfoTerCode,
				ContractCode contractCode) {
			return timeRecordReqSettingRepository.getTrRequest(empInfoTerCode, contractCode);
		}

	}
}
