package nts.uk.screen.at.app.monthlyperformance.correction.dto;

import lombok.Data;
/**
 * 月別実績の修正
 */
@Data
public class CorrectionOfMonthlyPerformance {
	/**
	 * カーソルの移動方向
	 */
	private String cursorMovementDirection;
	/**
	 * ゼロを表示する
	 */
	private boolean display;
	/**
	 * グリッドヘッダに項目番号を表示する
	 */
	private boolean displayItemNumberInGridHeader;
	/**
	 * 個人プロフィール列を表示する
	 */
	private boolean displayThePersonalProfileColumn;
	/**
	 * 前回の表示項目
	 */
	private String previousDisplayItem;
}
