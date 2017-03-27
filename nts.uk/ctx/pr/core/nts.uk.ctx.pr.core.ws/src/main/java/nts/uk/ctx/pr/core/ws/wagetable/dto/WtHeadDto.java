/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.ws.wagetable.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import nts.uk.ctx.pr.core.dom.wagetable.ElementCount;
import nts.uk.ctx.pr.core.dom.wagetable.WtCode;
import nts.uk.ctx.pr.core.dom.wagetable.WtHeadSetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.WtName;
import nts.uk.ctx.pr.core.dom.wagetable.element.WtElement;
import nts.uk.shr.com.primitive.Memo;

/**
 * The Class WtHeadDto.
 */
@Data
public class WtHeadDto implements WtHeadSetMemento {

	/** The code. */
	private String code;

	/** The name. */
	private String name;

	/** The mode. */
	private Integer mode;

	/** The memo. */
	private String memo;

	/** The elements. */
	private List<WtElementDto> elements;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.wagetable.WtHeadSetMemento#setCompanyCode(nts.uk.
	 * ctx.core.dom.company.CompanyCode)
	 */
	@Override
	public void setCompanyCode(String companyCode) {
		// Do nothing.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.wagetable.WtHeadSetMemento#setCode(nts.uk.ctx.pr.
	 * core.dom.wagetable.WtCode)
	 */
	@Override
	public void setCode(WtCode code) {
		this.code = code.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.wagetable.WtHeadSetMemento#setName(nts.uk.ctx.pr.
	 * core.dom.wagetable.WtName)
	 */
	@Override
	public void setName(WtName name) {
		this.name = name.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.wagetable.WtHeadSetMemento#setMemo(nts.uk.shr.com.
	 * primitive.Memo)
	 */
	@Override
	public void setMemo(Memo memo) {
		this.memo = memo.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.wagetable.WtHeadSetMemento#setMode(nts.uk.ctx.pr.
	 * core.dom.wagetable.ElementCount)
	 */
	@Override
	public void setMode(ElementCount mode) {
		this.mode = mode.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.wagetable.WtHeadSetMemento#setElements(java.util.
	 * List)
	 */
	@Override
	public void setElements(List<WtElement> elements) {
		this.elements = elements.stream()
				.map(item -> WtElementDto.builder().demensionNo(item.getDemensionNo().value)
						.type(item.getType().value).referenceCode(item.getReferenceCode()).build())
				.collect(Collectors.toList());
	}

}
