package nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.申請承認.申請.打刻申請.Imported.勤務場所名称
 * @author LienPTK
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkLocationNameImported {

	/** 場所コード */
	private String workLocationCode;

	/** 場所名称 */
	private String workLocationName;

}
