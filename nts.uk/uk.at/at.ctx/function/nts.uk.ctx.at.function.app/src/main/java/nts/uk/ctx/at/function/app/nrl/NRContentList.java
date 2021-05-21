package nts.uk.ctx.at.function.app.nrl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.function.app.nrl.data.FrameItemArranger;
import nts.uk.ctx.at.function.app.nrl.data.ItemSequence.MapItem;
import nts.uk.ctx.at.function.app.nrl.repository.Terminal;
import nts.uk.ctx.at.function.app.nrl.xml.Element;

/**
 * @author thanh_nx
 *
 *         コンテンツList
 */
public class NRContentList {

	// [S-1] デフォルトのコンテンツListを作る
	public static List<MapItem> createDefaultField(Command command, Optional<String> payloadLength, Terminal teminal) {
		List<MapItem> items = new ArrayList<>();
		items.add(FrameItemArranger.SOH());
		items.add(new MapItem(Element.HDR, command.Response));
		items.add(new MapItem(Element.LENGTH, payloadLength.orElse(null)));
		items.add(FrameItemArranger.Version());
		items.add(FrameItemArranger.FlagEndNoAck());
		items.add(FrameItemArranger.NoFragment());
		items.add(new MapItem(Element.NRL_NO, teminal.getNrlNo()));
		items.add(new MapItem(Element.MAC_ADDR, teminal.getMacAddress()));
		items.add(new MapItem(Element.CONTRACT_CODE, teminal.getContractCode()));
		items.add(FrameItemArranger.ZeroPadding());
		return items;
	};
}
