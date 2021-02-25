package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.service.checkpostappaccept;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.申請設定.申請種類別設定.アルゴリズム.事後申請がいつから受付可能か確認する.事後申請の受付制限
 * @author Doan Duy Hung
 *
 */
@Getter
public class PostAppAcceptLimit {
	
	/**
	 * 受付制限利用する
	 */
	private boolean useReceptionRestriction;
	
	/**
	 * 受付可能年月日
	 */
	@Setter
	private Optional<GeneralDate> opAcceptableDate;
	
	public PostAppAcceptLimit(boolean useReceptionRestriction) {
		this.useReceptionRestriction = useReceptionRestriction;
		this.opAcceptableDate = Optional.empty();
	}
	
}
