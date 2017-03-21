package nts.uk.ctx.basic.app.command.organization.position;

import java.time.LocalDate;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.basic.dom.organization.position.JobHistory;
import nts.uk.ctx.basic.dom.organization.position.PositionRepository;
import nts.uk.shr.com.context.AppContexts;




@Stateless
public class AddHistoryCommandHandler extends CommandHandler<AddHistoryCommand>{
	
	@Inject
	private PositionRepository positionRepository;
	
	
	protected void handle(CommandHandlerContext<AddHistoryCommand> context) {
		


		String companyCode = AppContexts.user().companyCode();
		String historyId = IdentifierUtil.randomUniqueId();
		
		String check = context.getCommand().getCompanyCode();
		
		AddHistoryCommand command = new AddHistoryCommand();
		command.setCompanyCode(companyCode);
		command.setHistoryId(historyId);
		command.setEndDate("9999-12-31");
//		command.setEndDate(context.getCommand().getEndDate());
		command.setStartDate(context.getCommand().getStartDate().toString());
		JobHistory jobHistory = command.toDomain();
		jobHistory.validate();
		
//		JobHistory jobHistory = new JobHistory(companyCode,hitoryId,context.getCommand().getEndDate(),context.getCommand().getStartDate());
//		positionRepository.addHistory(jobHistory);
		
		String pos1 = context.getCommand().getStartDate();
		GeneralDate endDate1 = GeneralDate.localDate(LocalDate.parse(pos1));
		GeneralDate endDate = endDate1.addDays(-1);
		if(check.compareTo("0")==0){
			Optional<JobHistory> historyEndDate = positionRepository.getHistoryByEdate(companyCode, "9999-12-31");
			if(historyEndDate.isPresent()){
				JobHistory jobHis = historyEndDate.get();
				jobHis.setEndDate(endDate);
				positionRepository.updateHistory(jobHis);
				positionRepository.addHistory(jobHistory);
				
			}
		}else{
			positionRepository.addHistory(jobHistory);
		}
		
		
	}
	
}