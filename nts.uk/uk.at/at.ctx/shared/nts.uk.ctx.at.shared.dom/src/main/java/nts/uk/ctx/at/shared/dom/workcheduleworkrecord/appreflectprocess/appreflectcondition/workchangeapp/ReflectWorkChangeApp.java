package nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.workchangeapp;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.shared(勤務予定、勤務実績).申請反映処理.申請反映条件.勤務変更申請.勤務変更申請の反映
 * @author Doan Duy Hung
 *
 */
@Getter
public class ReflectWorkChangeApp implements DomainAggregate {
	
	/**
	 * 会社ID
	 */
	private String companyID;
	
	/**
	 * 出退勤を反映するか
	 */
	private NotUseAtr whetherReflectAttendance;
	
}
