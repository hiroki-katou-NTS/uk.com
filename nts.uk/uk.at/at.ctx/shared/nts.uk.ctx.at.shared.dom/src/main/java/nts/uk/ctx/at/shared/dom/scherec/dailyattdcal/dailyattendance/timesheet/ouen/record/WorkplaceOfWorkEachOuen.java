package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.record;


import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.objecttype.DomainObject;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

/**
 * 応援別勤務の勤務先
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).日の勤怠計算.日別時間帯別勤怠.応援実績.時間帯.応援別勤務の勤務先
 */
@Getter
@AllArgsConstructor
public class WorkplaceOfWorkEachOuen implements DomainObject {

	/** 職場: 職場ID */
	@Setter
	private WorkplaceId workplaceId;
	
	/** 場所: 勤務場所コード */
	@Setter
	private Optional<WorkLocationCD> workLocationCD;
	
	/**
	 * 職場グループID
	 */
	private Optional<String> workplaceGroupId;
	
	//実績のデータを作成する
	public static WorkplaceOfWorkEachOuen create(WorkplaceId workplaceId, WorkLocationCD workLocationCD) {
		return new WorkplaceOfWorkEachOuen(workplaceId, Optional.ofNullable(workLocationCD), Optional.empty());
	}
	
	/**
	 * 勤務先組織を取得する
	 * @return
	 */
	public TargetOrgIdenInfor getRecipientOrg() {
		
		return TargetOrgIdenInfor.createByAutoDeterminingUnit(this.workplaceId.v(), this.workplaceGroupId);
	}
}
