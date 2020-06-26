/**
 * 
 */
package nts.uk.screen.at.app.query.kdp.kdp010.a.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.record.app.find.stamp.management.StampPageLayoutDto;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSetCommunal;
import nts.uk.screen.at.app.query.kdp.kdp001.a.DisplaySettingsStampScreenDto;

@Getter
@AllArgsConstructor
public class StampSetCommunalDto {

	private String cid;
	
	private DisplaySettingsStampScreenDto displaySetStampScreen;
	
	private List<StampPageLayoutDto> lstStampPageLayout;
	
	private Boolean nameSelectArt;
	
	private Boolean passwordRequiredArt;
	
	private Boolean employeeAuthcUseArt;
	
	private Integer authcFailCnt;
	
	public static StampSetCommunalDto fromDomain(StampSetCommunal domain) {
		return new StampSetCommunalDto(
				domain.getCid(), 
				DisplaySettingsStampScreenDto.fromDomain(domain.getDisplaySetStampScreen()), 
				domain.getLstStampPageLayout().stream().map(c->StampPageLayoutDto.fromDomain(c)).collect(Collectors.toList()), 
				domain.isNameSelectArt(), 
				domain.isPasswordRequiredArt(), 
				domain.isEmployeeAuthcUseArt(), 
				domain.getAuthcFailCnt().isPresent()?domain.getAuthcFailCnt().get().v():null);
	}
}
