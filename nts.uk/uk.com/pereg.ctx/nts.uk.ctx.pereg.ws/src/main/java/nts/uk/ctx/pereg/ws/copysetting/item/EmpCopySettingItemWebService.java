package nts.uk.ctx.pereg.ws.copysetting.item;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import find.person.info.category.PerInfoCtgMapDto;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pereg.app.command.copysetting.item.UpdatePerInfoItemDefCopy;
import nts.uk.ctx.pereg.app.command.copysetting.item.UpdatePerInfoItemDefCopyCommandHandler;
import nts.uk.ctx.pereg.app.find.copysetting.item.CopySetItemFinder;
import nts.uk.ctx.pereg.app.find.initsetting.item.SettingItemDto;

/**
 * @author sonnlb
 *
 */
@Path("ctx/pereg/copysetting/item")
@Produces("application/json")
public class EmpCopySettingItemWebService {

	@Inject
	private CopySetItemFinder finder;
	
	@Inject 
	private UpdatePerInfoItemDefCopyCommandHandler updatePerInfoItemDefCopyCommandHandler;

	@POST
	@Path("getAll/{employeeId}/{categoryCd}/{baseDate}")
	public List<SettingItemDto> getAllCopyItemByCtgCode(@PathParam("categoryCd") String categoryCd,
			@PathParam("employeeId") String employeeId, @PathParam("baseDate") String baseDate) {
		return this.finder.getAllCopyItemByCtgCode(categoryCd, employeeId,
				GeneralDate.fromString(baseDate, "yyyyMMdd"));
	}
	
	@POST
	@Path("find/perInfoCtgHasItems")
	public List<PerInfoCtgMapDto> getPerInfoCtgHasItems(String ctgName){
		return finder.getAllPerInfoCategoryWithCondition(ctgName);
	}
	
	@POST
	@Path("update/updatePerInfoItemDefCopy")
	public void updatePerInfoItemDefCopy(UpdatePerInfoItemDefCopy command){
		this.updatePerInfoItemDefCopyCommandHandler.handle(command);
	}

}
