/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.ws.person;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.basic.app.find.person.PersonDto;
import nts.uk.ctx.basic.app.find.person.PersonFinder;

/**
 * The Class PersonWs.
 */
@Path("basic/person")
@Produces("application/json")
public class PersonWs extends WebService{

	/** The finder. */
	@Inject
	private PersonFinder finder;
	
	
	/**
	 * Gets the all person.
	 *
	 * @return the all person
	 */
	@POST
	@Path("getallperson")
	public List<PersonDto> getAllPerson(){
		return this.finder.getAllPerson();
	}
}
