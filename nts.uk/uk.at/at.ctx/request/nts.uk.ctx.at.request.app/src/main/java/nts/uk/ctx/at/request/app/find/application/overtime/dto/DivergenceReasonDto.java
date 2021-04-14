package nts.uk.ctx.at.request.app.find.application.overtime.dto;

import lombok.Data;

@Data
public class DivergenceReasonDto {
	/**
	 * divergenceReasonID
	 */
	private String divergenceReasonID;
	/**
	 * reasonTemp
	 */
	private String reasonTemp;
	
	private int divergenceReasonIdDefault;
	
//	public static DivergenceReasonDto fromDomain(DivergenceReason divergenceReason) {
//		DivergenceReasonDto divergenceReasonDto = new DivergenceReasonDto();
//		divergenceReasonDto.setDivergenceReasonID(divergenceReason.getReasonTypeItem().getReasonID());
//		divergenceReasonDto.setReasonTemp(divergenceReason.getReasonTypeItem().getReasonTemp().toString());
//		// divergenceReasonDto.setDivergenceReasonIdDefault(divergenceReason.getReasonTypeItem().getDefaultFlg().value);
//		return divergenceReasonDto;
//	}
}
