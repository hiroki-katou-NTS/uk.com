package nts.uk.file.at.app.export.bento;

import java.util.List;

import lombok.Value;

@Value
public class ReservationMonthQuery {
	
	private List<String> empLst;
	
	private String title;
	
	private String startDate;
	
	private String endDate;
	
	private boolean ordered;
	
}
