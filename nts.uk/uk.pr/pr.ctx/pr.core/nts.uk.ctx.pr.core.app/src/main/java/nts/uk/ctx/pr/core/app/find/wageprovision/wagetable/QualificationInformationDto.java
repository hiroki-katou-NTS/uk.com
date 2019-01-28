package nts.uk.ctx.pr.core.app.find.wageprovision.wagetable;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.QualificationInformation;

/**
 * 資格情報
 */
@NoArgsConstructor
@Data
public class QualificationInformationDto {

	/**
	 * 資格コード
	 */
	private String qualificationCode;

	/**
	 * 資格名称
	 */
	private String qualificationName;

	private String integrationCode;

	public static QualificationInformationDto fromDomainToDto(QualificationInformation domain) {
		QualificationInformationDto dto = new QualificationInformationDto();
		dto.qualificationCode = domain.getQualificationCode().v();
		dto.qualificationName = domain.getQualificationName().v();
		dto.integrationCode = domain.getIntegrationCode().isPresent() ? domain.getIntegrationCode().get().v() : null;
		return dto;
	}
}
