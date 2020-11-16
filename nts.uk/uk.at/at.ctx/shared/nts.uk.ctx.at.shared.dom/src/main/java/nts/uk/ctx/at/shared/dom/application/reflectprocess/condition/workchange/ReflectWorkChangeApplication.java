package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.workchange;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.ApplicationReflect;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author thanh_nx
 *
 *         勤務変更申請の反映
 */
@Getter
public class ReflectWorkChangeApplication implements DomainAggregate, ApplicationReflect {

	// 会社ID
	private final String companyId;

	// 出退勤を反映するか
	private final NotUseAtr reflectAttendance;

	public ReflectWorkChangeApplication(String companyId, NotUseAtr reflectAttendance) {
		this.companyId = companyId;
		this.reflectAttendance = reflectAttendance;
	}

}
