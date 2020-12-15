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
public class GetWorkTimeDto {
	//	選択可能な就業時間帯コード<List>
	private List<String> posibleWorkTimes;
	//	選択中の就業時間帯コード<List>
	private List<String> workTimeCodes;
}
