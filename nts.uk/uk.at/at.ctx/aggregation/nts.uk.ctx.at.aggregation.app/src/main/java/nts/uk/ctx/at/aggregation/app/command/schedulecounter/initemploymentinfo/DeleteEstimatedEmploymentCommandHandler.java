package nts.uk.ctx.at.aggregation.app.command.schedulecounter.initemploymentinfo;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountForEmploymentRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;


// 雇用の目安金額を削除する
@Stateless
@Transactional
public class DeleteEstimatedEmploymentCommandHandler extends CommandHandler<DeleteEstimatedEmploymentCommand>{
	
	@Inject
	private CriterionAmountForEmploymentRepository criterionAmountForEmploymentRepository;
	
	@Override
	protected void handle(CommandHandlerContext<DeleteEstimatedEmploymentCommand> context) {
		
		DeleteEstimatedEmploymentCommand command = context.getCommand();
		
		criterionAmountForEmploymentRepository.delete(command.getCid(), new EmploymentCode(command.getEmploymentCode()));
	}

}
