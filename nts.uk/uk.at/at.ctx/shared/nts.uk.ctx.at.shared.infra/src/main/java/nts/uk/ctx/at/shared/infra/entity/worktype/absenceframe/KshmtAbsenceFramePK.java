package nts.uk.ctx.at.shared.infra.entity.worktype.absenceframe;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KshmtAbsenceFramePK implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "CID")
	public String companyId;
	/*欠勤枠ID*/
	@Column(name = "ABSENCE_FRAME_NO")
	public int absenceFrameNo;	
}
