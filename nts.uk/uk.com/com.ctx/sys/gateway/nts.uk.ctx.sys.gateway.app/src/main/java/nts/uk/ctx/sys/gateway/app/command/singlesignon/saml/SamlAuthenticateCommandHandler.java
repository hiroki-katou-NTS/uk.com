package nts.uk.ctx.sys.gateway.app.command.singlesignon.saml;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.gul.security.hash.password.PasswordHash;
import nts.uk.ctx.sys.gateway.dom.login.Contract;
import nts.uk.ctx.sys.gateway.dom.login.ContractRepository;

@Stateless
public class SamlAuthenticateCommandHandler extends CommandHandlerWithResult<SamlAuthenticateCommand, AuthenticateInfo> {
	
	@Inject
	private ContractRepository contractRepository;

	protected AuthenticateInfo handle(CommandHandlerContext<SamlAuthenticateCommand> context) {
		SamlAuthenticateCommand command = context.getCommand();
		String contractCode = command.getContractCode();
		String password = command.getPassword();
		
		// テナント認証情報のチェック
		this.checkInput(command);
		// テナント認証
		this.contractAccAuth(contractCode, password);
		
		// シングルサインオンを運用しているかチェック(仮)
		boolean useSamlSso = true;

		// 運用していない場合
		if(!useSamlSso) {
			return new AuthenticateInfo(useSamlSso, null);
		}
		
        // 認証用URL生成
		// IdP-Initiatedを用いる場合は「SAMLRequest」は不要なのでURLごと永続化しておく必要あり
		String authenticateUrl = "http://localhost:8180/auth/realms/my_territory/protocol/saml/clients/idempiere";
		// 認証後にアクセスしたいURLを「RelayState」として設定
		authenticateUrl = authenticateUrl + "?" +"RelayState=" + command.getRequestUrl();
		
		return new AuthenticateInfo(useSamlSso, authenticateUrl);
	}
	
	/**
	 * テナント認証
	 * @param contractCode
	 * @param password
	 * 
	 * 失敗のときはBusinessException（returnなし）
	 */
	private void contractAccAuth(String contractCode, String password) {
		Optional<Contract> contract = contractRepository.getContract(contractCode);
		if (contract.isPresent()) {
			this.checkPassword(contract, password);
			this.checkTime(contract);
		} else {
			throw new BusinessException("Msg_314");
		}
	}
	
	/**
	 * Check input.
	 *
	 * @param command the command
	 */
	private void checkInput(SamlAuthenticateCommand command) {
		if (command.getContractCode() == null || command.getContractCode().isEmpty()) {
			throw new BusinessException("Msg_313");
		}
		if (command.getPassword() == null || command.getPassword().isEmpty()) {
			throw new BusinessException("Msg_310");
		}
	}

	/**
	 * Check password.
	 *
	 * @param contract the contract
	 * @param password the password
	 */
	private void checkPassword(Optional<Contract> contract, String password) {
		if (!PasswordHash.verifyThat(password, contract.get().getContractCode().v()).isEqualTo(contract.get().getPassword().v())) {
			throw new BusinessException("Msg_302");
		}
	}
	
	/**
	 * Check time.
	 *
	 * @param contract the contract
	 */
	private void checkTime(Optional<Contract> contract) {
		if (contract.get().getContractPeriod().start().after(GeneralDate.today())
				|| contract.get().getContractPeriod().end().before(GeneralDate.today())) {
			throw new BusinessException("Msg_315");
		}
	}
}
