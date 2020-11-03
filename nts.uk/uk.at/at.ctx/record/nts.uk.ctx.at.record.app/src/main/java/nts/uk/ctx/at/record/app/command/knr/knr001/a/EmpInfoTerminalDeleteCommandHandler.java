package nts.uk.ctx.at.record.app.command.knr.knr001.a;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.record.dom.adapter.imploymentinfoterminal.infoterminal.EmpInfoTerminalComStatusAdapter;
import nts.uk.ctx.at.record.dom.adapter.imploymentinfoterminal.infoterminal.EmpInfoTerminalComStatusImport;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.ResultOfDeletion;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.EmpInfoTerminalRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service.DeleteEmpInfoTerminalService;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author dungbn
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class EmpInfoTerminalDeleteCommandHandler extends CommandHandler<EmpInfoTerminalDeleteCommand> {

	@Inject
	private EmpInfoTerminalRepository empInfoTerminalRepository;
	
	@Inject
	private EmpInfoTerminalComStatusAdapter empInfoTerminalComStatusAdapter;
	@Override
	protected void handle(CommandHandlerContext<EmpInfoTerminalDeleteCommand> context) {
		
		EmpInfoTerminalDeleteCommand command = context.getCommand();
		String contractCode = AppContexts.user().contractCode();
		RequireImpl require = new RequireImpl(empInfoTerminalRepository, empInfoTerminalComStatusAdapter);
		ResultOfDeletion result = DeleteEmpInfoTerminalService.create(require, contractCode, command.getEmpInfoTerCode());
		
		if (result.isError()) {
			throw new BusinessException("Msg_1897");
		}
		
		transaction.execute(() -> {
			result.getDeleteEmpInfoTerminal().get().run();
			if (result.getDeleteEmpInfoTerminalComStatus().isPresent()) {
				result.getDeleteEmpInfoTerminalComStatus().get().run();
			}
		});
		
	}
	
	@AllArgsConstructor
	private static class RequireImpl implements DeleteEmpInfoTerminalService.Require {
		
		private EmpInfoTerminalRepository empInfoTerminalRepository;
	
		private EmpInfoTerminalComStatusAdapter empInfoTerminalComStatusAdapter;
		
		@Override
		public Optional<EmpInfoTerminal> getEmpInfoTerminal(EmpInfoTerminalCode empInfoTerCode,
				ContractCode contractCode) {
			return empInfoTerminalRepository.getEmpInfoTerminal(empInfoTerCode, contractCode);
		}

		@Override
		public void delete(EmpInfoTerminal empInfoTerminal) {
			empInfoTerminalRepository.delete(empInfoTerminal);
			
		}

		@Override
		public Optional<EmpInfoTerminalComStatusImport> get(ContractCode contractCode,
				EmpInfoTerminalCode empInfoTerCode) {
			return empInfoTerminalComStatusAdapter.get(contractCode, empInfoTerCode);
		}

		@Override
		public void delete(EmpInfoTerminalComStatusImport empInfoTerminalComStatusImport) {
			empInfoTerminalComStatusAdapter.delete(empInfoTerminalComStatusImport);
			
		}
		
	}

}
