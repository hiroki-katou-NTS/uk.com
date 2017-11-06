package nts.uk.ctx.bs.employee.ws.regpersoninfo.copy.item;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.app.find.copy.item.CopySetItemFinder;
import nts.uk.ctx.bs.employee.app.find.init.item.SettingItemDto;

@Path("ctx/bs/person/info/setting/copySettingItem")
@Produces("application/json")
public class EmpCopySettingItemWebService extends WebService {

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
