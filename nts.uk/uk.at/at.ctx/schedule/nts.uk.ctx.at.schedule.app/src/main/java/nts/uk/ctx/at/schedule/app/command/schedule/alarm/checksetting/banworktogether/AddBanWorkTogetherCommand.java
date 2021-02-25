package nts.uk.ctx.at.schedule.app.command.schedule.alarm.checksetting.banworktogether;

import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.worktogether.ban.*;
import nts.uk.ctx.at.shared.app.find.workrule.shiftmaster.TargetOrgIdenInforDto;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;

import java.util.List;
import java.util.Optional;

@Data
public class AddBanWorkTogetherCommand {

    // 対象組織
    private TargetOrgIdenInforDto targetOrgIdenInfor;

    // コード
    private String code;

    // 名称
    private String name;

    // 適用する時間帯
    private int applicableTimeZoneCls;

    // 許容する人数
    private int upperLimit;

    // 同時出勤を禁止する社員の組み合わせ
    private List<String> targetList;

    public TargetOrgIdenInfor toDomainTargetOrgIdenInfor() {
        return new TargetOrgIdenInfor(
                EnumAdaptor.valueOf(this.getTargetOrgIdenInfor().getUnit(), TargetOrganizationUnit.class),
                this.targetOrgIdenInfor.getWorkplaceId() == null ? Optional.empty() : Optional.of(this.getTargetOrgIdenInfor().getWorkplaceId()),
                this.targetOrgIdenInfor.getWorkplaceGroupId() == null ? Optional.empty() : Optional.of(this.getTargetOrgIdenInfor().getWorkplaceGroupId())
        );
    }

}
