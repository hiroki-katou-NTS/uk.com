package nts.uk.screen.at.app.kha;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupportWorkSettingDto {
	/** 利用する */
	private Boolean isUse;
	
	/** 移動時間の計上先 */
	private Integer accountingOfMoveTime;
}
