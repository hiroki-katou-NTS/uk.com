package nts.uk.ctx.at.request.infra.entity.common.application;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "KRQDT_APPLICATION")
@AllArgsConstructor
@NoArgsConstructor
public class KafmtApplication extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public KafmtApplicationPK kafmtApplicationPK;

	/**
	 * 事前事後区分
	 */
	@Column(name="PRE_POST_ATR")
	public int prePostAtr; 

	/**
	 * 事後申請を自動生成する
	 */
	@Column(name="AUTO_POST_APP")
	public int autoPostApplication; 
	
	/**
	 * 入力日
	 */
	@Column(name="INPUT_DATE")
	public BigDecimal inputDate; 
	
	/**
	 * 入力者
	 */
	@Column(name="ENTERED_PERSON_SID")
	public String enteredPersonSID; 
	/**
	 * 差戻し理由
	 */
	@Column(name="REASON_REVERSION")
	public String reversionReason; 
	
	
	@Override
	protected Object getKey() {
		return kafmtApplicationPK;
	}
}
