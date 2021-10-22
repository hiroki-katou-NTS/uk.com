package nts.uk.ctx.at.function.app.nrl.request;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import nts.uk.ctx.at.function.app.nrl.Command;
import nts.uk.ctx.at.function.app.nrl.NRContentList;
import nts.uk.ctx.at.function.app.nrl.data.ItemSequence.MapItem;
import nts.uk.ctx.at.function.app.nrl.exceptions.ErrorCode;
import nts.uk.ctx.at.function.app.nrl.response.NRLResponse;
import nts.uk.ctx.at.function.app.nrl.xml.Element;
import nts.uk.ctx.at.function.app.nrl.xml.Frame;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.DateTimeSwitchUKModeImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.SendDateTimeSwitchUKModeAdapter;

/**
 * @author thanh_nx
 *
 *         UKモードへの切替日時リクエスト
 */
@RequestScoped
@Named(Command.UK_SWITCH_MODE)
public class DateTimeSwitchUKModeRequest extends NRLRequest<Frame> {

	@Inject
	private SendDateTimeSwitchUKModeAdapter sendDateTimeSwitch;

	@Override
	public void sketch(String empInfoTerCode, ResourceContext<Frame> context) {

		List<MapItem> items = NRContentList.createDefaultField(Command.UK_SWITCH_MODE,
				Optional.ofNullable(responseLength()), context.getTerminal());
		Optional<DateTimeSwitchUKModeImport> setting = sendDateTimeSwitch.process(empInfoTerCode,
				context.getTerminal().getContractCode());

		if (!setting.isPresent()) {
			// if(<>$data.isPresent()) return NAKを作る();
			context.setResponse(NRLResponse.noAccept(context.getTerminal().getNrlNo(),
					context.getTerminal().getMacAddress(), context.getTerminal().getContractCode()).build().addPayload(Frame.class, ErrorCode.PARAM.value));
			return;
		}

		items.add(new MapItem(Element.YEAR, String.valueOf(setting.get().getYear())));
		items.add(new MapItem(Element.MONTH, String.valueOf(setting.get().getMonth())));
		items.add(new MapItem(Element.DAY, String.valueOf(setting.get().getDay())));
		items.add(new MapItem(Element.HOUR, String.valueOf(setting.get().getHour())));
		items.add(new MapItem(Element.MINITE, String.valueOf(setting.get().getMinute())));
		items.add(new MapItem(Element.SECOND, String.valueOf(setting.get().getSecond())));
		items.add(new MapItem(Element.WEEK, String.valueOf(setting.get().getWeek())));
		context.collect(items);

	}

	@Override
	public String responseLength() {
		return "0032";
	}

}
