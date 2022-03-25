package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timedifferencemanagement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;

/**
 * AG: 地域別時差管理
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.打刻管理.地域別時差管理.地域別時差管理
 * 
 * @author chungnt
 *
 */

@AllArgsConstructor
@Getter
public class RegionalTimeDifference implements DomainAggregate {
	
	// コード
	private final RegionCode code;

	// 名称
	private RegionName name;

	// 時差
	private RegionalTime regionalTimeDifference;
}
