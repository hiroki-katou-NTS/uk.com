package nts.uk.ctx.at.record.infra.entity.remainingnumber;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KrcmtSpecialLeaveReamPK{
	

	/**
	 * @author laitv
	 */
	@Column(name = "SPECIAL_LEAVE_ID")
	public String specialLeaID;

}
