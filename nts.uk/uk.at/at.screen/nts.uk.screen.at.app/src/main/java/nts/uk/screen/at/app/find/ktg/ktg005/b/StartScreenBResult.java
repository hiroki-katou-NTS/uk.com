package nts.uk.screen.at.app.find.ktg.ktg005.b;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.screen.at.app.find.ktg.ktg005.a.ApplicationStatusDetailedSettingDto;

@Data
@AllArgsConstructor
public class StartScreenBResult {

	// 名称
	String topPagePartName;

	// 申請状況の詳細設定
	List<ApplicationStatusDetailedSettingDto> appSettings;

}
