/**
 * 4:17:52 PM Oct 19, 2017
 */
package nts.uk.screen.at.app.dailyperformance.correction;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessTypeFormatDailyRepository;

/**
 * @author hungnm
 *
 */
@Stateless
public class DPUpdateColWidthCommandHandler extends CommandHandler<UpdateColWidthCommand>{

	@Inject
	private BusinessTypeFormatDailyRepository repo;
	
	@Override
	protected void handle(CommandHandlerContext<UpdateColWidthCommand> context) {
		this.repo.updateColumnsWidth(context.getCommand().getLstHeader());
	}

}
