/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.wagetable.mode.DemensionalMode;
import nts.uk.shr.com.primitive.Memo;

/**
 * The Class WageTableHead.
 */
@Getter
public class WageTableHead extends DomainObject {

	/** The company code. */
	private CompanyCode companyCode;

	/** The code. */
	private WageTableCode code;

	/** The name. */
	private WageTableName name;

	/** The demension setting. */
	private DemensionalMode demensionSetting;

	/** The memo. */
	private Memo memo;

	/**
	 * Instantiates a new wage table head.
	 *
	 * @param companyCode
	 *            the company code
	 * @param code
	 *            the code
	 * @param name
	 *            the name
	 * @param demensionSet
	 *            the demension set
	 * @param memo
	 *            the memo
	 */
	public WageTableHead(CompanyCode companyCode, WageTableCode code, WageTableName name,
			DemensionalMode demensionSetting, Memo memo) {
		super();
		this.companyCode = companyCode;
		this.code = code;
		this.name = name;
		this.demensionSetting = demensionSetting;
		this.memo = memo;
	}

	// =================== Memento State Support Method ===================
	/**
	 * Instantiates a new wage table head.
	 *
	 * @param memento
	 *            the memento
	 */
	public WageTableHead(WageTableHeadGetMemento memento) {
		this.companyCode = memento.getCompanyCode();
		this.code = memento.getCode();
		this.name = memento.getName();
		this.demensionSetting = memento.getDemensionSetting();
		this.memo = memento.getMemo();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(WageTableHeadSetMemento memento) {
		memento.setCompanyCode(this.companyCode);
		memento.setCode(this.code);
		memento.setName(this.name);
		memento.setDemensionSetting(this.demensionSetting);
		memento.setMemo(this.memo);
	}

}
