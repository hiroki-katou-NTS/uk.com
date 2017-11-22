package nts.uk.shr.pereg.app.find;

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
	private String ctgId;
	private String ctgCd;
	private String empId;
	private GeneralDate standardDate;
	private String infoId;
}
