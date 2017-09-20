package entity.person.info.setting;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BPSST_PER_INIT_SET_ITEM")
public class BpsstPersonInitValueSettingItem extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public BpsstPersonInitValueSettingItemPk bpsstPersonInitValueSettingItemPk;

	@Basic(optional = false)
	@Column(name = "PER_INIT_SET_CTG_ID")
	public String personUnitValueSettingCtgId;

	@Basic(optional = false)
	@Column(name = "REFERENCE_METHOD")
	public int referenceMethod;
	
	@Basic(optional = true)
	@Column(name = "SAVE_DATA_TYPE")
	public int saveDataType;
	
	@Basic(optional = true)
	@Column(name = "STRING_VAL")
	public String stringValue;
	
	@Basic(optional = true)
	@Column(name = "INT_VAL")
	public int intValue;
	
	@Basic(optional = true)
	@Column(name = "DATE_VAL")
	public int dateValue;
	
	
	@Override
	protected Object getKey() {
		return bpsstPersonInitValueSettingItemPk;
	}

}
