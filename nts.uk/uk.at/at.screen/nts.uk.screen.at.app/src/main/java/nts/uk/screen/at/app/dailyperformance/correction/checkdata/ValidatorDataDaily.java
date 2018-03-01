package nts.uk.screen.at.app.dailyperformance.correction.checkdata;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;

@Stateless
public class ValidatorDataDaily {

	private static final Integer[] CHILD_CARE = { 759, 760, 761, 762 };
	private static final Integer[] CARE = { 763, 764, 765, 766 };

	// 育児と介護の時刻が両方入力されていないかチェックする
	public List<ItemValue> checkCareItemDuplicate(List<ItemValue> items) {
		List<ItemValue> childCares = hasChildCare(items);
		List<ItemValue> cares = hasCare(items);
		if (!childCares.isEmpty() && !cares.isEmpty()) {
			// throw new BusinessException("Msg_996");
			childCares.addAll(cares);
			return childCares;
		} else
			return Collections.emptyList();

	}

	private List<ItemValue> hasChildCare(List<ItemValue> items) {
		List<ItemValue> itemChild = items.stream().filter(x -> x.getItemId() == CHILD_CARE[0]
				|| x.getItemId() == CHILD_CARE[1] || x.getItemId() == CHILD_CARE[2] || x.getItemId() == CHILD_CARE[3])
				.collect(Collectors.toList());
		return itemChild.isEmpty() ? Collections.emptyList() : itemChild;
	}

	private List<ItemValue> hasCare(List<ItemValue> items) {
		List<ItemValue> itemCare = items.stream().filter(x -> x.getItemId() == CARE[0] || x.getItemId() == CARE[1]
				|| x.getItemId() == CARE[2] || x.getItemId() == CARE[3]).collect(Collectors.toList());
		return itemCare.isEmpty() ? Collections.emptyList() : itemCare;
	}
}
