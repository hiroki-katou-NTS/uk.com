package nts.uk.ctx.at.function.infra.entity.alarmworkplace.monthdayperiod;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * entity : 月数指定
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KFNMT_ASSIGN_MON_START")
public class KfnmtAssignMonthStart extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KfnmtAssignMonthStartPk pk;

	@Column(name = "CONTRACT_CD")
	public String contractCode;

	@Column(name = "ASSIGN_WAY_MON_START")
	public int specifyStartMonth;

	@Column(name = "NUMOF_MON")
	public int monthNo;

	@Column(name = "THIS_MON")
	public boolean curentMonth;

	@Column(name = "BEFORE_AFTER_ATR")
	public int monthPrevious;

	@Override
	protected Object getKey() {
		return this.pk;
	}

}
