package nts.uk.ctx.sys.gateway.app.command.singlesignon.saml;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;

import com.onelogin.saml2.util.Constants;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.security.saml.SamlResponseValidator;
import nts.gul.security.saml.SamlResponseValidator.ValidateException;
import nts.gul.security.saml.SamlSetting;
import nts.gul.security.saml.ValidSamlResponse;
import nts.uk.ctx.sys.gateway.dom.singlesignon.saml.IdpUserAssociation;

@Stateless
public class SamlValidateCommandHandler extends CommandHandler<SamlValidateCommand> {

	protected void handle(CommandHandlerContext<SamlValidateCommand> command) {
		HttpServletRequest request = command.getCommand().getRequest();

		SamlSetting samlSetting = new SamlSetting();
		// クライアント名(UKとかHLとか)
		samlSetting.SetSpEntityId("sso");
		// IdpのURL
		samlSetting.SetIdpEntityId("http://localhost:8180/auth/realms/my_territory");
		// 署名用アルゴリズム
		samlSetting.SetSignatureAlgorithm(Constants.RSA_SHA1);
		// X509証明書のFingerPrint
		// https://www.samltool.com/fingerprint.php ←ココで作れる
		samlSetting.SetIdpCertFingerprint("8dfc4a658496a05a3ed44357d97865007071b6e6");

		ValidSamlResponse validateResult;
		try {
			// SAMLResponseの検証処理
			validateResult = SamlResponseValidator.validate(request, samlSetting);
			
			// 認証失敗時
			if (!validateResult.isValid()) {
				System.out.println("ログイン失敗！");
				// 通常ログイン画面へ
			}

			// Idpユーザと社員の紐付けから社員を特定
			Optional<String> employeeID = IdpUserAssociation.getAssociateEmployee(validateResult.getIdpUser());

			// 社員特定できない
			if (!employeeID.isPresent()) {
				System.out.println("社員わからん！");
				// 通常紐付処理
			}
			
			System.out.println("ログイン成功！！！");
			// ログイン処理

		} catch (ValidateException e) {
			// 認証自体に失敗時
			// 通常ログイン画面へ
			System.out.println("認証失敗！");
		}
	}
}
