package nts.uk.ctx.at.function.app.nrl.request;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;

import nts.uk.ctx.at.function.app.nrl.Command;
import nts.uk.ctx.at.function.app.nrl.crypt.Codryptofy;
import nts.uk.ctx.at.function.app.nrl.data.FrameItemArranger;
import nts.uk.ctx.at.function.app.nrl.data.ItemSequence.MapItem;
import nts.uk.ctx.at.function.app.nrl.xml.Element;
import nts.uk.ctx.at.function.app.nrl.xml.Frame;

/**
 * Personal info request.
 * 
 * @author manhnd
 */
@RequestScoped
@Named(Command.PERSONAL_INFO)
public class PersonalInfoRequest extends NRLRequest<Frame> {

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.app.nrl.request.NRLRequest#sketch(nts.uk.ctx.at.function.app.nrl.request.ResourceContext)
	 */
	@Override
	public void sketch(ResourceContext<Frame> context) {
		List<MapItem> items = new ArrayList<>();
		items.add(FrameItemArranger.SOH());
		items.add(new MapItem(Element.HDR, Command.PERSONAL_INFO.Response));
		// TODO: Get personal info from DB, count records
		String payload = "";
		byte[] payloadBytes = Codryptofy.decode(payload);
		int length = payloadBytes.length + 32;
		items.add(new MapItem(Element.LENGTH, Integer.toHexString(length)));
		items.add(FrameItemArranger.Version());
		items.add(FrameItemArranger.FlagEndNoAck());
		items.add(FrameItemArranger.NoFragment());
		items.add(new MapItem(Element.NRL_NO, context.getTerminal().getNrlNo()));
		items.add(new MapItem(Element.MAC_ADDR, context.getTerminal().getMacAddress()));
		items.add(FrameItemArranger.ZeroPadding());
		// TODO: Number of records
		items.add(new MapItem(Element.NUMBER, ""));
		context.collectEncrypt(items, payload);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.app.nrl.request.NRLRequest#responseLength()
	 */
	@Override
	public String responseLength() {
		return null;
	}

}
