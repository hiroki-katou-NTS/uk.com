package nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.agree36;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.AgreeCondOt;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.KfnmtAlarmCheckConditionCategory;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KFNMT_36AGREE_COND_OT")
public class Kfnmt36AgreeCondOt extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public Kfnmt36AgreeCondOtPK kfnmt36AgreeCondOtPK;

	/** 36超過時間 */
	@Column(name = "OVERTIME_36")
	public int ot36;

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

	public Kfnmt36AgreeCondOt(Kfnmt36AgreeCondOtPK kfnmt36AgreeCondOtPK, int ot36, int excessNum, String messageDisp) {
		super();
		this.kfnmt36AgreeCondOtPK = kfnmt36AgreeCondOtPK;
		this.ot36 = ot36;
		this.excessNum = excessNum;
		this.messageDisp = messageDisp;
	}
	/**
	 * convert from domain to entity
	 * @param domain
	 * @return
	 * @author yennth
	 */
	public static Kfnmt36AgreeCondOt toEnity(AgreeCondOt domain){
		return new Kfnmt36AgreeCondOt(new Kfnmt36AgreeCondOtPK(domain.createId(), domain.getNo(), domain.getCode().v(), domain.getCompanyId(), domain.getCategory().value), 
										domain.getOt36().v(), 
										domain.getExcessNum().v(), 
										domain.getMessageDisp().v());
	}
}
