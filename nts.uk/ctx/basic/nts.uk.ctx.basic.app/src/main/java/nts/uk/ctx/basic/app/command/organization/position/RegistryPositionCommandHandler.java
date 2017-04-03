package nts.uk.ctx.basic.app.command.organization.position;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.persistence.EnumType;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.basic.dom.organization.position.AuthorizationCode;
import nts.uk.ctx.basic.dom.organization.position.HiterarchyOrderCode;
import nts.uk.ctx.basic.dom.organization.position.JobCode;
import nts.uk.ctx.basic.dom.organization.position.JobHistory;
import nts.uk.ctx.basic.dom.organization.position.JobName;
import nts.uk.ctx.basic.dom.organization.position.JobTitle;
import nts.uk.ctx.basic.dom.organization.position.JobTitleRef;
import nts.uk.ctx.basic.dom.organization.position.PositionRepository;
import nts.uk.ctx.basic.dom.organization.position.PresenceCheckScopeSet;
import nts.uk.ctx.basic.dom.organization.position.ReferenceSettings;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.primitive.Memo;

public class RegistryPositionCommandHandler extends CommandHandler<RegistryPositionCommand> {
	@Inject
	private PositionRepository positionRepository;

	@Override
	protected void handle(CommandHandlerContext<RegistryPositionCommand> context) {
		RegistryPositionCommand rePositionCommand = context.getCommand();
		String historyId = rePositionCommand.getHistoryId();
		String companyCode = AppContexts.user().companyCode();
		List<JobTitle> lstPositionBefor;
		List<JobTitleRef> lstJobTitleRef;
		// neu history = null thuc hien insert history isert position
		if (historyId.equals(null)) {
			GeneralDate endDate = GeneralDate.localDate(LocalDate.parse("9999/12/31"));

			String historyIdNew = IdentifierUtil.randomUniqueId();

			GeneralDate startDate = GeneralDate.localDate(LocalDate.parse(rePositionCommand.getStartDate()));
			JobHistory historyInfor = new JobHistory(companyCode, historyIdNew, endDate, startDate);

			// add position neu duoc copy tu lich su khac
			if (rePositionCommand.isChkCopy()) {

				// --Neu copy position tu position khac
				if (rePositionCommand.isChkCopy()) {
					// ---lay position cua lich su ngay truoc no
					Optional<JobHistory> historyByEndate = positionRepository.getHistoryByEdate(companyCode, endDate);
					String historyBefore = historyByEndate.get().getHistoryId();
					lstPositionBefor = positionRepository.findAllPosition(companyCode, historyBefore);
					List<JobTitle> lstPositionNow = lstPositionBefor.stream().map(org -> {
						return JobTitle.createFromJavaType(companyCode, 
								historyIdNew, 
								org.getJobCode().v(),
								org.getJobName().v(), 
								org.getPresenceCheckScopeSet().value, org.getJobOutCode().v(),
								org.getMemo().v(), 
								org.getHiterarchyOrderCode().v());
					}).collect(Collectors.toList());
					// isert postition
					positionRepository.add(lstPositionNow);
					// insert quyen
					// - lay list du lieu CMNMT_JOB_TITLE_REF

					lstJobTitleRef = positionRepository.findAllJobTitleRef(companyCode, historyIdNew,
							rePositionCommand.getJobCode());
					// - gan lai thong tin
					List<JobTitleRef> lstTitleRef = lstJobTitleRef.stream().map(org -> {
						return JobTitleRef.createFromJavaType(
								org.getAuthCode().v(), 
								companyCode, 
								historyIdNew,
								org.getJobCode().v(), 
								org.getReferenceSettings().value);
					}).collect(Collectors.toList());
					// - add quyen
					positionRepository.addJobTitleRef(lstTitleRef);
					

				} else {
					// neu them moi tu dau
					InsertPositionAndRef(rePositionCommand, companyCode, historyIdNew);
				}
				// add history
				positionRepository.addHistory(historyInfor);

			}
			// neu history != null thuc hien add or update position
		} else {
			PositionCommand commandInfor = rePositionCommand.getPositionCommand();
			// add moi position
			if(rePositionCommand.isChkInsert()){
				
				//check xem cai jobcode nay da ton tai hay chua
				positionRepository.getJobTitleByCode(companyCode, historyId, rePositionCommand.getJobCode()).ifPresent(x -> {throw new BusinessException("ER005");});
				InsertPositionAndRef(rePositionCommand, companyCode, historyId);
				
			}else{
				//check xem cai jobcode con ton tai ko
				Optional<JobTitle> upJobInfor = positionRepository.getJobTitleByCode(companyCode, historyId, rePositionCommand.getJobCode());
				if(!upJobInfor.isPresent()){
					throw new BusinessException("ER026");
				}
				
				JobTitle jobInfor = upJobInfor.get();
				jobInfor.setJobCode(new JobCode(rePositionCommand.getJobCode()));
				jobInfor.setJobName(new JobName(commandInfor.getJobName()));
				jobInfor.setJobOutCode(new JobCode(commandInfor.getJobOutCode()));
				jobInfor.setHiterarchyOrderCode(new HiterarchyOrderCode(commandInfor.getHiterarchyOrderCode()));
				jobInfor.setMemo(new Memo(commandInfor.getMemo()));
				jobInfor.setPresenceCheckScopeSet(EnumAdaptor.valueOf(commandInfor.getPresenceCheckScopeSet(), PresenceCheckScopeSet.class));
				//update position
				positionRepository.update(jobInfor);
				//update quyen
				//- check xem quyen ton tai ko
				List<JobTitleRef> refInfor = positionRepository.findAllJobTitleRef(companyCode, historyId, rePositionCommand.getJobCode());
				//-- neu chua co thi insert
				if(refInfor.isEmpty()){
					InsertRefInfor(rePositionCommand, companyCode, historyId);
				}else{
					List<AddJobTitleRefCommand> refInforUpdate = rePositionCommand.getRefCommand();
					List<JobTitleRef> newRefInfor = new ArrayList<JobTitleRef>();
					for (AddJobTitleRefCommand jobTitleRef : refInforUpdate) {
						JobTitleRef titleRef = new JobTitleRef(companyCode, 
								historyId,
								new JobCode(rePositionCommand.getJobCode()),
								new AuthorizationCode(jobTitleRef.getAuthorizationCode()),
								EnumAdaptor.valueOf(jobTitleRef.getReferenceSettings(), ReferenceSettings.class));
						newRefInfor.add(titleRef);
					}
					//update quyen (viet them)
					
				}
			}
		}
	}
	
	private void InsertPositionAndRef(RegistryPositionCommand rePositionCommand, String companyCode, String historyId){
		// add position
		PositionCommand addPositionCommand = rePositionCommand.getPositionCommand();
		JobTitle positionInfo = new JobTitle(companyCode
				, historyId
				, new JobCode(rePositionCommand.getJobCode())
				, new JobName(addPositionCommand.getJobName())
				, EnumAdaptor.valueOf(addPositionCommand.getPresenceCheckScopeSet(), PresenceCheckScopeSet.class)
				, new JobCode(addPositionCommand.getJobOutCode())
				, new Memo(addPositionCommand.getMemo())
				, new HiterarchyOrderCode(addPositionCommand.getHiterarchyOrderCode()));
		positionRepository.add(positionInfo);
		// add quyen
		InsertRefInfor(rePositionCommand, companyCode, historyId);
	}
	
	private void InsertRefInfor(RegistryPositionCommand rePositionCommand, String companyCode, String historyId){
		List<AddJobTitleRefCommand> refInfor = rePositionCommand.getRefCommand();
		List<JobTitleRef> newRefInfor = new ArrayList<JobTitleRef>();
		for (AddJobTitleRefCommand jobTitleRef : refInfor) {
			JobTitleRef titleRef = new JobTitleRef(companyCode, 
					historyId,
					new JobCode(rePositionCommand.getJobCode()),
					new AuthorizationCode(jobTitleRef.getAuthorizationCode()),
					EnumAdaptor.valueOf(jobTitleRef.getReferenceSettings(), ReferenceSettings.class));
			newRefInfor.add(titleRef);
		}
		positionRepository.addJobTitleRef(newRefInfor);
	}
	
}
