package nts.uk.ctx.at.request.infra.entity.setting.company.request.applicationsetting.apptypesetting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KrqstDisplayReasonPK implements Serializable{
	private static final long serialVersionUID = 1L;
	/** 会社ID **/
	@Column(name = "CID")
	public String companyId;
	
	/** 休暇申請の種類 **/
	@Column(name = "TYPE_OF_LEAVE_APP")
	public int typeOfLeaveApp;
}
