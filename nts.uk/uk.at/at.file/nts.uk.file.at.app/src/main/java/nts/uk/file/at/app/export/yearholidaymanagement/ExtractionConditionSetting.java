package nts.uk.file.at.app.export.yearholidaymanagement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 抽出条件_設定
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExtractionConditionSetting {
	// 日数
	private int days; 
	// 比較条件
	private ComparisonConditions comparisonConditions;
}
