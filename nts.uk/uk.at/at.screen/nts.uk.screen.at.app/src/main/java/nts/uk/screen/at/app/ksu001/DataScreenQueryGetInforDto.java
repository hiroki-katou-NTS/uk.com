package nts.uk.screen.at.app.ksu001;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

/**
 * @author laitv
 */
@Value
public class DataScreenQueryGetInforDto {
	public GeneralDate startDate; // ・期間 A3_1_2
	public GeneralDate endDate;   // ・期間 A3_1_4
	public TargetOrgIdenInfor targetOrgIdenInfor; // 対象組織識別情報
	public DisplayInforOrganization displayInforOrganization; // 組織の表示情報
}
