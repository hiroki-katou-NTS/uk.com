package nts.uk.ctx.at.shared.app.command.supportmanagement.supportalloworg;

import lombok.Data;

@Data
public class DeleteSupportAllowOrgCommand {
    /** 単位 */
    private int unit;

    private String orgId;
}

