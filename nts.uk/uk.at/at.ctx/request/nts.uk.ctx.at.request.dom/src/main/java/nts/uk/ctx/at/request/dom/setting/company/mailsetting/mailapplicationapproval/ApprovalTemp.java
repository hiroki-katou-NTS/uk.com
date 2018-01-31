package nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailapplicationapproval;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailholidayinstruction.Content;
/**
 * 申請承認メールテンプレート
 * @author yennth
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalTemp extends AggregateRoot{
	// 会社ID
	private String companyId;
	// 本文
	private Content content;
	public static ApprovalTemp createFromJavaType(String companyId, String content){
		return new ApprovalTemp(companyId, new Content(content));
	}
}
