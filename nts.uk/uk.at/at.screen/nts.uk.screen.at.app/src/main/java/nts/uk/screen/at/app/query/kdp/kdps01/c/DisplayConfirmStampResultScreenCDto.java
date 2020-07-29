package nts.uk.screen.at.app.query.kdp.kdps01.c;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author sonnlb 日の実績の確認状況
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
public class DisplayConfirmStampResultScreenCDto {
	/**
	 * 勤務種類名
	 */
	private String workTypeName;

	/**
	 * 就業時間帯名 ←就業時間帯の設定.表示名
	 */
	private String workTimeName;
}
