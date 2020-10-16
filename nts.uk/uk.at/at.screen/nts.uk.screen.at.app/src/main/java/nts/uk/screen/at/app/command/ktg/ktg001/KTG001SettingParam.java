package nts.uk.screen.at.app.command.ktg.ktg001;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.ApprovedAppStatusDetailedSetting;

@Data
public class KTG001SettingParam {
	String topPagePartName;
	List<ApprovedAppStatusDetailedSetting> approvedAppStatusDetailedSettings;

}
