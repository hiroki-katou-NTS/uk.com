package nts.uk.ctx.basic.infra.entity.position;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class CmnmtJobTittlePK implements Serializable{

	/**
	 * serialVersionUID
	 */
	public static final long serialVersionUID = 1L;
	
	@Basic(optional = false)
	@Column(name ="CCD")
	public String companyCd;
	
	@Basic(optional = false)
	@Column(name ="JOBCD")
	public String jobCode;

	@Basic(optional = false)
	@Column(name ="HIST_ID")
	public String historyID;
	
}
