package nts.uk.ctx.at.shared.infra.entity.remainingnumber.annlea;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
/**
 * 
 * @author phongtq
 * 年休付与時点残数履歴データ
 */

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KrcdtAnnLeaTimeRemainHistPK implements Serializable{
	
	/** 社員ID */
	@Column(name = "SID")
	public String sid;
	
	/** 付与処理日 */
	@Column(name = "GRANT_PROC_DATE")
	public GeneralDate grantProcessDate;

	/** 付与日 */
	@Column(name = "GRANT_DATE")
	public GeneralDate grantDate;
	
	private static final long serialVersionUID = 1L;

}
