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
* 日付型データ形式設定
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OIOMT_DATE_DATA_FORM_SET")
public class OiomtDateDataFormSet extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    @Version
    @Column(name = "EXCLUS_VER")
    public Long version;
    
    /**
    * ID
    */
    @EmbeddedId
    public OiomtDateDataFormSetPk dateDataFormSetPk;
    
    /**
    * 固定値
    */
    @Basic(optional = false)
    @Column(name = "FIXED_VALUE")
    public int fixedValue;
    
    /**
    * 固定値の値
    */
    @Basic(optional = false)
    @Column(name = "VALUE_OF_FIXED_VALUE")
    public String valueOfFixedValue;
    
    /**
    * 形式選択
    */
    @Basic(optional = false)
    @Column(name = "FORMAT_SELECTION")
    public int formatSelection;
    
    @Override
    protected Object getKey()
    {
        return dateDataFormSetPk;
    }
}
