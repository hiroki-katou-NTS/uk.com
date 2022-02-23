package nts.uk.ctx.at.shared.app.command.supportmanagement.supportalloworg;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CopySupportAllowOrgResult {
    private String workplaceId;
    private boolean copyResult;
    private OrgDisplayInfoDto orgDisplayInfo;
}

@Data
@AllArgsConstructor
class OrgDisplayInfoDto {
    private String orgcCode;
    private String orgName;
}
