package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * 要素情報
 */
@AllArgsConstructor
@Getter
public class ElementInformation extends DomainObject {

	/**
	 * 一次元要素
	 */
	private ElementAttribute oneDimensionalElement;

	/**
	 * 二次元要素
	 */
	private Optional<ElementAttribute> twoDimensionalElement;

	/**
	 * 三次元要素
	 */
	private Optional<ElementAttribute> threeDimensionalElement;

	public ElementInformation(Integer oneMasterNumericClassification, String oneFixedElement,
			String oneOptionalAdditionalElement, Integer twoMasterNumericClassification, String twoFixedElement,
			String twoOptionalAdditionalElement, Integer threeMasterNumericClassification, String threeFixedElement,
			String threeOptionalAdditionalElement) {
		this.oneDimensionalElement = new ElementAttribute(oneMasterNumericClassification, oneFixedElement,
				oneOptionalAdditionalElement);
		if (twoMasterNumericClassification == 0 && twoFixedElement == null && twoOptionalAdditionalElement == null)
			this.twoDimensionalElement = Optional.empty();
		else
			this.twoDimensionalElement = Optional.of(new ElementAttribute(twoMasterNumericClassification,
					twoFixedElement, twoOptionalAdditionalElement));
		if (threeMasterNumericClassification == 0 && threeFixedElement == null
				&& threeOptionalAdditionalElement == null)
			this.threeDimensionalElement = Optional.empty();
		else
			this.threeDimensionalElement = Optional.of(new ElementAttribute(threeMasterNumericClassification,
					threeFixedElement, threeOptionalAdditionalElement));
	}

	public ElementInformation(ElementAttribute oneDimensionalElement, ElementAttribute twoDimensionalElement,
			ElementAttribute threeDimensionalElement) {
		this.oneDimensionalElement = oneDimensionalElement;
		this.twoDimensionalElement = Optional.ofNullable(twoDimensionalElement);
		this.threeDimensionalElement = Optional.ofNullable(threeDimensionalElement);
	}

}
