/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.temporarywork;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.temporarywork.ManageWorkTemporary;
import nts.uk.ctx.at.record.dom.workrecord.temporarywork.ManageWorkTemporaryRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.temporarywork.KrcmtTemporaryMng;

/**
 * The Class JpaManageWorkTemporaryRepository.
 */
@Stateless
public class JpaManageWorkTemporaryRepository extends JpaRepository implements ManageWorkTemporaryRepository{

	
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.temporarywork.ManageWorkTemporaryRepository#findByCID(java.lang.String)
	 */
	@Override
	public Optional<ManageWorkTemporary> findByCID(String companyID) {
		return this.queryProxy().find(companyID, KrcmtTemporaryMng.class).map(e -> toDomain(e));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.temporarywork.ManageWorkTemporaryRepository#update(nts.uk.ctx.at.record.dom.workrecord.temporarywork.ManageWorkTemporary)
	 */
	@Override
	public void update(ManageWorkTemporary manageWorkTemporary) {
		this.commandProxy().update(toEntity(manageWorkTemporary));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.temporarywork.ManageWorkTemporaryRepository#add(nts.uk.ctx.at.record.dom.workrecord.temporarywork.ManageWorkTemporary)
	 */
	@Override
	public void add(ManageWorkTemporary manageWorkTemporary) {
		this.commandProxy().insert(toEntity(manageWorkTemporary));
	}
	
	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the krcst manage work temp
	 */
	private KrcmtTemporaryMng toEntity(ManageWorkTemporary domain) {
		KrcmtTemporaryMng entity = new KrcmtTemporaryMng();
		domain.saveToMemento(new JpaManageWorkTemporarySetMemento(entity));
		return entity;
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the manage work temporary
	 */
	private ManageWorkTemporary toDomain(KrcmtTemporaryMng entity) {
		ManageWorkTemporary domain = new ManageWorkTemporary(new JpaManageWorkTemporaryGetMemento(entity));
		return domain;
	}

}

