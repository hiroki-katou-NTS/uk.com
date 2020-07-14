package nts.uk.ctx.at.request.dom.application.lateorleaveearly;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReflectionInformation_New;
@Setter
@Getter
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
