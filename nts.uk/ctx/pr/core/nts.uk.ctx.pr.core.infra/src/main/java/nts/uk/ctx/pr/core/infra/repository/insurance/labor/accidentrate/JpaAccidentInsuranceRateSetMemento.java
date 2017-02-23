/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.insurance.labor.accidentrate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRateSetMemento;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.InsuBizRateItem;
import nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.BusinessTypeEnum;
import nts.uk.ctx.pr.core.infra.entity.insurance.labor.accidentrate.QismtWorkAccidentInsu;
import nts.uk.ctx.pr.core.infra.entity.insurance.labor.accidentrate.QismtWorkAccidentInsuPK;

/**
 * The Class JpaAccidentInsuranceRateSetMemento.
 */
public class JpaAccidentInsuranceRateSetMemento implements AccidentInsuranceRateSetMemento {

	/** The type value. */
	protected List<QismtWorkAccidentInsu> typeValue;

	/**
	 * Instantiates a new jpa accident insurance rate set memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaAccidentInsuranceRateSetMemento(List<QismtWorkAccidentInsu> typeValue) {
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.
	 * AccidentInsuranceRateSetMemento#setHistoryId(java.lang.String)
	 */
	@Override
	public void setHistoryId(String historyId) {
		for (int index = 0; index < this.typeValue.size(); index++) {
			QismtWorkAccidentInsu itemI = this.typeValue.get(index);
			QismtWorkAccidentInsuPK pk = new QismtWorkAccidentInsuPK();
			pk.setHistId(historyId);
			itemI.setQismtWorkAccidentInsuPK(pk);
			this.typeValue.set(index, itemI);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.
	 * AccidentInsuranceRateSetMemento#setCompanyCode(nts.uk.ctx.core.dom.
	 * company.CompanyCode)
	 */
	@Override
	public void setCompanyCode(CompanyCode companyCode) {
		for (int index = 0; index < this.typeValue.size(); index++) {
			QismtWorkAccidentInsu itemI = this.typeValue.get(index);
			QismtWorkAccidentInsuPK pk = itemI.getQismtWorkAccidentInsuPK();
			pk.setCcd(companyCode.v());
			itemI.setQismtWorkAccidentInsuPK(pk);
			this.typeValue.set(index, itemI);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.
	 * AccidentInsuranceRateSetMemento#setApplyRange(nts.uk.ctx.pr.core.dom.
	 * insurance.MonthRange)
	 */
	@Override
	public void setApplyRange(MonthRange applyRange) {
		for (int index = 0; index < this.typeValue.size(); index++) {
			QismtWorkAccidentInsu itemI = this.typeValue.get(index);
			itemI.setStrYm(applyRange.getStartMonth().v());
			itemI.setEndYm(applyRange.getEndMonth().v());
			this.typeValue.set(index, itemI);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.
	 * AccidentInsuranceRateSetMemento#setRateItems(java.util.Set)
	 */
	@Override
	public void setRateItems(Set<InsuBizRateItem> items) {
		for (InsuBizRateItem item : items) {
			if (item.getInsuBizType().equals(BusinessTypeEnum.Biz1St)) {
				QismtWorkAccidentInsu item01 = this.typeValue.get(0);
				QismtWorkAccidentInsuPK pk = item01.getQismtWorkAccidentInsuPK();
				pk.setWaInsuCd(item.getInsuBizType().value);
				item01.setQismtWorkAccidentInsuPK(pk);
				item01.setWaInsuRate(BigDecimal.valueOf(item.getInsuRate()));
				item01.setWaInsuRound(item.getInsuRound().value);
				this.typeValue.set(0, item01);
			}
			if (item.getInsuBizType().equals(BusinessTypeEnum.Biz2Nd)) {
				QismtWorkAccidentInsu item02 = this.typeValue.get(1);
				QismtWorkAccidentInsuPK pk = item02.getQismtWorkAccidentInsuPK();
				pk.setWaInsuCd(item.getInsuBizType().value);
				item02.setQismtWorkAccidentInsuPK(pk);
				item02.setWaInsuRate(BigDecimal.valueOf(item.getInsuRate()));
				item02.setWaInsuRound(item.getInsuRound().value);
				this.typeValue.set(1, item02);
			}
			if (item.getInsuBizType().equals(BusinessTypeEnum.Biz3Rd)) {
				QismtWorkAccidentInsu item03 = this.typeValue.get(2);
				QismtWorkAccidentInsuPK pk = item03.getQismtWorkAccidentInsuPK();
				pk.setWaInsuCd(item.getInsuBizType().value);
				item03.setQismtWorkAccidentInsuPK(pk);
				item03.setWaInsuRate(BigDecimal.valueOf(item.getInsuRate()));
				item03.setWaInsuRound(item.getInsuRound().value);
				this.typeValue.set(2, item03);
			}
			if (item.getInsuBizType().equals(BusinessTypeEnum.Biz4Th)) {
				QismtWorkAccidentInsu item04 = this.typeValue.get(3);
				QismtWorkAccidentInsuPK pk = item04.getQismtWorkAccidentInsuPK();
				pk.setWaInsuCd(item.getInsuBizType().value);
				item04.setQismtWorkAccidentInsuPK(pk);
				item04.setWaInsuRate(BigDecimal.valueOf(item.getInsuRate()));
				item04.setWaInsuRound(item.getInsuRound().value);
				this.typeValue.set(3, item04);
			}
			if (item.getInsuBizType().equals(BusinessTypeEnum.Biz5Th)) {
				QismtWorkAccidentInsu item05 = this.typeValue.get(4);
				QismtWorkAccidentInsuPK pk = item05.getQismtWorkAccidentInsuPK();
				pk.setWaInsuCd(item.getInsuBizType().value);
				item05.setQismtWorkAccidentInsuPK(pk);
				item05.setWaInsuRate(BigDecimal.valueOf(item.getInsuRate()));
				item05.setWaInsuRound(item.getInsuRound().value);
				this.typeValue.set(4, item05);
			}
			if (item.getInsuBizType().equals(BusinessTypeEnum.Biz6Th)) {
				QismtWorkAccidentInsu item06 = this.typeValue.get(5);
				QismtWorkAccidentInsuPK pk = item06.getQismtWorkAccidentInsuPK();
				pk.setWaInsuCd(item.getInsuBizType().value);
				item06.setQismtWorkAccidentInsuPK(pk);
				item06.setWaInsuRate(BigDecimal.valueOf(item.getInsuRate()));
				item06.setWaInsuRound(item.getInsuRound().value);
				this.typeValue.set(5, item06);
			}
			if (item.getInsuBizType().equals(BusinessTypeEnum.Biz7Th)) {
				QismtWorkAccidentInsu item07 = this.typeValue.get(6);
				QismtWorkAccidentInsuPK pk = item07.getQismtWorkAccidentInsuPK();
				pk.setWaInsuCd(item.getInsuBizType().value);
				item07.setQismtWorkAccidentInsuPK(pk);
				item07.setWaInsuRate(BigDecimal.valueOf(item.getInsuRate()));
				item07.setWaInsuRound(item.getInsuRound().value);
				this.typeValue.set(6, item07);
			}
			if (item.getInsuBizType().equals(BusinessTypeEnum.Biz8Th)) {
				QismtWorkAccidentInsu item08 = this.typeValue.get(7);
				QismtWorkAccidentInsuPK pk = item08.getQismtWorkAccidentInsuPK();
				pk.setWaInsuCd(item.getInsuBizType().value);
				item08.setQismtWorkAccidentInsuPK(pk);
				item08.setWaInsuRate(BigDecimal.valueOf(item.getInsuRate()));
				item08.setWaInsuRound(item.getInsuRound().value);
				this.typeValue.set(7, item08);
			}
			if (item.getInsuBizType().equals(BusinessTypeEnum.Biz9Th)) {
				QismtWorkAccidentInsu item09 = this.typeValue.get(8);
				QismtWorkAccidentInsuPK pk = item09.getQismtWorkAccidentInsuPK();
				pk.setWaInsuCd(item.getInsuBizType().value);
				item09.setQismtWorkAccidentInsuPK(pk);
				item09.setWaInsuRate(BigDecimal.valueOf(item.getInsuRate()));
				item09.setWaInsuRound(item.getInsuRound().value);
				this.typeValue.set(8, item09);
			}
			if (item.getInsuBizType().equals(BusinessTypeEnum.Biz10Th)) {
				QismtWorkAccidentInsu item10 = this.typeValue.get(9);
				QismtWorkAccidentInsuPK pk = item10.getQismtWorkAccidentInsuPK();
				pk.setWaInsuCd(item.getInsuBizType().value);
				item10.setQismtWorkAccidentInsuPK(pk);
				item10.setWaInsuRate(BigDecimal.valueOf(item.getInsuRate()));
				item10.setWaInsuRound(item.getInsuRound().value);
				this.typeValue.set(9, item10);
			}
		}
	}

}
