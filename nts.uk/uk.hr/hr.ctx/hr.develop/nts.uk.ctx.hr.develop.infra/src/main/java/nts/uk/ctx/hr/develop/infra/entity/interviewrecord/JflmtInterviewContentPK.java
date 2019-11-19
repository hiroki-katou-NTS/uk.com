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
public class JflmtInterviewContentPK implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Column(name = "INTERVIEW_ITEM_ID")
	public String interviewItemID;
}
