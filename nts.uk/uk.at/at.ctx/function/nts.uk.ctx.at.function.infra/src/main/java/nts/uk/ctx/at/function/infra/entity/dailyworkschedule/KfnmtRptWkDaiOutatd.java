package nts.uk.ctx.at.function.infra.entity.dailyworkschedule;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The class KfnmtRptWkDaiOutnote
 * @author LienPTK
 *
 */
@Getter
@Setter
@Entity
@Table(name="KFNMT_RPT_WK_DAI_OUTATD")
@NoArgsConstructor
public class KfnmtRptWkDaiOutatd extends UkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@EmbeddedId
	private KfnmtRptWkDaiOutatdPK id;

	/** The cid. */
	@Column(name="CID")
	private String cid;
	
	/** The atd display. */
	@Column(name="ATD_DISPLAY")
	private BigDecimal atdDisplay;

	/** The contract cd. */
	@Column(name="CONTRACT_CD")
	private String contractCd;
	
	/** The exclus ver. */
	@Version
	@Column(name = "EXCLUS_VER")
	public int exclusVer;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="LAYOUT_ID", referencedColumnName="LAYOUT_ID", insertable = false, updatable = false)
	public KfnmtRptWkDaiOutItem kfnmtRptWkDaiOutItem;

	@Override
	protected Object getKey() {
		return this.id;
	}
}
