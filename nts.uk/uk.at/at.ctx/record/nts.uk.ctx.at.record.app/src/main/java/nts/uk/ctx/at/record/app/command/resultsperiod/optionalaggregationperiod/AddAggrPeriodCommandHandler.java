package nts.uk.ctx.at.record.app.command.resultsperiod.optionalaggregationperiod;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDateTime;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodExcution;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodExcutionRepository;
//import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodInfor;
//import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodInforRepository;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodTarget;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodTargetRepository;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.AnyAggrPeriod;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.AnyAggrPeriodRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AddAggrPeriodCommandHandler
		extends CommandHandlerWithResult<AddAggrPeriodCommand, AddAggrPeriodCommandResult> {

	@Inject
	private AnyAggrPeriodRepository repository;
	@Inject
	private AggrPeriodExcutionRepository excutionrRepository;
	@Inject
	private AggrPeriodTargetRepository targetRepository;
//	@Inject
//	private AggrPeriodInforRepository inforRepository; 

	@Override
	protected AddAggrPeriodCommandResult handle(CommandHandlerContext<AddAggrPeriodCommand> context) {
		String companyId = AppContexts.user().companyId();
		String executionEmpId = AppContexts.user().employeeId();
		AddAggrPeriodCommand command = context.getCommand();
		
		
		
		GeneralDateTime endDateTime = GeneralDateTime.now();
		GeneralDateTime startDateTime = GeneralDateTime.now();

		
		AnyAggrPeriod anyAggrPeriod = AnyAggrPeriod.createFromMemento(companyId, command.getAggrPeriodCommand());
		String optionalAggrPeriodID = IdentifierUtil.randomUniqueId();
		if (command.getMode() == 0) {
			boolean existsBranch = repository.checkExisted(companyId,
					command.getAggrPeriodCommand().getAggrFrameCode());
			if (existsBranch) {
				throw new BusinessException("Msg_3");
			}
			
			List<AnyAggrPeriod> aggrList = repository.findAllByCompanyId(companyId);
			if (aggrList.size() <= 99) {

				// Add Optional Aggr Period
				repository.addAnyAggrPeriod(anyAggrPeriod);

				List<AggrPeriodTarget> periodTarget = command.getTargetCommand().toDomain(optionalAggrPeriodID);

				// Add Aggr Period Target
				targetRepository.addTarget(periodTarget);

				AggrPeriodExcution periodExcution = command.getExecutionCommand().toDomain(companyId, executionEmpId,
						optionalAggrPeriodID, startDateTime, endDateTime);

				// Add Aggr Period Excution
				excutionrRepository.addExcution(periodExcution);
				
				// Thêm lỗi khi chưa có xử lý tính toán
//				if(optionalAggrPeriod.getAggrFrameCode().v().equals("001")){
//				AggrPeriodInfor periodInfors = command.getInforCommand().toDomain(executionEmpId,optionalAggrPeriodID);
//				inforRepository.addPeriodInfor(periodInfors);
//				}

			} else {
				throw new BusinessException("Msg_1165");
			}
		} else {
			
			// Update Optional Aggr Period
			repository.updateAnyAggrPeriod(anyAggrPeriod);
			
			List<AggrPeriodTarget> periodTarget = command.getTargetCommand().toDomain(optionalAggrPeriodID);

			// Add Aggr Period Target
			targetRepository.addTarget(periodTarget);

			AggrPeriodExcution periodExcution = command.getExecutionCommand().toDomain(companyId, executionEmpId,
					optionalAggrPeriodID, startDateTime, endDateTime);

			// Add Aggr Period Excution
			excutionrRepository.addExcution(periodExcution);

			// Thêm lỗi khi chưa có xử lý tính toán
//			if(optionalAggrPeriod.getAggrFrameCode().v().equals("001")){
//			AggrPeriodInfor periodInfors = command.getInforCommand().toDomain(executionEmpId,optionalAggrPeriodID);
//			inforRepository.addPeriodInfor(periodInfors);
//			}
			

		}
		AddAggrPeriodCommandResult aggrPeriodCommandResult = new AddAggrPeriodCommandResult();
		aggrPeriodCommandResult.setAnyPeriodAggrLogId(optionalAggrPeriodID);
		aggrPeriodCommandResult.setStartDateTime(startDateTime);
		aggrPeriodCommandResult.setEndDateTime(endDateTime);
		return aggrPeriodCommandResult;
		

	}
}
