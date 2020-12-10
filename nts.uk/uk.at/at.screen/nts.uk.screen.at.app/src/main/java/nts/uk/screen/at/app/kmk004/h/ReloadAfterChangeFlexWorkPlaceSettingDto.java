package nts.uk.screen.at.app.kmk004.h;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.screen.at.app.query.kmk004.common.WorkplaceIdDto;

/**
 * 
 * @author sonnlb
 *
 *         職場別基本設定（フレックス勤務）を作成・変更・削除した時
 */
@NoArgsConstructor
@Data
public class ReloadAfterChangeFlexWorkPlaceSettingDto {
	
	// 職場別基本設定（フレックス勤務）を表示する
	DisplayFlexBasicSettingByWorkPlaceDto displayFlexBasicSettingByWorkPlace;
	
	List<WorkplaceIdDto> wkpIds;
}
