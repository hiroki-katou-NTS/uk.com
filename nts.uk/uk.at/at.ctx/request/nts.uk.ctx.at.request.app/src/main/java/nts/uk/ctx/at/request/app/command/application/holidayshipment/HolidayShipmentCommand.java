package nts.uk.ctx.at.request.app.command.application.holidayshipment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HolidayShipmentCommand {
	private String absAppID;
	private String recAppID;
	private int appVersion;
	private String memo;
	private String comboBoxReason;
    private String textAreaReason;
    private Integer user;
    private Integer reflectPerState;
}
