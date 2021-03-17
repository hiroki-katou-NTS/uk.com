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
public class GetWorkTypeDto {
	//	選択可能な勤務種類コード<List>
	private List<String> posibleWorkTypes;
	//	選択中の勤務種類コード<List>
	private List<String> workTypeCodes;
}
