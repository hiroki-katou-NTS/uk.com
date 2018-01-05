package nts.uk.ctx.at.shared.app.command.specialholiday;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.specialholiday.grantdate.GrantDateCom;
import nts.uk.ctx.at.shared.dom.specialholiday.grantdate.GrantDateSet;
import nts.uk.shr.com.context.AppContexts;

@Data
@AllArgsConstructor
public class GrantDateComCommand {
	/*付与日のID*/
	private String specialHolidayCode;

	/*付与基準日*/
	private int grantDateAtr;

	/*一律基準日*/
	private GeneralDate grantDate;
	
	private List<GrantDateSetCommand> grantDateSets;

	public GrantDateCom toDomain() {
		String companyId = AppContexts.user().companyId();
		
		List<GrantDateSet> grantDateSet = this.grantDateSets.stream().map(x-> {
			return GrantDateSet.createFromJavaType(companyId, 
					x.getSpecialHolidayCode(), 
					x.getGrantDateNo(),
					x.getGrantDateMonth(),
					x.getGrantDateYear());
		}).collect(Collectors.toList());
		
		return  GrantDateCom.createFromJavaType(companyId, specialHolidayCode, grantDateAtr, grantDate, grantDateSet);
	}
}
