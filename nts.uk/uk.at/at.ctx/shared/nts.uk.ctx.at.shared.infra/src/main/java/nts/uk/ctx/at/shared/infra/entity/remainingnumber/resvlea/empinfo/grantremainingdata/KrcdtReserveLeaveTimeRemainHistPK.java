package nts.uk.ctx.at.shared.infra.entity.remainingnumber.resvlea.empinfo.grantremainingdata;

import java.io.Serializable;
/**
 * 
 * @author phongtq
 *
 */

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KrcdtReserveLeaveTimeRemainHistPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "SID")
	public String sid;

	// 付与処理日
	@Column(name = "GRANT_PROC_DATE")
	public GeneralDate grantProcessDate;
	
	@Column(name = "GRANT_DATE")
	public GeneralDate grantDate;
}
