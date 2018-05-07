package nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.agree36;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
/**
 * 
 * @author yennth
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "KFNMT_36AGREE_COND_ERR")
public class Kfnmt36AgreeCondErr extends UkJpaEntity implements Serializable{
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

	@Override
	protected Object getKey() {
		return kfnmt36AgreeCondErrPK;
	}
	
}
