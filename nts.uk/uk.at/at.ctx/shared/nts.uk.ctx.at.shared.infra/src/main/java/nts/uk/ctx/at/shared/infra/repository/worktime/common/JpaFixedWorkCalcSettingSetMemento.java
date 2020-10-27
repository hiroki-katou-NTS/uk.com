/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.fixedset.ExceededPredAddVacationCalc;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkCalcSettingSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.OverTimeCalcNoBreak;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtDiffTimeWorkSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedWorkSet;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class JpaFixedWorkCalcSettingSetMemento.
 *
 * @param <T>
 *            the generic type
 */
public class JpaFixedWorkCalcSettingSetMemento<T extends ContractUkJpaEntity> implements FixedWorkCalcSettingSetMemento {

	/** The entity. */
	private T entity;

	/**
	 * Instantiates a new jpa fixed work calc setting set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaFixedWorkCalcSettingSetMemento(T entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkCalcSettingSetMemento
	 * #setExceededPredAddVacationCalc(nts.uk.ctx.at.shared.dom.worktime.
	 * fixedset.ExceededPredAddVacationCalc)
	 */
	@Override
	public void setExceededPredAddVacationCalc(ExceededPredAddVacationCalc exceededPredAddVacationCalc) {
		if (this.entity instanceof KshmtFixedWorkSet) {
			exceededPredAddVacationCalc.saveToMemento(
					new JpaExceededPredAddVacationCalcSetMemento<KshmtFixedWorkSet>((KshmtFixedWorkSet) this.entity));
		}
		if (this.entity instanceof KshmtDiffTimeWorkSet) {
			exceededPredAddVacationCalc
					.saveToMemento(new JpaExceededPredAddVacationCalcSetMemento<KshmtDiffTimeWorkSet>(
							(KshmtDiffTimeWorkSet) this.entity));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkCalcSettingSetMemento
	 * #setOverTimeCalcNoBreak(nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * OverTimeCalcNoBreak)
	 */
	@Override
	public void setOverTimeCalcNoBreak(OverTimeCalcNoBreak overTimeCalcNoBreak) {
		if (this.entity instanceof KshmtFixedWorkSet) {
			overTimeCalcNoBreak.saveToMemento(
					new JpaOverTimeCalcNoBreakSetMemento<KshmtFixedWorkSet>((KshmtFixedWorkSet) this.entity));
		}
		if (this.entity instanceof KshmtDiffTimeWorkSet) {
			overTimeCalcNoBreak.saveToMemento(
					new JpaOverTimeCalcNoBreakSetMemento<KshmtDiffTimeWorkSet>((KshmtDiffTimeWorkSet) this.entity));
		}
	}
}
