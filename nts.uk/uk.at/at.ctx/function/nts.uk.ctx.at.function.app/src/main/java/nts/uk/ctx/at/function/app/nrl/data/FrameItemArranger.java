package nts.uk.ctx.at.function.app.nrl.data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.enterprise.context.RequestScoped;

import nts.uk.ctx.at.function.app.nrl.Command;
import nts.uk.ctx.at.function.app.nrl.DefaultValue;
import nts.uk.ctx.at.function.app.nrl.exceptions.IllegalCommandException;
import nts.uk.ctx.at.function.app.nrl.xml.Content;
import nts.uk.ctx.at.function.app.nrl.xml.Element;
import nts.uk.ctx.at.function.app.nrl.xml.Frame;
import nts.uk.ctx.at.function.app.nrl.xml.Item;

/**
 * Frame item arranger.
 * 
 * @author manhnd
 */
@RequestScoped
public class FrameItemArranger extends ItemSequence<Frame> {
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.app.nrl.data.ItemSequence#fromMap(java.util.Map, nts.uk.ctx.at.function.app.nrl.Command)
	 */
	public Frame fromMap(Map<String, String> data, Command command) {
		List<Item> headItems = new ArrayList<>();
		List<Item> dataItems = new ArrayList<>();
		Comparator<? super Entry<String, String>> comparator;
		final List<String> orders = enumerate(command, false).orElseThrow(IllegalCommandException::new);
		
		comparator = (e1, e2) -> {
			int i1 = orders.indexOf(e1.getKey());
			int i2 = orders.indexOf(e2.getKey());
			return i1 - i2;
		};
		List<Entry<String, String>> entries = data.entrySet().stream().sorted(comparator).collect(Collectors.toList());
		IntStream.range(0, data.size()).forEach(i -> {
			Entry<String, String> entry = entries.get(i);
			headItems.add(new Item(i, entry.getKey()));
			dataItems.add(new Item(i, entry.getValue()));
		});
		
		@SuppressWarnings("serial")
		List<Content> contents = new ArrayList<Content>() {
			{
				add(new Content(DefaultValue.HEAD_TYPE, headItems));
				add(new Content(DefaultValue.DATA_TYPE, dataItems));
			}
		}; 
		return new Frame(Integer.parseInt(command.Response) + "", contents);
	}
	
	/**
	 * SOH.
	 * @return item
	 */
	public static MapItem SOH() {
		return new MapItem(Element.SOH, DefaultValue.SOH.toUpperCase());
	}
	
	/**
	 * HDR_NAK.
	 * @return item
	 */
	public static MapItem HDR_NAK() {
		return new MapItem(Element.HDR, DefaultValue.HDR_NAK);
	}
	
	/**
	 * Length_NAK.
	 * @return item
	 */
	public static MapItem Length_NAK() {
		return new MapItem(Element.LENGTH, DefaultValue.LENGTH_NAK);
	}
	
	/**
	 * Version.
	 * @return item
	 */
	public static MapItem Version() {
		return new MapItem(Element.VERSION, DefaultValue.VERSION);
	}
	
	/**
	 * Flag end no ack.
	 * @return item
	 */
	public static MapItem FlagEndNoAck() {
		return new MapItem(Element.FLAG, DefaultValue.FLAG_END_NOACK);
	}
	
	/**
	 * No fragment.
	 * @return item
	 */
	public static MapItem NoFragment() {
		return new MapItem(Element.FRAGMENT_NUMBER, DefaultValue.NO_FRAG);
	}
	
	/**
	 * Zero padding.
	 * @return item
	 */
	public static MapItem ZeroPadding() {
		return new MapItem(Element.PADDING, DefaultValue.ZERO_PADDING);
	}
}
