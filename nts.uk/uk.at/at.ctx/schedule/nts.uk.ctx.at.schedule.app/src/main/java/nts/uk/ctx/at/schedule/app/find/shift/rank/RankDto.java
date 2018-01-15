package nts.uk.ctx.at.schedule.app.find.shift.rank;

import lombok.Value;
import nts.uk.ctx.at.schedule.dom.shift.rank.Rank;
/**
 * 
 * @author Trung Tran
 *
 */
@Value
public class RankDto {
	private String companyId;
	private String rankCode;
	private String rankMemo;
	private int displayOrder;

	public static RankDto fromDomain(Rank rank) {
		return new RankDto(rank.getCompanyId(), rank.getRankCode().v(), rank.getRankMemo().v(), rank.getDisplayOrder());
	}
}
