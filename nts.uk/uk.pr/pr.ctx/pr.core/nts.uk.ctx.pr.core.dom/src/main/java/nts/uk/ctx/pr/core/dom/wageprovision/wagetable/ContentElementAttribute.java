package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * 要素属性
 */
@AllArgsConstructor
@Getter
public class ContentElementAttribute extends DomainObject {

	/**
	 * 第一要素項目
	 */
	private ElementItem firstElementItem;

	/**
	 * 第二要素項目
	 */
	private Optional<ElementItem> secondElementItem;

	/**
	 * 第三要素項目
	 */
	private Optional<ElementItem> thirdElementItem;

	public ContentElementAttribute(String firstMasterCode, Long firstFrameNumber, BigDecimal firstFrameLowerLimit,
			BigDecimal firstFrameUpperLimit, String secondMasterCode, Long secondFrameNumber,
			BigDecimal secondFrameLowerLimit, BigDecimal secondFrameUpperLimit, String thirdMasterCode,
			Long thirdFrameNumber, BigDecimal thirdFrameLowerLimit, BigDecimal thirdFrameUpperLimit) {
		this.firstElementItem = new ElementItem(firstMasterCode, firstFrameNumber, firstFrameLowerLimit,
				firstFrameUpperLimit);
		if (secondMasterCode == null && secondFrameNumber == null && secondFrameLowerLimit == null
				&& secondFrameUpperLimit == null)
			this.secondElementItem = Optional.empty();
		else
			this.secondElementItem = Optional.of(
					new ElementItem(secondMasterCode, secondFrameNumber, secondFrameLowerLimit, secondFrameUpperLimit));
		if (thirdMasterCode == null && thirdFrameNumber == null && thirdFrameLowerLimit == null
				&& thirdFrameUpperLimit == null)
			this.thirdElementItem = Optional.empty();
		else
			this.thirdElementItem = Optional
					.of(new ElementItem(thirdMasterCode, thirdFrameNumber, thirdFrameLowerLimit, thirdFrameUpperLimit));
	}

	public ContentElementAttribute(ElementItem firstElementItem, ElementItem secondElementItem,
			ElementItem thirdElementItem) {
		this.firstElementItem = firstElementItem;
		this.secondElementItem = Optional.of(secondElementItem);
		this.thirdElementItem = Optional.of(thirdElementItem);
	}
}
