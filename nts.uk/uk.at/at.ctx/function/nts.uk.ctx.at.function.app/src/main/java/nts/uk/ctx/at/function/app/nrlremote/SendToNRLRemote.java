package nts.uk.ctx.at.function.app.nrlremote;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.uk.ctx.at.function.app.nrl.Command;
import nts.uk.ctx.at.function.app.nrl.NRContentList;
import nts.uk.ctx.at.function.app.nrl.crypt.Codryptofy;
import nts.uk.ctx.at.function.app.nrl.data.ItemSequence.MapItem;
import nts.uk.ctx.at.function.app.nrl.request.NRLRequest;
import nts.uk.ctx.at.function.app.nrl.request.Named;
import nts.uk.ctx.at.function.app.nrl.request.ResourceContext;
import nts.uk.ctx.at.function.app.nrl.xml.Frame;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.MacAddress;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.EmpInfoTerminalRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetFormatList;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetUpdateList;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.repo.TimeRecordSetFormatListRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.repo.TimeRecordSetUpdateListRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.service.ConvertTimeRecordUpdateToXmlService;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;

@RequestScoped
@Named(value = Command.TR_REMOTE)
public class SendToNRLRemote extends NRLRequest<Frame> {

	@Inject
	private EmpInfoTerminalRepository empInfoTerminalRepository;

	@Inject
	private TimeRecordSetFormatListRepository timeRecordSetFormatListRepository;

	@Inject
	private TimeRecordSetUpdateListRepository trSetUpdateListRepository;

	@Override
	public void sketch(String empInfoTerCode, ResourceContext<Frame> context) {
		String payload = "";
		RequireImpl impl = new RequireImpl(empInfoTerminalRepository, timeRecordSetFormatListRepository,
				trSetUpdateListRepository);
		val xml = ConvertTimeRecordUpdateToXmlService.convertToXml(impl,
				new ContractCode(context.getTerminal().getContractCode()), new EmpInfoTerminalCode(empInfoTerCode));
		if (xml.isPresent()) {
			payload = Codryptofy.convertToShiftJIS(xml.get());
		}
		payload = Codryptofy.paddingWithByte(payload, 51200);
		List<MapItem> items = NRContentList.createFieldForPadding2(Command.TR_REMOTE,
				Optional.ofNullable(Integer.toHexString(51242)), context.getTerminal());
		context.collect(items, payload);
	}

	@Override
	public String responseLength() {
		return null;
	}

	@AllArgsConstructor
	public class RequireImpl implements ConvertTimeRecordUpdateToXmlService.Require {

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
