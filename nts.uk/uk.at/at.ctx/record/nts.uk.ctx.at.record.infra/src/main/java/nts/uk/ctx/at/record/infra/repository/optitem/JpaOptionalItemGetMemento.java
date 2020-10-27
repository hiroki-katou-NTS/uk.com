/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.optitem;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.infra.entity.optitem.KrcmtAnyfResultRange;
import nts.uk.ctx.at.record.infra.entity.optitem.KrcmtAnyv;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.scherec.optitem.CalcResultRange;
import nts.uk.ctx.at.shared.dom.scherec.optitem.EmpConditionAtr;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemAtr;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemGetMemento;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemName;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemNo;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemUsageAtr;
import nts.uk.ctx.at.shared.dom.scherec.optitem.PerformanceAtr;
import nts.uk.ctx.at.shared.dom.scherec.optitem.UnitOfOptionalItem;

/**
 * The Class JpaOptionalItemGetMemento.
 */
public class JpaOptionalItemGetMemento implements OptionalItemGetMemento {

	/** The type value. */
	private KrcmtAnyv typeValue;
	
	/** The krcst calc result range. */
	private KrcmtAnyfResultRange krcmtAnyfResultRange;

	/**
	 * Instantiates a new jpa optional item get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaOptionalItemGetMemento(KrcmtAnyv typeValue, KrcmtAnyfResultRange... krcmtAnyfResultRangeView) {
		
		if(krcmtAnyfResultRangeView.length > 0) {
			this.krcmtAnyfResultRange = krcmtAnyfResultRangeView[0];
		}
		
		this.typeValue = typeValue;
	}
	
	public JpaOptionalItemGetMemento(KrcmtAnyv typeValue, KrcmtAnyfResultRange krcmtAnyfResultRange) {
		this.typeValue = typeValue;
		this.krcmtAnyfResultRange = krcmtAnyfResultRange;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.optitem.OptionalItemGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.typeValue.getKrcmtAnyvPK().getCid());
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
		return new OptionalItemNo(this.typeValue.getKrcmtAnyvPK().getOptionalItemNo());
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
				this.krcmtAnyfResultRange != null
						? this.krcmtAnyfResultRange
						: this.typeValue.getKrcmtAnyfResultRange()));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.optitem.OptionalItemGetMemento#getUnit()
	 */
	@Override
	public UnitOfOptionalItem getUnit() {
		return new UnitOfOptionalItem(this.typeValue.getUnitOfOptionalItem());
	}

}
