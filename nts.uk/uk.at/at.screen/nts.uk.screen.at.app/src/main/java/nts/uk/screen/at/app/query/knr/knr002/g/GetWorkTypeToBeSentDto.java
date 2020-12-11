package nts.uk.screen.at.app.query.knr.knr002.g;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author xuannt
 *
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetWorkTypeToBeSentDto {
	//	勤務種類コード
	private List<String> selectableWorks;
	private List<String> workTypeCodes;
}
