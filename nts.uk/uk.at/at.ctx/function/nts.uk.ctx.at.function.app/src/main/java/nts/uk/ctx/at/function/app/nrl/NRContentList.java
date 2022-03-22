package nts.uk.ctx.at.function.app.nrl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

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

	// [S-0] デフォルトのコンテンツListを作る
	public static List<MapItem> createDefaultField(Command command, Optional<String> payloadLength, Terminal teminal, String padding) {
		List<MapItem> items = new ArrayList<>();
		items.add(FrameItemArranger.SOH());
		items.add(new MapItem(Element.HDR, command.Response));
		items.add(new MapItem(Element.LENGTH, payloadLength.map(x -> StringUtils.leftPad(x, 4, "0").toUpperCase()).orElse(null)));
		items.add(FrameItemArranger.Version());
		items.add(FrameItemArranger.FlagEndNoAck());
		items.add(FrameItemArranger.NoFragment());
		items.add(new MapItem(Element.NRL_NO, teminal.getNrlNo()));
		items.add(new MapItem(Element.MAC_ADDR, teminal.getMacAddress()));
		items.add(new MapItem(Element.CONTRACT_CODE, teminal.getContractCode()));
		items.add(new MapItem(Element.PADDING, padding));
		return items;
	};
	
	// [S-1] デフォルトのコンテンツListを作る
		public static List<MapItem> createDefaultField(Command command, Optional<String> payloadLength, Terminal teminal) {
			return createDefaultField(command, payloadLength, teminal, DefaultValue.ZERO_PADDING);
		};
		
	// [S-2] デフォルトのコンテンツListを作る
	public static List<MapItem> createFieldForPadding2(Command command, Optional<String> payloadLength,
			Terminal teminal) {
		List<MapItem> items = new ArrayList<>();
		items.add(FrameItemArranger.SOH());
		items.add(new MapItem(Element.HDR, command.Response));
		items.add(new MapItem(Element.PADDING1, "0000"));
		items.add(new MapItem(Element.LENGTH,
				payloadLength.map(x -> StringUtils.leftPad(x, 4, "0").toUpperCase()).orElse(null)));
		items.add(FrameItemArranger.Version());
		items.add(FrameItemArranger.FlagEndNoAck());
		items.add(FrameItemArranger.NoFragment());
		items.add(new MapItem(Element.NRL_NO, teminal.getNrlNo()));
		items.add(new MapItem(Element.MAC_ADDR, teminal.getMacAddress()));
		items.add(new MapItem(Element.CONTRACT_CODE, teminal.getContractCode()));
		items.add(new MapItem(Element.PADDING2, "0000"));
		return items;
	};
}
