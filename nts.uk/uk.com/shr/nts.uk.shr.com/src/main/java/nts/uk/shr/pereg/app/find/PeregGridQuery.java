package nts.uk.shr.pereg.app.find;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;


@Getter
@Setter
@NoArgsConstructor
public class PeregGridQuery {

	private String categoryId;

	private String categoryCode;
	
	private GeneralDate standardDate;
	
	private List<EmployeeQuery> lstEmployee;
}