package nts.uk.ctx.at.shared.dom.supportmanagement.supportableemployee;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.supportmanagement.SupportType;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

/**
 * 応援チケット
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.応援管理.応援可能な社員.応援チケット
 * @author kumiko_otake
 */
@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class SupportTicket {

	/** 社員ID **/
	private final EmployeeId employeeId;
	/** 応援先 **/
	private final TargetOrgIdenInfor recipient;
	/** 応援形式 **/
	private final SupportType supportType;
	/** 年月日 **/
	private final GeneralDate date;
	/** 時間帯 **/
	private final Optional<TimeSpanForCalc> timespan;

}
