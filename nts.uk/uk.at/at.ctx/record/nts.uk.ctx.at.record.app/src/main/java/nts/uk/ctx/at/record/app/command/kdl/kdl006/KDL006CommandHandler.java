package nts.uk.ctx.at.record.app.command.kdl.kdl006;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.task.tran.AtomTask;
import nts.arc.task.tran.TransactionService;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.workrecord.workrecord.EmploymentConfirmed;
import nts.uk.ctx.at.record.dom.workrecord.workrecord.EmploymentConfirmedRepository;
import nts.uk.ctx.at.record.dom.workrecord.workrecord.RegisterOfCancelWorkConfirmation;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author thanhpv
 *
 */
@Stateless
public class KDL006CommandHandler {

	@Inject
	private EmploymentConfirmedRepository employmentConfirmedRepo; 
	
	@Inject
	private TransactionService transaction;
	
	public void Register(List<WorkPlaceConfirmCommand> context) {
		String companyId = AppContexts.user().companyId();
		String employeeId = AppContexts.user().employeeId();
		RegisterOfCancelWorkConfirmation.Require require = new RegisterOfCancelWorkConfirmationRequireImpl(employmentConfirmedRepo);
		List<Optional<AtomTask>> atomTasks = new ArrayList<Optional<AtomTask>>();  
		for (WorkPlaceConfirmCommand workPlace : context) {
			atomTasks.add(RegisterOfCancelWorkConfirmation.get(
					require, 
					companyId, 
					workPlace.workPlaceId,
					workPlace.closureId, 
					new YearMonth(workPlace.currentMonth), 
					Optional.of(employeeId), 
					Optional.of(GeneralDateTime.now()), 
					workPlace.whetherToCancel));
		}
		
		transaction.execute(() -> {
			for (Optional<AtomTask> atomTask : atomTasks) {
				if(atomTask.isPresent()) {
					atomTask.get().run();
				}
			}
		});
	}

	@AllArgsConstructor
	private class RegisterOfCancelWorkConfirmationRequireImpl implements RegisterOfCancelWorkConfirmation.Require{

		private EmploymentConfirmedRepository employmentConfirmedRepo;
		
		@Override
		public void insert(EmploymentConfirmed domain) {
			this.insert(domain);
		}

		@Override
		public void delete(EmploymentConfirmed domain) {
			this.employmentConfirmedRepo.delete(domain);
		}

		@Override
		public Optional<EmploymentConfirmed> get(String companyId, String workplaceId, ClosureId closureId, YearMonth processYM) {
			return this.get(companyId, workplaceId, closureId, processYM);
		}

	}

}
