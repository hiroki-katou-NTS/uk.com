package nts.uk.ctx.hr.develop.ws.sysoperationset.eventmanage;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.ctx.hr.develop.app.sysoperationset.eventoperation.command.JMM018Cmd;
import nts.uk.ctx.hr.develop.app.sysoperationset.eventoperation.commandhandler.UpdateEventMenuOperation;
import nts.uk.ctx.hr.develop.app.sysoperationset.eventoperation.dto.JMM018Dto;
import nts.uk.ctx.hr.develop.app.sysoperationset.eventoperation.find.JMM018Finder;
import nts.uk.ctx.hr.develop.dom.sysoperationset.eventoperation.MenuInfoEx;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.ctx.hr.develop.dom.sysoperationset.eventoperation.MenuOperationRepository;

@Path("hrdeveventmenu/eventmenuoperation")
@Produces(MediaType.APPLICATION_JSON)
public class EventManageWebservice {
	@Inject
	private JMM018Finder finder;
	
	@Inject
	private MenuOperationRepository repoMenuO;
	
	@Inject
	private UpdateEventMenuOperation eventMenu;
	
	@POST
	@Path("/getAll")
	public JMM018Dto getEventMenu(){
		return finder.finderJMM018();
	}
	
	@POST
	@Path("/update-event-menu")
	public void updateEventMenu(JMM018Cmd command){
		eventMenu.handle(command);
	}
	
	//承認機能を使用するイベント種類を取得
	@POST
	@Path("findByApprUse")
	public List<MenuInfoEx> findByApprUse(){
		return repoMenuO.findByApprUse(AppContexts.user().companyId());
	}
}
