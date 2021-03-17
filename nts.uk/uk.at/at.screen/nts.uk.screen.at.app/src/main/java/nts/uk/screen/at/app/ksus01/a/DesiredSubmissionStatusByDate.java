package nts.uk.screen.at.app.ksus01.a;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DesiredSubmissionStatusByDate {

	private String date;
	
	private int status;
}
