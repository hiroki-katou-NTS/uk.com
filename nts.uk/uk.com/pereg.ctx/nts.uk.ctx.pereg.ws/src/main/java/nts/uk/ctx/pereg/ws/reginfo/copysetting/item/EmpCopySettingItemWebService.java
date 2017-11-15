package nts.uk.ctx.pereg.ws.reginfo.copysetting.item;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.pereg.app.find.reginfo.copysetting.item.CopySetItemFinder;
import nts.uk.ctx.pereg.app.find.reginfo.initsetting.item.SettingItemDto;

/**
 * @author sonnlb
 *
 */
@Path("ctx/bs/person/info/setting/copySettingItem")
@Produces("application/json")
public class EmpCopySettingItemWebService {

	@Inject
	private CopySetItemFinder finder;

	@POST
	@Path("getAll/{employeeId}/{categoryCd}/{baseDate}")
	public List<SettingItemDto> getAllCopyItemByCtgCode(@PathParam("categoryCd") String categoryCd,
			@PathParam("employeeId") String employeeId, @PathParam("baseDate") String baseDate) {
		return this.finder.getAllCopyItemByCtgCode(categoryCd, employeeId,
				GeneralDate.fromString(baseDate, "yyyyMMdd"));
	}

}
