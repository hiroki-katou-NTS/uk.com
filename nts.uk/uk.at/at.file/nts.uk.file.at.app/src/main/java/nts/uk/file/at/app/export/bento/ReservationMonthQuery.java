package nts.uk.file.at.app.export.bento;

import java.util.List;

import lombok.Data;
import lombok.Value;

@Value
@Data
public class ReservationMonthQuery {
	
	private List<String> empLst;
	
	private String title;
	
	private String startDate;
	
	private String endDate;
	
	private boolean ordered;
	
}
