package nts.uk.ctx.at.request.dom.application.applist.service.detail;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public class TimeNo417 {

	//実績時間 + 申請時間
	private int totalOv;
	//36時間
	private int time36;
	//36年間超過回数
	private int numOfYear36Over;
	//36年間超過月
	private List<Integer> lstOverMonth;
}
