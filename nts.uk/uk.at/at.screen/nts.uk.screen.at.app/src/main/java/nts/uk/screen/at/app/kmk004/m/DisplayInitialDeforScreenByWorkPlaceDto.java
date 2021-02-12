package nts.uk.screen.at.app.kmk004.m;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.screen.at.app.query.kmk004.common.WorkplaceIdDto;

/**
 * 職場別法定労働時間の登録（変形労働）の初期画面を表示する
 * 
 * @author tutt
 *
 */
@Data
@NoArgsConstructor
public class DisplayInitialDeforScreenByWorkPlaceDto {

	// 職場リスト
	private List<WorkplaceIdDto> wkpIds;
	
	// 職場を選択する
	private SelectWorkplaceDeforDto selectWorkplaceDeforDto;

}
