package nts.uk.ctx.sys.gateway.dom.securitypolicy;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.sys.gateway.dom.loginold.ContractCode;

@Getter
public class PasswordPolicy extends AggregateRoot {
	private ContractCode contractCode;
	private NotificationPasswordChange notificationPasswordChange;
	private boolean loginCheck;
	private boolean initialPasswordChange;
	private boolean isUse;
	private PasswordHistoryCount historyCount;
	private PasswordLowestDigits lowestDigits;
	private PasswordValidityPeriod validityPeriod;
	private NumberOfDigits numberOfDigits;
	private SymbolCharacters symbolCharacters;
	private AlphabetDigit alphabetDigit;

	public PasswordPolicy(ContractCode contractCode, NotificationPasswordChange notificationPasswordChange,
			boolean loginCheck, boolean initialPasswordChange, boolean isUse, PasswordHistoryCount historyCount,
			PasswordLowestDigits lowestDigits, PasswordValidityPeriod validityPeriod, NumberOfDigits numberOfDigits,
			SymbolCharacters symbolCharacters, AlphabetDigit alphabetDigit) {
		super();
		this.contractCode = contractCode;
		this.notificationPasswordChange = notificationPasswordChange;
		this.loginCheck = loginCheck;
		this.initialPasswordChange = initialPasswordChange;
		this.isUse = isUse;
		this.historyCount = historyCount;
		this.lowestDigits = lowestDigits;
		this.validityPeriod = validityPeriod;
		this.numberOfDigits = numberOfDigits;
		this.symbolCharacters = symbolCharacters;
		this.alphabetDigit = alphabetDigit;
	}

	public static PasswordPolicy createFromJavaType(String contractCode, int notificationPasswordChange,
			boolean loginCheck, boolean initialPasswordChange, boolean isUse, int historyCount, int lowestDigits,
			int validityPeriod, int numberOfDigits, int symbolCharacters, int alphabetDigit) {
		return new PasswordPolicy(new ContractCode(contractCode),
				new NotificationPasswordChange(new BigDecimal(notificationPasswordChange)), loginCheck,
				initialPasswordChange, isUse, new PasswordHistoryCount(new BigDecimal(historyCount)),
				new PasswordLowestDigits(new BigDecimal(lowestDigits)),
				new PasswordValidityPeriod(new BigDecimal(validityPeriod)),
				new NumberOfDigits(new BigDecimal(numberOfDigits)),
				new SymbolCharacters(new BigDecimal(symbolCharacters)),
				new AlphabetDigit(new BigDecimal(alphabetDigit)));

	}

}
