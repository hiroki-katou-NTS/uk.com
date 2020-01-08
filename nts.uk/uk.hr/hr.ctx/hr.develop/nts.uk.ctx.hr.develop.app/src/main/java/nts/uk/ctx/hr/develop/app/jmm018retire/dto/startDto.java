package nts.uk.ctx.hr.develop.app.jmm018retire.dto;

import lombok.Data;

@Data
public class startDto {
	// 処理結果
	private boolean result;
	
	// メッセージID
	private String message;

	// 関連マスタ取得
	private boolean getRelatedMaster;
	
	private RelateMasterDto relateMaster;
	
	private MandatoryRetirementDto mandatoryRetirement;

	public startDto(boolean result, String message, boolean getRelatedMaster, RelateMasterDto relateMaster,
			MandatoryRetirementDto mandatoryRetirement) {
		super();
		this.result = result;
		this.message = message;
		this.getRelatedMaster = getRelatedMaster;
		this.relateMaster = relateMaster;
		this.mandatoryRetirement = mandatoryRetirement;
	}
}
