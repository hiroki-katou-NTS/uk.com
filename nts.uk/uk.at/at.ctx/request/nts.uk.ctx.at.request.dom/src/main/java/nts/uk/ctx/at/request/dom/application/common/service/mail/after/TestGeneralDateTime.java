package nts.uk.ctx.at.request.dom.application.common.service.mail.after;

import nts.arc.time.GeneralDateTime;

public class TestGeneralDateTime {
	// TEST FILE
	public static void main(String[] args) {
		GeneralDateTime xxx = GeneralDateTime.now();
		GeneralDateTime yyy = xxx.addMonths(2);
		yyy = yyy.addSeconds(- xxx.seconds() - 1);
		System.out.println(xxx);		
		System.out.println(yyy.toString());
	}
}
