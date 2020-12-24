package nts.uk.screen.at.app.query.knr.knr002.k;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 
 * @author xuannt
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BentoMenuDto {
	//	弁当メニュー．枠番
	private int bentoMenuFrameNumber;
	//	弁当メニュー．弁当名
	private String bentoMenuName;
}
