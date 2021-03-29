package nts.uk.ctx.at.function.infra.entity.attendancerecord;

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
 * The persistent class for the KFNST_SEAL_COLUMN database table.
 * 
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "KFNST_SEAL_COLUMN")
public class KfnstSealColumn extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The column id. */
	@Id
	@Column(name = "COLUMN_ID")
	private String columnId;

	/** The cid. */
	@Column(name = "CID")
	private String cid;

	/** The export cd. */
	@Column(name = "EXPORT_CD")
	private BigDecimal exportCd;

	/** The seal stamp name. */
	@Column(name = "SEAL_STAMP_NAME")
	private String sealStampName;

	/** The seal order. */
	@Column(name = "SEAL_ORDER")
	private BigDecimal sealOrder;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.columnId;
	}

}