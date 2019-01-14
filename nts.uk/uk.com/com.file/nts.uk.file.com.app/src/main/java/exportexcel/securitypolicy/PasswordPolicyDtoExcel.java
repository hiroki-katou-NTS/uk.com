package exportexcel.securitypolicy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PasswordPolicyDtoExcel {
	public int notificationPasswordChange;
	public boolean loginCheck;
	public boolean initialPasswordChange;
	public boolean isUse;
	public int historyCount;
	public int lowestDigits;
	public int validityPeriod;
	public int numberOfDigits;
	public int symbolCharacters;
	public int alphabetDigit;
}
