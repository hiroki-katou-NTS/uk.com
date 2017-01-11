package nts.uk.ctx.basic.infra.entity.payclassification;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QmnmtPayClassPK implements Serializable{

	/**
	 * serialVersionUID
	 */
	public static final long serialVersionUID = 1L;
	
	@Basic(optional = false)
	@Column(name ="CCD")
	public String companyCd;
	
	@Basic(optional = false)
	@Column(name ="PAYCLASS_CD")
	public String payClassCode;

	@Basic(optional = false)
	@Column(name ="STR_D")
	public String startDate;
	
	@Basic(optional = false)
	@Column(name ="HIST_ID")
	public String historyID;
}
