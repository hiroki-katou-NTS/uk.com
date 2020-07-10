package nts.uk.nrl.request;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;

import nts.uk.nrl.Command;
import nts.uk.nrl.data.FrameItemArranger;
import nts.uk.nrl.data.ItemSequence.MapItem;
import nts.uk.nrl.xml.Element;
import nts.uk.nrl.xml.Frame;

/**
 * Status polling request.
 * 
 * @author manhnd
 */
@RequestScoped
@Named(Command.POLLING)
public class StatusPollingRequest extends NRLRequest<Frame> {
	
	/* (non-Javadoc)
	 * @see nts.uk.nrl.request.NRLRequest#sketch(nts.uk.nrl.request.ResourceContext)
	 */
	@Override
	public void sketch(ResourceContext<Frame> context) {
		
		List<MapItem> items = new ArrayList<>();
		items.add(FrameItemArranger.SOH());
		items.add(new MapItem(Element.HDR, Command.POLLING.Response));
		items.add(new MapItem(Element.LENGTH, responseLength()));
		items.add(FrameItemArranger.Version());
		items.add(FrameItemArranger.FlagEndNoAck());
		items.add(FrameItemArranger.NoFragment());
		items.add(new MapItem(Element.NRL_NO, context.getTerminal().getNrlNo()));
		items.add(new MapItem(Element.MAC_ADDR, context.getTerminal().getMacAddress()));
		items.add(FrameItemArranger.ZeroPadding());
		items.add(new MapItem(Element.STATUS, Element.Value.STATUS_REQ_NORMAL_RES));
		// TODO: Get flag from DB to set to request
		items.add(new MapItem(Element.REQUEST1, Element.Value.FLAG_ON));
		items.add(new MapItem(Element.REQUEST2, Element.Value.FLAG_OFF));
		items.add(new MapItem(Element.REQUEST3, Element.Value.FLAG_OFF));
		items.add(new MapItem(Element.REQUEST4, Element.Value.FLAG_OFF));
		items.add(new MapItem(Element.REQUEST5, Element.Value.FLAG_OFF));
		items.add(new MapItem(Element.REQUEST6, Element.Value.FLAG_OFF));
		items.add(new MapItem(Element.REQUEST7, Element.Value.FLAG_OFF));
		items.add(new MapItem(Element.REQUEST8, Element.Value.FLAG_OFF));
		items.add(new MapItem(Element.REQUEST9, Element.Value.FLAG_OFF));
		items.add(new MapItem(Element.REQUEST10, Element.Value.FLAG_OFF));
		items.add(new MapItem(Element.REQUEST11, Element.Value.FLAG_OFF));
		items.add(new MapItem(Element.REQUEST12, Element.Value.FLAG_OFF));
		items.add(new MapItem(Element.REQUEST13, Element.Value.FLAG_OFF));
		items.add(new MapItem(Element.REQUEST14, Element.Value.FLAG_OFF));
		items.add(new MapItem(Element.REQUEST15, Element.Value.FLAG_OFF));
		items.add(new MapItem(Element.REQUEST16, Element.Value.FLAG_OFF));
		items.add(new MapItem(Element.REQUEST17, Element.Value.FLAG_OFF));
		context.collect(items);
	}

	/* (non-Javadoc)
	 * @see nts.uk.nrl.request.NRLRequest#responseLength()
	 */
	@Override
	public String responseLength() {
		return "0030";
	}
}
