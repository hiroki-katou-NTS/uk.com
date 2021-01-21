package nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.displaysetting;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode
public class KscmtSyacndDispCtlQuaPk implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/** 会社ID */
	@Basic(optional = false)
	@NotNull
	@Column(name = "CID")
	public String cid;

	@Basic(optional = false)
	@NotNull
	@Column(name = "QUALIFICATION_CD")
	public String qualification;

}
