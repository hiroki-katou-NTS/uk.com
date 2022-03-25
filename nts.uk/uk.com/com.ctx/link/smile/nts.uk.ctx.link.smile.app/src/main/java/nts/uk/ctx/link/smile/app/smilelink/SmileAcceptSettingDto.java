package nts.uk.ctx.link.smile.app.smilelink;

import java.util.Optional;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author yen_nth
 *
 */
@Data
@NoArgsConstructor
public class SmileAcceptSettingDto {
	// Smile連携受入項目
	private int cooperationAcceptance;
	
	// Smile連携受入区分
	private int cooperationAcceptanceClassification;
	
	// 外部受入条件コード
	private String cooperationAcceptanceConditions;

	public SmileAcceptSettingDto(int cooperationAcceptance, int cooperationAcceptanceClassification,
			Optional<String> cooperationAcceptanceConditions) {
		super();
		this.cooperationAcceptance = cooperationAcceptance;
		this.cooperationAcceptanceClassification = cooperationAcceptanceClassification;
		this.cooperationAcceptanceConditions = cooperationAcceptanceConditions.isPresent() ? cooperationAcceptanceConditions.get() : null;
	}
}
