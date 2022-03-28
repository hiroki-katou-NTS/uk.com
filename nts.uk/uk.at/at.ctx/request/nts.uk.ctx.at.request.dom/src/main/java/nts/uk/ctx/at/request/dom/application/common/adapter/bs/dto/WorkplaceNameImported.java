package nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.申請承認.申請.打刻申請.Imported.職場名称
 * 
 * @author LienPTK
 */
@Data
@AllArgsConstructor
public class WorkplaceNameImported {
	
	/** 職場ID */
	private String workplaceId;
	
	/** 職場コード */
	private String wkpCode;
	
	/** 職場名 */
	private String wkpName;
}
