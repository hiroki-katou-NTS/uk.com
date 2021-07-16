package nts.uk.ctx.exio.dom.input.canonicalize;

import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.uk.ctx.exio.dom.input.DataItemList;

@SuppressWarnings("serial")
public class CanonicalItemList extends ArrayList<CanonicalItem> {

	public CanonicalItemList() {
		super();
	}
	
	public CanonicalItemList(Collection<CanonicalItem> items) {
		super(items);
	}
	
	public static CanonicalItemList of(DataItemList list) {
		return list.stream()
				.map(CanonicalItem::of)
				.collect(Collectors.collectingAndThen(toList(), CanonicalItemList::new));
	}
	
	public Optional<CanonicalItem> getItemByNo(int itemNo) {
		return stream()
				.filter(item -> item.getItemNo() == itemNo)
				.findFirst();
	}
	
	
}
