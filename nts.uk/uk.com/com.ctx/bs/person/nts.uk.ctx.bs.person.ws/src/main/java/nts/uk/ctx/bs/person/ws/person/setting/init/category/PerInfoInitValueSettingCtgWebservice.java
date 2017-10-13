package nts.uk.ctx.bs.person.ws.person.setting.init.category;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import find.person.setting.init.category.PerInfoInitValueSettingCtgDto;
import find.person.setting.init.category.PerInfoInitValueSettingCtgFinder;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.bs.person.dom.person.setting.init.category.PerInfoInitValueSettingCtg;

@Path("ctx/bs/person/info/setting/init/ctg")
@Produces("application/json")
public class PerInfoInitValueSettingCtgWebservice extends WebService {

	@Inject
	private PerInfoInitValueSettingCtgFinder finder;

	@POST
	@Path("findAll")
	public List<PerInfoInitValueSettingCtg> getAllInitValueSetting() {
		return this.finder.getAllCategory();
	}

	// sonnlb

	@POST
	@Path("findAllBySetId/{settingId}")
	public List<PerInfoInitValueSettingCtgDto> getAllCategoryBySetId(@PathParam("findAllBySetId") String settingId) {
		return this.finder.getAllCategoryBySetId(settingId);
	}

	// sonnlb

}
