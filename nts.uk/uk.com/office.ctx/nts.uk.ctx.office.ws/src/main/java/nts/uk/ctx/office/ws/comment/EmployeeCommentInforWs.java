package nts.uk.ctx.office.ws.comment;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.office.app.command.comment.EmployeeCommentInformationCommand;
import nts.uk.ctx.office.app.command.comment.EmployeeCommentInformationDelCommand;
import nts.uk.ctx.office.app.command.comment.EmployeeCommentInformationDeleteCommandHandler;
import nts.uk.ctx.office.app.command.comment.EmployeeCommentInformationInsertCommandHandler;

@Path("ctx/office/comment")
@Produces("application/json")
public class EmployeeCommentInforWs {
	
	@Inject
	private EmployeeCommentInformationInsertCommandHandler empCmtInforInsertCommandHandler;
	
	@Inject
	private EmployeeCommentInformationDeleteCommandHandler empCmtInforDeleteCommandHandler;
	
	@POST
	@Path("register")
	public void registerComment(EmployeeCommentInformationCommand command) {
		this.empCmtInforInsertCommandHandler.handle(command);
	}
	
	@POST
	@Path("delete")
	public void deleteComment(EmployeeCommentInformationDelCommand command) {
		this.empCmtInforDeleteCommandHandler.handle(command);
	}
}
