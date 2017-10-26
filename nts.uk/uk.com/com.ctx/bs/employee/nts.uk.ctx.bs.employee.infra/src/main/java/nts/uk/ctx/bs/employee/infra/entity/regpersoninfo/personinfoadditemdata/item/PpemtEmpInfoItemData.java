package nts.uk.ctx.bs.employee.infra.entity.regpersoninfo.personinfoadditemdata.item;

import java.io.Serializable;
import java.math.BigDecimal;

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
public class PpemtEmpInfoItemData extends JpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public PpemtEmpInfoItemDataPk ppemtEmpInfoItemDataPk;

	@Basic(optional = false)
	@Column(name = "SAVE_DATA_ATR")
	public int saveDataType;

	@Basic(optional = false)
	@Column(name = "STRING_VAL")
	public String stringValue;

	@Basic(optional = false)
	@Column(name = "INT_VAL")
	public BigDecimal intValue;

	@Basic(optional = false)
	@Column(name = "DATE_VAL")
	public GeneralDate dateValue;

	@Override
	protected Object getKey() {

		return ppemtEmpInfoItemDataPk;
	}

}
