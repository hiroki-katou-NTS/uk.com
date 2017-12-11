package nts.uk.ctx.pereg.ws.initsetting.category;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.pereg.app.find.person.setting.init.category.PerInfoInitValueSettingCtgFinder;
import nts.uk.ctx.pereg.app.find.person.setting.init.category.SettingCtgDto;

/**
 * @author sonnlb
 *
 */
@Path("ctx/pereg/initsetting/category")
@Produces("application/json")
public class PerInfoInitValueSettingCtgWebservice {

	// sonnlb code start
	@Inject
	private PerInfoInitValueSettingCtgFinder cgtFinder;
	// sonnlb code end

	// sonnlb code start

	@POST
	@Path("findAllBySetId/{settingId}")
	public List<SettingCtgDto> getAllCategoryBySetId(@PathParam("settingId") String settingId) {
		return this.cgtFinder.getAllCategoryBySetId(settingId);
	}

	// sonnlb code end
}
