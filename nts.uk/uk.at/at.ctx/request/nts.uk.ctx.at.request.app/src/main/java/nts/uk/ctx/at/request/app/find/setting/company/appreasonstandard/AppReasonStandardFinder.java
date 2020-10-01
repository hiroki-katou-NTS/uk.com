package nts.uk.ctx.at.request.app.find.setting.company.appreasonstandard;

import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppReasonStandardRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class AppReasonStandardFinder {
    @Inject
    private AppReasonStandardRepository appReasonStandardRepo;

    public List<AppReasonStandardDto> getAllByCompany() {
        String companyId = AppContexts.user().companyId();
        return appReasonStandardRepo.findByCompanyId(companyId).stream().map(AppReasonStandardDto::fromDomain).collect(Collectors.toList());
    }
}
