package nts.uk.ctx.at.schedule.app.find.shift.rank.ranksetting;

import lombok.Value;
import nts.uk.ctx.at.schedule.dom.shift.rank.ranksetting.RankSet;

/**
 * 
 * @author Trung Tran
 *
 */
@Value
public class RankSetDto {
	/**
	 * employee Id
	 */
	String sId;
	/**
	 * rankCode
	 */
	String rankCode;

	public static RankSetDto fromDomain(RankSet rankSet) {
		return new RankSetDto(rankSet.getSId(), rankSet.getRankCode().v());
	}
}
