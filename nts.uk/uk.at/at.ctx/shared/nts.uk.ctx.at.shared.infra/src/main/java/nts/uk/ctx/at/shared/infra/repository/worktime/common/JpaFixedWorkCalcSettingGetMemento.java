/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.fixedset.ExceededPredAddVacationCalc;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkCalcSettingGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.OverTimeCalcNoBreak;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtDiffTimeWorkSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedWorkSet;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class JpaFixedWorkCalcSettingGetMemento.
 */
public class JpaFixedWorkCalcSettingGetMemento<T extends ContractUkJpaEntity> implements FixedWorkCalcSettingGetMemento {

	/** The entity. */
	private T entity;

	/**
	 * Instantiates a new jpa fixed work calc setting get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaFixedWorkCalcSettingGetMemento(T entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkCalcSettingGetMemento
	 * #getExceededPredAddVacationCalc()
	 */
	@Override
	public ExceededPredAddVacationCalc getExceededPredAddVacationCalc() {
		if (this.entity instanceof KshmtFixedWorkSet) {
			return new ExceededPredAddVacationCalc(
					new JpaExceededPredAddVacationCalcGetMemento<KshmtFixedWorkSet>((KshmtFixedWorkSet) this.entity));
		}
		if (this.entity instanceof KshmtDiffTimeWorkSet) {
			return new ExceededPredAddVacationCalc(new JpaExceededPredAddVacationCalcGetMemento<KshmtDiffTimeWorkSet>(
					(KshmtDiffTimeWorkSet) this.entity));
		}
		throw new IllegalStateException("entity type is not valid");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkCalcSettingGetMemento
	 * #getOverTimeCalcNoBreak()
	 */
	@Override
	public OverTimeCalcNoBreak getOverTimeCalcNoBreak() {
		if (this.entity instanceof KshmtFixedWorkSet) {
			return new OverTimeCalcNoBreak(
					new JpaOverTimeCalcNoBreakGetMemento<KshmtFixedWorkSet>((KshmtFixedWorkSet) this.entity));
		}
		if (this.entity instanceof KshmtDiffTimeWorkSet) {
			return new OverTimeCalcNoBreak(
					new JpaOverTimeCalcNoBreakGetMemento<KshmtDiffTimeWorkSet>((KshmtDiffTimeWorkSet) this.entity));
		}
		throw new IllegalStateException("entity type is not valid");
	}

}
