/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.infra.repository.mailserver;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.env.dom.mailserver.MailServer;
import nts.uk.ctx.sys.env.dom.mailserver.MailServerRepository;
import nts.uk.ctx.sys.env.infra.entity.mailserver.SevstMailServer;

/**
 * The Class JpaMailServerRepository.
 */
@Stateless
public class JpaMailServerRepository extends JpaRepository implements MailServerRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.env.dom.mailserver.MailServerRepository#findBy(java.lang.
	 * String)
	 */
	@Override
	public Optional<MailServer> findBy(String companyId) {
		return this.queryProxy()
				.find(companyId, SevstMailServer.class)
				.map(e -> this.toDomain(e));
	}

	@Override
	public void add(MailServer mailSetting) {
		this.commandProxy().insert(this.toEntity(mailSetting));
	}

	@Override
	public void update(MailServer mailSetting) {
		Optional<SevstMailServer> optinal = this.queryProxy()
				.find(mailSetting.getCompanyId(), SevstMailServer.class);
		
		SevstMailServer entity = optinal.get();
	
		JpaMailServerSetMemento memento = new JpaMailServerSetMemento(entity);
		mailSetting.saveToMemento(memento);
		
		this.commandProxy().update(entity);
		
	}
	
	
	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return mail server entity
	 */
	private SevstMailServer toEntity(MailServer domain){
		SevstMailServer entity = new SevstMailServer();
		domain.saveToMemento(new JpaMailServerSetMemento(entity));
		return entity;
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the management category
	 */
	private MailServer toDomain(SevstMailServer entity){
		return new MailServer(new JpaMailServerGetMemento(entity));
	}

}
