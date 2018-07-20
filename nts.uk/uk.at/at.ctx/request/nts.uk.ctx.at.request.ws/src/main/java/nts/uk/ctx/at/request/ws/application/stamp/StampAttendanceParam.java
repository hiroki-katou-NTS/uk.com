package nts.uk.ctx.at.request.ws.application.stamp;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class StampAttendanceParam {
	private List<String> employeeIDLst; 
	private String date;
	private Integer stampRequestMode;
}
