package nts.uk.ctx.at.function.infra.entity.dailyworkschedule;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

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

	@Override
	protected Object getKey() {
		return this.id;
	}
}
