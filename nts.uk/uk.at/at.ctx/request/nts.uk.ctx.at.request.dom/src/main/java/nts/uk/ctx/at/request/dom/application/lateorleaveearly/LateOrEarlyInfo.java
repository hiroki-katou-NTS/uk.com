package nts.uk.ctx.at.request.dom.application.lateorleaveearly;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
//取り消す初期情報
public class LateOrEarlyInfo {
//	チェックする
	private Boolean isCheck;
//	勤務NO
	private int workNo;
//	活性する
	private Boolean isActive;
//	表示する
	private Boolean isIndicated;
//	遅刻早退区分
	private LateOrEarlyAtr category;
}
