package nts.uk.ctx.basic.app.command.organization.position;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.basic.dom.organization.position.HiterarchyOrderCode;
import nts.uk.ctx.basic.dom.organization.position.JobCode;
import nts.uk.ctx.basic.dom.organization.position.JobHistory;
import nts.uk.ctx.basic.dom.organization.position.JobName;
import nts.uk.ctx.basic.dom.organization.position.JobTitle;
import nts.uk.ctx.basic.dom.organization.position.PositionRepository;
import nts.uk.ctx.basic.dom.organization.position.PresenceCheckScopeSet;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.primitive.Memo;

@Stateless
public class AddHistoryCommandHandler extends CommandHandler<AddHistoryCommand> {

	@Inject
	private PositionRepository positionRepository;

	protected void handle(CommandHandlerContext<AddHistoryCommand> context) {

		String checkAddJhist = context.getCommand().getCheckAddJhist();
		String checkAddJtitle = context.getCommand().getCheckAddJtitle();
		AddHistoryCommand command = context.getCommand();
		String companyCode = AppContexts.user().companyCode();
		String historyId = IdentifierUtil.randomUniqueId();
		int a = context.getCommand().getJobTitle().getPresenceCheckScopeSet();
		// case insert
		if (positionRepository.ExistedHistoryBydate(companyCode, command.getJobHist().getStartDate())) {
			throw new BusinessException(new RawErrorMessage("履歴の期間が正しくありません。"));
		} else {
		JobTitle jobTitle= new JobTitle(companyCode,
				historyId,
				new JobCode(context.getCommand().getJobTitle().getJobCode()),				
				new JobName(context.getCommand().getJobTitle().getJobName()),
				EnumAdaptor.valueOf(context.getCommand().getJobTitle().getPresenceCheckScopeSet(), PresenceCheckScopeSet.class),
				new JobCode("1"),
				new Memo(context.getCommand().getJobTitle().getMemo()),
				new HiterarchyOrderCode(context.getCommand().getJobTitle().getHierarchyOrderCode())
				);
		JobTitle jobTitleUpdate = new JobTitle(
				companyCode,
				context.getCommand().getJobHist().getHistoryId(),
				new JobCode(context.getCommand().getJobTitle().getJobCode()),				
				new JobName(context.getCommand().getJobTitle().getJobName()),
				EnumAdaptor.valueOf(context.getCommand().getJobTitle().getPresenceCheckScopeSet(), PresenceCheckScopeSet.class),
				new JobCode("1"),
				new Memo(context.getCommand().getJobTitle().getMemo()),
				new HiterarchyOrderCode(context.getCommand().getJobTitle().getHierarchyOrderCode()));
		
		if(checkAddJhist.compareTo("2")==0){
			Optional<JobHistory> checkJhist = positionRepository.findSingleHistory(companyCode, historyId);
			if(!checkJhist.isPresent()){
				if(checkAddJtitle.compareTo("2")==0){
					
					JobHistory jobHistory = new JobHistory(
									companyCode,
									historyId,
									GeneralDate.localDate(LocalDate.parse("9999-12-31")),
									GeneralDate.localDate(LocalDate.parse(context.getCommand().getJobHist().getStartDate().toString()))
									
									);
					
					String tmp = context.getCommand().getJobHist().getStartDate().toString();
					GeneralDate eDate = GeneralDate.localDate(LocalDate.parse(tmp));
					GeneralDate endDate = eDate.addDays(-1);
					Optional<JobHistory> hisEndate = positionRepository.getHistoryByEdate(companyCode, GeneralDate.localDate(LocalDate.parse("9999-12-31")));
					if(hisEndate.isPresent()){
						JobHistory jobHis = hisEndate.get();
						jobHis.setEndDate(endDate);
						positionRepository.updateHistory(jobHis);
						positionRepository.addHistory(jobHistory);
						positionRepository.add(jobTitle);
					}
				}else
				if(checkAddJtitle.compareTo("1")==0){
				
					JobHistory jobHistory = new JobHistory(
									companyCode,
									historyId,
									GeneralDate.localDate(LocalDate.parse(context.getCommand().getJobHist().getStartDate().toString())),
									GeneralDate.localDate(LocalDate.parse("9999/12/31"))
									);
					
					String tmp = context.getCommand().getJobHist().getStartDate().toString();
					GeneralDate eDate = GeneralDate.localDate(LocalDate.parse(tmp));
					GeneralDate endDate = eDate.addDays(-1);
					Optional<JobHistory> hisEndate = positionRepository.getHistoryByEdate(companyCode, GeneralDate.localDate(LocalDate.parse("9999-12-31")));
					List<JobTitle> listJobTitleCopy = positionRepository.findAllPosition(companyCode, context.getCommand().getJobHist().getHistoryId());
					if(hisEndate.isPresent()&& listJobTitleCopy.size()>=1){
						JobHistory jobHis = hisEndate.get();
						jobHis.setEndDate(endDate);
						//copy
						for(int i=0;i<listJobTitleCopy.size();i++){
							JobTitle jTitleCopy = listJobTitleCopy.get(i);
							jTitleCopy.setHistoryId(historyId);
							positionRepository.add(jTitleCopy);
						}
						positionRepository.updateHistory(jobHis);
						positionRepository.addHistory(jobHistory);
					}
				}
			}else{
				throw new BusinessException("Is Existed");
			}
		}else{
			throw new BusinessException("Is Existed");
		
		} if(checkAddJhist.compareTo("0")==0){
				if(checkAddJtitle.compareTo("3")==0){
					
					Optional<JobTitle> jTitle = positionRepository.getJobTitleByCode(companyCode, 
													context.getCommand().getJobHist().getHistoryId(),
													context.getCommand().getJobTitle().getJobCode());
					if(!jTitle.isPresent()){
						positionRepository.add(jobTitleUpdate);
					}else{
						throw new BusinessException("Is Existed");
					}
				}
				if(checkAddJtitle.compareTo("4")==0){
					Optional<JobTitle> jTitle = positionRepository.getJobTitleByCode(companyCode, 
													context.getCommand().getJobHist().getHistoryId(),
													context.getCommand().getJobTitle().getJobCode());
					if(jTitle.isPresent()){
						positionRepository.update(jobTitleUpdate);
								
					}else{
						throw new BusinessException("Is null");
					}
				}
			}else
				if(checkAddJhist.compareTo("1")==0){
					if(checkAddJtitle.compareTo("2")==0){
						
						JobHistory jobHistory = new JobHistory(
										companyCode,
										historyId,
										GeneralDate.localDate(LocalDate.parse(context.getCommand().getJobHist().getStartDate().toString())),
										GeneralDate.localDate(LocalDate.parse("9999-12-31"))
										);
						
						Optional<JobTitle> jTitle = positionRepository.getJobTitleByCode(companyCode, 
														context.getCommand().getJobHist().getHistoryId(),
														context.getCommand().getJobTitle().getJobCode());
						
						if(!jTitle.isPresent()){
							positionRepository.addHistory(jobHistory);
							positionRepository.add(jobTitle);
						}else{
							throw new BusinessException("Is Existed");
						}
					}
				}
//			JobHistory jobHistory = new JobHistory(companyCode,historyId, command.getJobHist().getEndDate(), command.getJobHist().getStartDate());
//			jobHistory.validate();
//			positionRepository.addHistory(jobHistory);
//			GeneralDate pos1 = context.getCommand().getJobHist().getStartDate();
//			GeneralDate endDate = pos1.addDays(-1);
//			Optional<JobHistory> historyEndDate = positionRepository.getHistoryBySdate(companyCode, pos1);
//			if (historyEndDate.isPresent()) {
//				JobHistory jobHis = historyEndDate.get();
//				jobHis.setEndDate(endDate);
//				positionRepository.updateHistory(jobHis);
//			// positionRepository.addHistory(jobHistory);

			}
		}

	

}