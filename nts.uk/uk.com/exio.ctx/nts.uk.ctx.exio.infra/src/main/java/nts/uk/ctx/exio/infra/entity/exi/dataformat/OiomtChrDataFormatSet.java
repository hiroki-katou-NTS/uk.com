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
* 文字型データ形式設定
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OIOMT_CHR_DATA_FORMAT_SET")
public class OiomtChrDataFormatSet extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    @Version
    @Column(name = "EXCLUS_VER")
    public Long version;
    
    /**
    * ID
    */
    @EmbeddedId
    public OiomtChrDataFormatSetPk chrDataFormatSetPk;
    
    /**
    * コード編集
    */
    @Basic(optional = false)
    @Column(name = "CD_EDITING")
    public int cdEditing;
    
    /**
    * 固定値
    */
    @Basic(optional = false)
    @Column(name = "FIXED_VALUE")
    public int fixedValue;
    
    /**
    * 有効桁長
    */
    @Basic(optional = false)
    @Column(name = "EFFECTIVE_DIGIT_LENGTH")
    public int effectiveDigitLength;
    
    /**
    * コード変換コード
    */
    @Basic(optional = false)
    @Column(name = "CD_CONVERT_CD")
    public int cdConvertCd;
    
    /**
    * コード編集方法
    */
    @Basic(optional = false)
    @Column(name = "CD_EDIT_METHOD")
    public int cdEditMethod;
    
    /**
    * コード編集桁
    */
    @Basic(optional = false)
    @Column(name = "CD_EDIT_DIGIT")
    public int cdEditDigit;
    
    /**
    * 固定値の値
    */
    @Basic(optional = false)
    @Column(name = "FIXED_VAL")
    public String fixedVal;
    
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
    
    @Override
    protected Object getKey()
    {
        return chrDataFormatSetPk;
    }
}
