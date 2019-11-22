package nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice;

import com.sun.org.apache.bcel.internal.classfile.Code;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.context.AppContexts;

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

    public PublicEmploymentSecurityOffice(String publicEmploymentSecurityOfficeCode, String publicEmploymentSecurityOfficeName) {
        this.companyId = AppContexts.user().companyId();
        this.publicEmploymentSecurityOfficeCode = new PublicEmploymentSecurityOfficeCode(publicEmploymentSecurityOfficeCode);
        this.publicEmploymentSecurityOfficeName = new PublicEmploymentSecurityOfficeName(publicEmploymentSecurityOfficeName);
    }
}
