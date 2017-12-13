package nts.uk.ctx.at.record.infra.entity.dailyperformanceformat.businesstype;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KrcmtBusinessTypeOfEmployeePK implements Serializable{
	private static final long serialVersionUID = 1L;

	/** 履歴ID */
	@Column(name = "HIST_ID")
	public String historyId;
	
	
}
