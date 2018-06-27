package nts.uk.ctx.pereg.ws.copysetting.item;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.pereg.app.command.copysetting.item.UpdatePerInfoItemDefCopy;
import nts.uk.ctx.pereg.app.command.copysetting.item.UpdatePerInfoItemDefCopyCommandHandler;
import nts.uk.ctx.pereg.app.find.copysetting.item.CopySettingItemDto;
import nts.uk.ctx.pereg.app.find.copysetting.item.CopySettingItemFinder;
import nts.uk.ctx.pereg.app.find.copysetting.item.CopySettingItemQuery;
import nts.uk.ctx.pereg.app.find.initsetting.item.SettingItemDto;

/**
 * @author sonnlb
 *
 */
@Path("ctx/pereg/copysetting/item")
@Produces("application/json")
public class EmpCopySettingItemWebService {

	@Inject
	private CopySettingItemFinder finder;

	@Inject
	private UpdatePerInfoItemDefCopyCommandHandler updatePerInfoItemDefCopyCommandHandler;

	@POST
	@Path("getAll")
	public List<SettingItemDto> getAllCopyItemByCtgCode(CopySettingItemQuery query) {
		return this.finder.getAllCopyItemByCtgCode(query);
	}

	@POST
	@Path("update/updatePerInfoItemDefCopy")
	public void updatePerInfoItemDefCopy(UpdatePerInfoItemDefCopy command) {
		this.updatePerInfoItemDefCopyCommandHandler.handle(command);
	}

	@POST
	@Path("findby/getPerInfoItemByCtgId")
	public List<CopySettingItemDto> getPerInfoItemByCtgId(String ctgId) {
		return this.finder.getPerInfoDefById(ctgId);
	}

}
