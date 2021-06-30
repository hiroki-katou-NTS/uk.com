package nts.uk.screen.at.app.query.kdp.kdps01.c;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.stamp.management.DisplayScreenStampingResultDto;
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeRecordImport;

/**
 * 
 * @author sonnlb 日の実績の確認状況
 * 
 *         ドメインモデル：社員の打刻情報
 * 
 *         日の実績の確認状況
 *
 *         表示可能項目＜勤怠項目ID、名称、属性、PrimitiveValue＞
 * 
 *         実績値＜勤怠項目ID、値、年月日＞
 * 
 *         勤務種類名 ←勤務種類.表示名
 * 
 *         就業時間帯名 ←就業時間帯の設定.表示名
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
public class DisplayConfirmStampResultScreenCDto {
	/**
	 * ドメインモデル：社員の打刻情報
	 */
	private List<DisplayScreenStampingResultDto> stampings;

	/**
	 * 日の実績の確認状況
	 */
	private ConfirmStatusActualResultDto confirmResult;
	/**
	 * 表示可能項目＜勤怠項目ID、名称、属性、PrimitiveValue＞
	 */
	private List<ItemDisplayedDto> lstItemDisplayed;

	/**
	 * 実績値＜勤怠項目ID、値、年月日＞
	 */
	private List<ItemValueDto> itemValues;
	/**
	 * 勤務種類名
	 */
	private String workTypeName;

	/**
	 * 就業時間帯名 ←就業時間帯の設定.表示名
	 */
	private String workTimeName;
	
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
