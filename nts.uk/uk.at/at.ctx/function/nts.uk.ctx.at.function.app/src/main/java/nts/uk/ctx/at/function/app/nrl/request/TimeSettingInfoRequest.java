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
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.SendTimeInfomationImport;

/**
 * @author ThanhNX
 *
 */
@RequestScoped
@Named(Command.TIMESET_INFO)
public class TimeSettingInfoRequest extends NRLRequest<Frame> {

	@Inject
	private SendNRDataAdapter sendNRDataAdapter;

	@Override
	public void sketch(ResourceContext<Frame> context) {

		List<MapItem> items = new ArrayList<>();
		items.add(FrameItemArranger.SOH());
		items.add(new MapItem(Element.HDR, Command.TIMESET_INFO.Response));
		items.add(new MapItem(Element.LENGTH, responseLength()));
		items.add(FrameItemArranger.Version());
		items.add(FrameItemArranger.FlagEndNoAck());
		items.add(FrameItemArranger.NoFragment());
		items.add(new MapItem(Element.NRL_NO, context.getTerminal().getNrlNo()));
		items.add(new MapItem(Element.MAC_ADDR, context.getTerminal().getMacAddress()));
		items.add(FrameItemArranger.ZeroPadding());
		// Get flag from DB to set to request
		SendTimeInfomationImport setting = sendNRDataAdapter.sendSystemTime();

		items.add(new MapItem(Element.YEAR, String.valueOf(setting.getYear())));
		items.add(new MapItem(Element.MONTH, String.valueOf(setting.getMonth())));
		items.add(new MapItem(Element.DAY, String.valueOf(setting.getDay())));
		items.add(new MapItem(Element.HOUR, String.valueOf(setting.getHour())));
		items.add(new MapItem(Element.MINITE, String.valueOf(setting.getMinute())));
		items.add(new MapItem(Element.SECOND, String.valueOf(setting.getSecond())));
		items.add(new MapItem(Element.WEEK, String.valueOf(setting.getWeek())));
		context.collect(items);

	}

	@Override
	public String responseLength() {
		// TODO Auto-generated method stub
		return null;
	}

}
