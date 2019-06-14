package nts.uk.ctx.pr.core.infra.entity.wageprovision.organizationinfor.salarycls.salaryclshistory;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
* 社員給与分類項目: 主キー情報
*/
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QpbmtEmpSalaCategoryPk implements Serializable
{
    private static final long serialVersionUID = 1L;


    /**
     * 社員ID
     */
    @Basic(optional = false)
    @Column(name = "SID")
    public String sid;

    /**
     * 履歴ID
     */
    @Basic(optional = false)
    @Column(name = "HIST_ID")
    public String hisId;
    
}
