/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.mode;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.uk.ctx.pr.core.dom.wagetable.ElementCount;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableElement;
import nts.uk.ctx.pr.core.dom.wagetable.element.CodeRefMode;
import nts.uk.ctx.pr.core.dom.wagetable.element.ElementMode;

/**
 * The Class EligibilityDimensionalMode.
 */
@Getter
public class QualificaDimensionalMode implements DimensionalMode {

	/** The Constant mode. */
	public static final ElementCount mode = ElementCount.Qualification;

	/** The demensions. */
	private List<WageTableElement> elements;

	/**
	 * Instantiates a new qualifica dimensional mode.
	 *
	 * @param elements
	 *            the elements
	 */
	public QualificaDimensionalMode(List<WageTableElement> elements) {

		super();

		List<ElementMode> elementModes = elements.stream().map(item -> item.getElementModeSetting())
				.collect(Collectors.toList());

		if (!elementModes.stream().anyMatch(item -> item instanceof CodeRefMode)) {
			throw new BusinessException("");
		}

		this.elements = elements;
	}

}
