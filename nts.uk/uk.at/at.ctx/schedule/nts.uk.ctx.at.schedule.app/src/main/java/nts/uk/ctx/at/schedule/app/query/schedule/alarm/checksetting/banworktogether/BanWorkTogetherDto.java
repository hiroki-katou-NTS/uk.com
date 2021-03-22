package nts.uk.ctx.at.schedule.app.query.schedule.alarm.checksetting.banworktogether;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.worktogether.ban.BanWorkTogether;
import nts.uk.ctx.at.shared.app.find.workrule.shiftmaster.TargetOrgIdenInforDto;

import java.util.List;

@Data
@AllArgsConstructor
public class BanWorkTogetherDto {

    /** 対象組織 */
    private TargetOrgIdenInforDto targetOrgIdenInfor;

    /** コード */
    private String code;

    /** 名称 */
    private String name;

    /** 適用する時間帯 */
    private int applicableTimeZoneCls;

    /** 禁止する社員の組み合わせ */
    private List<String> empBanWorkTogetherLst;

    /** 許容する人数 */
    private Integer upperLimit;

    public static BanWorkTogetherDto fromDomain(BanWorkTogether domain) {
        if (domain == null) {
            return null;
        }
        TargetOrgIdenInforDto targetOrgIdenInfor = new TargetOrgIdenInforDto(
                domain.getTargetOrg().getUnit().value,
                domain.getTargetOrg().getWorkplaceId().isPresent() ? domain.getTargetOrg().getWorkplaceId().get() : null,
                domain.getTargetOrg().getWorkplaceGroupId().isPresent() ? domain.getTargetOrg().getWorkplaceGroupId().get() : null
        );
        return new BanWorkTogetherDto(
                targetOrgIdenInfor,
                domain.getCode().v(),
                domain.getName().v(),
                domain.getApplicableTimeZoneCls().value,
                domain.getEmpBanWorkTogetherLst(),
                domain.getUpperLimit()
        );
    }

}
