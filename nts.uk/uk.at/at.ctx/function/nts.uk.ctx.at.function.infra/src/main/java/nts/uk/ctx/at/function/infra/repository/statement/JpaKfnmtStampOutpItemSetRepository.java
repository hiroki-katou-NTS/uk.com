/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.infra.repository.statement;

import java.util.Optional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.statement.StampingOutputItemSet;
import nts.uk.ctx.at.function.dom.statement.StampingOutputItemSetRepository;
import nts.uk.ctx.at.function.infra.entity.statement.KfnmtStampOutpItemSet;
import nts.uk.ctx.at.function.infra.entity.statement.KfnmtStampOutpItemSetPK;

/**
 * The Class JpaKfnmtStampOutpItemSetRepository.
 */
public class JpaKfnmtStampOutpItemSetRepository extends JpaRepository implements StampingOutputItemSetRepository{

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.statement.StampingOutputItemSetRepository#getByCidAndCode(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<StampingOutputItemSet> getByCidAndCode(String companyId, String code) {
		KfnmtStampOutpItemSetPK primaryKey = new KfnmtStampOutpItemSetPK(companyId, code);
		return this.queryProxy().find(primaryKey, KfnmtStampOutpItemSet.class).map(entity -> this.toDomain(entity));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.statement.StampingOutputItemSetRepository#add(nts.uk.ctx.at.function.dom.statement.StampingOutputItemSet)
	 */
	@Override
	public void add(StampingOutputItemSet domain) {
		this.commandProxy().insert(this.toEntity(domain));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.statement.StampingOutputItemSetRepository#update(nts.uk.ctx.at.function.dom.statement.StampingOutputItemSet)
	 */
	@Override
	public void update(StampingOutputItemSet domain) {
		this.commandProxy().update(this.toEntity(domain));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.statement.StampingOutputItemSetRepository#removeByCidAndCode(java.lang.String, java.lang.String)
	 */
	@Override
	public void removeByCidAndCode(String companyId, String code) {
		KfnmtStampOutpItemSetPK primaryKey = new KfnmtStampOutpItemSetPK(companyId, code);
		this.commandProxy().remove(KfnmtStampOutpItemSet.class, primaryKey);;
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the stamping output item set
	 */
	private StampingOutputItemSet toDomain(KfnmtStampOutpItemSet entity) {
		return new StampingOutputItemSet(new JpaKfnmtStampOutpItemSetGetMemento(entity));
		
	}
	
	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kfnmt stamp outp item set
	 */
	private KfnmtStampOutpItemSet toEntity(StampingOutputItemSet domain) {
		KfnmtStampOutpItemSet entity = new KfnmtStampOutpItemSet();
		domain.saveToMemento(new JpaKfnmtStampOutpItemSetSetMemento(entity));
		return entity;
	}

}
