package nts.uk.ctx.at.schedule.infra.entity.workschedule.displaysetting;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * 
 * @author Hieult
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode
public class KscmtDispSettingPK implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	/**会社ID **/ 
	@Basic(optional = false)
	@NotNull
	@Column(name = "CID")
	public String cid;
}
