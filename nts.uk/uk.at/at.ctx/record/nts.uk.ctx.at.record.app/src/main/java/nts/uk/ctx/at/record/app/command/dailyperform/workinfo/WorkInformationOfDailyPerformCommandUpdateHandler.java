package nts.uk.ctx.at.record.app.command.dailyperform.workinfo;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.daily.DailyRecordAdUpService;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.shared.app.util.attendanceitem.CommandFacade;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class WorkInformationOfDailyPerformCommandUpdateHandler extends CommandFacade<WorkInformationOfDailyPerformCommand> {

//	@Inject
//	private WorkInformationRepository repo;
	
	@Inject
	private WorkTypeRepository workTypeRepo;
	
	@Inject
	private DailyRecordAdUpService repo;

	@Override
	protected void handle(CommandHandlerContext<WorkInformationOfDailyPerformCommand> context) {
		WorkInfoOfDailyPerformance domain = context.getCommand().toDomain();
		/** check worktype*/
		checkWorkType(domain);
		
		/** update domain */
		repo.adUpWorkInfo(domain);
		
		/** fire changed event */
		if(context.getCommand().isTriggerEvent()){
			domain.workInfoChanged();
		}
	}
	
	private void checkWorkType(WorkInfoOfDailyPerformance domain) {
		String comId = AppContexts.user().companyId();
		if(domain.getWorkInformation().getRecordInfo().getWorkTypeCode() != null && domain.getWorkInformation().getScheduleInfo().getWorkTypeCode() != null && 
				domain.getWorkInformation().getRecordInfo().getWorkTypeCode().equals(domain.getWorkInformation().getScheduleInfo().getWorkTypeCode())){
			checkTogether(domain, comId);
			return;
		}
		checkSeperate(domain, comId);
	}
	
	private void checkTogether(WorkInfoOfDailyPerformance domain, String comId) {
		workTypeRepo.findByPK(comId, domain.getWorkInformation().getRecordInfo().getWorkTypeCode().v()).ifPresent(wt -> {
			if(wt.isNoneWorkTimeType()){
				domain.getWorkInformation().getRecordInfo().removeWorkTimeInHolydayWorkType();
				domain.getWorkInformation().getScheduleInfo().removeWorkTimeInHolydayWorkType();
			}
		});
	}

	private void checkSeperate(WorkInfoOfDailyPerformance domain, String comId) {
		workTypeRepo.findByPK(comId, domain.getWorkInformation().getRecordInfo().getWorkTypeCode().v()).ifPresent(wt -> {
			if(wt.isNoneWorkTimeType()){
				domain.getWorkInformation().getRecordInfo().removeWorkTimeInHolydayWorkType();
			}
		});
		workTypeRepo.findByPK(comId, domain.getWorkInformation().getScheduleInfo().getWorkTypeCode().v()).ifPresent(wt -> {
			if(wt.isNoneWorkTimeType()){
				domain.getWorkInformation().getScheduleInfo().removeWorkTimeInHolydayWorkType();
			}
		});
	}

}
