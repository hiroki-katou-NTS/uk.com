package nts.uk.screen.at.app.query.kdp.kdps01.a;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.stamp.card.management.personalengraving.AppDispNameExp;

/**
 * 
 * @author sonnlb
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetOmissionContentDto {
	/**
	 * 
	 */
	List<AppDispNameExp> appDispNames;
	/**
	 * ・Temporary：日別勤怠エラー情報
	 * 
	 */
	private List<DailyOmissionAttdErrorInfoDto> errorInfo;
	/**
	 * ・申請表示名(LIST)
	 */
	
//	private List<AppDispNameDto> appNames;
}
