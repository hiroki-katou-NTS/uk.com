package nts.uk.shr.pereg.app.find;

import javax.ejb.Stateless;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PeregQuery {
	
	private String categoryId;
	
	private String categoryCode;
	
	private String employeeId;
	
	private String personId;
	
	private GeneralDate standardDate;
	
	private String infoId;
	
}
