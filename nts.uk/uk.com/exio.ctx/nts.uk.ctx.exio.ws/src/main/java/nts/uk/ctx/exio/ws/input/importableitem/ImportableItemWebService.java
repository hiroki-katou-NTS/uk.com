package nts.uk.ctx.exio.ws.input.importableitem;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItem;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItemsRepository;

@Path("exio/input/importableitem")
@Produces(MediaType.APPLICATION_JSON)
public class ImportableItemWebService {

	@Inject
	private ImportableItemsRepository repo;
	
	@POST
	@Path("domain/{id}")
	public List<ImportableItem> find(@PathParam("id") int importableDomainId) {
		return repo.get(ImportingDomainId.valueOf(importableDomainId));
	}
}
