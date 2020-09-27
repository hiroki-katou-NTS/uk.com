/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.infra.entity.dailyworkschedule;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;


/**
 * The Class KfnmtPrintRemarkCont.
 * @author HoangDD
 */
@Entity
@Table(name="KFNMT_RPT_WK_DAI_OUTNOTE")
@Getter
@Setter
@NoArgsConstructor
public class KfnmtRptWkDaiOutnote extends UkJpaEntity implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@EmbeddedId
	private KfnmtRptWkDaiOutnotePK id;
	
	/** The cid. */
	@Column(name="CID")
	private String cid;

	/** The use cls. */
	@Column(name="USE_CLS")
	private BigDecimal useCls;

	/** The contract cd. */
	@Column(name="CONTRACT_CD")
	private String contractCd;
	
	/** The exclus ver. */
	@Version
	@Column(name = "EXCLUS_VER")
	public int exclusVer;

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.id;
	}

}