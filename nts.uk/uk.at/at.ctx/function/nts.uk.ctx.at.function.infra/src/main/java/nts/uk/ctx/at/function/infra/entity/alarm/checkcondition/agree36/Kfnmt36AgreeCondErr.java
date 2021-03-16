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
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.AgreeConditionError;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.KfnmtAlarmCheckConditionCategory;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
/**
 * 
 * @author yennth
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "KFNMT_ALST_CHK_36AGR_ERAL")
public class Kfnmt36AgreeCondErr extends ContractUkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public Kfnmt36AgreeCondErrPK kfnmt36AgreeCondErrPK;

	/** 使用区分 */
	@Column(name = "USE_ATR")
	public int useAtr;
	
	/** 期間 */
	@Column(name ="PERIOD_ATR")
	public int period;
	
	/** エラーアラーム */
	@Column(name = "ERROR_ALARM")
	public int errorAlarm;
	
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
		return kfnmt36AgreeCondErrPK;
	}

	public Kfnmt36AgreeCondErr(Kfnmt36AgreeCondErrPK kfnmt36AgreeCondErrPK, int useAtr, int period, int errorAlarm,
			String messageDisp) {
		super();
		this.kfnmt36AgreeCondErrPK = kfnmt36AgreeCondErrPK;
		this.useAtr = useAtr;
		this.period = period;
		this.errorAlarm = errorAlarm;
		this.messageDisp = messageDisp;
	}
	/**
	 * convert from domain to entity
	 * @param domain
	 * @return
	 * @author yennth
	 */
	public static Kfnmt36AgreeCondErr toEntity(AgreeConditionError domain){
		return new Kfnmt36AgreeCondErr(new Kfnmt36AgreeCondErrPK(domain.createId(), domain.getCode().v(), domain.getCompanyId(), domain.getCategory().value), 
										domain.getUseAtr().value, 
										domain.getPeriod().value, 
										domain.getErrorAlarm().value, 
										domain.getMessageDisp().v());
	}
}
