package nts.uk.file.pr.app.export.comparingsalarybonus.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TotalByHyrachi {
	private String hyrachi;
	private double grandTotalHyrachi1 = 0d;
	private double grandTotalHyrachi2 = 0d;
	private double grandTotalDifferentHyrachi = 0d;
}
