package nts.uk.screen.at.app.query.knr.knr002.k;

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
public class GetBentoMenuDto {
	//	(弁当メニュー．枠番、弁当メニュー．弁当名)<List>
	private List<BentoMenuDto> bentoMenuList;
	//	弁当メニュー枠番<List>
	private List<Integer> bentoMenu;
}

