package nts.uk.ctx.at.request.app.command.application.applicationlist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
/**
 * before and after request set command for kaf022
 * @author yennth
 *
 */
@Data
@AllArgsConstructor
@Getter
public class BfReqSetCommand {
	// 申請種類
	private Integer appType;
	// チェック方法
	private Integer retrictPreMethodFlg;
	// 利用する
	private Integer retrictPreUseFlg;
	// 日数
	private Integer retrictPreDay;
	// 時刻
	private Integer retrictPreTimeDay;
	// 未来日許可しない
	private Integer retrictPostAllowFutureFlg;
	// 早出残業
	private Integer preOtTime;
	// 通常残業
	private Integer normalOtTime; 
}
