package nts.uk.ctx.at.request.infra.entity.application.holidayshipment.subdigestion;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 消化対象振休管理PK
 * 
 * @author sonnlb
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class KrqdtSubDigestionPK implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	/**
	 * 振休申請ID
	 */
	@Basic(optional = false)
	@Column(name = "ABSENCE_LEAVE_APP_ID")
	private String absenceLeaveAppID;

}
