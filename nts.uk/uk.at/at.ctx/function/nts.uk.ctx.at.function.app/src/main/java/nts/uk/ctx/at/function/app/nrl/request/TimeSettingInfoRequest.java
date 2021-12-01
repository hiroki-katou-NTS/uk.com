package nts.uk.ctx.at.function.app.nrl.request;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import nts.uk.ctx.at.function.app.nrl.Command;
import nts.uk.ctx.at.function.app.nrl.NRContentList;
import nts.uk.ctx.at.function.app.nrl.data.ItemSequence.MapItem;
import nts.uk.ctx.at.function.app.nrl.xml.Element;
import nts.uk.ctx.at.function.app.nrl.xml.Frame;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.SendNRDataAdapter;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.SendTimeInfomationImport;

/**
 * @author ThanhNX
 *
 *時刻合わせリクエスト
 */
@RequestScoped
@Named(Command.TIMESET_INFO)
public class TimeSettingInfoRequest extends NRLRequest<Frame> {

	@Inject
	private SendNRDataAdapter sendNRDataAdapter;

	@Override
	public void sketch(String empInfoTerCode, ResourceContext<Frame> context) {

		List<MapItem> items = NRContentList.createDefaultField(Command.TIMESET_INFO,
				Optional.ofNullable(responseLength()), context.getTerminal());
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
		return "0032";
	}

}
