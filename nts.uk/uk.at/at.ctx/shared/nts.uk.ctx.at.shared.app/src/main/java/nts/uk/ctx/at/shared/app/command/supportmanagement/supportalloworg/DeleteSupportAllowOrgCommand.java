package nts.uk.ctx.at.shared.app.command.supportmanagement.supportalloworg;

import lombok.Data;

@Data
public class DeleteSupportAllowOrgCommand {
    /** 単位 */
    private int unit;

    /** 職場ID  （単位：職場）*/
    private String workplaceId;

    /** 職場グループID（単位：職場グループ）*/
    private String workplaceGroupId;
}

