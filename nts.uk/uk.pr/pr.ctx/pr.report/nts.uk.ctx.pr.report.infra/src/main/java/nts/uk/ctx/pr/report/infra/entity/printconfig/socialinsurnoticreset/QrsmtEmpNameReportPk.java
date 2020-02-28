package nts.uk.ctx.pr.report.infra.entity.printconfig.socialinsurnoticreset;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
* 社員ローマ字氏名届情報: 主キー情報
*/
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QrsmtEmpNameReportPk implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * 社員ID
    */
    @Basic(optional = false)
    @Column(name = "SID")
    public String empId;

    /**
     * 社員ID
     */
    @Basic(optional = false)
    @Column(name = "CID")
    public String cid;
    
}
