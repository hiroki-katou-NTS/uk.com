package nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailholidayinstruction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 休出指示のメール内容
 * @author yennth
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MailHdInstruction extends AggregateRoot{
	// 会社ID
	private String companyId;
	// 件名
	private Subject subject;
	// 本文
	private Content content;
	
	public static MailHdInstruction createFromJavaType(String companyId, String subject, String content){
		return new MailHdInstruction(companyId, new Subject(subject), new Content(content));
	}
}
