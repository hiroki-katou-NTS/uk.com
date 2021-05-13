package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author sonnlb
 *
 *         時間消化使用情報
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TimeDigestionUsageInfor {
	/**
	 * 時間年休使用時間
	 */
	private Integer nenkyuTime = 0;
	/**
	 * 時間代休使用時間
	 */
	private Integer kyukaTime = 0;
	/**
	 * 60H超休使用時間
	 */
	private Integer hChoukyuTime = 0;
	/**
	 * 子の看護使用時間
	 */
	private Integer childCareTime = 0;
	/**
	 * 介護使用時間
	 */
	private Integer longCareTime = 0;
}
