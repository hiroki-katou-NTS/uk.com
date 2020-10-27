package nts.uk.ctx.at.record.infra.entity.monthlyclosureupdatelog;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
 * @author HungTT
 *
 */

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtMcloseTargetPk {

	// 社員ID
	@Column(name = "SID")
	public String employeeId;

	// 月締め更新ログID
	@Column(name = "MONTH_CLOSE_UPD_LOG_ID")
	public String monthlyClosureUpdateLogId;

}
