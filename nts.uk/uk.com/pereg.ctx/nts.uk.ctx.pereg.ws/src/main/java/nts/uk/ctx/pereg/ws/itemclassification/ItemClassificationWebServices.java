
package nts.uk.ctx.pereg.ws.itemclassification;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoClsDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoClsFinder;

@Path("ctx/pereg/person/itemcls")
@Produces("application/json")
public class ItemClassificationWebServices {
	
	@Inject
	private LayoutPersonInfoClsFinder clsFinder;
	
	@POST
	@Path("getById/{lid}")
	public List<LayoutPersonInfoClsDto> getListClsById(@PathParam("lid") String lid) {
		List<LayoutPersonInfoClsDto> x  = clsFinder.getListClsDto(lid);
		return x;
	}
	
}
