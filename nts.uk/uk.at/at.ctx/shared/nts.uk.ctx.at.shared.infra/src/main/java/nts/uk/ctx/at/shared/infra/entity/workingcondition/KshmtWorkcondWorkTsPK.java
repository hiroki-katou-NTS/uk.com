/**
 * 
 */
package nts.uk.ctx.at.shared.infra.entity.workingcondition;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author laitv
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KshmtWorkcondWorkTsPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The sid. */
	@Column(name = "SID")
	private String sid;
	
	@Column(name = "HIST_ID")
	private String hisId;
	
	@Column(name = "PER_WORK_DAY_ATR")
	private int perWorkDayAtr;
	
}
