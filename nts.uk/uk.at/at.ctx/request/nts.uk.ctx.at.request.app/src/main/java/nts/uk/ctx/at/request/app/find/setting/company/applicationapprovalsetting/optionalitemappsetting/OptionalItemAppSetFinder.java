package nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.optionalitemappsetting;

import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.optionalitemappsetting.OptionalItemAppSetRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class OptionalItemAppSetFinder {
    @Inject
    private OptionalItemAppSetRepository repository;

    public List<OptionalItemAppSetDto> findAllByCompany() {
        String companyId = AppContexts.user().companyId();
        return repository.findByCompany(companyId).stream().map(OptionalItemAppSetDto::fromDomain).collect(Collectors.toList());
    }

    public OptionalItemAppSetDto findByCode(String code) {
        String companyId = AppContexts.user().companyId();
        return repository.findByCompanyAndCode(companyId, code).map(OptionalItemAppSetDto::fromDomain).orElse(null);
    }
}
