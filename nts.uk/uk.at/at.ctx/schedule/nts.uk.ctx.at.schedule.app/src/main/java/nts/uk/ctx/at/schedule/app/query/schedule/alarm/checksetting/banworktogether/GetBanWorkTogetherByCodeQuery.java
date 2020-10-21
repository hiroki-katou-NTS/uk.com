package nts.uk.ctx.at.schedule.app.query.schedule.alarm.checksetting.banworktogether;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.worktogether.ban.BanWorkTogether;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.worktogether.ban.BanWorkTogetherCode;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.worktogether.ban.BanWorkTogetherRepository;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.勤務予定のアラームチェック.同時出勤.同時出勤禁止.App.コードを指定して同時出勤禁止を取得する
 *
 * @author hai.tt
 */

@Stateless
public class GetBanWorkTogetherByCodeQuery {

    @Inject
    private BanWorkTogetherRepository banWorkTogetherRepo;

    /**
     * コードを指定して同時出勤禁止を取得する
     * @param unit
     * @param workplaceId
     * @param workplaceGroupId
     * @param code
     * @return
     */
    public BanWorkTogetherDto get(GetBanWorkDto param) {
        TargetOrgIdenInfor targetOrgIdenInfor = new TargetOrgIdenInfor(
                EnumAdaptor.valueOf(param.getUnit(), TargetOrganizationUnit.class),
                param.getWorkplaceId() == null ? Optional.empty() : Optional.of(param.getWorkplaceId()),
                param.getWorkplaceId() == null ? Optional.empty() : Optional.of(param.getWorkplaceId())
        );
        Optional<BanWorkTogether> banWorkTogetherOp = banWorkTogetherRepo.get(AppContexts.user().companyId(), targetOrgIdenInfor, new BanWorkTogetherCode(param.getCode()));
        return BanWorkTogetherDto.fromDomain(banWorkTogetherOp.orElse(null));
    }

}
