package nts.uk.ctx.pr.core.infra.entity.wageprovision.companyuniformamount;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
* 給与会社単価設定: 主キー情報
*/
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QpbmtPayUnitPriSetPk implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * 履歴ID
    */
    @Basic(optional = false)
    @Column(name = "HIS_ID")
    public String hisId;
    
}
