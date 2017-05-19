package nts.uk.file.pr.app.export.comparingsalarybonus.data;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DeparmentInf {	
	private String Depcode;
	private String Depname;
	private String hyrachi;
	private String historyId;
	private int lengthHyrachi = 0;
	List<DetailEmployee> lstEmployee;
	private Double totalMonth1 = 0.0;
	private Double totalMonth2 = 0.0;
	private Double totalDifferent = 0.0;
	public DeparmentInf(String depCode, String depName, String history, String hyrachi,List<DetailEmployee> lstEmployee) {
	  this.Depcode = depCode;
	  this.Depname = depName;
	  this.historyId = history;
	  this.hyrachi = hyrachi;
	  this.lengthHyrachi = hyrachi.length() / 3;
	  this.lstEmployee = lstEmployee;
	  total(lstEmployee);
	}
	
	private void total(List<DetailEmployee> lstEmployee) {

		lstEmployee.stream().forEach(c -> {
			c.getLstData().stream().forEach(s -> {
				totalMonth1 += s.getMonth1();
				totalMonth2 += s.getMonth2();
				totalDifferent += s.getDifferentSalary();

			});
		});
	}
	
}
