package nts.uk.ctx.pereg.infra.entity.person.additemdata.item;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PPEMT_EMP_INFO_ITEM_DATA")
// 個人情報項目データ

public class PpemtEmpInfoItemData extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public PpemtEmpInfoItemDataPk ppemtEmpInfoItemDataPk;

	// 保存データ状態

	@Basic(optional = false)
	@Column(name = "SAVE_DATA_ATR")
	public int saveDataType;

	// 文字列

	@Basic(optional = false)
	@Column(name = "STRING_VAL")
	public String stringValue;

	// 数値

	@Basic(optional = false)
	@Column(name = "INT_VAL")
	public BigDecimal intValue;

	// 日付

	@Basic(optional = false)
	@Column(name = "DATE_VAL")
	public GeneralDate dateValue;

	@Override
	protected Object getKey() {

		return ppemtEmpInfoItemDataPk;
	}

}
