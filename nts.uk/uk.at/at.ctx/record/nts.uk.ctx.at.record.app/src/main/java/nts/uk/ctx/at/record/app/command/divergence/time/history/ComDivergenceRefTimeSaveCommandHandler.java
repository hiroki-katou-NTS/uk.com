package nts.uk.ctx.at.record.app.command.divergence.time.history;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceType;
import nts.uk.ctx.at.record.dom.divergence.time.history.CompanyDivergenceReferenceTime;
import nts.uk.ctx.at.record.dom.divergence.time.history.CompanyDivergenceReferenceTimeRepository;

/**
 * The Class ComDivergenceRefTimeSaveCommandHandler.
 */
@Stateless
public class ComDivergenceRefTimeSaveCommandHandler extends CommandHandler<ComDivergenceRefTimeSaveCommand>{
	
	/** The Constant USE. */
	private final static int USE = 1;
	
	/** The repository. */
	@Inject
	private CompanyDivergenceReferenceTimeRepository repository;

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<ComDivergenceRefTimeSaveCommand> context) {
		//get command
		ComDivergenceRefTimeSaveCommand command = context.getCommand();
		
		//validate
		command.getListDataSetting().stream().forEach(item -> {
			if(item.getNotUseAtr().value == USE){
				if(item.getAlarmTime() < item.getErrorTime()){
					throw new BusinessException("Msg_82");
				}
			}
		});
				
		//convert to domain
		List<CompanyDivergenceReferenceTime> listDomain = command.getListDataSetting().stream().map(e -> {
			if(e.getNotUseAtr().value == USE){
				return new CompanyDivergenceReferenceTime(e);
			}else {
				Optional<CompanyDivergenceReferenceTime> oldDomain = this.repository.findByKey(e.getHistoryId(), DivergenceType.valueOf(e.getDivergenceTimeNo()));
				return oldDomain.get();
			}
		}).collect(Collectors.toList());
		
		//update
		this.repository.update(listDomain);
	}

}
