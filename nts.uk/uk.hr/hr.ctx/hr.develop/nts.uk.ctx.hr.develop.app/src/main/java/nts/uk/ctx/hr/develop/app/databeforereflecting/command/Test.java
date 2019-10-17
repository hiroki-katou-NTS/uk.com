package nts.uk.ctx.hr.develop.app.databeforereflecting.command;

import java.time.LocalDate;
import java.time.Period;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LocalDate retirementDate = LocalDate.of(2019, 9, 30); // A222_12
		LocalDate dismissalNoticeDate = LocalDate.of(2019, 8, 31);//A222_35
		
		Period period = Period.between(dismissalNoticeDate, retirementDate);
	    int diff = period.getDays();
	 
		System.out.println(diff);
		
	}

}
