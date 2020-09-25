package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.init;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ReflectPlanPerState;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalAtr;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.OutputMode;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.User;

/**
 * refactor 4
 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム."14-1.詳細画面起動前申請共通設定を取得する(get detail setting)".申請詳細画面情報
 * @author Doan Duy Hung
 *
 */
@Getter
public class AppDetailScreenInfo {
	
	/**
	 * 申請
	 */
	private Application application;
	
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
	@Setter
	private Optional<Boolean> authorizableFlags;
	
	/**
	 * ログイン者の承認区分
	 */
	@Setter
	private Optional<ApprovalAtr> approvalATR;
	
	/**
	 * 代行期限フラグ
	 */
	@Setter
	private Optional<Boolean> alternateExpiration;
	
	public AppDetailScreenInfo(Application application, List<ApprovalPhaseStateImport_New> approvalLst,
			String authorComment, User user, ReflectPlanPerState reflectPlanState, OutputMode outputMode) {
		this.application = application;
		this.approvalLst = approvalLst;
		this.authorComment = authorComment;
		this.user = user;
		this.reflectPlanState = reflectPlanState;
		this.outputMode = outputMode;
		this.authorizableFlags = Optional.empty();
		this.approvalATR = Optional.empty();
		this.alternateExpiration = Optional.empty();
	}
	
}
