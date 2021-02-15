package nts.uk.ctx.sys.gateway.dom.securitypolicy.password.complexity;


import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import lombok.val;
import nts.arc.testing.assertion.NtsAssert;

public class PasswordComplexityRequirementTest {

	private static final String NUMERALS = "0123456789";

	private static final String ALPHABETS_LOWER = "abcdefghijklmnopqrstuvwxyz";
	
	private static final String ALPHABETS_ALL = ALPHABETS_LOWER + ALPHABETS_LOWER.toUpperCase();

	@Test
	public void isNumeral() {
		
		boolean actual1 = NUMERALS.chars()
				.allMatch(c -> Privates.isNumeral((char)c));
		
		assertThat(actual1).isTrue();
		
		boolean actual2 = ALPHABETS_ALL.chars()
				.noneMatch(c -> Privates.isNumeral((char)c));

		assertThat(actual2).isTrue();
	}
	
	@Test
	public void isAlphabet() {
		
		boolean actual1 = ALPHABETS_ALL.chars()
				.allMatch(c -> Privates.isAlphabet((char)c));
		
		assertThat(actual1).isTrue();
		
		boolean actual2 = NUMERALS.chars()
				.noneMatch(c -> Privates.isAlphabet((char)c));

		assertThat(actual2).isTrue();
	}
	
	@Test
	public void validatePassword_0() {
		
		val target = requirement(0, 0, 0, 0);
		assertThat(target.validatePassword("")).isTrue();
	}
	
	@Test
	public void validatePassword_1() {
		
		val target = requirement(1, 0, 0, 0);
		assertThat(target.validatePassword("a")).isTrue();
		assertThat(target.validatePassword("")).isFalse();
	}
	
	@Test
	public void validatePassword_2() {
		
		val target = requirement(0, 1, 0, 0);
		assertThat(target.validatePassword("a")).isTrue();
		assertThat(target.validatePassword("1")).isFalse();
	}
	
	@Test
	public void validatePassword_3() {
		
		val target = requirement(0, 1, 1, 0);
		assertThat(target.validatePassword("a")).isFalse();
		assertThat(target.validatePassword("1")).isFalse();
		assertThat(target.validatePassword("a1")).isTrue();
	}
	
	@Test
	public void validatePassword_4() {
		
		val target = requirement(0, 1, 1, 1);
		assertThat(target.validatePassword("a")).isFalse();
		assertThat(target.validatePassword("1")).isFalse();
		assertThat(target.validatePassword("$")).isFalse();
		assertThat(target.validatePassword("a1")).isFalse();
		assertThat(target.validatePassword("a$")).isFalse();
		assertThat(target.validatePassword("1$")).isFalse();
		assertThat(target.validatePassword("a1$")).isTrue();
	}
	
	private static PasswordComplexityRequirement requirement(int minimum, int alphabet, int numeral, int symbol) {
		return new PasswordComplexityRequirement(
				new PasswordMinimumLength(minimum),
				digits(alphabet),
				digits(numeral),
				digits(symbol));
	}
	
	private static PasswordSpecifiedCharacterDigits digits(int d) {
		return new PasswordSpecifiedCharacterDigits(d);
	}

	private static class Privates {
		
		static boolean isNumeral(char c) {
			return NtsAssert.Invoke.staticMethod(PasswordComplexityRequirement.class, "isNumeral", c);
		}
		
		static boolean isAlphabet(char c) {
			return NtsAssert.Invoke.staticMethod(PasswordComplexityRequirement.class, "isAlphabet", c);
		}
	}
}
