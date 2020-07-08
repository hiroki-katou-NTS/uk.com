package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.gul.location.GeoCoordinate;

/**
 * VO : 打刻場所情報
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻.打刻場所情報
 * @author tutk
 *
 */
@AllArgsConstructor
public class StampLocationInfor implements DomainValue {
	
	/**
	 * 打刻位置情報
	 */
	@Getter
	private final GeoCoordinate positionInfor;
	
	/**
	 * エリア外の打刻区分
	 */
	@Getter
	private final boolean outsideAreaAtr;
	
}
