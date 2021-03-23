package nts.uk.ctx.at.record.infra.repository.workrecord.goout;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.goout.OutManage;
import nts.uk.ctx.at.record.dom.workrecord.goout.OutManageRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.goout.KrcmtGooutMng;

/**
 * The Class JpaOutManageRepository.
 *
 * @author hoangdd
 */
@Stateless
public class JpaOutManageRepository extends JpaRepository implements OutManageRepository{
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.goout.OutManageRepository#findByID(java.lang.String)
	 */
	@Override
	public Optional<OutManage> findByID(String companyID) {
		return this.queryProxy().find(companyID, KrcmtGooutMng.class).map(e -> this.toDomain(e));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.goout.OutManageRepository#update(nts.uk.ctx.at.record.dom.workrecord.goout.OutManage)
	 */
	@Override
	public void update(OutManage outManage) {
		this.commandProxy().update(this.toEntity(outManage));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.goout.OutManageRepository#add(nts.uk.ctx.at.record.dom.workrecord.goout.OutManage)
	 */
	@Override
	public void add(OutManage outManage) {
		this.commandProxy().insert(this.toEntity(outManage));
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the out manage
	 */
	private OutManage toDomain(KrcmtGooutMng entity) {
		OutManage domain = new OutManage(new JpaOutManageGetMemento(entity));
		return domain;
	}
	
	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the krcst out manage
	 */
	private KrcmtGooutMng toEntity(OutManage domain) {
		KrcmtGooutMng entity = new KrcmtGooutMng();
		domain.saveToMemento(new JpaOutManageSetMemento(entity));
		return entity;
	}

}

