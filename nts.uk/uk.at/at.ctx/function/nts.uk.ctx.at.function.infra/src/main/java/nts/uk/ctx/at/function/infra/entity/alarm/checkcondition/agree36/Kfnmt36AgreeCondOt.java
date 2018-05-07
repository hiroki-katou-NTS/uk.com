package nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.agree36;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KFNMT_36AGREE_COND_OT")
public class Kfnmt36AgreeCondOt extends UkJpaEntity implements Serializable{
	
	@EmbeddedId
	public Kfnmt36AgreeCondOtPK kfnmt36AgreeCondOtPK;
	
	/** 36超過時間 */
	@Column(name = "36_OVERTIME")
	public BigDecimal ot36;
	
	/** 36超過回数 */
	@Column(name = "36_EXCESS_NUM")
	public int excessNum;
	
	/** 表示するメッセージ */
	@Column(name = "MESSAGE_DISP")
	public String messageDisp;
	
	private static final long serialVersionUID = 1L;

	@Override
	protected Object getKey() {
		return null;
	}

}
