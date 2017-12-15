/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.worktimeset;

import java.util.List;

import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone;

/**
 * The Interface WorkTimeSettingRepository.
 */
public interface WorkTimeSettingService {

	// 打刻反映時間帯を取得する
	List<StampReflectTimezone> getStampReflectTimezone(String companyId, String workTypeCode);

}
