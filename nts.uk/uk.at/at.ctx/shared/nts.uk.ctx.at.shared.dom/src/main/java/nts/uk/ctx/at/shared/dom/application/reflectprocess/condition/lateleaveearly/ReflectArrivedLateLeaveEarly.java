package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.lateleaveearly;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.ApplicationReflect;

/**
 * @author thanh_nx
 *
 *         遅刻早退取消申請の反映
 */
@Getter
public class ReflectArrivedLateLeaveEarly implements DomainAggregate, ApplicationReflect {

	// 会社ID
	private String companyId;

	// 遅刻早退報告を行った場合はアラームとしない
	private boolean noAlarm;

	public ReflectArrivedLateLeaveEarly(String companyId, boolean noAlarm) {
		super();
		this.companyId = companyId;
		this.noAlarm = noAlarm;
	}

}
