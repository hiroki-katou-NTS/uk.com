package nts.uk.ctx.hr.develop.infra.entity.interviewrecord;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class JflmtInterviewRecordPK  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	
	/** 面談記録ID --- interviewRecordId**/
	@NotNull
	@Column(name = "INTERVIEW_REC_ID")
	public String interviewRecordId;
}
