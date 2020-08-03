package nts.uk.ctx.at.function.app.nrlremote;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import nts.uk.ctx.at.function.app.nrl.Command;
import nts.uk.ctx.at.function.app.nrl.crypt.Codryptofy;
import nts.uk.ctx.at.function.app.nrl.data.FrameItemArranger;
import nts.uk.ctx.at.function.app.nrl.data.ItemSequence.MapItem;
import nts.uk.ctx.at.function.app.nrl.request.NRLRequest;
import nts.uk.ctx.at.function.app.nrl.request.Named;
import nts.uk.ctx.at.function.app.nrl.request.ResourceContext;
import nts.uk.ctx.at.function.app.nrl.xml.Element;
import nts.uk.ctx.at.function.app.nrl.xml.Frame;
import nts.uk.ctx.at.function.app.nrlremote.xml.NRLRemoteDataXml;

@RequestScoped
@Named(value = Command.TR_REMOTE, decrypt = true)
public class TimeRecordRemoteSetting extends NRLRequest<Frame> {

	@Inject
	private ConvertTimeRecordUpdateToXml convertTRRemote;

	@Override
	public void sketch(ResourceContext<Frame> context) {
		List<MapItem> items = new ArrayList<>();
		items.add(FrameItemArranger.SOH());
		items.add(new MapItem(Element.HDR, Command.TR_REMOTE.Response));
		String payload = "";
		NRLRemoteDataXml xml = convertTRRemote.convertToXml(context.getEntity());
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

}
