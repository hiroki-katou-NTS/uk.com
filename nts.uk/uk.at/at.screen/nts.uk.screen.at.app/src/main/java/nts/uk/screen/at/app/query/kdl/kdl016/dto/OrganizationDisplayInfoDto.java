package nts.uk.screen.at.app.query.kdl.kdl016.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrganizationDisplayInfoDto {
    private String orgId;

    private int orgUnit;

    private String orgCode;

    /** 表示名 **/
    private  String displayName;
}
