package nts.uk.ctx.at.shared.dom.application.lateleaveearly;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.application.common.ApplicationShare;

/**
 * @author thanh_nx
 *
 *         遅刻早退取消申請
 */
@Setter
@Getter
@NoArgsConstructor
public class ArrivedLateLeaveEarlyShare extends ApplicationShare {

	// 取消
	private List<LateCancelationShare> lateCancelation;
	
	// 時刻報告
	private List<TimeReportShare> lateOrLeaveEarlies;

	public ArrivedLateLeaveEarlyShare(ApplicationShare application) {
		super(application);
	}

	public ArrivedLateLeaveEarlyShare(List<LateCancelationShare> lateCancelation,
			List<TimeReportShare> lateOrLeaveEarlies, ApplicationShare app) {
		super(app);
		this.lateCancelation = lateCancelation;
		this.lateOrLeaveEarlies = lateOrLeaveEarlies;
	}

	/*
	 * 申請内容
	 */
	public String contentApplication() {
		return null;
	}

}
