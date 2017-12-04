package nts.uk.ctx.pereg.ws.person.info.value;

import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoClsDto;

@Path("ctx/bs/itemdefinition/value")
@Produces(MediaType.APPLICATION_JSON)
public class ItemDefinitionValueWebservice extends WebService {

	@POST
	@Path("sample-get")
	public List<LayoutPersonInfoClsDto> getListItemDefinedwValue() {
		return null;
	}

	@POST
	@Path("sample-post")
	public List<LayoutPersonInfoClsDto> postListItemDefinedwValue(List<LayoutPersonInfoClsDto> cls) {
		return cls;
	}
}
