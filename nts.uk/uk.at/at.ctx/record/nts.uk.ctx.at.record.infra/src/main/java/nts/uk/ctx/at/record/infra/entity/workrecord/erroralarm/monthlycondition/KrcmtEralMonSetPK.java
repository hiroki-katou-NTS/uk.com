/**
 * 11:10:43 AM Mar 29, 2018
 */
package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.monthlycondition;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @author hungnm
 *
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KrcmtEralMonSetPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Column(name = "CID")
	public String companyId;
	
	@NotNull
	@Column(name = "ERROR_ALARM_CD")
	public String errorAlarmCode;
	
}
