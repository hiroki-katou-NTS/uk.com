package nts.uk.ctx.at.request.app.find.application.lateorleaveearly;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;

/**
 * @author anhnm
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class LateEarlyInitDto {
	
	private List<String> appDates;
	
	private int appType;
	
	private AppDispInfoStartupDto appDispInfoStartupDto;

}
