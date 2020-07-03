package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import lombok.Value;

/**
 * 計算処理の実行内容を切り替えるオプション
 */
@Value
public class CalculateOption {

	/**
	 * trueの場合、「時刻は全てマスタ通り」という前提で計算を行う。
	 * 時刻の手修正が発生しないケースではこれをtrueにしておくと、処理時間が短縮できる。
	 */
	private final boolean isMasterTime;
	
	/**
	 * trueの場合、スケジュールの計算としてみなす。
	 * スケジュールに不要な項目の計算処理をスキップすることで、処理時間が短縮できる。
	 */
	private final boolean isSchedule;
	
	/**
	 * デフォルト、このオプションの導入前と同じ処理になる設定値を返す
	 */
	public static CalculateOption asDefault() {
		return new CalculateOption(false, false);
	}
}
