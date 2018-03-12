package nts.uk.ctx.at.request.app.find.setting.company.mailsetting.overtimeworkinstructionmail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.overtimeworkinstructionmail.MailOtInstruction;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class MailOtInstructionDto {
	// 会社ID
	private String companyId;
	// 件名
	private String subject;
	// 本文
	private String content;
	public static MailOtInstructionDto convertToDto(MailOtInstruction domain){
		return new MailOtInstructionDto(domain.getCompanyId(), 
				domain.getSubject() == null ? null : domain.getSubject().v(), 
				domain.getContent() == null ? null : domain.getContent().v());
	}
}
