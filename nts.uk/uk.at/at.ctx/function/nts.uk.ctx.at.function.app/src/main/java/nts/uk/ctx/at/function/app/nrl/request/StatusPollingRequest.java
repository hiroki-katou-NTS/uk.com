package nts.uk.ctx.at.function.app.nrl.request;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import nts.uk.ctx.at.function.app.nrl.Command;
import nts.uk.ctx.at.function.app.nrl.data.FrameItemArranger;
import nts.uk.ctx.at.function.app.nrl.data.ItemSequence.MapItem;
import nts.uk.ctx.at.function.app.nrl.xml.Element;
import nts.uk.ctx.at.function.app.nrl.xml.Frame;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.SendNRDataAdapter;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.SendTimeRecordSettingImport;

/**
 * Status polling request.
 * 
 * @author manhnd
 */
@RequestScoped
@Named(Command.POLLING)
public class StatusPollingRequest extends NRLRequest<Frame> {
	
	@Inject
	private SendNRDataAdapter sendNRDataAdapter;
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.app.nrl.request.NRLRequest#sketch(nts.uk.ctx.at.function.app.nrl.request.ResourceContext)
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
		// Get flag from DB to set to request
		String nrlNo = context.getEntity().pickItem(Element.NRL_NO);
		//TODO: default ContractCode "000000000000"
		SendTimeRecordSettingImport setting = sendNRDataAdapter
				.sendTimeRecordSetting(Integer.parseInt(nrlNo.trim()), "000000000000").orElse(new SendTimeRecordSettingImport());

		items.add(new MapItem(Element.REQUEST1, setting.isRequest1() ? Element.Value.FLAG_ON : Element.Value.FLAG_OFF));
		items.add(new MapItem(Element.REQUEST2, setting.isRequest2() ? Element.Value.FLAG_ON : Element.Value.FLAG_OFF));
		items.add(new MapItem(Element.REQUEST3, setting.isRequest3() ? Element.Value.FLAG_ON : Element.Value.FLAG_OFF));
		items.add(new MapItem(Element.REQUEST4, setting.isRequest4() ? Element.Value.FLAG_ON : Element.Value.FLAG_OFF));
		items.add(new MapItem(Element.REQUEST5, Element.Value.FLAG_OFF));
		items.add(new MapItem(Element.REQUEST6, setting.isRequest6() ? Element.Value.FLAG_ON : Element.Value.FLAG_OFF));
		items.add(new MapItem(Element.REQUEST7, setting.isRequest7() ? Element.Value.FLAG_ON : Element.Value.FLAG_OFF));
		items.add(new MapItem(Element.REQUEST8, setting.isRequest8() ? Element.Value.FLAG_ON : Element.Value.FLAG_OFF));
		items.add(new MapItem(Element.REQUEST9, setting.isRequest9() ? Element.Value.FLAG_ON : Element.Value.FLAG_OFF));
		items.add(new MapItem(Element.REQUEST10, setting.isRequest10() ? Element.Value.FLAG_ON : Element.Value.FLAG_OFF));
		items.add(new MapItem(Element.REQUEST11, setting.isRequest11() ? Element.Value.FLAG_ON : Element.Value.FLAG_OFF));
		items.add(new MapItem(Element.REQUEST12, Element.Value.FLAG_OFF));
		items.add(new MapItem(Element.REQUEST13, Element.Value.FLAG_OFF));
		items.add(new MapItem(Element.REQUEST14, Element.Value.FLAG_OFF));
		items.add(new MapItem(Element.REQUEST15, Element.Value.FLAG_OFF));
		items.add(new MapItem(Element.REQUEST16, Element.Value.FLAG_OFF));
		items.add(new MapItem(Element.REQUEST17, Element.Value.FLAG_OFF));
		context.collect(items);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.app.nrl.request.NRLRequest#responseLength()
	 */
	@Override
	public String responseLength() {
		return "0030";
	}
}
