package nts.uk.ctx.at.record.pub.reservation.bento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class BentoMenuExport {

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
	 * 受付時間帯NO
	 */
	private int receptionTimezoneNo;
	
	/**
	 * 勤務場所コード
	 */
	private String workLocationCode;
}
