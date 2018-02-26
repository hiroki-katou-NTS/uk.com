package nts.uk.ctx.exio.infra.entity.exi.dataformat;

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
* 時刻型データ形式設定
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OIOMT_INS_TIME_DAT_FM_SET")
public class OiomtInsTimeDatFmSet extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    @Version
    @Column(name = "EXCLUS_VER")
    public Long version;
    
    /**
    * ID
    */
    @EmbeddedId
    public OiomtInsTimeDatFmSetPk insTimeDatFmSetPk;
    
    /**
    * 区切り文字設定
    */
    @Basic(optional = false)
    @Column(name = "DELIMITER_SET")
    public int delimiterSet;
    
    /**
    * 固定値
    */
    @Basic(optional = false)
    @Column(name = "FIXED_VALUE")
    public int fixedValue;
    
    /**
    * 時分/分選択
    */
    @Basic(optional = false)
    @Column(name = "HOUR_MIN_SELECT")
    public int hourMinSelect;
    
    /**
    * 有効桁長
    */
    @Basic(optional = false)
    @Column(name = "EFFECTIVE_DIGIT_LENGTH")
    public int effectiveDigitLength;
    
    /**
    * 端数処理
    */
    @Basic(optional = false)
    @Column(name = "ROUND_PROC")
    public int roundProc;
    
    /**
    * 進数選択
    */
    @Basic(optional = false)
    @Column(name = "DECIMAL_SELECT")
    public int decimalSelect;
    
    /**
    * 固定値の値
    */
    @Basic(optional = false)
    @Column(name = "VALUE_OF_FIXED_VALUE")
    public String valueOfFixedValue;
    
    /**
    * 有効桁数開始桁
    */
    @Basic(optional = false)
    @Column(name = "START_DIGIT")
    public int startDigit;
    
    /**
    * 有効桁数終了桁
    */
    @Basic(optional = false)
    @Column(name = "END_DIGIT")
    public int endDigit;
    
    /**
    * 端数処理区分
    */
    @Basic(optional = false)
    @Column(name = "ROUND_PROC_CLS")
    public int roundProcCls;
    
    @Override
    protected Object getKey()
    {
        return insTimeDatFmSetPk;
    }
}
