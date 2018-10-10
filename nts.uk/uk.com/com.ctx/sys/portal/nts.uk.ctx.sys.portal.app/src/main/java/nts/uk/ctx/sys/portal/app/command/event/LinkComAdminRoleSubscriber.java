package nts.uk.ctx.sys.portal.app.command.event;

import java.util.List;
import javax.inject.Inject;
import nts.arc.layer.dom.event.DomainEventSubscriber;
import nts.uk.ctx.sys.portal.dom.webmenu.WebMenuCode;
import nts.uk.ctx.sys.portal.dom.webmenu.webmenulinking.RoleByRoleTies;
import nts.uk.ctx.sys.portal.dom.webmenu.webmenulinking.RoleByRoleTiesEvent;
import nts.uk.ctx.sys.portal.dom.webmenu.webmenulinking.RoleByRoleTiesRepository;
import nts.uk.shr.com.context.AppContexts;

public class LinkComAdminRoleSubscriber implements DomainEventSubscriber<RoleByRoleTiesEvent> {
	@Inject
	private RoleByRoleTiesRepository roleTiesRepo;

	@Override
	public Class<RoleByRoleTiesEvent> subscribedToEventType() {
		return RoleByRoleTiesEvent.class;
	}

	@Override
	public void handle(RoleByRoleTiesEvent domainEvent) {
		this.process(domainEvent.getRoleId());

	}
	
	private void process(String roleId) {
		//会社ID　　　　＝「000000000000-0000」（固定）
		//ロールID　　　＝Event「会社管理者ロール新規登録」．ロールID
		//メニューコード＝「0002」（固定）
		RoleByRoleTies roleTies = new RoleByRoleTies(roleId, new WebMenuCode("0002"),
				AppContexts.user().zeroCompanyIdInContract());
		this.roleTiesRepo.insertRoleByRoleTies(roleTies);
	}

	
}
