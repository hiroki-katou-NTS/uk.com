package nts.uk.ctx.at.function.infra.entity.dailyperformanceformat;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KrcmtBusinessTypeSMonthlyPK implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column(name = "CID")
	public String companyId;
	
	@Column(name = "BUSINESS_TYPE_CD")
	public String businessTypeCode;
	
	@Column(name = "ATTENDANCE_ITEM_ID")
	public int attendanceItemId;
}
