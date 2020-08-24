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
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The persistent class for the KFNMT_RPT_WK_ATD_OUTSEAL database table.
 * 
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "KFNMT_RPT_WK_ATD_OUTSEAL")
public class KfnmtRptWkAtdOutseal extends UkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The column id. */
	@Id
	@Column(name = "COLUMN_ID")
	private String columnId;
	
	/** The exclus ver. */
	@Column(name="EXCLUS_VER")
	private long exclusVer;
	
	/** The contract cd. */
	@Column(name="CONTRACT_CD")
	private String contractCd;

	/** The cid. */
	@Column(name = "CID")
	private String cid;
	
	/** The layout id. */
	@Column(name="LAYOUT_ID")
	private String layoutId;

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