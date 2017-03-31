package nts.uk.ctx.basic.app.command.organization.position;

import java.time.LocalDate;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.basic.dom.organization.position.JobHistory;
import nts.uk.ctx.basic.dom.organization.position.PositionRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UpdateHistoryCommandHandler extends CommandHandler<UpdateHistoryCommand> {

	@Inject
	private PositionRepository positionRepository;

	@Override
	protected void handle(CommandHandlerContext<UpdateHistoryCommand> context) {

		String companyCode = AppContexts.user().companyCode();
		String historyId = context.getCommand().getJobHist().getHistoryId();
		String checkUpdate = context.getCommand().getCheckUpdate();
		String checkDelete = context.getCommand().getCheckDelete();
		String sDateEdit = context.getCommand().getJobHist().getStartDate().toString();
		String endDateEdit = context.getCommand().getJobHist().getEndDate().toString();
		GeneralDate eDateNew = GeneralDate.localDate(LocalDate.parse(endDateEdit));
		Optional<JobHistory> checkJhist = positionRepository.findSingleHistory(companyCode, historyId);
		if(checkJhist.isPresent()){
			if(checkDelete.compareTo("0")==0){//update
				GeneralDate sDateNew = context.getCommand().getJobHist().getStartDate();
				GeneralDate eDateUpdateNew = sDateNew.addDays(-1);
				JobHistory jobHist = new JobHistory(companyCode,historyId,sDateNew,eDateNew);
	
					if(checkUpdate.compareTo("1")==0 ){
						Optional<JobHistory> hisEndate = positionRepository.getHistoryByEdate(companyCode,GeneralDate.localDate(LocalDate.parse(sDateEdit)));
						if(hisEndate.isPresent()){
							JobHistory jobHistPre = hisEndate.get();
							jobHistPre.setEndDate(eDateUpdateNew);
							positionRepository.updateHistory(jobHistPre);
							positionRepository.updateHistory(jobHist);
						}
					}
					if(checkUpdate.compareTo("2")==0 && checkDelete.compareTo("0")==0){
						positionRepository.updateHistory(jobHist);
					}
	
			}
			if(checkUpdate.compareTo("0")==0 && checkDelete.compareTo("1")==0){
				Optional<JobHistory> hisEndate = positionRepository.getHistoryByEdate(companyCode,GeneralDate.localDate(LocalDate.parse(sDateEdit)));
				if(hisEndate.isPresent()){
					JobHistory jobHistPre = hisEndate.get();
					jobHistPre.setEndDate(eDateNew);
					positionRepository.updateHistory(jobHistPre);
					positionRepository.deleteHist(companyCode, historyId);
					positionRepository.deleteJobTitleByHisId(companyCode, historyId);
				}
			}
		
		}else{
			throw new BusinessException("du lieu khong con ton tai tron DB");
		}

	}

}
