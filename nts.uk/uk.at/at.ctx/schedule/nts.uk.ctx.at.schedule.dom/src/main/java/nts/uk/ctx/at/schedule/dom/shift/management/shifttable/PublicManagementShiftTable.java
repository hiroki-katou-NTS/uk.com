package nts.uk.ctx.at.schedule.dom.shift.management.shifttable;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

/**
 * シフト表の公開管理
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.シフト管理.シフト勤務.シフト表
 * @author HieuLt
 *
 */
@Getter
@AllArgsConstructor
public class PublicManagementShiftTable implements DomainAggregate {
	
	/** 対象組織 **/
	private final TargetOrgIdenInfor targetOrgIdenInfor;
	/** 公開期間の終了日**/ 
	private GeneralDate endDatePublicationPeriod;
	/** 編集開始日 **/
	private Optional<GeneralDate> optEditStartDate;
	
	//[C-1] 作る
	public static PublicManagementShiftTable createPublicManagementShiftTable(TargetOrgIdenInfor targetOrgIdenInfor , GeneralDate endDatePublicationPeriod ,Optional<GeneralDate> optEditStartDate ){

		if(optEditStartDate.isPresent()) {
			if((optEditStartDate.get()).after(endDatePublicationPeriod) ){
				throw new RuntimeException("System Error");
			}
		}
		return new PublicManagementShiftTable(targetOrgIdenInfor, endDatePublicationPeriod, optEditStartDate);
	}
	
}
