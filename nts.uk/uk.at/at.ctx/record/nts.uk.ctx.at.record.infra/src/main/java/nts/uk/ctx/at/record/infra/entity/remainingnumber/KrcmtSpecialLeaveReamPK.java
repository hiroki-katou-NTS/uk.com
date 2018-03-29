package nts.uk.ctx.at.record.infra.entity.remainingnumber;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KrcmtSpecialLeaveReamPK implements Serializable{
	

	/**
	 * @author laitv
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "SPECIAL_LEAVE_ID")
	public String specialLeaID;

}
