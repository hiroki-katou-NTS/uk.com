package nts.uk.shr.com.validate.validator;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeStyleValidator {

	private static final String TIME_FORMAT = "^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$";

	public static boolean validateTimeStyle(String value) {
		Pattern pattern = Pattern.compile(TIME_FORMAT);
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}
	
	public static Optional<String> validateMinMax(int min, int max, String value) {
		int minutes = TimeStyleValidator.getMinutes(value);
		
		// validate minimum
		if (minutes < min) {
			return Optional.of(ErrorIdFactory.getTimeMinErrorId());
		}

		// validate maximum
		if (minutes > max) {
			return Optional.of(ErrorIdFactory.getTimeMaxErrorId());
		}

		return Optional.empty();
	}
	
	private static int getMinutes(String value) {
		String[] times = value.split(":");
		int hour = Integer.parseInt(times[0]);
		int minute = Integer.parseInt(times[1]);	
		return hour * 60 + minute;
	}
	
}
