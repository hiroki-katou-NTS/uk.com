package nts.uk.ctx.at.shared.dom.workrule.overtime;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumConstant;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.shared.dom.ot.frame.NotUseAtr;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameName;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleofovertimework.RoleOvertimeWorkEnum;
/**
 * Refactor5
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.shared.就業規則.時間外.残業.残業枠
 * @author hoangnd
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
// 残業枠
public class OverTimeFrame implements DomainAggregate{
	// 会社ID
	private String companyId;
	
	//残業枠NO
	private OvertimeWorkFrameNo overtimeWorkFrNo;
	
	//使用区分
	private NotUseAtr useClassification;

	//振替枠名称
	private OvertimeWorkFrameName transferFrName;

	//残業枠名称
	private OvertimeWorkFrameName overtimeWorkFrName;
	
	// 代休振替対象
	private EnumConstant subTransferTarget;
	
	// 役割
	private RoleOvertimeWorkEnum role;
}
