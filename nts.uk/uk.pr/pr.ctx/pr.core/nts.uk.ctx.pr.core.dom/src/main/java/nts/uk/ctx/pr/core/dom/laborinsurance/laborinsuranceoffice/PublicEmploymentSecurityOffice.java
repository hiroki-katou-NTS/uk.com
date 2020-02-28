package nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 公共職業安定所
 */
@Getter
public class PublicEmploymentSecurityOffice extends AggregateRoot {
    /**
     * 会社ID
     */
    private String companyId;

    /**
     * 公共職業安定所コード
     */
    private PublicEmploymentSecurityOfficeCode publicEmploymentSecurityOfficeCode;

    /**
     * 公共職業安定所名
     */
    private PublicEmploymentSecurityOfficeName publicEmploymentSecurityOfficeName;

    public PublicEmploymentSecurityOffice(String cid, String publicEmploymentSecurityOfficeCode, String publicEmploymentSecurityOfficeName) {
        this.companyId = cid;
        this.publicEmploymentSecurityOfficeCode = new PublicEmploymentSecurityOfficeCode(publicEmploymentSecurityOfficeCode);
        this.publicEmploymentSecurityOfficeName = new PublicEmploymentSecurityOfficeName(publicEmploymentSecurityOfficeName);
    }
}
