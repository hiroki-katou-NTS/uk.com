package nts.uk.ctx.at.shared.app.command.supportmanagement.supportalloworg;

import lombok.Data;

import java.util.List;

@Data
public class RegisterSupportAllowOrgCommand {
    /** 対象組織の単位 */
    private int orgUnit;

    /** 組織ID */
    private String orgId;

    /** List<応援可能組織> */
    private List<OrgCanBeSupportDto> orgCanBeSupports;
}

@Data
class OrgCanBeSupportDto {
    private String orgId;
}
