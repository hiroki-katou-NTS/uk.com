package nts.uk.screen.at.app.ksm008.query.c.dto;

import lombok.Data;

@Data
public class GetEmployeeInfoByWorkplaceDto {

    // 対象組織の単位
    private int unit;

    // 職場ID
    private String workplaceId;

    // 職場グループID
    private String workplaceGroupId;

}
