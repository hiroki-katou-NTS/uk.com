package nts.uk.ctx.at.request.dom.setting.company.mailsetting.overtimeworkinstructionmail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailholidayinstruction.Content;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailholidayinstruction.Subject;
/**
 * 残業指示のメール内容
 * @author yennth
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MailOtInstruction {
	// 会社ID
	private String companyId;
	// 件名
	private Subject subject;
	// 本文
	private Content content;
	public static MailOtInstruction createFromJavaType(String companyId, String subject, String content){
		return new MailOtInstruction(companyId, 
					StringUtil.isNullOrEmpty(subject, true) ? null : new Subject(subject), 
					StringUtil.isNullOrEmpty(content, true) ? null : new Content(content));
	}
}
