package nts.uk.ctx.sys.gateway.app.command.securitypolicy.lockoutdata;

import java.util.Optional;
import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.gateway.dom.adapter.user.UserAdapter;
import nts.uk.ctx.sys.gateway.dom.adapter.user.UserImportNew;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.lockoutdata.LockOutData;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.lockoutdata.LockOutDataDto;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.lockoutdata.LockOutDataRepository;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.lockoutdata.LockType;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.lockoutdata.LoginMethod;

/*
 * @author: Nguyen Van Hanh
 */
@Stateless
public class AddLockOutDataCommandHandler extends CommandHandler<AddLockOutDataCommand> {

	/** The user adapter repository. */
	@Inject
	UserAdapter userAdapter;

	/** The lock out data repository. */
	@Inject
	private LockOutDataRepository lockOutDataRepository;

	@Override
	protected void handle(CommandHandlerContext<AddLockOutDataCommand> context) {

		// UserId
		String userId = context.getCommand().getUserID();

		Optional<UserImportNew> user = userAdapter.findByUserId(userId);

		if (!user.isPresent())
			return;

		// ドメインモデル「ロックアウトデータ」の重複チェックを行う
		if (checkDuplicateLocking(user.get().getUserId()))
			throw new BusinessException("Msg_868");

		// Add to domain model LockOutData
		LockOutDataDto dto = LockOutDataDto.builder()
				.userId(user.get().getUserId())
				.contractCode(user.get().getContractCode())
				.logoutDateTime(GeneralDateTime.now())
				.lockType(LockType.ENFORCEMENT_LOCK.value)
				.loginMethod(LoginMethod.NORMAL_LOGIN.value)
				.build();
		LockOutData lockOutData = new LockOutData(dto);
		this.lockOutDataRepository.add(lockOutData);
	}
	
	private boolean checkDuplicateLocking(String userId) {
		Optional<LockOutData> otp = lockOutDataRepository.findByUserId(userId);
		return otp.isPresent();
	}

}
