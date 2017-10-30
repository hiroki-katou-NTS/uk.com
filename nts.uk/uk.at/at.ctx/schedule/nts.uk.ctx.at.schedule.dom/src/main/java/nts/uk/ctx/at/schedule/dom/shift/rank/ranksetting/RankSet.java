package nts.uk.ctx.at.schedule.dom.shift.rank.ranksetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.schedule.dom.shift.rank.RankCode;

/**
 * ランク設定
 * 
 * @author sonnh1
 *
 */
@Getter
@AllArgsConstructor
public class RankSet extends AggregateRoot {
	/**
	 * ランクコード
	 */
	private RankCode rankCode;
	/**
	 * 社員ID
	 */
	private String sId;

	public static RankSet createFromJavaType(String rankCode, String sId) {
		return new RankSet(new RankCode(rankCode), sId);
	}
}
