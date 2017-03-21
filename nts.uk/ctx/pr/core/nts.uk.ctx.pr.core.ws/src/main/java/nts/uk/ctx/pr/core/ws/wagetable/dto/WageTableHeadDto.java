/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.ws.wagetable.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.wagetable.ElementCount;
import nts.uk.ctx.pr.core.dom.wagetable.WtCode;
import nts.uk.ctx.pr.core.dom.wagetable.WtHeadSetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.WtName;
import nts.uk.ctx.pr.core.dom.wagetable.element.WtElement;
import nts.uk.shr.com.primitive.Memo;

/**
 * The Class WageTableHeadDto.
 */
@Builder
@Getter
@Setter
public class WageTableHeadDto implements WtHeadSetMemento {

	/** The code. */
	private String code;

	/** The name. */
	private String name;

	/** The demension set. */
	private Integer demensionSet;

	/** The memo. */
	private String memo;

	@Override
	public void setCompanyCode(CompanyCode companyCode) {
		// Do nothing.
	}

	@Override
	public void setCode(WtCode code) {
		this.code = code.v();
	}

	@Override
	public void setName(WtName name) {
		this.name = name.v();
	}

	@Override
	public void setMemo(Memo memo) {
		this.memo = memo.v();
	}

	@Override
	public void setMode(ElementCount mode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setElements(List<WtElement> elements) {
		// TODO Auto-generated method stub
		
	}

}
