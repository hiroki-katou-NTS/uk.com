package nts.uk.ctx.sys.portal.ws.toppagesetting;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.sys.portal.app.command.toppagesetting.AddTopPagePersonSettingCommandHandler;
import nts.uk.ctx.sys.portal.app.command.toppagesetting.DeleteTopPagePersonSettingCommandHandler;
import nts.uk.ctx.sys.portal.app.command.toppagesetting.TopPagePersonSettingCommandBase;
import nts.uk.ctx.sys.portal.app.command.toppagesetting.UpdateTopPagePersonSettingCommandHandler;
import nts.uk.ctx.sys.portal.app.find.toppagesetting.TopPagePersonSettingDto;
import nts.uk.ctx.sys.portal.app.find.toppagesetting.TopPagePersonSettingFinder;

/**
 * 
 * @author sonnh1
 *
 */
@Path("sys/portal/toppagesetting/personset")
@Produces("application/json")
public class TopPagePersonSetWebService {

	@Inject
	TopPagePersonSettingFinder topPagePersonSettingFinder;

	@Inject
	UpdateTopPagePersonSettingCommandHandler updateTopPagePersonSettingCommandHandler;
	
	@Inject
	AddTopPagePersonSettingCommandHandler addTopPagePersonSettingCommandHandler;
	
	@Inject
	DeleteTopPagePersonSettingCommandHandler removeTopPagePersonSettingCommandHandler;

	/**
	 * Find all.
	 * ドメインモデル「個人別トップページ設定」を取得する
	 * @param listSid the list sid
	 * @return the list
	 */
	@POST
	@Path("findBySids")
	public List<TopPagePersonSettingDto> findAll(List<String> listSid) {
		return this.topPagePersonSettingFinder.getAllByEmployeeIds(listSid);
	}
	
	@POST
	@Path("findBySid")
	public TopPagePersonSettingDto findBySid(String sId) {
		return this.topPagePersonSettingFinder.getByCompanyIdAndEmployeeId(sId).orElse(null);
	}

	@POST
	@Path("save")
	public void update(TopPagePersonSettingCommandBase command) {
		Optional<TopPagePersonSettingDto> dto = this.topPagePersonSettingFinder.getByCompanyIdAndEmployeeId(command.getEmployeeId());
		if (dto.isPresent()) {
			this.updateTopPagePersonSettingCommandHandler.handle(command);
		} else {
			this.addTopPagePersonSettingCommandHandler.handle(command);
		}
	}
	
	@POST
	@Path("remove")
	public void remove(TopPagePersonSettingCommandBase topPagePersonSettingCommandBase) {
		this.removeTopPagePersonSettingCommandHandler.handle(topPagePersonSettingCommandBase);;
	}
}
