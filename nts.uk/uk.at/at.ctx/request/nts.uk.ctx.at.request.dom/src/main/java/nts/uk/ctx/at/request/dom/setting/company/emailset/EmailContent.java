package nts.uk.ctx.at.request.dom.setting.company.emailset;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.メール設定.メール内容
 * @author Doan Duy Hung
 *
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmailContent {
	
	/**
	 * 区分
	 */
	private Division division;
	
	/**
	 * 件名
	 */
	private Optional<EmailSubject> opEmailSubject;
	
	/**
	 * 本文
	 */
	private Optional<EmailText> opEmailText;
	
}
