package nts.uk.screen.at.app.kdl045.query;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Map<時間休暇種類, 合計使用時間> Dto
 * 
 * @author tutk
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class UsageTimeAndType {
	/**
	 * 時間休暇種類
	 */
	private Integer typeVacation;
	
	/**
	 * 合計使用時間
	 */
	private Integer totalTime;

	public UsageTimeAndType(Integer typeVacation, Integer totalTime) {
		super();
		this.typeVacation = typeVacation;
		this.totalTime = totalTime;
	}

}
