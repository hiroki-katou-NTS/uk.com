package nts.uk.ctx.at.schedule.infra.entity.scheduleitemmanagement;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KscmtScheItemOrderPK implements Serializable {
private static final long serialVersionUID = 1L;
	
	/*会社ID*/
	@Column(name = "CID")
	public String companyId;
	
	/*予定項目ID*/
	@Column(name = "SCHEDULE_ITEM_ID")
	public String scheduleItemId;
}
