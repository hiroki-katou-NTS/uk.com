package nts.uk.screen.at.app.query.kdp.kdps01.b;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.EmployeeStampInfo;

/**
 * 
 * @author sonnlb
 *
 */
@Data
@AllArgsConstructor
public class DisplayConfirmStampResultDto {
	/**
	 * ドメインモデル：社員の打刻情報
	 */
	List<EmployeeStampInfo> empDatas;
	/**
	 * 勤務場所名
	 */
	private String workLocationName;

}
