package nts.uk.file.pr.app.export.comparingsalarybonus.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DeparmentInf {
	private String Depcode;
	private String Depname;
	List<DetailEmployee> lstEmployee;
	Double totalMonth1 = 0.0;
	Double totalMonth2 = 0.0;
	Double totalDifferent = 0.0;
	public TotalValue total() {

		this.lstEmployee.stream().forEach(c -> {
			c.getLstData().stream().forEach(s -> {
				//System.out.println(s.getMonth1().doubleValue());
				Double total1 =s.getMonth1().doubleValue();
				Double total2 = s.getMonth2().doubleValue();
				Double total3 = s.getDifferentSalary().doubleValue();
				totalMonth1 = totalMonth1 + total1;
				totalMonth2 = totalMonth2 + total2;
				totalDifferent = totalDifferent + total3;

			});
		});
		return new TotalValue(totalMonth1, totalMonth2, totalDifferent);
	}
}
