/**
 * 
 */
package nts.uk.ctx.at.record.app.command.kdp.kdp010.a.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.stamp.management.StampPageLayoutDto;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.NumberAuthenfailures;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSetCommunal;
import nts.uk.shr.com.context.AppContexts;

@Data
@NoArgsConstructor
public class StampSetCommunalCommand {

	private String cid;
	
	private DisplaySettingsStampScreenCommand displaySetStampScreen;
	
	private List<StampPageLayoutDto> lstStampPageLayout;
	
	private Boolean nameSelectArt;
	
	private Boolean passwordRequiredArt;
	
	private Boolean employeeAuthcUseArt;
	
	private Integer authcFailCnt;
	
	public StampSetCommunal toDomain() {
		return new StampSetCommunal(
				AppContexts.user().companyId(), 
				this.displaySetStampScreen.toDomain(), 
				new ArrayList(), 
				this.nameSelectArt, 
				this.passwordRequiredArt, 
				this.employeeAuthcUseArt, 
				this.authcFailCnt == null?Optional.empty():Optional.of(new NumberAuthenfailures(this.authcFailCnt)));
	}
}
