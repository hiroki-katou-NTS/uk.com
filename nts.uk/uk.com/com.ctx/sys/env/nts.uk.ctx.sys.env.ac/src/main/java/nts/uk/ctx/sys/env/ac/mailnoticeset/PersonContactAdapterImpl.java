/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.ac.mailnoticeset;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.person.pub.contact.PersonContactObject;
import nts.uk.ctx.bs.person.pub.contact.PersonContactPub;
import nts.uk.ctx.sys.env.dom.mailnoticeset.adapter.PersonContactAdapter;
import nts.uk.ctx.sys.env.dom.mailnoticeset.dto.PersonContactImport;

/**
 * The Class PersonContactAdapterImpl.
 */
@Stateless
public class PersonContactAdapterImpl implements PersonContactAdapter {

	/** The person contact pub. */
	@Inject
	private PersonContactPub personContactPub;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.env.dom.mailnoticeset.adapter.PersonContactAdapter#
	 * getListContact(java.util.List)
	 */
	@Override
	public List<PersonContactImport> getListContact(List<String> personIds) {
		List<PersonContactObject> listContact = this.personContactPub.getList(personIds);
		return listContact.stream().map(item -> new PersonContactImport(item.getPersonId(), item.getMailAdress(),
				item.getMobileMailAdress(), item.getCellPhoneNumber())).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.env.dom.mailnoticeset.adapter.PersonContactAdapter#
	 * register(nts.uk.ctx.sys.env.dom.mailnoticeset.dto.PersonContactImport)
	 */
	@Override
	public void register(PersonContactImport person) {
		this.personContactPub.register(person.getPersonId(), person.getCellPhoneNo(),
				person.getMailAddress(), person.getMobileMailAddress());
	}

}
