package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.init;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.ReflectPlanPerState;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalAtr;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.OutputMode;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.User;

/**
 * 申請詳細画面情報
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AppDetailScreenInfo {
	
	/**
	 * 申請
	 */
	private Application_New application;
	
	/**
	 * 承認ルートインスタンス
	 */
	private List<ApprovalPhaseStateImport_New> approvalLst;
	
	/**
	 * 承認コメント
	 */
	private String authorComment;
	
	/**
	 * 利用者
	 */
	private User user; 
	
	/**
	 * ステータス
	 */
	private ReflectPlanPerState reflectPlanState;
	
	/**
	 * 詳細画面モード
	 */
	private OutputMode outputMode;
	
	/**
	 * 承認できるフラグ
	 */
	private Optional<Boolean> authorizableFlags;
	
	/**
	 * ログイン者の承認区分
	 */
	private Optional<ApprovalAtr> approvalATR;
	
	/**
	 * 代行期限フラグ
	 */
	private Optional<Boolean> alternateExpiration;
	
}
