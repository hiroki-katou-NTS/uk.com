/**
 * 
 */
package nts.uk.ctx.hr.notice.app.command.report.regis.person;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.hr.notice.dom.report.registration.person.RegistrationPersonReportRepository;

/**
 * @author laitv
 *
 */
@Stateless
public class SaveDraftRegisPersonReportHandler extends CommandHandler<SaveReportInputContainer>{
	
	@Inject
	private RegistrationPersonReportRepository repo;

	@Override
	protected void handle(CommandHandlerContext<SaveReportInputContainer> context) {
		SaveReportInputContainer command = context.getCommand();
		System.out.println("command");
	}
}
