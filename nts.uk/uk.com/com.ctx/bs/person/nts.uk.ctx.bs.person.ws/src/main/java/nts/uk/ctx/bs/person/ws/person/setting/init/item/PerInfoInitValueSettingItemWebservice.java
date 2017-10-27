package nts.uk.ctx.bs.person.ws.person.setting.init.item;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import find.person.setting.init.item.PerInfoInitValueSetItemFinder;
import find.person.setting.init.item.PerInfoInitValueSettingItemDto;
import nts.arc.layer.ws.WebService;

@Path("ctx/bs/person/info/setting/init/item")
@Produces("application/json")
public class PerInfoInitValueSettingItemWebservice extends WebService {
	@Inject
	private PerInfoInitValueSetItemFinder finder;

	@POST
	@Path("find/{settingId}/{perInfoCtgId}")
	public List<PerInfoInitValueSettingItemDto> getAllItem(@PathParam("settingId") String settingId, @PathParam("perInfoCtgId") String perInfoCtgId) {
		return this.finder.getAllItem(settingId, perInfoCtgId);
	}


}
