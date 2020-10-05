package nts.uk.file.at.app.export.yearholidaymanagement;

import lombok.Data;


/**
 * 抽出条件_設定
 *
 */
@Data
public class ExtractionConditionSetting {
	// 日数
	private int days; 
	// 比較条件
	private ComparisonConditions comparisonConditions;
}
