package nts.uk.ctx.at.function.app.nrl.request;

import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.RequestScoped;

import nts.uk.ctx.at.function.app.nrl.Command;
import nts.uk.ctx.at.function.app.nrl.data.FrameItemArranger;
import nts.uk.ctx.at.function.app.nrl.data.ItemSequence.MapItem;
import nts.uk.ctx.at.function.app.nrl.xml.Element;
import nts.uk.ctx.at.function.app.nrl.xml.Frame;

/**
 * Test request.
 * 
 * @author manhnd
 */
@RequestScoped
@Named(Command.TEST)
public class TestRequest extends NRLRequest<Frame> {
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.app.nrl.request.NRLRequest#sketch(nts.uk.ctx.at.function.app.nrl.request.ResourceContext)
	 */
	@Override
	public void sketch(String empInfoTerCode, ResourceContext<Frame> context) {
		String contractCode = context.getEntity().pickItem(Element.CONTRACT_CODE);
		List<MapItem> items = Arrays.asList(FrameItemArranger.SOH(), new MapItem(Element.HDR, Command.TEST.Response),
				new MapItem(Element.LENGTH, Element.Value.ACCEPT_RES_LEN), FrameItemArranger.Version(),
				FrameItemArranger.FlagEndNoAck(), FrameItemArranger.NoFragment(),
				new MapItem(Element.NRL_NO, context.getTerminal().getNrlNo()),
				new MapItem(Element.MAC_ADDR, context.getTerminal().getMacAddress()),
				new MapItem(Element.CONTRACT_CODE, contractCode), FrameItemArranger.ZeroPadding());
		context.collect(items);
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.app.nrl.request.NRLRequest#responseLength()
	 */
	@Override
	public String responseLength() {
		return null;
	}

}
