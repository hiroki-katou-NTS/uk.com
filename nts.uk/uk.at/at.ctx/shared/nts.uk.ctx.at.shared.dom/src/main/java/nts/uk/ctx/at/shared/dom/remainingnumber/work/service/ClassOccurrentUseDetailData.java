package nts.uk.ctx.at.shared.dom.remainingnumber.work.service;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.remainingnumber.work.InforFormerRemainData;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.OccurrenceUseDetail;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;

public interface ClassOccurrentUseDetailData {
	/**
	 * 分類を指定して発生使用明細を取得する
	 * @param inforData
	 * @return
	 */
	Optional<OccurrenceUseDetail> getOccurrenceUseDetail(InforFormerRemainData inforData, WorkTypeClassification workTypeClass);
}
