package nts.uk.ctx.at.request.pub.application.approvalstatus;

import java.util.Optional;
import nts.arc.time.GeneralDate;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.申請承認.Export.勤務種類から促す申請を判断する.勤務種類から促す申請を判断する
 * <<Public>>
 * 
 * @author tutt
 *
 */
public interface JudgingApplicationByWorkTypePub {

	/**
	 *
	 * @param cid          会社ID
	 * @param sid          社員ID
	 * @param date         年月日
	 * @param workTypeCode 勤務種類コード
	 * @return Optional<申請種類>
	 */
	public Optional<Integer> judgingApp(String cid, String sid, GeneralDate date, String workTypeCode);

}
