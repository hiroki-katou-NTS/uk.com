package nts.uk.ctx.at.record.infra.entity.monthly.performance;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtEditStateOfMothlyPerPK implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/** 社員ID */
	@Column(name = "SID")
	public String employeeID;
	
	/** 勤怠項目ID */
	@Column(name = "ATTENDANCE_ITEM_ID")
	public Integer attendanceItemID;
	
	/** 期間 - start */
	@Column(name = "STR_YMD")
	public GeneralDate startYmd;
	
	/** 期間 - end */
	@Column(name = "END_YMD")
	public GeneralDate endYmd;
}
