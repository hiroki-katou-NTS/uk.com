/**
 * 
 */
package nts.uk.screen.at.app.ksu001.displayinworkinformation;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

/**
 * @author laitv
 *
 */
@NoArgsConstructor
@AllArgsConstructor
public class DisplayInWorkInfoParam {
	
	public List<String> listSid;             // ・社員IDリスト：List<社員ID>
	public GeneralDate startDate;            // ・期間
	public GeneralDate endDate;    	         // ・期間
	public boolean getActualData;            // ・実績も取得するか：boolean : có lấy data thực tế không A4_7
}
