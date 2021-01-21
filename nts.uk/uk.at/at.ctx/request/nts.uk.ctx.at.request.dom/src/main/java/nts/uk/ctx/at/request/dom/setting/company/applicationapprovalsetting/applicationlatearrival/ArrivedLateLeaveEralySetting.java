package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationlatearrival;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
//遅刻早退取消申請設定
public class ArrivedLateLeaveEralySetting {
//	【削除】日別実績のアラームを消す
	private Boolean isResult;
//	会社ID
	private String id;
//	取り消す設定
	private CancelCategory cancelCategory;
}
