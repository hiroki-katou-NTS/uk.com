/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.wagetable;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableCode;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableHeadSetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableName;
import nts.uk.ctx.pr.core.dom.wagetable.mode.DemensionalMode;
import nts.uk.ctx.pr.core.infra.entity.wagetable.QwtmtWagetableHead;
import nts.uk.ctx.pr.core.infra.entity.wagetable.QwtmtWagetableHeadPK;
import nts.uk.ctx.pr.core.infra.entity.wagetable.element.QwtmtWagetableElement;
import nts.uk.ctx.pr.core.infra.repository.wagetable.element.JpaWageTableElementSetMemento;
import nts.uk.shr.com.primitive.Memo;

/**
 * The Class JpaWageTableHeadSetMemento.
 */
public class JpaWageTableHeadSetMemento implements WageTableHeadSetMemento {

	/** The type value. */
	protected QwtmtWagetableHead typeValue;

	/**
	 * Instantiates a new jpa wage table head set memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaWageTableHeadSetMemento(QwtmtWagetableHead typeValue) {
		this.typeValue = typeValue;
	}

	/**
	 * Sets the company code.
	 *
	 * @param companyCode
	 *            the new company code
	 */
	@Override
	public void setCompanyCode(CompanyCode companyCode) {
		QwtmtWagetableHeadPK qwtmtWagetableHeadPK = new QwtmtWagetableHeadPK();
		qwtmtWagetableHeadPK.setCcd(companyCode.v());
		this.typeValue.setQwtmtWagetableHeadPK(qwtmtWagetableHeadPK);
	}

	/**
	 * Sets the code.
	 *
	 * @param code
	 *            the new code
	 */
	@Override
	public void setCode(WageTableCode code) {
		QwtmtWagetableHeadPK qwtmtWagetableHeadPK = this.typeValue.getQwtmtWagetableHeadPK();
		qwtmtWagetableHeadPK.setWageTableCd(code.v());
		this.typeValue.setQwtmtWagetableHeadPK(qwtmtWagetableHeadPK);
	}

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	@Override
	public void setName(WageTableName name) {
		this.typeValue.setWageTableName(name.v());
	}

	/**
	 * Sets the demension setting.
	 *
	 * @param demensionSetting
	 *            the new demension setting
	 */
	@Override
	public void setDemensionSetting(DemensionalMode demensionSetting) {
		String company = this.typeValue.getQwtmtWagetableHeadPK().getCcd();
		String wageTableCode = this.typeValue.getQwtmtWagetableHeadPK().getWageTableCd();

		this.typeValue.setDemensionSet(demensionSetting.getMode().value);
		List<QwtmtWagetableElement> wagetableElementList = demensionSetting.getElements().stream()
				.map(item -> {
					QwtmtWagetableElement entity = new QwtmtWagetableElement();
					item.saveToMemento(
							new JpaWageTableElementSetMemento(company, wageTableCode, entity));
					return entity;
				}).collect(Collectors.toList());
		this.typeValue.setWagetableElementList(wagetableElementList);
	}

	/**
	 * Sets the memo.
	 *
	 * @param memo
	 *            the new memo
	 */
	@Override
	public void setMemo(Memo memo) {
		this.typeValue.setMemo(memo.v());
	}
}
