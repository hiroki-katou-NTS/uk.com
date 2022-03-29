package nts.uk.ctx.at.record.app.command.kdp.kdp010.a.command;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.app.command.stamp.management.StampPageLayoutCommand;
import nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.StampingAreaLimit;
import nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.StampingAreaRestriction;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.settingforsmartphone.SettingsSmartphoneStamp;
import nts.uk.ctx.at.shared.dom.ot.frame.NotUseAtr;
import nts.uk.shr.com.context.AppContexts;

@NoArgsConstructor
@Data
public class SettingsSmartphoneStampCommand {
	
	private String cid;
	
	private DisplaySettingsStampScreenCommand displaySettingsStampScreen;
	
	private List<StampPageLayoutCommand> pageLayoutSettings;
	
	private Integer buttonEmphasisArt;	
	
	private Integer googleMap;
	
	// 位置情報を利用する
	private Integer locationInfoUse;

	// 打刻エリア制限する
	private Integer areaLimitAtr;
	
	public SettingsSmartphoneStamp toDomain() {
		return new SettingsSmartphoneStamp(
				AppContexts.user().companyId(), 
				this.displaySettingsStampScreen.toDomain(), 
				this.pageLayoutSettings.stream().map(c->c.toDomain()).collect(Collectors.toList()), 
				this.buttonEmphasisArt == 1,
				new StampingAreaRestriction(EnumAdaptor.valueOf(locationInfoUse, NotUseAtr.class),
						EnumAdaptor.valueOf(areaLimitAtr, StampingAreaLimit.class)));
	}
}
