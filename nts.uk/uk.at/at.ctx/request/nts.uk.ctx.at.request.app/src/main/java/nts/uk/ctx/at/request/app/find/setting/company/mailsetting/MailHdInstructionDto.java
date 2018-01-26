package nts.uk.ctx.at.request.app.find.setting.company.mailsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailholidayinstruction.MailHdInstruction;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class MailHdInstructionDto {
	// 会社ID
	private String companyId;
	// 件名
	private String subject;
	// 本文
	private String content;
	public static MailHdInstructionDto convertToDto(MailHdInstruction domain){
		return new MailHdInstructionDto(domain.getCompanyId(), domain.getSubject().v(), domain.getContent().v());
	}
}
