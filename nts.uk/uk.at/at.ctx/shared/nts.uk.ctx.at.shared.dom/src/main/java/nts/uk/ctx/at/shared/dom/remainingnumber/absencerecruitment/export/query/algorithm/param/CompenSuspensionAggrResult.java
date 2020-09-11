package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author ThanhNX
 *
 *         振出振休集計結果
 */
@Data
@AllArgsConstructor
public class CompenSuspensionAggrResult {

	// 振休発生日数
	private Double suOccurDay;

	// 振休使用日数
	private Double suDayUse;

}
