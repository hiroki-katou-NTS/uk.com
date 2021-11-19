package nts.uk.ctx.sys.gateway.app.command.login.password.userpassword;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.task.tran.TransactionService;
import nts.uk.ctx.sys.gateway.dom.login.password.userpassword.ChangeLoginPasswordOfUser;
import nts.uk.ctx.sys.shared.dom.user.User;
import nts.uk.ctx.sys.shared.dom.user.UserRepository;
import nts.uk.shr.pereg.app.command.MyCustomizeException;
import nts.uk.shr.pereg.app.command.PeregUpdateListCommandHandler;

/**
 * @author huylq
 * 管理者がパスワードを変更する
 */
@Stateless
public class UpdateEmpLoginPasswordListCommandHandler extends CommandHandlerWithResult<List<UpdateEmpLoginPasswordCommand>, List<MyCustomizeException>>
	implements PeregUpdateListCommandHandler<UpdateEmpLoginPasswordCommand>  {

	@Inject
	private ChangeLoginPasswordCommandRequire requireProvider;
	
	@Inject
	private TransactionService transaction;
	
	@Inject
	private UserRepository userRepo;
	
	@Override
	public String targetCategoryCd() {
		return "CS00100";
	}

	@Override
	public Class<?> commandClass() {
		return UpdateEmpLoginPasswordCommand.class;
	}

	@Override
	protected List<MyCustomizeException> handle(CommandHandlerContext<List<UpdateEmpLoginPasswordCommand>> context) {
		val require = requireProvider.create();
		val command = context.getCommand();
		
		command.forEach(cmd -> {
			Optional<User> userOpt = userRepo.getByAssociatedPersonId(cmd.getPersonId());
			
			if (userOpt.isPresent()) {
				val atomTask = ChangeLoginPasswordOfUser.change(require, userOpt.get().getUserID(), cmd.getPassword());
				
				transaction.execute(atomTask);
			}
		});
		return new ArrayList<MyCustomizeException>();
	}

}
