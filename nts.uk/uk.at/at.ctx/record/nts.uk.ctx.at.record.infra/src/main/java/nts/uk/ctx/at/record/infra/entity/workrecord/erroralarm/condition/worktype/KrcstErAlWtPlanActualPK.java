/**
 * 5:14:10 PM Dec 5, 2017
 */
package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.worktype;

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
public class KrcstErAlWtPlanActualPK implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Column(name ="ERAL_CHECK_ID")
	public String eralCheckId;
	
	@NotNull
	@Column(name ="WORKTYPE_CD")
	public String workTypeCode;
	
}
