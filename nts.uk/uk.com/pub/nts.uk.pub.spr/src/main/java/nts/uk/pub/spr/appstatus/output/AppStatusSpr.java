package nts.uk.pub.spr.appstatus.output;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class AppStatusSpr {
	
	private GeneralDate date;
	
	private Integer status1;
	
	private Integer status2;
	
	private Optional<String> applicationID1;
	
	private Optional<String> applicationID2;
	
}
