package nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.appovertime;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeAppAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime.FlexWorkAtr;
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

    public List<OvertimeQuotaSetUseDto> getOvertimeQuotaSettings(Integer overtimeAtr, Integer flexAtr) {
        String companyId = AppContexts.user().companyId();
        OvertimeAppAtr overtimeAppAtr = EnumAdaptor.valueOf(overtimeAtr, OvertimeAppAtr.class);
        FlexWorkAtr flexWorkAtr = EnumAdaptor.valueOf(flexAtr, FlexWorkAtr.class);
        List<OvertimeQuotaSetUseDto> result = new ArrayList<>();
        overtimeAppSetRepo.getOvertimeQuotaSetting(companyId, overtimeAppAtr, flexWorkAtr).ifPresent(a -> {
            result.addAll(OvertimeQuotaSetUseDto.fromDomain(a));
        });
        return result;
    }
}
