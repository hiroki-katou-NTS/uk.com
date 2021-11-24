package nts.uk.ctx.at.request.dom.application.common.adapter.bs;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.WorkplaceNameImported;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;

public interface WorkplaceAdapter {

	/**
	 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.申請承認.申請.打刻申請.Imported.職場IDリスト、基準日から職場情報を取得する
	 *
	 * @param wplIds 職場IDリスト
	 * @param baseDate 基準日
	 * @return List<職場名称Imported>
	 */
	public List<WorkplaceNameImported> findWkpInfo(List<WorkplaceId> wplIds, GeneralDate baseDate);

}
