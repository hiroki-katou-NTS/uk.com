package nts.uk.file.at.app.export.monthlyrole;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ControlOfAttMonthlyDtoExcel {

	private int itemMonthlyID;
	
	private String headerBgColorOfMonthlyPer;

	private Integer inputUnitOfTimeItem;
}
