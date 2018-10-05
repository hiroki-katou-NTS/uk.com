/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.optitem;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.optitem.CalcResultRange;
import nts.uk.ctx.at.record.dom.optitem.EmpConditionAtr;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemAtr;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemGetMemento;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemName;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemNo;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemUsageAtr;
import nts.uk.ctx.at.record.dom.optitem.PerformanceAtr;
import nts.uk.ctx.at.record.infra.entity.optitem.KrcstCalcResultRange;
import nts.uk.ctx.at.record.dom.optitem.UnitOfOptionalItem;
import nts.uk.ctx.at.record.infra.entity.optitem.KrcstCalcResultRange;
import nts.uk.ctx.at.record.infra.entity.optitem.KrcstOptionalItem;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class JpaOptionalItemGetMemento.
 */
public class JpaOptionalItemGetMemento implements OptionalItemGetMemento {

	/** The type value. */
	private KrcstOptionalItem typeValue;
	
	/** The krcst calc result range. */
	private KrcstCalcResultRange krcstCalcResultRange;

	/**
	 * Instantiates a new jpa optional item get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaOptionalItemGetMemento(KrcstOptionalItem typeValue, KrcstCalcResultRange... krcstCalcResultRangeView) {
		
		if(krcstCalcResultRangeView.length > 0) {
			this.krcstCalcResultRange = krcstCalcResultRangeView[0];
		}
		
		this.typeValue = typeValue;
	}
	
	public JpaOptionalItemGetMemento(KrcstOptionalItem typeValue, KrcstCalcResultRange krcstCalcResultRange) {
		this.typeValue = typeValue;
		this.krcstCalcResultRange = krcstCalcResultRange;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.optitem.OptionalItemGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.typeValue.getKrcstOptionalItemPK().getCid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.optitem.OptionalItemGetMemento#getOptionalItemNo
	 * ()
	 */
	@Override
	public OptionalItemNo getOptionalItemNo() {
		return new OptionalItemNo(this.typeValue.getKrcstOptionalItemPK().getOptionalItemNo());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.OptionalItemGetMemento#
	 * getOptionalItemName()
	 */
	@Override
	public OptionalItemName getOptionalItemName() {
		return new OptionalItemName(this.typeValue.getOptionalItemName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.OptionalItemGetMemento#
	 * getOptionalItemAtr()
	 */
	@Override
	public OptionalItemAtr getOptionalItemAtr() {
		return EnumAdaptor.valueOf(this.typeValue.getOptionalItemAtr(), OptionalItemAtr.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.OptionalItemGetMemento#
	 * getOptionalItemUsageAtr()
	 */
	@Override
	public OptionalItemUsageAtr getOptionalItemUsageAtr() {
		return EnumAdaptor.valueOf(this.typeValue.getUsageAtr(), OptionalItemUsageAtr.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.OptionalItemGetMemento#
	 * getEmpConditionAtr()
	 */
	@Override
	public EmpConditionAtr getEmpConditionAtr() {
		return EnumAdaptor.valueOf(this.typeValue.getEmpConditionAtr(), EmpConditionAtr.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.optitem.OptionalItemGetMemento#getPerformanceAtr
	 * ()
	 */
	@Override
	public PerformanceAtr getPerformanceAtr() {
		return EnumAdaptor.valueOf(this.typeValue.getPerformanceAtr(), PerformanceAtr.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.OptionalItemGetMemento#
	 * getCalculationResultRange()
	 */
	@Override
	public CalcResultRange getCalculationResultRange() {
		return new CalcResultRange(new JpaCalcResultRangeGetMemento(
				this.krcstCalcResultRange != null
						? this.krcstCalcResultRange
						: this.typeValue.getKrcstCalcResultRange()));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.optitem.OptionalItemGetMemento#getUnit()
	 */
	@Override
	public UnitOfOptionalItem getUnit() {
		return new UnitOfOptionalItem(this.typeValue.getUnitOfOptionalItem());
	}

}
