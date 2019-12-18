package nts.uk.ctx.at.shared.ws.person;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.shared.app.find.person.PersonAdaptorFinder;
import nts.uk.ctx.at.shared.dom.person.PersonImport;

@Path("ctx/at/shared/person")
@Produces("application/json")
public class PersonWebServices {
	/** The closure service. */
	@Inject
	private PersonAdaptorFinder personAdaptor;

	@POST
	@Path("findById")
	public List<PersonImport> findById(FindbyIdDto dto) {
		return this.personAdaptor.findByPids(dto.getPersonIds());
	}

}
