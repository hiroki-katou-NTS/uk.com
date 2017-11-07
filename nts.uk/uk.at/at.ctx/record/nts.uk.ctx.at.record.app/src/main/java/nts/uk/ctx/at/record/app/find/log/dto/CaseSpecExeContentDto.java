package nts.uk.ctx.at.record.app.find.log.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.workrecord.log.CaseSpecExeContent;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CaseSpecExeContentDto {
	/**
	 * ID (運用ケース)
	 */
	private String caseSpecExeContentID;

	/**
	 * 並び順
	 */
	private int orderNumber;

	/**
	 * 運用ケース名
	 */
	private String useCaseName;
	
	public static CaseSpecExeContentDto fromDomain(CaseSpecExeContent domain) {
		
		return new CaseSpecExeContentDto(
				domain.getCaseSpecExeContentID(),
				domain.getOrderNumber(),
				domain.getUseCaseName().v()
				);
	}
	
	
	

}
