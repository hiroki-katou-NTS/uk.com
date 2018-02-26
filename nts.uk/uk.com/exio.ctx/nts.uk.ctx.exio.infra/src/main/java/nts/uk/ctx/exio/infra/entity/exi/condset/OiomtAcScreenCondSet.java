package nts.uk.ctx.exio.infra.entity.exi.condset;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 受入選別条件設定
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OIOMT_AC_SCREEN_COND_SET")
public class OiomtAcScreenCondSet extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    @Version
    @Column(name = "EXCLUS_VER")
    public Long version;
    
    /**
    * ID
    */
    @EmbeddedId
    public OiomtAcScreenCondSetPk acScreenCondSetPk;
    
    /**
    * 比較条件選択
    */
    @Basic(optional = false)
    @Column(name = "SEL_COMPARE_COND")
    public int selCompareCond;
    
    /**
    * 時間‗条件値2
    */
    @Basic(optional = false)
    @Column(name = "TIME_COND_VAL2")
    public int timeCondVal2;
    
    /**
    * 時間‗条件値1
    */
    @Basic(optional = false)
    @Column(name = "TIME_COND_VAL1")
    public int timeCondVal1;
    
    /**
    * 時刻‗条件値2
    */
    @Basic(optional = false)
    @Column(name = "TIME_MO_COND_VAL2")
    public int timeMoCondVal2;
    
    /**
    * 時刻‗条件値1
    */
    @Basic(optional = false)
    @Column(name = "TIME_MO_COND_VAL1")
    public int timeMoCondVal1;
    
    /**
    * 日付‗条件値2
    */
    @Basic(optional = false)
    @Column(name = "DATE_COND_VAL2")
    public GeneralDate dateCondVal2;
    
    /**
    * 日付‗条件値1
    */
    @Basic(optional = false)
    @Column(name = "DATE_COND_VAL1")
    public GeneralDate dateCondVal1;
    
    /**
    * 文字‗条件値2
    */
    @Basic(optional = false)
    @Column(name = "CHAR_COND_VAL2")
    public String charCondVal2;
    
    /**
    * 文字‗条件値1
    */
    @Basic(optional = false)
    @Column(name = "CHAR_COND_VAL1")
    public String charCondVal1;
    
    /**
    * 数値‗条件値2
    */
    @Basic(optional = false)
    @Column(name = "NUM_COND_VAL2")
    public String numCondVal2;
    
    /**
    * 数値‗条件値1
    */
    @Basic(optional = false)
    @Column(name = "NUM_COND_VAL1")
    public String numCondVal1;
    
    @Override
    protected Object getKey()
    {
        return acScreenCondSetPk;
    }
}
