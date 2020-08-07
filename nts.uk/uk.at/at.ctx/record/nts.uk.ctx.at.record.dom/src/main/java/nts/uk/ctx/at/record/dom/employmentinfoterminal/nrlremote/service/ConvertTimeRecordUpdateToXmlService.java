package nts.uk.ctx.at.function.app.nrlremote;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.uk.ctx.at.function.app.nrl.xml.Element;
import nts.uk.ctx.at.function.app.nrl.xml.Frame;
import nts.uk.ctx.at.function.app.nrl.xml.Item;
import nts.uk.ctx.at.function.app.nrlremote.xml.NRLRemoteDataXml;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.MacAddress;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.EmpInfoTerminalRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetFormatList;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetUpdateList;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.dto.TimeRecordSettingInfoDto;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.repo.TimeRecordSetFormatListRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.repo.TimeRecordSetUpdateListRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.service.SendTimeRecordSetInfoService;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;

/**
 * @author ThanhNX
 *
 *         タイムレコード更新をXmlに変換する
 */
@Stateless
public class ConvertTimeRecordUpdateToXml {

	@Inject
	private EmpInfoTerminalRepository empInfoTerminalRepository;

	@Inject
	private TimeRecordSetFormatListRepository timeRecordSetFormatListRepository;

	@Inject
	private TimeRecordSetUpdateListRepository trSetUpdateListRepository;

	// [1] 変換する
	public NRLRemoteDataXml convertToXml(Frame frame) {

		// $NRL_No = $フレームxml.アイテムを選ぶ("NRL_No");
		Item mac = frame.getItem(Element.MAC_ADDR);
		RequireImpl impl = new RequireImpl(empInfoTerminalRepository, timeRecordSetFormatListRepository,
				trSetUpdateListRepository);
		Optional<TimeRecordSettingInfoDto> settingUpdateOpt = SendTimeRecordSetInfoService.send(impl,
				new MacAddress(mac.getValue()), new ContractCode("000000000000"));

		if (!settingUpdateOpt.isPresent())
			return null;

		return new NRLRemoteDataXml("", createPayLoad(settingUpdateOpt.get()));

	}

	// [1] payloadを作る
	private String createPayLoad(TimeRecordSettingInfoDto settingDto) {
		if (settingDto.getLstUpdateRecept().isEmpty())
			return "";
		StringBuilder builder = new StringBuilder("");
		settingDto.getLstUpdateRecept().stream().forEach(x -> {
			val master = settingDto.getLstReceptFormat().stream()
					.filter(y -> y.getVariableName().equals(x.getVariableName())).findFirst();
			builder.append(x.getVariableName());
			builder.append("=");
			builder.append(x.getUpdateValue());
			master.ifPresent(data -> builder.append(data.getRebootFlg().equals("1") ? ",1" : ""));
			builder.append("@");
		});
		return StringUtils.removeEnd(builder.toString(), "@");
	}

	@AllArgsConstructor
	public class RequireImpl implements SendTimeRecordSetInfoService.Require {

		private final EmpInfoTerminalRepository empInfoTerminalRepository;

		private final TimeRecordSetFormatListRepository timeRecordSetFormatListRepository;

		private final TimeRecordSetUpdateListRepository trSetUpdateListRepository;

		@Override
		public Optional<EmpInfoTerminal> getEmpInfoTerWithMac(MacAddress macAdd, ContractCode contractCode) {
			return empInfoTerminalRepository.getEmpInfoTerWithMac(macAdd, contractCode);
		}

		@Override
		public Optional<TimeRecordSetUpdateList> findSettingUpdate(EmpInfoTerminalCode empInfoTerCode,
				ContractCode contractCode) {
			return trSetUpdateListRepository.findSettingUpdate(empInfoTerCode, contractCode);
		}

		@Override
		public Optional<TimeRecordSetFormatList> findSetFormat(EmpInfoTerminalCode empInfoTerCode,
				ContractCode contractCode) {
			return timeRecordSetFormatListRepository.findSetFormat(empInfoTerCode, contractCode);
		}

	}
}
