package nts.uk.screen.at.app.ktgwidget.find.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RemainingNumber {
	
	private String name = "";

	private double before = 0.0;
	
	private double after = 0.0;
	
	private GeneralDate grantDate = GeneralDate.today();
	
	private boolean showAfter = false;
}
