/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.find.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableCode;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableHeadSetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableName;
import nts.uk.ctx.pr.core.dom.wagetable.mode.DemensionalMode;
import nts.uk.shr.com.primitive.Memo;

/**
 * The Class WageTableHeadDto.
 */
@Builder
@Getter
@Setter
public class WageTableHeadDto implements WageTableHeadSetMemento {

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
	public void setCode(WageTableCode code) {
		this.code = code.v();
	}

	@Override
	public void setName(WageTableName name) {
		this.name = name.v();
	}

	@Override
	public void setDemensionSetting(DemensionalMode demensionSetting) {
		this.demensionSet = demensionSetting.getMode().value;
	}

	@Override
	public void setMemo(Memo memo) {
		this.memo = memo.v();
	}

}
