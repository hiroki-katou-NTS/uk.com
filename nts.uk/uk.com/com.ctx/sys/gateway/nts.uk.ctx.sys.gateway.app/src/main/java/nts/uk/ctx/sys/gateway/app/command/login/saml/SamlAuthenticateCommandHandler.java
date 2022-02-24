package nts.uk.ctx.sys.gateway.app.command.login.saml;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import lombok.SneakyThrows;
import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.gateway.dom.singlesignon.saml.SamlOperationRepository;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.TenantAuthenticationRepository;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class SamlAuthenticateCommandHandler extends CommandHandlerWithResult<SamlAuthenticateCommand, SamlAuthenticateInfo> {

	@Inject
	private TenantAuthenticationRepository tenantAuthenticationRepository;

	@Inject
	private SamlOperationRepository samlOperationRepository;

	@SneakyThrows
	protected SamlAuthenticateInfo handle(CommandHandlerContext<SamlAuthenticateCommand> context) {
		SamlAuthenticateCommand command = context.getCommand();
		String tenantCode = command.getTenantCode();
		String password = command.getTenantPassword();

		// テナント認証情報のチェック
		this.checkInput(command);
		// テナント認証
		val tenant = tenantAuthenticationRepository.find(tenantCode)
				.orElseThrow(() -> new BusinessException("Msg_314"));

		if(!tenant.verifyPassword(password)) {
			// テナントパスワードが間違っている
			throw new BusinessException("Msg_302");
		}
		if(!tenant.isAvailableAt(GeneralDate.today())) {
			// テナントの有効期限が切れている
			throw new BusinessException("Msg_315");
		}

		val optSamlOpe = samlOperationRepository.find(tenantCode);

		if(!optSamlOpe.isPresent()) {
			// SAMLの運用が取得できなかった場合
			return SamlAuthenticateInfo.noSamlOperation();
		}
		val samlOpe = optSamlOpe.get();

		// 認証用URLの取得
		val optEntryUrl = samlOpe.createIdpEntryUrl(tenantCode, password, command.getRequestUrl());
		if(optEntryUrl.isPresent()) {
			return SamlAuthenticateInfo.useSso(optEntryUrl.get());
		}
		else{
			return SamlAuthenticateInfo.noUseSso();
		}
	}

	private void checkInput(SamlAuthenticateCommand command) {
		if (StringUtils.isEmpty(command.getTenantCode())) {
			throw new BusinessException("Msg_313");
		}
		if (StringUtils.isEmpty(command.getTenantPassword())) {
			throw new BusinessException("Msg_310");
		}
	}
}
