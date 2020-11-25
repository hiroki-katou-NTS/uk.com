package nts.uk.screen.at.app.ksm008.query.c.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.DisplayInfoOrganization;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DisplayInfoOrganizationDto {

    /** 呼称 **/
    private String designation;
    /** コード **/
    private String code;
    /** 名称 **/
    private String name;
    /** 表示名 **/
    private String displayName;
    /** 呼称 **/
    private String genericTerm;

    public static DisplayInfoOrganizationDto fromDomain(DisplayInfoOrganization domain) {
        if (domain == null) {
            return null;
        }
        return new DisplayInfoOrganizationDto(
                domain.getDesignation(),
                domain.getCode(),
                domain.getName(),
                domain.getDisplayName(),
                domain.getGenericTerm()
        );
    }

}
