package nts.uk.ctx.sys.gateway.app.command.login.saml;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import lombok.val;
import nts.arc.diagnose.stopwatch.embed.EmbedStopwatch;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.gul.security.saml.RelayState;
import nts.uk.ctx.sys.gateway.dom.singlesignon.saml.SamlOperationRepository;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.FindTenant;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.TenantAuthentication;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.TenantAuthenticationRepository;

@Stateless
public class SamlAuthenticateCommandHandler extends CommandHandlerWithResult<SamlAuthenticateCommand, AuthenticateInfo> {
	
	@Inject
	private TenantAuthenticationRepository tenantAuthenticationRepository;
	
	@Inject
	private SamlOperationRepository samlOperationRepository;

	protected AuthenticateInfo handle(CommandHandlerContext<SamlAuthenticateCommand> context) {
		SamlAuthenticateCommand command = context.getCommand();
		String tenantCode = command.getTenantCode();
		String password = command.getTenantPassword();
		
		// テナント認証情報のチェック
		this.checkInput(command);
		// テナント認証
		FindTenant.Require require = EmbedStopwatch.embed(new RequireImpl());
		val optTenant = FindTenant.byTenantCode(require, tenantCode);
		if (!optTenant.isPresent()) {
			// テナントが取得できない
			throw new BusinessException("Msg_314");
		} 
		val tenant = optTenant.get();
		if(!tenant.verify(password)) {
			// テナントパスワードが間違っている
			throw new BusinessException("Msg_302");
		}
		if(!tenant.isAvailableAt(GeneralDate.today())) {
			// テナントの有効期限が切れている
			throw new BusinessException("Msg_315");
		}

		// シングルサインオンを運用しているかチェック(仮)

		val optSamlOpe = samlOperationRepository.find(tenantCode);
		
		if(!optSamlOpe.isPresent()) {
			// SAMLの運用が取得できなかった場合
			throw new BusinessException("Msg_1979");
		}
		val samlOpe = optSamlOpe.get();
		val useSamlSso = samlOpe.isUseSingleSignOn();
		// 運用していない場合
		if(!useSamlSso) {
			return new AuthenticateInfo(useSamlSso, null);
		}
		
        // 認証用URL生成
		String authenticateUrl = samlOpe.getIdpRedirectUrl();
		
		// 認証後にアクセスしたい情報を「RelayState」として設定
		RelayState relayState = new RelayState();
		// テナントコード
		relayState.add("tenantCode", tenantCode);
		// テナントパスワード
		relayState.add("tenantPassword", password);
		// アクセスしようとしているURL
		final String requestUrl = toScreen(command);
		relayState.add("requestUrl", requestUrl);
		
		authenticateUrl = authenticateUrl + "?" +"RelayState=" + relayState.serialize();
		
		return new AuthenticateInfo(useSamlSso, authenticateUrl);
	}

	// メソッド名に困っている
	private String toScreen(SamlAuthenticateCommand command) {
		if(!StringUtils.isEmpty(command.getRequestUrl())) {
			return  command.getRequestUrl();
		}
		// 指定がなければトップページへ
		return  "/nts.uk.com.web/view/ccg/008/a/index.xhtml";
	}
	
	/**
	 * Check input.
	 *
	 * @param command the command
	 */
	private void checkInput(SamlAuthenticateCommand command) {
		if (StringUtils.isEmpty(command.getTenantCode())) {
			throw new BusinessException("Msg_313");
		}
		if (StringUtils.isEmpty(command.getTenantPassword())) {
			throw new BusinessException("Msg_310");
		}
	}
	
	public class RequireImpl implements FindTenant.Require{

		@Override
		public Optional<TenantAuthentication> getTenantAuthentication(String tenantCode) {
			return tenantAuthenticationRepository.find(tenantCode);
		}

		@Override
		public Optional<TenantAuthentication> getTenantAuthentication(String tenantCode, GeneralDate date) {
			return tenantAuthenticationRepository.find(tenantCode, date);
		}
	}
}
