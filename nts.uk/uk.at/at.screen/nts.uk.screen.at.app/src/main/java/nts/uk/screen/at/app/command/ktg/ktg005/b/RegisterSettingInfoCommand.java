package nts.uk.screen.at.app.command.ktg.ktg005.b;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.ApplicationStatusDetailedSetting;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.ApplicationStatusWidgetItem;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.StandardWidget;
import nts.uk.screen.at.app.find.ktg.ktg005.a.ApplicationStatusDetailedSettingDto;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 
 * @author sonnlb
 *
 */
@NoArgsConstructor
@Data
public class RegisterSettingInfoCommand {

	String topPagePartName;

	List<ApplicationStatusDetailedSettingDto> appSettings;

	public StandardWidget toDomain(String companyId) {
		StandardWidget widget = new StandardWidget(companyId, null, null, null, null, null);

		List<ApplicationStatusDetailedSetting> appStatus = appSettings.stream()
				.map(x -> new ApplicationStatusDetailedSetting(EnumAdaptor.valueOf(x.getDisplayType(), NotUseAtr.class),
						EnumAdaptor.valueOf(x.getItem(), ApplicationStatusWidgetItem.class)))
				.collect(Collectors.toList());
		widget.setName(this.topPagePartName);
		widget.setAppStatusDetailedSettingList(appStatus);

		return widget;
	}
}
