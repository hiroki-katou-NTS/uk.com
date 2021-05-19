package nts.uk.ctx.at.record.dom.workrecord.errorsetting.algorithm;

import javax.ejb.Stateless;

/**
 * 該当する勤怠項目を加減算する
 *
 */
@Stateless
public class AddOrSubAttendanceItem {
	
	
	public Double getValue() {
		Double value = 0.0;
		// 加算する勤怠項目一覧から入力パラメータ「実績一覧．勤怠項目ID」を絞りこむ (filter input parameter 「実績一覧．勤怠項目ID」 từ list item chuyên cần cộng)
		
		// 減算する勤怠項目一覧から入力パラメータ「実績一覧．勤怠項目ID」を絞りこむ (filer input parameter 「実績一覧．勤怠項目ID」 từ list item chuyên cần trừ)
		
		// しぼり込んだ勤怠項目がいずれか存在するかチェックする(check xem từng utem chuyên cần đã filter có tồn tại hay k?)
		
		// いずれも0件の場合
		if (false) {
			return 0.0;
		}
		
		// いずれか1件以上の場合
		// 加算する勤怠項目の値を集計する(thống kê value item chuyên cần cộng)
		
		// 減算する勤怠項目の値を集計する (thống kê value của item chuyên cần trừ)
		
		// 集計値＝「加算する勤怠項目の集計値－減算する勤怠項目の集計値」 (value thống kê = 「加算する勤怠項目の集計値－減算する勤怠項目の集計値」)
		
		return value;
	}
}
