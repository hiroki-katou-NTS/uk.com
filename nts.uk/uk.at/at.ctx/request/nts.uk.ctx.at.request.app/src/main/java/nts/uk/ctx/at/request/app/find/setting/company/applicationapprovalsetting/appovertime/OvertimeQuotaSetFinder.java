package nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.appovertime;

import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime.OvertimeAppSetRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class OvertimeQuotaSetFinder {
    @Inject
    private OvertimeAppSetRepository overtimeAppSetRepo;

    public List<OvertimeQuotaSetUseDto> getOvertimeQuotaSettings() {
        String companyId = AppContexts.user().companyId();
        List<OvertimeQuotaSetUseDto> result = new ArrayList<>();
        overtimeAppSetRepo.getOvertimeQuotaSetting(companyId).forEach(a -> {
            result.addAll(OvertimeQuotaSetUseDto.fromDomain(a));
        });
        return result;
    }
}
