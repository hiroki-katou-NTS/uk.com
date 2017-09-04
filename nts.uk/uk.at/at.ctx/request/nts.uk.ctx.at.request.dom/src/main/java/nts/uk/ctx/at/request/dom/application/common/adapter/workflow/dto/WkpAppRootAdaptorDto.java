package nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author vunv
 *
 */
@Data
@AllArgsConstructor
public class WkpAppRootAdaptorDto {

	/** 会社ID */
	private String companyId;
	/** 承認ID */
	public String approvalId;
	/** 職場ID */
	private String workplaceId;
	/** 履歴ID */
	private String historyId;
	/** 申請種類 */
	private int applicationType;
	/** 開始日 */
	private GeneralDate startDate;
	/** 完了日 */
	private GeneralDate endDate;
	/** 分岐ID */
	private String branchId;
	/** 任意項目申請ID */
	private String anyItemApplicationId;
	/** 確認ルート種類 */
	private int confirmationRootType;
	/** 就業ルート区分 */
	private int employmentRootAtr;
}
