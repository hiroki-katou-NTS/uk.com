package nts.uk.ctx.at.function.dom.adapter.reservation.bento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class BentoMenuImport {
	
	/**
	 * 枠番
	 */
	private int frameNo;
	
	/**
	 * 弁当名
	 */
	private String name;
	
	/**
	 * 金額１
	 */
	private int amount1;
	
	/**
	 * 金額２
	 */
	private int amount2;
	
	/**
	 * 単位
	 */
	private String unit;
	
	/**
	 * 締め時刻1で予約可能
	 */
	private boolean reservationTime1Atr;
	
	/**
	 * 締め時刻2で予約可能
	 */
	private boolean reservationTime2Atr;
}
