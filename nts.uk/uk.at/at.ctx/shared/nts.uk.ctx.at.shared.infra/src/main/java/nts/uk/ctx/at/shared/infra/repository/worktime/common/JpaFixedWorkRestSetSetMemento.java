/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.CommonRestSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedRestCalculateMethod;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkRestSetSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtDiffTimeWorkSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedWorkSet;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class JpaFixedWorkRestSetSetMemento.
 *
 * @param <T>
 *            the generic type
 */
public class JpaFixedWorkRestSetSetMemento<T extends ContractUkJpaEntity> implements FixedWorkRestSetSetMemento {

	/** The entity. */
	private T entity;

	/**
	 * Instantiates a new jpa fixed work rest set set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaFixedWorkRestSetSetMemento(T entity) {
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkRestSetSetMemento
	 * #setCommonRestSet(nts.uk.ctx.at.shared.dom.worktime.common.
	 * CommonRestSetting)
	 */
	@Override
	public void setCommonRestSet(CommonRestSetting set) {
		if (this.entity instanceof KshmtFixedWorkSet) {
			((KshmtFixedWorkSet) this.entity).setLevRestCalcType(set.getCalculateMethod().value);
			return;
		}
		if (this.entity instanceof KshmtDiffTimeWorkSet) {
			((KshmtDiffTimeWorkSet) this.entity).setDtCommonRestSet(set.getCalculateMethod().value);
			return;
		}
		throw new IllegalStateException("entity type is not valid");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkRestSetSetMemento#
	 * setIsPlanActualNotMatchMasterRefer(boolean)
	 */
	@Override
	public void setIsPlanActualNotMatchMasterRefer(boolean isPlanActualNotMatchMasterRefer) {
		if (this.entity instanceof KshmtFixedWorkSet) {
			((KshmtFixedWorkSet) this.entity)
					.setIsPlanActualNotMatchMasterRefe(BooleanGetAtr.getAtrByBoolean(isPlanActualNotMatchMasterRefer));
			return;
		}
		if (this.entity instanceof KshmtDiffTimeWorkSet) {
			((KshmtDiffTimeWorkSet) this.entity)
					.setDtIsPlanActualNotMatchMasterRefe(BooleanGetAtr.getAtrByBoolean(isPlanActualNotMatchMasterRefer));
			return;
		}
		throw new IllegalStateException("entity type is not valid");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkRestSetSetMemento
	 * #setCalculateMethod(nts.uk.ctx.at.shared.dom.worktime.common.
	 * FixedRestCalculateMethod)
	 */
	@Override
	public void setCalculateMethod(FixedRestCalculateMethod method) {
		if (this.entity instanceof KshmtFixedWorkSet) {
			((KshmtFixedWorkSet) this.entity).setCalcMethod(method.value);
			return;
		}
		if (this.entity instanceof KshmtDiffTimeWorkSet) {
			((KshmtDiffTimeWorkSet) this.entity).setDtCalcMethod(method.value);
			return;
		}
		throw new IllegalStateException("entity type is not valid");
	}

}
