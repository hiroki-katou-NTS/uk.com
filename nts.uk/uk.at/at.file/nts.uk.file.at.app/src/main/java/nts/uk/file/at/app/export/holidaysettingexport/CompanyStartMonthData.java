package nts.uk.file.at.app.export.holidaysettingexport;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyStartMonthData extends AggregateRoot {
	
	private Integer startMonth;

}
