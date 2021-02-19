package nts.uk.ctx.sys.assist.app.command.datarestoration;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.security.crypt.commonkey.CommonKeyCrypt;
import nts.uk.ctx.sys.assist.dom.storage.ResultOfSaving;
import nts.uk.ctx.sys.assist.dom.storage.ResultOfSavingRepository;

/**
 * アルゴリズム「パスワードをチェックする」
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class CheckPasswordCommandHandler extends CommandHandlerWithResult<CheckPasswordCommand, Boolean> {
	
	@Inject 
	private ResultOfSavingRepository resultOfSavingRepository;

	@Override
	protected Boolean handle(CommandHandlerContext<CheckPasswordCommand> context) {
		/**
		 * Inputドメインモデル「データ保存の保存結果」．圧縮パスワードをチェックする
		 */
		Optional<ResultOfSaving> resOptional = resultOfSavingRepository.getResultOfSavingById(context.getCommand().getStoreProcessingId());
		if (resOptional.isPresent()) {
			String decodedPassword = resOptional.get().getCompressedPassword()
					.map(i -> CommonKeyCrypt.decrypt(i.v()))
					.orElse(null); 
			return decodedPassword != null && decodedPassword.equals(context.getCommand().getPassword());
		} 
		return false;
	}
}