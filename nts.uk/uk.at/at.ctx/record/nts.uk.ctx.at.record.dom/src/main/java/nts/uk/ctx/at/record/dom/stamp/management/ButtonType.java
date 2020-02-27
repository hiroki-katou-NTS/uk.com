package nts.uk.ctx.at.record.dom.stamp.management;

import lombok.Getter;

/**
 * ボタン種類
 * @author phongtq
 *
 */
public class ButtonType {
	
	/** 予約区分 */
	@Getter
	private ReservationArt textColor;
	
	/** 打刻種類 */
	@Getter
	private StampType stampType;

}
