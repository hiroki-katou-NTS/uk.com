package entity.person.info.data.item;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.arc.time.GeneralDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PPEMT_EMP_INFO_ITEM_DATA")
public class BsydtEmpInfoItemData extends JpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public BsydtEmpInfoItemDataPk bsydtEmpInfoItemDataPk;

	@Basic(optional = false)
	@Column(name = "PER_INFO_CTG_ID")
	public String perInfoCtgId;

	@Basic(optional = false)
	@Column(name = "DATA_STATE_ATR")
	public int dataState;

	@Basic(optional = false)
	@Column(name = "STRING_VAL")
	public String stringValue;

	@Basic(optional = false)
	@Column(name = "INT_VAL")
	public int intValue;

	@Basic(optional = false)
	@Column(name = "DATE_VAL")
	public GeneralDate dateValue;

	@Override
	protected Object getKey() {

		return bsydtEmpInfoItemDataPk;
	}

}
