package nts.uk.ctx.pereg.app.command.specialholiday;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SpecialHolidayTestDto {
	private boolean isEffective;
	private String name;
	private int code;
}
