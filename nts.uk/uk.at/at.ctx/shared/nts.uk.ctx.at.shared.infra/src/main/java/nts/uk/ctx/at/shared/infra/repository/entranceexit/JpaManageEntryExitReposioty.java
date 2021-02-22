package nts.uk.ctx.at.shared.infra.repository.entranceexit;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.entranceexit.ManageEntryExit;
import nts.uk.ctx.at.shared.dom.entranceexit.ManageEntryExitRepository;
import nts.uk.ctx.at.shared.infra.entity.entranceexit.KshmtGateMng;

/**
 * @author hoangdd
 *
 */
@Stateless
public class JpaManageEntryExitReposioty extends JpaRepository implements ManageEntryExitRepository{

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.entranceexit.ManageEntryExitRepository#findByID(java.lang.String)
	 */
	@Override
	public Optional<ManageEntryExit> findByID(String companyID) {
		return this.queryProxy().find(companyID, KshmtGateMng.class).map(e -> this.toDomain(e));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.entranceexit.ManageEntryExitRepository#add(nts.uk.ctx.at.shared.dom.entranceexit.ManageEntryExit)
	 */
	@Override
	public void add(ManageEntryExit manageEntryExit) {
		this.commandProxy().insert(this.toEntity(manageEntryExit));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.entranceexit.ManageEntryExitRepository#update(nts.uk.ctx.at.shared.dom.entranceexit.ManageEntryExit)
	 */
	@Override
	public void update(ManageEntryExit manageEntryExit) {
		this.commandProxy().update(this.toEntity(manageEntryExit));
	}

	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kshst manage entry exit
	 */
	private KshmtGateMng toEntity(ManageEntryExit domain) {
		KshmtGateMng entity = new KshmtGateMng();
		domain.saveToMemento(new JpaManageEntryExitSetMemento(entity));
		return entity;
	}
	
	private ManageEntryExit toDomain(KshmtGateMng entity) {
		ManageEntryExit domain = new ManageEntryExit(new JpaManageEntryExitGetMemento(entity));
		return domain;
	}
}

