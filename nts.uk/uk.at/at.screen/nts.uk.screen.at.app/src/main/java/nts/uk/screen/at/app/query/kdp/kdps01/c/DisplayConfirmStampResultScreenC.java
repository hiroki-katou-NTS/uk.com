package nts.uk.screen.at.app.query.kdp.kdps01.c;

import javax.ejb.Stateless;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.screen.at.app.query.kdp.kdps01.b.DisplayConfirmStampResultDto;

/**
 * 
 * @author sonnlb
 *         UKDesign.UniversalK.就業.KDP_打刻.KDPS01_打刻入力(スマホ).C:打刻結果確認実績.メニュー別OCD.打刻結果(スマホ)の確認及び実績の確認画面を表示する
 */
@Stateless
public class DisplayConfirmStampResultScreenC {
	/**
	 * 打刻結果(スマホ)の確認及び実績の確認画面を取得する
	 * 
	 * @param 打刻日時
	 * @param 反映対象日
	 * @param 表示項目一覧(勤怠項目ID(リスト))
	 * @return ドメインモデル：社員の打刻情報
	 * @return 日の実績の確認状況
	 * @return 表示可能項目＜勤怠項目ID、名称、属性、PrimitiveValue＞
	 * @return 実績値＜勤怠項目ID、値、年月日＞
	 * @return 勤務種類名 ←勤務種類.表示名
	 * @return 就業時間帯名 ←就業時間帯の設定.表示名
	 */
	public DisplayConfirmStampResultDto getStampInfoResult(DatePeriod period) {

		return null;
	}
}
