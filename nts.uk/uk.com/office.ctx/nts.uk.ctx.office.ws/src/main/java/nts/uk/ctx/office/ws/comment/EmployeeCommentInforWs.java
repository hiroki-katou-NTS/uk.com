package nts.uk.ctx.office.ws.comment;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.office.app.command.comment.EmployeeCommentInformationCommand;
import nts.uk.ctx.office.app.command.comment.EmployeeCommentInformationInsertCommandHandler;

@Path("ctx/office/comment")
@Produces("application/json")
public class EmployeeCommentInforWs {
	
	@Inject
	private EmployeeCommentInformationInsertCommandHandler empCmtInforCommand;
	
	@POST
	@Path("register")
	public void registerComment(EmployeeCommentInformationCommand command) {
		this.empCmtInforCommand.handle(command);
	}
}
