package nts.uk.ctx.at.schedule.dom.shift.rank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * ランク
 * 
 * @author sonnh1
 *
 */
@Getter
@AllArgsConstructor
public class Rank extends AggregateRoot {
	/**
	 * 会社ID
	 */
	private String companyId;
	/**
	 * ランクコード
	 */
	private RankCode rankCode;
	/**
	 * ランクメモ
	 */
	private RankMemo rankMemo;
	/**
	 * 並び順
	 */
	private int displayOrder;

	public static Rank convertFromJavaType(String companyId, String rankCode, String rankMemo, int orderBy) {
		return new Rank(companyId, new RankCode(rankCode), new RankMemo(rankMemo), orderBy);
	}
}
