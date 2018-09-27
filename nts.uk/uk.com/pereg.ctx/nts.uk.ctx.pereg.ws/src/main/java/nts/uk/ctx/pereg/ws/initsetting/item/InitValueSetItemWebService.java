package nts.uk.ctx.pereg.ws.initsetting.item;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.ctx.pereg.app.find.initsetting.item.InitValueSetItemFinder;
import nts.uk.ctx.pereg.app.find.initsetting.item.SettingItemDto;
import nts.uk.ctx.pereg.app.find.initsetting.item.FindInitItemDto;

/**
 * @author sonnlb
 *
 */
@Path("ctx/pereg/initsetting/item")
@Produces(MediaType.APPLICATION_JSON)
public class InitValueSetItemWebService {

	@Inject
	private InitValueSetItemFinder finder;

	@POST
	@Path("findInit")
	public List<SettingItemDto> getAllInitItem(FindInitItemDto command) {
		return this.finder.getAllInitItemByCtgCode(true, command, false);
	}

}
