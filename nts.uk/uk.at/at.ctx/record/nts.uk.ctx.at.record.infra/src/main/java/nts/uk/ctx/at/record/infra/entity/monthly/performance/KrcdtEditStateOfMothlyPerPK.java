package nts.uk.ctx.at.record.infra.entity.monthly.performance;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtEditStateOfMothlyPerPK implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/** 社員ID */
	@Column(name = "SID")
	public String employeeID;
	
	/** 処理年月 */
	@Column(name = "YM")
	public int processDate;
	
	/** 締めID */
	@Column(name = "CLOSURE_ID")
	public int closureID;
	
	/** 締め日 */
	@Column(name = "CLOSURE_DAY")
	public Integer closeDay;

	/** 末日とする */
	@Column(name = "IS_LAST_DAY")
	public Integer isLastDay;
	
	/** 勤怠項目ID */
	@Column(name = "ATTENDANCE_ITEM_ID")
	public Integer attendanceItemID;
}
