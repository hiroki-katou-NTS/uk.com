package nts.uk.screen.at.app.ksm008.query.c.dto;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.schedule.app.query.schedule.alarm.checksetting.banworktogether.BanWorkTogetherDto;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.worktogether.ban.BanWorkTogether;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.worktogether.ban.BanWorkTogetherRepository;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.screen.at.app.ksu001.getinfoofInitstartup.TargetOrgIdenInforDto;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class GetBanWorkListByTargetOrgQuery {

    @Inject
    private BanWorkTogetherRepository BanWorkRepo;

    public List<BanWorkTogetherDto> get(GetEmployeeInfoByWorkplaceDto param) {
        TargetOrgIdenInfor targetOrgIdenInfor = new TargetOrgIdenInfor(
                EnumAdaptor.valueOf(param.getUnit(), TargetOrganizationUnit.class),
                param.getWorkplaceId() == null ? Optional.empty() : Optional.of(param.getWorkplaceId()),
                param.getWorkplaceGroupId() == null ? Optional.empty() : Optional.of(param.getWorkplaceGroupId())
        );
        return BanWorkRepo.getAll(AppContexts.user().companyId(), targetOrgIdenInfor)
                .stream().map(BanWorkTogetherDto::fromDomain).collect(Collectors.toList());
    }

}
