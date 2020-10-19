package nts.uk.ctx.at.schedule.app.query.schedule.alarm.banholidaytogether;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.banholidaytogether.BanHolidayTogether;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.banholidaytogether.BanHolidayTogetherRepository;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class BanHolidayTogetherQuery {
    @Inject
    private BanHolidayTogetherRepository BanHolidayTogetherRepo;

    /**
     * 同日休日禁止リストを取得する
     *
     * @param unit             対象組織.単位
     * @param workplaceId      対象組織.職場ID
     * @param workplaceGroupId 対象組織.職場グループID
     * @return List<同時休日禁止>
     */
    public BanHolidayTogetherQueryDto getAllBanHolidayTogether(int unit, String workplaceId, String workplaceGroupId) {
        TargetOrgIdenInfor targeOrg = new TargetOrgIdenInfor(
                EnumAdaptor.valueOf(unit, TargetOrganizationUnit.class),
                Optional.ofNullable(workplaceId),
                Optional.ofNullable(workplaceGroupId)
        );

        String companyId = AppContexts.user().companyId();

        List<BanHolidayTogether> listBanHolidayTogether = BanHolidayTogetherRepo.getAll(companyId, targeOrg);

        BanHolidayTogetherQueryDto data = new BanHolidayTogetherQueryDto();

        if (listBanHolidayTogether != null && !listBanHolidayTogether.isEmpty()) {
            List<String> banHolidayTogetherCode = listBanHolidayTogether.stream().map(item -> item.getBanHolidayTogetherCode().v()).collect(Collectors.toList());
            List<String> banHolidayTogetherName = listBanHolidayTogether.stream().map(item -> item.getBanHolidayTogetherName().v()).collect(Collectors.toList());

            data.setBanHolidayTogetherCode(banHolidayTogetherCode);
            data.setBanHolidayTogetherName(banHolidayTogetherName);
        }

        return data;
    }
}
