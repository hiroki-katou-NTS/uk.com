package nts.uk.ctx.at.request.dom.application.lateleaveearly;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateCancelation;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.TimeReport;
/**
 * 遅刻早退取消申請
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.申請承認.申請.遅刻早退取消申請
 * @author AnhNM
 *
 */
@Setter
@Getter
@NoArgsConstructor
public class ArrivedLateLeaveEarly extends Application{
	/**
	 * 取消
	 */
	private List<LateCancelation> lateCancelation;
	/**
	 * 時刻報告
	 */
	private List<TimeReport> lateOrLeaveEarlies;
	
	public ArrivedLateLeaveEarly(Application application) {
		super(application);
	}
	/**
	 * 申請内容
	 * @return
	 */
	public String contentApplication() {
		return null;
	}

}
