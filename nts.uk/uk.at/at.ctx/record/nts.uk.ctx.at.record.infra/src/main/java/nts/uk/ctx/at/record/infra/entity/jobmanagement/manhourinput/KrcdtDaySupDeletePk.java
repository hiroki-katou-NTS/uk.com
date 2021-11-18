package nts.uk.ctx.at.record.infra.entity.jobmanagement.manhourinput;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author tutt
 *
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KrcdtDaySupDeletePk implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "SID")
	public String sId;
	
	@Column(name = "YMD")
	public GeneralDate ymd;
	
	@Column(name = "NO")
	public int supTaskNo;
}
