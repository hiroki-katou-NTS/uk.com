package nts.uk.ctx.hr.shared.dom.notice.report.registration.person;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApprRootStateHrImport {
	/**エラーフラグ*/
	private boolean errorFlg;
	/**人事承認状態データ*/
	private ApprStateHrImport apprState;
}
