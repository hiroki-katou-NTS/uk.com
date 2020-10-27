/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.CommonRestSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedRestCalculateMethod;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkRestSetGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.RestTimeOfficeWorkCalcMethod;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtDiffTimeWorkSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedWorkSet;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class JpaFixedWorkRestSetGetMemento.
 *
 * @param <T>
 *            the generic type
 */
public class JpaFixedWorkRestSetGetMemento<T extends ContractUkJpaEntity> implements FixedWorkRestSetGetMemento {

	/** The entity. */
	private T entity;

	/**
	 * Instantiates a new jpa fixed work rest set get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaFixedWorkRestSetGetMemento(T entity) {
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkRestSetGetMemento#
	 * getCommonRestSet()
	 */
	@Override
	public CommonRestSetting getCommonRestSet() {
		if (this.entity instanceof KshmtFixedWorkSet) {
			return new CommonRestSetting(
					RestTimeOfficeWorkCalcMethod.valueOf(((KshmtFixedWorkSet) this.entity).getLevRestCalcType()));
		}
		if (this.entity instanceof KshmtDiffTimeWorkSet) {
			return new CommonRestSetting(
					RestTimeOfficeWorkCalcMethod.valueOf(((KshmtDiffTimeWorkSet) this.entity).getDtCommonRestSet()));
		}
		throw new IllegalStateException("entity type is not valid");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkRestSetGetMemento#
	 * getIsPlanActualNotMatchMasterRefer()
	 */
	@Override
	public boolean getIsPlanActualNotMatchMasterRefer() {
		if (this.entity instanceof KshmtFixedWorkSet) {
			return BooleanGetAtr.getAtrByInteger(((KshmtFixedWorkSet) this.entity).getIsPlanActualNotMatchMasterRefe());
		}
		if (this.entity instanceof KshmtDiffTimeWorkSet) {
			return BooleanGetAtr
					.getAtrByInteger(((KshmtDiffTimeWorkSet) this.entity).getDtIsPlanActualNotMatchMasterRefe());
		}
		throw new IllegalStateException("entity type is not valid");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkRestSetGetMemento#
	 * getCalculateMethod()
	 */
	@Override
	public FixedRestCalculateMethod getCalculateMethod() {
		if (this.entity instanceof KshmtFixedWorkSet) {
			return FixedRestCalculateMethod.valueOf(((KshmtFixedWorkSet) this.entity).getCalcMethod());
		}
		if (this.entity instanceof KshmtDiffTimeWorkSet) {
			return FixedRestCalculateMethod.valueOf(((KshmtDiffTimeWorkSet) this.entity).getDtCalcMethod());
		}
		throw new IllegalStateException("entity type is not valid");
	}

}
