package nts.uk.ctx.sys.gateway.app.find.login.saml.operate;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.sys.gateway.dom.login.sso.saml.operate.SamlOperationRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * <<Query>> SAMLの運用を取得する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class SamlOperationQuery {

	@Inject
    private SamlOperationRepository repo;

    public SamlOperationDto find() {
        return repo.find(AppContexts.user().contractCode())
                .map(d -> SamlOperationDto.fromDomain(d))
                .orElse(null);
    }
}
