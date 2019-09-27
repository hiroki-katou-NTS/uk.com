package nts.uk.ctx.pr.shared.infra.entity.familyinfo.empfamilysocialins;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
* 社員家族社会保険履歴: 主キー情報
*/
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QqsmtEmpFamilyInsHisPk implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
    * 社員ID
    */
    @Basic(optional = false)
    @Column(name = "EMP_ID")
    public String empId;
    
    /**
    * 家族ID
    */
    @Basic(optional = false)
    @Column(name = "FAMILY_ID")
    public int familyId;
    
    /**
    * 履歴ID
    */
    @Basic(optional = false)
    @Column(name = "HISTORY_ID")
    public String historyId;
    
}
