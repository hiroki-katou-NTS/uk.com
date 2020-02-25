package nts.uk.ctx.at.schedule.dom.employeeinfo.rank;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.objecttype.DomainAggregate;

/**
 * ランク
 * 
 * @author sonnh1
 *
 */
@Getter
public class Rank implements DomainAggregate {
	/**
	 * 会社ID
	 */
	private final String companyId;
	/**
	 * コード
	 */
	private final RankCode rankCode;
	/**
	 * 記号
	 */
	@Setter
	private RankSymbol rankSymbol;

	public Rank(String companyId, RankCode rankCode, RankSymbol rankSymbol) {
		super();
		this.companyId = companyId;
		this.rankCode = rankCode;
		this.rankSymbol = rankSymbol;
	}

}
