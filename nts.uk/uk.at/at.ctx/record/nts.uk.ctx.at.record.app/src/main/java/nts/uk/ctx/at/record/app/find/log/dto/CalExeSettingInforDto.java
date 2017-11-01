package nts.uk.ctx.at.record.app.find.log.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CalExeSettingInforDto {

	/**
	 * 0 : 日別作成
	 * 1 : 日別計算
	 * 2 : 承認結果反映
	 * 3 : 月別集計
	 */
	
	/**
	 *  0 :通常実行
	 *  1 :再実行
	 */
	private int executionType;
	
	/**
	 * executionType : name Japan
	 */
	private String executionTypeName;
}
