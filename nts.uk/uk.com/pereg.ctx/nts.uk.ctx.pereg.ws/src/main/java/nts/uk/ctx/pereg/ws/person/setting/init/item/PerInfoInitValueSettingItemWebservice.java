package nts.uk.ctx.pereg.ws.person.setting.init.item;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pereg.app.find.person.setting.init.item.PerInfoInitValueSetItemFinder;
import nts.uk.ctx.pereg.app.find.person.setting.init.item.PerInfoInitValueSettingItemDto;
import nts.uk.ctx.pereg.app.find.person.setting.init.item.ReferenceHistoryDto;
import nts.uk.ctx.pereg.app.find.person.setting.init.item.ReferenceHistoryFinder;

@Path("ctx/pereg/person/info/setting/init/item")
@Produces("application/json")
public class PerInfoInitValueSettingItemWebservice extends WebService {
	@Inject
	private PerInfoInitValueSetItemFinder finder;
	@Inject
	private ReferenceHistoryFinder refHistFinder;

	@POST
	@Path("find/{settingId}/{perInfoCtgId}")
	public List<PerInfoInitValueSettingItemDto> getAllItem(@PathParam("settingId") String settingId, @PathParam("perInfoCtgId") String perInfoCtgId) {
		List<PerInfoInitValueSettingItemDto> x = this.finder.getAllItem(settingId, perInfoCtgId);
		return x;
	}

	@POST
	@Path("referenceHistory")
	public ReferenceHistoryDto referHistSel(ReferenceHistoryDto param){
		return this.refHistFinder.referHistSel(param);
	}
}
