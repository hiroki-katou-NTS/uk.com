package nts.uk.screen.at.app.dailyperformance.correction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DivergenceTimeDto {
	
	// 乖離時間NO
	private int divergenceTimeNo;

	/** The c id. */
	// 会社ID
	private String companyId;

	/** The Use classification. */
	// 使用区分
	private int divTimeUseSet;
	
	//乖離理由を選択肢から選ぶ input
	private Boolean dvgcReasonInputed;
	
	//乖離理由を入力する
	private Boolean dvgcReasonSelected;

}
