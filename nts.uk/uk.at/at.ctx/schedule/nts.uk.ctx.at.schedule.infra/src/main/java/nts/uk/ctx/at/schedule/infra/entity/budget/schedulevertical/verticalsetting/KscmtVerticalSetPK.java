package nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KscmtVerticalSetPK implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/*会社ID*/
	@Column(name = "CID")
	public String companyId;
	
	/* コード */
	@Column(name = "VERTICAL_CAL_CD")
	public String verticalCalCd;
}
