package nts.uk.ctx.at.request.dom.application.lateorleaveearly;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.Application;
@Setter
@Getter
@NoArgsConstructor
//遅刻早退取消申請
public class ArrivedLateLeaveEarly extends Application{
//	取消
	private List<LateCancelation> lateCancelation;
//	時刻報告
	private List<TimeReport> lateOrLeaveEarlies;
	
	public ArrivedLateLeaveEarly(Application application) {
		super(application);
	}
	/*
	 * 申請内容
	 * */
	public String contentApplication() {
		return null;
	}

}
