package nts.uk.ctx.sys.gateway.app.find.login.saml.operate;

import nts.uk.ctx.sys.gateway.dom.login.sso.saml.operate.SamlOperationRepository;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class SamlOperationFinder {

    @Inject
    private SamlOperationRepository repo;

    public Optional<SamlOperationDto> find(String tenantCode) {
        return repo.find(tenantCode)
                .map(d -> SamlOperationDto.fromDomain(d));
    }
}
