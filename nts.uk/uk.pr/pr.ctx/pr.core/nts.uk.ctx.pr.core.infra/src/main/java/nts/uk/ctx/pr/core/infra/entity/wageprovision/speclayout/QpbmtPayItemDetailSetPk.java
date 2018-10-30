package nts.uk.ctx.pr.core.infra.entity.wageprovision.speclayout;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
* 支給項目明細設定: 主キー情報
*/
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QpbmtPayItemDetailSetPk implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
    * 履歴ID
    */
    @Basic(optional = false)
    @Column(name = "HIST_ID")
    public String histId;
    
}
