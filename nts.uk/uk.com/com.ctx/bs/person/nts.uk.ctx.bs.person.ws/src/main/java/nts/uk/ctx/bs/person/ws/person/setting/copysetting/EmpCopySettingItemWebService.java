package nts.uk.ctx.bs.person.ws.person.setting.copysetting;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import find.person.info.item.PerInfoCopyItemDto;
import find.person.setting.copysetting.EmpCopySettingItemFinder;
import nts.arc.layer.ws.WebService;

@Path("ctx/bs/person/info/setting/copySettingItem")
@Produces("application/json")
public class EmpCopySettingItemWebService extends WebService {

	@Inject
	private EmpCopySettingItemFinder finder;

	@POST
	@Path("getAll/{categoryId}")
	public List<PerInfoCopyItemDto> getItemListFromCtgId(@PathParam("categoryId") String categoryId) {
		return this.finder.getEmpCopySettingItemList(categoryId);
	}

}
