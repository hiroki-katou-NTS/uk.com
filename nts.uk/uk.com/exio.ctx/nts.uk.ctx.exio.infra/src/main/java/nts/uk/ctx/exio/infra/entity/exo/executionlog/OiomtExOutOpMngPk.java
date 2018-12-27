package nts.uk.ctx.exio.infra.entity.exo.executionlog;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
* 外部出力動作管理: 主キー情報
*/
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class OiomtExOutOpMngPk implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * 
    */
    @Basic(optional = false)
    @Column(name = "EX_OUT_PRO_ID")
    public String exOutProId;
    
}
