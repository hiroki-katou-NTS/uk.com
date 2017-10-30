/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.ac.person;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.person.pub.person.PersonInfoExport;
import nts.uk.ctx.bs.person.pub.person.PersonPub;
import nts.uk.ctx.sys.auth.dom.adapter.person.MailAddress;
import nts.uk.ctx.sys.auth.dom.adapter.person.PersonAdapter;
import nts.uk.ctx.sys.auth.dom.adapter.person.PersonImport;

/**
 * The Class PersonAdapterImpl.
 */
@Stateless
public class PersonAdapterImpl implements PersonAdapter {
	
	/** The person pub. */
	@Inject
	private PersonPub personPub;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.auth.dom.adapter.person.PersonAdapter#findById(java.lang.String)
	 */
	@Override
	public PersonImport findById(String personId) {
		PersonInfoExport personExport = this.personPub.findById(personId);
		PersonImport personInfoImport = PersonImport.builder()
				.personId(personExport.getPersonId())
				.personName(personExport.getPersonName())
				.mailAddress(new MailAddress(personExport.getPMailAddr().v()))
				.build();
		return personInfoImport;
	}
}
