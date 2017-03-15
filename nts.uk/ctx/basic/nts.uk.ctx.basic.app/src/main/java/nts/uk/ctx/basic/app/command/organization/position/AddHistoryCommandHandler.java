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
		
//		String companyCode = AppContexts.user().companyCode();
//		String hitoryId = IdentifierUtil.randomUniqueId();
//		JobHistory jobHistory = new JobHistory(companyCode,hitoryId,context.getCommand().getEndDate(),context.getCommand().getStartDate());
//		positionRepository.addHistory(jobHistory);
		
		String check = context.getCommand().getCompanyCode();
		String companyCode = AppContexts.user().companyCode();
		String historyId = IdentifierUtil.randomUniqueId();
		AddHistoryCommand command = new AddHistoryCommand();
		command.setCompanyCode(companyCode);
		command.setHistoryId(historyId);
		command.setEndDate(context.getCommand().getEndDate());
		command.setStartDate(context.getCommand().getStartDate());
		JobHistory jobHistory = command.toDomain();
		jobHistory.validate();
		
		String pos = context.getCommand().getStartDate().toString();
		GeneralDate eDate = GeneralDate.localDate(LocalDate.parse(pos));
		GeneralDate endDate = eDate.addDays(-1);
		if(check.compareTo("1")==0){
			Optional<JobHistory> hisEndate = positionRepository.getHistoryByEdate(companyCode, pos);
			if(hisEndate.isPresent()){
				JobHistory jobHis = hisEndate.get();
				jobHis.setEndDate(endDate);
				positionRepository.updateHistory(jobHis);
				positionRepository.addHistory(jobHistory);
				
			}
		}else{
			positionRepository.addHistory(jobHistory);
		}
		
		
	}
	
}