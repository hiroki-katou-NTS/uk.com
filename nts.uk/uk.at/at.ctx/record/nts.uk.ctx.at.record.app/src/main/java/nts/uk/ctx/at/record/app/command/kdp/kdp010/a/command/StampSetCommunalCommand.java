/**
 * 
 */
package nts.uk.ctx.at.record.app.command.kdp.kdp010.a.command;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.command.stamp.management.StampPageLayoutCommand;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.AuthenticationMethod;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.NumberAuthenfailures;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSetCommunal;
import nts.uk.shr.com.context.AppContexts;

@Data
@NoArgsConstructor
public class StampSetCommunalCommand {

	private String cid;
	
	private DisplaySettingsStampScreenCommand displaySetStampScreen;
	
	private List<StampPageLayoutCommand> lstStampPageLayout;
	
	private Integer nameSelectArt;
	
	private Integer passwordRequiredArt;
	
	private Integer employeeAuthcUseArt;
	
	private Integer authcFailCnt;
	
	private int authcMethod;
	
	public StampSetCommunal toDomain() {
		return new StampSetCommunal(
				AppContexts.user().companyId(), 
				this.displaySetStampScreen.toDomain(), 
				this.lstStampPageLayout.stream().map(c->c.toDomain()).collect(Collectors.toList()), 
				this.nameSelectArt == 1, 
				this.passwordRequiredArt == 1, 
				this.employeeAuthcUseArt == 1, 
				this.authcFailCnt == null?Optional.empty():Optional.of(new NumberAuthenfailures(this.authcFailCnt)),
			    AuthenticationMethod.valueOf(this.authcMethod));
	}
}
