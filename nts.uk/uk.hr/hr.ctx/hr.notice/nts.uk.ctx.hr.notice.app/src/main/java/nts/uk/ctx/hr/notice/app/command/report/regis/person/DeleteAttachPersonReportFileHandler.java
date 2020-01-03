/**
 * 
 */
package nts.uk.ctx.hr.notice.app.command.report.regis.person;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.hr.notice.dom.report.registration.person.AttachPersonReportFileRepository;

/**
 * @author laitv
 *
 */
@Stateless
public class DeleteAttachPersonReportFileHandler extends CommandHandler<DeleteDocumentReportCommand>{

	@Inject
	private AttachPersonReportFileRepository repo;
	
	@Override
	protected void handle(CommandHandlerContext<DeleteDocumentReportCommand> context) {
		DeleteDocumentReportCommand command = context.getCommand();
		repo.delete(command.fileId, command.cid);
	}

}
