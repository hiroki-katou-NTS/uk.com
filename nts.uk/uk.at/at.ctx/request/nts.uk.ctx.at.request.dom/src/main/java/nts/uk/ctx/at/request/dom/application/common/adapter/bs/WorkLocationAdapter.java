package nts.uk.ctx.at.request.dom.application.common.adapter.bs;

import java.util.List;

import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.WorkLocationNameImported;

public interface WorkLocationAdapter {

	/**
	 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.申請承認.申請.打刻申請.Imported.コードリストから勤務場所名称を取得する
	 *
	 * @param workLocationCDs List<勤務場所コード>
	 * @return the list
	 */
	public List<WorkLocationNameImported> findWorkLocationName(List<String> workLocationCDs);
}
