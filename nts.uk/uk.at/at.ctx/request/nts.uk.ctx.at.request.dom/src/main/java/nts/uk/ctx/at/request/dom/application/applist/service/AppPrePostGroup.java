package nts.uk.ctx.at.request.dom.application.applist.service;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.applist.service.detail.AppHolidayWorkFull;
import nts.uk.ctx.at.request.dom.application.applist.service.detail.AppOverTimeInfoFull;

@Getter
@AllArgsConstructor
public class AppPrePostGroup {
	//事前
	private String preAppID;
	//事後
	private String postAppID;
	//実績
	@Setter
	private List<OverTimeFrame> time;
	
	private AppOverTimeInfoFull appPre;
	private String reasonAppPre;
	private AppHolidayWorkFull appPreHd;
}
