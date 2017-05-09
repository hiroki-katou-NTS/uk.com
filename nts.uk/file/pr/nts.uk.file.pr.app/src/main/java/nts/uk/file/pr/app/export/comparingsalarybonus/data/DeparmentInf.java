package nts.uk.file.pr.app.export.comparingsalarybonus.data;

import java.util.List;

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
}
