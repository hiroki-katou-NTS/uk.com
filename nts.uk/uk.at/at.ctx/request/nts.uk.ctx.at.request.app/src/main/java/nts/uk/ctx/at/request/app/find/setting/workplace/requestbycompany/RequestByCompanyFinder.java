package nts.uk.ctx.at.request.app.find.setting.workplace.requestbycompany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.setting.workplace.appuseset.ApplicationUseSetDto;
import nts.uk.ctx.at.request.dom.setting.workplace.requestbycompany.RequestByCompanyRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class RequestByCompanyFinder {
    @Inject
    private RequestByCompanyRepository requestByCompanyRepo;

    /**
     *      * 起動初期の処理
     * @return
     */
    public List<ApplicationUseSetDto> findByCompany() {
        String companyId = AppContexts.user().companyId();
        return requestByCompanyRepo.findByCompanyId(companyId)
                .map(d -> d.getApprovalFunctionSet().getAppUseSetLst().stream().map(ApplicationUseSetDto::fromDomain).collect(Collectors.toList()))
                .orElse(null);
    }
}
