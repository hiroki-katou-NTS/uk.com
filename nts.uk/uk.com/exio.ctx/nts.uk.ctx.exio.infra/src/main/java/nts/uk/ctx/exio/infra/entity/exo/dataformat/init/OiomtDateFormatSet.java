package nts.uk.ctx.exio.infra.entity.exo.dataformat.init;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.exio.dom.exo.base.ItemType;
import nts.uk.ctx.exio.dom.exo.dataformat.init.DateFormatSet;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 日付型データ形式設定
*/
@NoArgsConstructor
@Entity
@Table(name = "OIOMT_DATE_FORMAT_SET")
public class OiomtDateFormatSet extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public OiomtDateFormatSetPk dateFormatSetPk;
    
    
    /**
    * NULL値置換
    */
    @Basic(optional = false)
    @Column(name = "NULL_VALUE_SUBSTITUTION")
    public int nullValueSubstitution;
    
    /**
    * 固定値
    */
    @Basic(optional = false)
    @Column(name = "FIXED_VALUE")
    public int fixedValue;
    
    /**
    * 固定値の値
    */
    @Basic(optional = true)
    @Column(name = "VALUE_OF_FIXED_VALUE")
    public String valueOfFixedValue;
    
    /**
    * 小数桁
    */
    @Basic(optional = true)
    @Column(name = "VALUE_OF_NULL_VALUE_SUBS")
    public String valueOfNullValueSubs;
    
    /**
    * 形式選択
    */
    @Basic(optional = false)
    @Column(name = "FORMAT_SELECTION")
    public int formatSelection;
    
    @Override
    protected Object getKey()
    {
        return dateFormatSetPk;
    }

    public DateFormatSet toDomain() {
        return new DateFormatSet(ItemType.DATE.value,this.dateFormatSetPk.cid, this.nullValueSubstitution, this.fixedValue, this.valueOfFixedValue, this.valueOfNullValueSubs, this.formatSelection);
    }
    public static OiomtDateFormatSet toEntity(DateFormatSet domain) {
        return new OiomtDateFormatSet(
        		new OiomtDateFormatSetPk(domain.getCid()),
        		domain.getNullValueReplace().value,
        		domain.getFixedValue().value,
        		domain.getValueOfFixedValue().isPresent() ? domain.getValueOfFixedValue().get().v() : null,
        		domain.getValueOfNullValueReplace().isPresent() ? domain.getValueOfNullValueReplace().get().v() : null,
        		domain.getFormatSelection().value);
    }

	public OiomtDateFormatSet(OiomtDateFormatSetPk dateFormatSetPk, int nullValueSubstitution,
			int fixedValue, String valueOfFixedValue, String valueOfNullValueSubs, int formatSelection) {
		super();
		this.dateFormatSetPk = dateFormatSetPk;
		this.nullValueSubstitution = nullValueSubstitution;
		this.fixedValue = fixedValue;
		this.valueOfFixedValue = valueOfFixedValue;
		this.valueOfNullValueSubs = valueOfNullValueSubs;
		this.formatSelection = formatSelection;
	}

}
