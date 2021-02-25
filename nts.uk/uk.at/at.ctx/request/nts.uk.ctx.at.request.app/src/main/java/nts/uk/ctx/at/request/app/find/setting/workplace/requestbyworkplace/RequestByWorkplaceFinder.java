package nts.uk.ctx.at.request.app.find.setting.workplace.requestbyworkplace;

import nts.uk.ctx.at.request.dom.setting.workplace.requestbyworkplace.RequestByWorkplaceRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class RequestByWorkplaceFinder {
    @Inject
    private RequestByWorkplaceRepository requestByWorkplaceRepo;

    public List<RequestByWorkplaceDto> findAllByCompany() {
        String companyId = AppContexts.user().companyId();
        return requestByWorkplaceRepo.findByCompany(companyId).stream().map(RequestByWorkplaceDto::fromDomain).collect(Collectors.toList());
    }
}
