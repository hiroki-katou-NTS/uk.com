/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.ws.wagetable.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.dom.wagetable.ElementType;

/**
 * The Class DemensionItemModel.
 */
@Getter
@Setter
public class DemensionItemModel {

	/** The type. */
	private Integer type;

	/** The code. */
	private String code;

	/** The name. */
	private String name;

	/**
	 * Instantiates a new demension item model.
	 *
	 * @param type the type
	 * @param code the code
	 * @param name the name
	 */
	public DemensionItemModel(Integer type, String code, String name) {
		super();
		this.type = type;
		this.code = code;
		this.name = name;
	}

	/**
	 * Instantiates a new demension item model.
	 *
	 * @param type the type
	 */
	public DemensionItemModel(ElementType type) {
		super();
		this.type = type.value;
		this.code = String.valueOf(type.value);
		this.name = type.displayName;
	}

}
