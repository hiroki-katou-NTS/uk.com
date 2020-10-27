package nts.uk.screen.at.app.shift.management.workavailability;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author quytb
 *
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WorkAvailabilityInfoDto {
	/** 年月日 */
	private String desireDay;

	/** コード／名称*/
	private String employeeCdName;
	
	/** 表示情報.種類 */
	private String method;

	/** 表示情報.名称リスト */
	private String shift;

	/** 表示情報.時間帯リスト */
	private String timezone;

	/** 勤務希望のメモ */
	private String remarks;
}
