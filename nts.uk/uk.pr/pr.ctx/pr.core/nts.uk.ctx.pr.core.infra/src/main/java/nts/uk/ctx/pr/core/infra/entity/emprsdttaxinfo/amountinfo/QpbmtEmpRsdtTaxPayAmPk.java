package nts.uk.ctx.pr.core.infra.entity.emprsdttaxinfo.amountinfo;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
* 社員住民税納付額情報: 主キー情報
*/
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QpbmtEmpRsdtTaxPayAmPk implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * 社員ID
    */
    @Basic(optional = false)
    @Column(name = "SID")
    public String sid;
    
    /**
    * 年度
    */
    @Basic(optional = false)
    @Column(name = "YEAR")
    public int year;
    
}
