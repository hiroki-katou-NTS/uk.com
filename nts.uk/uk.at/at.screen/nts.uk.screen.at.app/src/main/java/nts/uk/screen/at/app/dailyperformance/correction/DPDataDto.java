/**
 * 3:12:24 PM Sep 5, 2017
 */
package nts.uk.screen.at.app.dailyperformance.correction;

import java.util.HashMap;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hungnm
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class DPDataDto {

	private String state;
    private String error;
    private String date;
    private boolean sign;
    private String employeeId;
    private String employeeName;
}
