package nts.uk.screen.at.app.command.ktg.ktg001;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.screen.at.app.ktgwidget.find.dto.StatusDetailedSettingDto;

@Data
@NoArgsConstructor
public class ApproveStatusSettingCommand {
	
	String topPagePartName;
	
	List<StatusDetailedSettingDto> approvedAppStatusDetailedSettings;

}
