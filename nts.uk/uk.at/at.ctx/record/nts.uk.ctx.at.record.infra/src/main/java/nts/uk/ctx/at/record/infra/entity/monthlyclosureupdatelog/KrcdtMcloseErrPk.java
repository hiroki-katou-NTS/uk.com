package nts.uk.ctx.at.record.infra.entity.monthlyclosureupdatelog;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author HungTT
 *
 */

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtMcloseErrPk {

	// 社員ID
	@Column(name = "SID")
	public String employeeId;

	// 月締め更新ログID
	@Column(name = "MONTH_CLOSE_UPD_LOG_ID")
	public String monthlyClosureUpdateLogId;

	// 実締め終了日
	@Column(name = "ACTUAL_CLOSURE_END")
	public GeneralDate actualClosureEndDate;

	// リソースID
	@Column(name = "RESOURCE_ID")
	public String resourceId;
	
}
