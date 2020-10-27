package nts.uk.ctx.at.schedule.infra.entity.shift.businesscalendar.specificdate;

import java.io.Serializable;
//import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KscmtSpecDateItemPK implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "CID")
	public String companyId;
	
	@Column(name = "SPECIFIC_DATE_ITEM_NO")
	public Integer itemNo;

}
