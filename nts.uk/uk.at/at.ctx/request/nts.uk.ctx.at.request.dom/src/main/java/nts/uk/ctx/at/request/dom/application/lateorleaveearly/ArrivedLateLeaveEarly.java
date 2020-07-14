package nts.uk.ctx.at.request.dom.application.lateorleaveearly;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.Application;


/**
 * refactor 4
 * @author anhnm
 * 
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.申請承認.申請.遅刻早退取消申請.遅刻早退取消申請
 *
 */

//遅刻早退取消申請
@Getter
@Setter
public class ArrivedLateLeaveEarly extends Application {

	//	遅刻早退取消先
	private List<LateCancelation> lateCancelation;
	
	//	遅刻早退時刻報告
	private List<TimeReport> timeReport;
	
	public ArrivedLateLeaveEarly(List<LateCancelation> lateCancelation, List<TimeReport> timeReport, Application application) {
		super(application);
		this.timeReport = timeReport;
		this.lateCancelation = lateCancelation;
	}
}
