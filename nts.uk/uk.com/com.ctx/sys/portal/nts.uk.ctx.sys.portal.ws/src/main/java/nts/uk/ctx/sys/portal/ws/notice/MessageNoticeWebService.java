package nts.uk.ctx.sys.portal.ws.notice;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import lombok.Data;
import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.sys.portal.app.command.notice.DeleteMessageNoticeCommand;
import nts.uk.ctx.sys.portal.app.command.notice.DeleteMessageNoticeCommandHandler;
import nts.uk.ctx.sys.portal.app.command.notice.RegisterMessageNoticeCommand;
import nts.uk.ctx.sys.portal.app.command.notice.RegisterMessageNoticeCommandHandler;
import nts.uk.ctx.sys.portal.app.query.notice.EmployeeNotificationDto;
import nts.uk.ctx.sys.portal.app.query.notice.MessageNoticeDto;
import nts.uk.ctx.sys.portal.app.query.notice.MessageNoticeScreenQuery;
import nts.uk.ctx.sys.portal.app.query.notice.NotificationCreated;
import nts.uk.ctx.sys.portal.dom.notice.adapter.EmployeeInfoImport;
import nts.uk.ctx.sys.portal.dom.notice.adapter.WorkplaceInfoImport;
import nts.uk.shr.com.context.AppContexts;

@Path("sys/portal/notice")
@Stateless
public class MessageNoticeWebService extends WebService {
	
	@Inject
	private MessageNoticeScreenQuery screenQuery;
	
	@Inject
	private RegisterMessageNoticeCommandHandler registerHandler;
	
	@Inject
	private DeleteMessageNoticeCommandHandler deleteHandler;
	
	@POST
	@Path("/getEmployeeNotification")
	public EmployeeNotificationDto getEmployeeNotification() {
		GeneralDate sysDate = GeneralDate.today();
		return screenQuery.getEmployeeNotification(new DatePeriod(sysDate, sysDate));
	}
	
	@POST
	@Path("/notificationCreatedByEmp")
	public NotificationCreated notificationCreatedByEmp(NotificationParams params) {
		String sid = AppContexts.user().employeeId();
		return screenQuery.notificationCreatedByEmp(sid, params.refeRange, params.msg);
	}
	
	@POST
	@Path("/getNameOfDestinationWkp")
	public List<WorkplaceInfoImport> getNameOfDestinationWkp(List<String> wkIds) {
		return screenQuery.getNameOfDestinationWkp(wkIds);
	}
	
	@POST
	@Path("/acquireNameOfDestinationEmployee")
	public List<EmployeeInfoImport> acquireNameOfDestinationEmployee(List<String> listSID) {
		return this.screenQuery.acquireNameOfDestinationEmployee(listSID);
	}
	
	@POST
	@Path("/registerMessageNotice")
	public void registerMessageNotice(RegisterMessageNoticeCommand command) {
		this.registerHandler.handle(command);
	}
	
	@POST
	@Path("/deleteMessageNotice")
	public void deleteMessageNotice(DeleteMessageNoticeCommand command) {
		this.deleteHandler.handle(command);
	}
}

@Data
class NotificationParams {
	Integer refeRange;
	MessageNoticeDto msg;
}
