package nts.uk.screen.at.app.kmt013;

import lombok.Value;

@Value
public class SupportableOrgInfoDto {
    /**
     * 単位
     */
    private int unit;

    /**
     * 職場ID/ 職場グループID
     */
    private String orgId;

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

}
