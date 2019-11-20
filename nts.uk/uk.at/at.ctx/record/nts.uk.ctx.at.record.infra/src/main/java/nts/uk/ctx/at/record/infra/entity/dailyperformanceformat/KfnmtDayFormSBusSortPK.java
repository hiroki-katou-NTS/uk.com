package nts.uk.ctx.at.record.infra.entity.dailyperformanceformat;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
/**
 * 
 * @author anhdt
 *
 */

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KfnmtDayFormSBusSortPK implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 会社ID */
	@Column(name = "CID")
	public String companyId;
	
	/** 勤怠項目ID */
	@Column(name = "ATTENDANCE_ITEM_ID")
	public BigDecimal attendanceItemId;
}
