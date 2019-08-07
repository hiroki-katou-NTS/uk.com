package nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.emphealinsurassocinfor;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
* 健保組合情報: 主キー情報
*/
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QqbmtHealCarePorInfoPk implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * 履歴ID
    */
    @Basic(optional = false)
    @Column(name = "HIS_ID")
    public String hisId;
    
}
