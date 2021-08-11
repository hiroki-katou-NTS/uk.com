package nts.uk.ctx.sys.gateway.dom.securitypolicy.password.complexity;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;

/**
 * 複雑さ
 * パスワードの複雑性要件
 */
@Value
public class PasswordComplexityRequirement implements DomainValue {

	/** 最低桁数 */
	private final PasswordMinimumLength minimumLength;

	/** 英字桁数 */
	private final PasswordSpecifiedCharacterDigits alphabetDigits;
	
	/** 数字桁数 */
	private final PasswordSpecifiedCharacterDigits numeralDigits;
	
	/** 記号桁数 */
	private final PasswordSpecifiedCharacterDigits symbolDigits;
	
	public static PasswordComplexityRequirement createFromJavaType(
			int minimumLength,
			int alphabetDigits,
			int numeralDigits,
			int symbolDigits) {
		
		return new PasswordComplexityRequirement(
				new PasswordMinimumLength(minimumLength),
				new PasswordSpecifiedCharacterDigits(alphabetDigits),
				new PasswordSpecifiedCharacterDigits(numeralDigits),
				new PasswordSpecifiedCharacterDigits(symbolDigits));
	}
	
	/**
	 * 検証する
	 * @param password
	 * @return
	 */
	public boolean validatePassword(String password) {
		
		if (password.length() < minimumLength.v()) {
			return false;
		}
		
		int alphabets = 0;
		int numerals = 0;
		int symbols = 0; // 半角英数字以外はすべて記号とみなす
		
		for (int i = 0; i < password.length(); i++) {
			char c = password.charAt(i);
			if (isAlphabet(c)) {
				alphabets++;
			} else if (isNumeral(c)) {
				numerals++;
			} else {
				symbols++;
			}
		}
		
		return alphabets >= alphabetDigits.v()
				&& numerals >= numeralDigits.v()
				&& symbols >= symbolDigits.v();
	}
	
	private static boolean isAlphabet(char c) {
		return 'a' <= c && c <= 'z' || 'A' <= c && c <= 'Z';
	}
	
	private static boolean isNumeral(char c) {
		return '0' <= c && c <= '9';
	}
}
