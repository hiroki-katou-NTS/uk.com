package nts.uk.ctx.at.schedule.dom.employeeinfo.rank;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
/**
 * 社員ランク
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.社員情報.ランク.ランク分け
 * @author phongtq
 *
 */
@Getter
public class EmployeeRank implements DomainAggregate {
	/** 社員ID */
	private final String SID;
	
	/** ランクコード */
	private final RankCode emplRankCode;

	public EmployeeRank(String SID, RankCode emplRankCode) {
		super();
		this.SID = SID;
		this.emplRankCode = emplRankCode;
	}
}
