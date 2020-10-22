package nts.uk.screen.at.app.command.ktg.ktg001;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.screen.at.app.ktgwidget.find.dto.ApprovedAppStatusDetailedSettingDto;

@Data
@NoArgsConstructor
public class KTG001SettingParam {
	String topPagePartName;
	List<ApprovedAppStatusDetailedSettingDto> approvedAppStatusDetailedSettings;

}
