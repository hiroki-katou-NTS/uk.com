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
public class JflmtInterviewAnalysisPK implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@NotNull
	/** 分析情報ID --- analysisInfoId **/
	@Column(name = "ANALYSIS_INFO_ID")
	public String analysisInfoId;
}
