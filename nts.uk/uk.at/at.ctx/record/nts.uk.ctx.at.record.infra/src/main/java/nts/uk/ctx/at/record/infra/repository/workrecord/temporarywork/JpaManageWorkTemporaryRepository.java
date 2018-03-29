/**
 * 
 */
package nts.uk.ctx.at.record.infra.repository.workrecord.temporarywork;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.temporarywork.ManageWorkTemporary;
import nts.uk.ctx.at.record.dom.workrecord.temporarywork.ManageWorkTemporaryRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.temporarywork.KrcstManageWorkTemp;

/**
 * @author hoangdd
 *
 */
@Stateless
public class JpaManageWorkTemporaryRepository extends JpaRepository implements ManageWorkTemporaryRepository{

	
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.temporarywork.ManageWorkTemporaryRepository#findByCID(java.lang.String)
	 */
	@Override
	public Optional<ManageWorkTemporary> findByCID(String companyID) {
		return this.queryProxy().find(companyID, KrcstManageWorkTemp.class).map(e -> toDomain(e));
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
	
	private KrcstManageWorkTemp toEntity(ManageWorkTemporary domain) {
		KrcstManageWorkTemp entity = new KrcstManageWorkTemp();
		domain.saveToMemento(new JpaManageWorkTemporarySetMemento(entity));
		return entity;
	}
	
	private ManageWorkTemporary toDomain(KrcstManageWorkTemp entity) {
		ManageWorkTemporary domain = new ManageWorkTemporary(new JpaManageWorkTemporaryGetMemento(entity));
		return domain;
	}

}

