package nts.uk.ctx.at.shared.app.command.workingcondition;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.workingcondition.TimeZone;
import nts.uk.shr.pereg.app.command.MyCustomizeException;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TimeZoneCustom {
	private List<TimeZone> timezoneLst = new ArrayList<>();
	private List<MyCustomizeException> errors = new ArrayList<>();
	
}
