package nts.uk.ctx.at.shared.infra.entity.worktype.specialholidayframe;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KshmtSpecialHolidayFramePK implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "CID")
	public String companyId;
	/*特別休暇枠ID*/
	@Column(name = "SPHD_FRAME_NO")
	public int specialHdFrameNo;	

}
