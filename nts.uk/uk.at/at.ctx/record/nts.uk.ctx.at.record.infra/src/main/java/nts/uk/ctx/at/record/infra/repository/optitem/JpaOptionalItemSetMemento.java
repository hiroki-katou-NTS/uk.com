/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.optitem;

import java.util.Optional;

import nts.uk.ctx.at.record.infra.entity.optitem.KrcstCalcResultRange;
import nts.uk.ctx.at.record.infra.entity.optitem.KrcmtAnyv;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.scherec.optitem.CalcResultRange;
import nts.uk.ctx.at.shared.dom.scherec.optitem.CalculationClassification;
import nts.uk.ctx.at.shared.dom.scherec.optitem.EmpConditionAtr;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemAtr;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemName;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemNo;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemSetMemento;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemUsageAtr;
import nts.uk.ctx.at.shared.dom.scherec.optitem.PerformanceAtr;
import nts.uk.ctx.at.shared.dom.scherec.optitem.UnitOfOptionalItem;

/**
 * The Class JpaOptionalItemSetMemento.
 */
public class JpaOptionalItemSetMemento implements OptionalItemSetMemento {

	/** The type value. */
	private KrcmtAnyv entity;

	/**
	 * Instantiates a new jpa optional item set memento.
	 *
	 * @param entity the entity
	 */
	public JpaOptionalItemSetMemento(KrcmtAnyv entity) {
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.optitem.OptionalItemSetMemento#setCompanyId(nts.
	 * uk.ctx.at.shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId comId) {
		this.entity.getKrcmtAnyvPK().setCid(comId.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.optitem.OptionalItemSetMemento#setOptionalItemNo
	 * (nts.uk.ctx.at.record.dom.optitem.OptionalItemNo)
	 */
	@Override
	public void setOptionalItemNo(OptionalItemNo optionalItemNo) {
		this.entity.getKrcmtAnyvPK().setOptionalItemNo(optionalItemNo.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.OptionalItemSetMemento#
	 * setOptionalItemName(nts.uk.ctx.at.record.dom.optitem.OptionalItemName)
	 */
	@Override
	public void setOptionalItemName(OptionalItemName optionalItemName) {
		this.entity.setOptionalItemName(optionalItemName.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.OptionalItemSetMemento#
	 * setOptionalItemAtr(nts.uk.ctx.at.record.dom.optitem.OptionalItemAtr)
	 */
	@Override
	public void setOptionalItemAtr(OptionalItemAtr optionalItemAtr) {
		this.entity.setOptionalItemAtr(optionalItemAtr.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.OptionalItemSetMemento#
	 * setOptionalItemUsageAtr(nts.uk.ctx.at.record.dom.optitem.
	 * OptionalItemUsageAtr)
	 */
	@Override
	public void setOptionalItemUsageAtr(OptionalItemUsageAtr optionalItemUsageAtr) {
		this.entity.setUsageAtr(optionalItemUsageAtr.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.OptionalItemSetMemento#
	 * setEmpConditionAtr(nts.uk.ctx.at.record.dom.optitem.EmpConditionAtr)
	 */
	@Override
	public void setEmpConditionAtr(EmpConditionAtr empConditionAtr) {
		this.entity.setEmpConditionAtr(empConditionAtr.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.optitem.OptionalItemSetMemento#setPerformanceAtr
	 * (nts.uk.ctx.at.record.dom.optitem.PerformanceAtr)
	 */
	@Override
	public void setPerformanceAtr(PerformanceAtr performanceAtr) {
		this.entity.setPerformanceAtr(performanceAtr.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.OptionalItemSetMemento#
	 * setCalculationResultRange(nts.uk.ctx.at.record.dom.optitem.
	 * CalcResultRange)
	 */
	@Override
	public void setCalculationResultRange(CalcResultRange calculationResultRange) {
		KrcstCalcResultRange entityRange = this.entity.getKrcstCalcResultRange();
		calculationResultRange.saveToMemento(new JpaCalcResultRangeSetMemento(entityRange));
		this.entity.setKrcstCalcResultRange(entityRange);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.optitem.OptionalItemSetMemento#setUnit(nts.uk.
	 * ctx.at.record.dom.optitem.UnitOfOptionalItem)
	 */
	@Override
	public void setUnit(Optional<UnitOfOptionalItem> unit) {
		this.entity.setUnitOfOptionalItem(unit.isPresent() ? unit.get().v() : null);
	}

    @Override
    public void setCalAtr(CalculationClassification calcResultRange) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setNote(Optional<String> note) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setDescription(Optional<String> description) {
        // TODO Auto-generated method stub
        
    }

}
