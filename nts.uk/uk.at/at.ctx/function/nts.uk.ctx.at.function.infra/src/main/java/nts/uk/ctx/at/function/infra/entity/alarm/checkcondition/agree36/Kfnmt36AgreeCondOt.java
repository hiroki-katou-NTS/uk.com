package nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.agree36;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.KfnmtAlarmCheckConditionCategory;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KFNMT_36AGREE_COND_OT")
public class Kfnmt36AgreeCondOt extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public Kfnmt36AgreeCondOtPK kfnmt36AgreeCondOtPK;

	/** 36超過時間 */
	@Column(name = "OVERTIME_36")
	public BigDecimal ot36;

	/** 36超過回数 */
	@Column(name = "EXCESS_NUM_36")
	public int excessNum;

	/** 表示するメッセージ */
	@Column(name = "MESSAGE_DISP")
	public String messageDisp;

	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "CD", referencedColumnName = "CD", insertable = false, updatable = false),
		@JoinColumn(name = "CATEGORY", referencedColumnName = "CATEGORY", insertable = false, updatable = false),
		@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false)
	})
	public KfnmtAlarmCheckConditionCategory condition;

	@Override
	protected Object getKey() {
		return null;
	}

}
