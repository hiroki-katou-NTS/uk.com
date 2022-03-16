package nts.uk.ctx.sys.gateway.app.find.login.saml.validate;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.sys.gateway.dom.login.sso.saml.validate.SamlResponseValidationRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * <<Query>> SAMLレスポンスの検証を取得する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class SamlResponseValidationQuery {
	
	@Inject
	private SamlResponseValidationRepository repo;

	public SamlResponseValidationDto find() {
		return this.repo
				.find(AppContexts.user().contractCode())
				.map(SamlResponseValidationDto::fromDomain)
				.orElse(null);
	}
}
