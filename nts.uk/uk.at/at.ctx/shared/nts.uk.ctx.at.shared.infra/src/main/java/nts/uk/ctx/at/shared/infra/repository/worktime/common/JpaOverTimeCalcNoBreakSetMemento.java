/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.common.CalcMethodNoBreak;
import nts.uk.ctx.at.shared.dom.worktime.common.OTFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.OverTimeCalcNoBreakSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtDiffTimeWorkSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedWorkSet;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class JpaOverTimeCalcNoBreakSetMemento.
 */
public class JpaOverTimeCalcNoBreakSetMemento<T extends ContractUkJpaEntity> implements OverTimeCalcNoBreakSetMemento {

	/** The entity. */
	private T entity;

	/**
	 * Instantiates a new jpa over time calc no break set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaOverTimeCalcNoBreakSetMemento(T entity) {
		super();
		this.entity = entity;
	}

	/**
	 * Sets the calc method.
	 *
	 * @param calcMethod
	 *            the new calc method
	 */
	@Override
	public void setCalcMethod(CalcMethodNoBreak calcMethod) {
		if (this.entity instanceof KshmtFixedWorkSet) {
			if (calcMethod != null) {
				((KshmtFixedWorkSet) this.entity).setOtCalcMethod(calcMethod.value);
			}
		}
		if (this.entity instanceof KshmtDiffTimeWorkSet) {
			if (calcMethod != null) {
				((KshmtDiffTimeWorkSet) this.entity).setOtCalcMethod(calcMethod.value);
			}
		}
	}

	/**
	 * Sets the in law OT.
	 *
	 * @param inLawOT
	 *            the new in law OT
	 */
	@Override
	public void setInLawOT(OTFrameNo inLawOT) {
		if (this.entity instanceof KshmtFixedWorkSet) {
			if (inLawOT != null) {
				((KshmtFixedWorkSet) this.entity).setOtInLaw(inLawOT.v());
			}
		}
		if (this.entity instanceof KshmtDiffTimeWorkSet) {
			if (inLawOT != null) {
				((KshmtDiffTimeWorkSet) this.entity).setOtInLaw(inLawOT.v());
			}
		}
	}

	/**
	 * Sets the not in law OT.
	 *
	 * @param notInLawOT
	 *            the new not in law OT
	 */
	@Override
	public void setNotInLawOT(OTFrameNo notInLawOT) {
		if (this.entity instanceof KshmtFixedWorkSet) {
			if (notInLawOT != null) {
				((KshmtFixedWorkSet) this.entity).setOtNotInLaw(notInLawOT.v());
			}
		}
		if (this.entity instanceof KshmtDiffTimeWorkSet) {
			if (notInLawOT != null) {
				((KshmtDiffTimeWorkSet) this.entity).setOtNotInLaw(notInLawOT.v());
			}
		}
	}
}
