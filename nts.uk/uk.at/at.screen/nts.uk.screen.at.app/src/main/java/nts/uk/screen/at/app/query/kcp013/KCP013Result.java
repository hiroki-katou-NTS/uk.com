package nts.uk.screen.at.app.query.kcp013;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KCP013Result {
	
	private List<AcquireWorkHours> listWorkTime;
	private boolean hasWorkTimeInModeWorkPlace;
	private boolean modeCompany; // company or workplace

}
