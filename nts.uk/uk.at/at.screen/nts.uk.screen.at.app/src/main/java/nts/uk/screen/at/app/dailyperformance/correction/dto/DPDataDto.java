/**
 * 3:12:24 PM Sep 5, 2017
 */
package nts.uk.screen.at.app.dailyperformance.correction.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * @author hungnm
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class DPDataDto {
    private int id;
	private String state;
    private String error;
    private GeneralDate date;
    private boolean sign;
    private String employeeId;
    private String employeeCode;
    private String employeeName;
}
