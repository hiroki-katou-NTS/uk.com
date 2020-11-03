package nts.uk.screen.at.app.ksm008.query.j;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.DisplayInfoOrganization;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ksm008JOrgInfoDto {
    /**
     * 対象組織情報
     */
    private TargetOrgIdenInfor targeOrg;

    /**
     * 組織の表示情報
     */
    private DisplayInfoOrganization displayInfoOrganization;
}