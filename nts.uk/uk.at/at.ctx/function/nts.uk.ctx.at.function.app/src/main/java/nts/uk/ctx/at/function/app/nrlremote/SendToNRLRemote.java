package nts.uk.ctx.at.function.app.nrlremote;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.function.app.nrl.Command;
import nts.uk.ctx.at.function.app.nrl.crypt.Codryptofy;
import nts.uk.ctx.at.function.app.nrl.data.FrameItemArranger;
import nts.uk.ctx.at.function.app.nrl.data.ItemSequence.MapItem;
import nts.uk.ctx.at.function.app.nrl.request.NRLRequest;
import nts.uk.ctx.at.function.app.nrl.request.Named;
import nts.uk.ctx.at.function.app.nrl.request.ResourceContext;
import nts.uk.ctx.at.function.app.nrl.xml.Element;
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
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.xml.NRLRemoteDataXml;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;

@RequestScoped
@Named(value = Command.TR_REMOTE, decrypt = true)
public class SendToNRLRemote extends NRLRequest<Frame> {

	@Inject
	private EmpInfoTerminalRepository empInfoTerminalRepository;

	@Inject
	private TimeRecordSetFormatListRepository timeRecordSetFormatListRepository;

	@Inject
	private TimeRecordSetUpdateListRepository trSetUpdateListRepository;

	@Override
	public void sketch(ResourceContext<Frame> context) {
		List<MapItem> items = new ArrayList<>();
		items.add(FrameItemArranger.SOH());
		items.add(new MapItem(Element.HDR, Command.TR_REMOTE.Response));
		String payload = "";
		Frame frame = context.getEntity();
		RequireImpl impl = new RequireImpl(empInfoTerminalRepository, timeRecordSetFormatListRepository,
				trSetUpdateListRepository);
		NRLRemoteDataXml xml = ConvertTimeRecordUpdateToXmlService.convertToXml(impl,
				frame.getItem(Element.MAC_ADDR).getValue());
		if (xml != null) {
			payload = xml.getPayload();
		}
		byte[] payloadBytes = Codryptofy.decode(payload);
		int length = payloadBytes.length + 32;
		items.add(new MapItem(Element.LENGTH, Integer.toHexString(length)));

		items.add(FrameItemArranger.Version());
		items.add(FrameItemArranger.FlagEndNoAck());
		items.add(FrameItemArranger.NoFragment());
		items.add(new MapItem(Element.NRL_NO, context.getTerminal().getNrlNo()));
		items.add(new MapItem(Element.MAC_ADDR, context.getTerminal().getMacAddress()));
		items.add(FrameItemArranger.ZeroPadding());
		context.collectEncrypt(items, payload);

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
