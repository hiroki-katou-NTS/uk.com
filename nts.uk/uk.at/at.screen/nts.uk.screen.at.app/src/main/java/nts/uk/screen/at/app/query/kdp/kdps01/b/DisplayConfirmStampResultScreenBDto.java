package nts.uk.screen.at.app.query.kdp.kdps01.b;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeRecordImport;
import nts.uk.screen.at.app.query.kdp.kdp001.a.EmployeeStampInfoDto;

/**
 * 
 * @author sonnlb
 *
 */
@Data
@AllArgsConstructor
public class DisplayConfirmStampResultScreenBDto {
	/**
	 * ドメインモデル：社員の打刻情報
	 */
	private List<EmployeeStampInfoDto> empDatas;

	/**
	 * 打刻場所コード
	 */
	private String workLocationCd;
	/**
	 * 勤務場所名
	 */
	private String workLocationName;
	
	private EmployeeRecordImport empInfo;
	
	/**
	 * 職場コード
	 */
	private String workplaceCd;
	
	/**
	 * 職場名称
	 */
	private String workplaceName;
	
}
