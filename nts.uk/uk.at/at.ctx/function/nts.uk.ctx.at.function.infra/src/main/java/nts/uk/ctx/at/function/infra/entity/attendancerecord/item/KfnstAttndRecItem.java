package nts.uk.ctx.at.function.infra.entity.attendancerecord.item;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;


/**
 * The persistent class for the KFNST_ATTND_REC_ITEM database table.
 * 
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="KFNST_ATTND_REC_ITEM")
public class KfnstAttndRecItem extends ContractUkJpaEntity implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The record item id. */
	@Id
	@Column(name="RECORD_ITEM_ID")
	private String recordItemId;

	/** The cid. */
	@Column(name="CID")
	private String cid;

	/** The column index. */
	@Column(name="COLUMN_INDEX")
	private long columnIndex;

	/** The export cd. */
	@Column(name="EXPORT_CD")
	private long exportCd;

	/** The formula type. */
	@Column(name="FORMULA_TYPE")
	private BigDecimal formulaType;

	/** The output atr. */
	@Column(name="OUTPUT_ATR")
	private long outputAtr;

	/** The position. */
	@Column(name="[POSITION]")
	private long position;

	/** The time item id. */
	@Column(name="TIME_ITEM_ID")
	private long timeItemId;

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.recordItemId;
	}

}