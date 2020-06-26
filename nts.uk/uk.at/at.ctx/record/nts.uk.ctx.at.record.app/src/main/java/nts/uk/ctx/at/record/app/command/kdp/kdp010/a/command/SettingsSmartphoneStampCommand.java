package nts.uk.ctx.at.record.app.command.kdp.kdp010.a.command;

import java.util.ArrayList;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.SettingsSmartphoneStamp;
import nts.uk.shr.com.context.AppContexts;

@NoArgsConstructor
@Data
public class SettingsSmartphoneStampCommand {
	
	private String cid;
	
	private DisplaySettingsStampScreenCommand displaySettingsStampScreen;
	
	private Boolean buttonEmphasisArt;	
	
	private Boolean googleMap;
	
	public SettingsSmartphoneStamp toDomain() {
		return new SettingsSmartphoneStamp(
				AppContexts.user().companyId(), 
				this.displaySettingsStampScreen.toDomain(), 
				new ArrayList(), 
				this.buttonEmphasisArt);
	}
}
