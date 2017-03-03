/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.element;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pr.core.dom.wagetable.ElementType;
import nts.uk.ctx.pr.core.dom.wagetable.EmployeeLevel;

/**
 * The Class CodeRefMode.
 */
@Getter
public class LevelMode extends BaseMode {

	/** The items. */
	private List<LevelItem> items;

	/**
	 * Instantiates a new code ref mode.
	 *
	 * @param companyCode
	 *            the company code
	 * @param refNo
	 *            the ref no
	 */
	public LevelMode() {
		super(ElementType.LEVEL);

		// Create items
		this.items = Arrays.asList(EmployeeLevel.values()).stream()
				.map(item -> new LevelItem(item.value, IdentifierUtil.randomUniqueId()))
				.collect(Collectors.toList());
	}

}
