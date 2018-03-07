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
* 数値型データ形式設定
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OIOMT_NUM_DATA_FORMAT_SET")
public class OiomtNumDataFormatSet extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    @Version
    @Column(name = "EXCLUS_VER")
    public Long version;
    
    /**
    * ID
    */
    @EmbeddedId
    public OiomtNumDataFormatSetPk numDataFormatSetPk;
    
    /**
    * 固定値
    */
    @Basic(optional = false)
    @Column(name = "FIXED_VALUE")
    public int fixedValue;
    
    /**
    * 小数区分
    */
    @Basic(optional = false)
    @Column(name = "DECIMAL_DIVISION")
    public int decimalDivision;
    
    /**
    * 有効桁長
    */
    @Basic(optional = false)
    @Column(name = "EFFECTIVE_DIGIT_LENGTH")
    public int effectiveDigitLength;
    
    /**
    * コード変換コード
    */
    @Basic(optional = true)
    @Column(name = "CD_CONVERT_CD")
    public Integer cdConvertCd;
    
    /**
    * 固定値の値
    */
    @Basic(optional = true)
    @Column(name = "VALUE_OF_FIXED_VALUE")
    public String valueOfFixedValue;
    
    /**
    * 少数桁数
    */
    @Basic(optional = true)
    @Column(name = "DECIMAL_DIGIT_NUM")
    public Integer decimalDigitNum;
    
    /**
    * 有効桁数開始桁
    */
    @Basic(optional = true)
    @Column(name = "START_DIGIT")
    public Integer startDigit;
    
    /**
    * 有効桁数終了桁
    */
    @Basic(optional = true)
    @Column(name = "END_DIGIT")
    public Integer endDigit;
    
    /**
    * 小数点区分
    */
    @Basic(optional = true)
    @Column(name = "DECIMAL_POINT_CLS")
    public Integer decimalPointCls;
    
    /**
    * 小数端数
    */
    @Basic(optional = true)
    @Column(name = "DECIMAL_FRACTION")
    public Integer decimalFraction;
    
    @Override
    protected Object getKey()
    {
        return numDataFormatSetPk;
    }
}
