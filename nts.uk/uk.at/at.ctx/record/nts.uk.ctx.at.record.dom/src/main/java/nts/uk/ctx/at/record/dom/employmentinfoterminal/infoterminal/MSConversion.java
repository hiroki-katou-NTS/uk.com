package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author thanh_nx
 *
 *         ｍsの変換
 */
@AllArgsConstructor
@Getter
public class MSConversion {

	// 打刻分類
	private StampClassifi stampClassifi;

	// 反映先
	private StampDestination stampDestination;

}
