package nts.uk.shr.com.validate.validator;

import java.util.Optional;

public class TimeMinMaxValidator {

	public static Optional<String> validateMinMax(int min, int max, String value) {
		int minutes = TimeMinMaxValidator.getMinutes(value);
		
		// validate minimum
		if (minutes < min) {
			return Optional.of(ErrorIdFactory.TimeMinErrorId);
		}

		// validate maximum
		if (minutes > max) {
			return Optional.of(ErrorIdFactory.TimeMaxErrorId);
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
