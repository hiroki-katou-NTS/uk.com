package nts.uk.ctx.pereg.ws.initsetting.item;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.pereg.app.find.initsetting.item.InitValueSetItemFinder;
import nts.uk.ctx.pereg.app.find.initsetting.item.SettingItemDto;

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
	@Path("findInit/{settingId}/{categoryCd}/{baseDate}")
	public List<SettingItemDto> getAllInitItem(@PathParam("settingId") String settingId,
			@PathParam("categoryCd") String categoryCd, @PathParam("baseDate") String baseDate) {
		return this.finder.getAllInitItemByCtgCode(settingId, categoryCd, GeneralDate.fromString(baseDate, "yyyyMMdd"));
	}

}
