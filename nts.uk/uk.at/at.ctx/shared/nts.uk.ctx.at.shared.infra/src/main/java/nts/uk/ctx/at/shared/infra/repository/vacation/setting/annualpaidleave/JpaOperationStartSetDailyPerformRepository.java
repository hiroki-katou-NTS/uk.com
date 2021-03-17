/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.annualpaidleave;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.OperationStartSetDailyPerform;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.OperationStartSetDailyPerformRepository;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.annualpaidleave.KshmtDayOpeStartDate;

/**
 * The Class JpaOperationStartSetDailyPerformRepository.
 */
@Stateless
public class JpaOperationStartSetDailyPerformRepository extends JpaRepository implements OperationStartSetDailyPerformRepository{

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.OperationStartSetDailyPerformRepository#findByCid(nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Optional<OperationStartSetDailyPerform> findByCid(CompanyId companyId) {
		return this.queryProxy().find(companyId, KshmtDayOpeStartDate.class).map(entity -> this.toDomain(entity));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.OperationStartSetDailyPerformRepository#add(nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.OperationStartSetDailyPerform)
	 */
	@Override
	public void add(OperationStartSetDailyPerform domain) {
		this.commandProxy().insert(this.toEntity(domain));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.OperationStartSetDailyPerformRepository#edit(nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.OperationStartSetDailyPerform)
	 */
	@Override
	public void update(OperationStartSetDailyPerform domain) {
		this.commandProxy().update(this.toEntity(domain));
	}
	
	private OperationStartSetDailyPerform toDomain(KshmtDayOpeStartDate entity) {
		return new OperationStartSetDailyPerform(new JpaOperationStartSetDailyPerformGetMemento(entity));
	}

	private KshmtDayOpeStartDate toEntity(OperationStartSetDailyPerform domain) {
		KshmtDayOpeStartDate entity = new KshmtDayOpeStartDate();
		domain.saveToMemento(new JpaOperationStartSetDailyPerformSetMemento(entity));
		return entity;
	}
}

