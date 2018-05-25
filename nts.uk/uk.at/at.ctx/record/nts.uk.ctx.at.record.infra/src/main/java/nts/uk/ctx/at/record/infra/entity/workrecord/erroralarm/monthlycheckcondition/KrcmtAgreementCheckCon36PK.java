package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.monthlycheckcondition;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class KrcmtAgreementCheckCon36PK implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "ERAL_CHECK_ID")
	public String errorAlarmCheckID;
	
	@Column(name = "CLASSIFICATION")
	public int classification;
}
