package nts.uk.shr.com.validate.validator;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

import nts.uk.shr.com.validate.constraint.implement.StringCharType;
import nts.uk.shr.com.validate.constraint.implement.StringConstraint;

public class StringValidatorTest {

	// ALPHABET
	@Test
	public void testAlphabet() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.ALPHABET, 10);
		Optional<String> result = StringValidator.validate(constraint, "aBc");
		Assert.assertEquals(result.isPresent(), false);
	}

	@Test
	public void testAlphabetFalse1() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.ALPHABET, 10);
		Optional<String> result = StringValidator.validate(constraint, "aBc1");
		Assert.assertEquals(result.get(), ErrorIdFactory.CharTypeErrorId);
	}

	@Test
	public void testAlphabetFalse2() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.ALPHABET, 10);
		Optional<String> result = StringValidator.validate(constraint, "aBcあ");
		Assert.assertEquals(result.get(), ErrorIdFactory.CharTypeErrorId);
	}

	@Test
	public void testAlphabetFalse3() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.ALPHABET, 10);
		Optional<String> result = StringValidator.validate(constraint, "aBcカ");
		Assert.assertEquals(result.get(), ErrorIdFactory.CharTypeErrorId);
	}

	@Test
	public void testAlphabetFalse4() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.ALPHABET, 10);
		Optional<String> result = StringValidator.validate(constraint, "aB c");
		Assert.assertEquals(result.get(), ErrorIdFactory.CharTypeErrorId);
	}

	// NUMERIC

	@Test
	public void testNumeric() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.NUMERIC, 10);
		Optional<String> result = StringValidator.validate(constraint, "102");
		Assert.assertEquals(result.isPresent(), false);
	}

	@Test
	public void testNumericFalse() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.NUMERIC, 10);
		Optional<String> result = StringValidator.validate(constraint, "a102");
		Assert.assertEquals(result.get(), ErrorIdFactory.CharTypeErrorId);
	}

	@Test
	public void testNumericFalse1() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.NUMERIC, 10);
		Optional<String> result = StringValidator.validate(constraint, "102あ");
		Assert.assertEquals(result.get(), ErrorIdFactory.CharTypeErrorId);
	}

	@Test
	public void testNumericFalse2() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.NUMERIC, 10);
		Optional<String> result = StringValidator.validate(constraint, "a102カ");
		Assert.assertEquals(result.get(), ErrorIdFactory.CharTypeErrorId);
	}

	@Test
	public void testNumericFalse3() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.NUMERIC, 10);
		Optional<String> result = StringValidator.validate(constraint, "102　");
		Assert.assertEquals(result.get(), ErrorIdFactory.CharTypeErrorId);
	}

	// ALPHA_NUMERIC

	@Test
	public void testAlphaNumeric() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.ALPHA_NUMERIC, 10);
		Optional<String> result = StringValidator.validate(constraint, "aBc102");
		Assert.assertEquals(result.isPresent(), false);
	}

	@Test
	public void testAlphaNumericFalse() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.ALPHA_NUMERIC, 10);
		Optional<String> result = StringValidator.validate(constraint, "aBc102あ");
		Assert.assertEquals(result.get(), ErrorIdFactory.CharTypeErrorId);
	}

	@Test
	public void testAlphaNumericFalse1() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.ALPHA_NUMERIC, 10);
		Optional<String> result = StringValidator.validate(constraint, "aBc102カ");
		Assert.assertEquals(result.get(), ErrorIdFactory.CharTypeErrorId);
	}

	@Test
	public void testAlphaNumericFalse2() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.ALPHA_NUMERIC, 10);
		Optional<String> result = StringValidator.validate(constraint, "aBc102 ");
		Assert.assertEquals(result.get(), ErrorIdFactory.CharTypeErrorId);
	}

	// HIRAGANA
	@Test
	public void testHiragana() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.HIRAGANA, 10);
		Optional<String> result = StringValidator.validate(constraint, "ありがとう");
		Assert.assertEquals(result.isPresent(), false);
	}

	public void testHiraganaFalse() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.HIRAGANA, 10);
		Optional<String> result = StringValidator.validate(constraint, "ありがとうa");
		Assert.assertEquals(result.get(), ErrorIdFactory.CharTypeErrorId);
	}

	public void testHiraganaFalse1() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.HIRAGANA, 10);
		Optional<String> result = StringValidator.validate(constraint, "ありがとう1");
		Assert.assertEquals(result.get(), ErrorIdFactory.CharTypeErrorId);
	}

	public void testHiraganaFalse2() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.HIRAGANA, 10);
		Optional<String> result = StringValidator.validate(constraint, "ありがとうカ");
		Assert.assertEquals(result.get(), ErrorIdFactory.CharTypeErrorId);
	}

	public void testHiraganaFalse3() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.HIRAGANA, 10);
		Optional<String> result = StringValidator.validate(constraint, "ありがとう　");
		Assert.assertEquals(result.get(), ErrorIdFactory.CharTypeErrorId);
	}

	// KATAKANA

	@Test
	public void testKatakana() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.KATAKANA, 10);
		Optional<String> result = StringValidator.validate(constraint, "アメリカ");
		Assert.assertEquals(result.isPresent(), false);
	}

	@Test
	public void testKatakanaFalse() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.KATAKANA, 10);
		Optional<String> result = StringValidator.validate(constraint, "アメリカa");
		Assert.assertEquals(result.get(), ErrorIdFactory.CharTypeErrorId);
	}

	@Test
	public void testKatakanaFalse1() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.KATAKANA, 10);
		Optional<String> result = StringValidator.validate(constraint, "アメリカ1");
		Assert.assertEquals(result.get(), ErrorIdFactory.CharTypeErrorId);
	}

	@Test
	public void testKatakanaFalse2() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.KATAKANA, 10);
		Optional<String> result = StringValidator.validate(constraint, "アメリカあ");
		Assert.assertEquals(result.get(), ErrorIdFactory.CharTypeErrorId);
	}

	@Test
	public void testKatakanaFalse3() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.KATAKANA, 10);
		Optional<String> result = StringValidator.validate(constraint, "アメリカ ");
		Assert.assertEquals(result.get(), ErrorIdFactory.CharTypeErrorId);
	}

	@Test
	public void testAnyHalfWidth() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.ANY_HALF_WIDTH, 10);
		Optional<String> result = StringValidator.validate(constraint, "ｶa12 ");
		Assert.assertEquals(result.isPresent(), false);
	}

	@Test
	public void testAnyHalfWidthFalse() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.ANY_HALF_WIDTH, 10);
		Optional<String> result = StringValidator.validate(constraint, "ｶa12あ");
		Assert.assertEquals(result.get(), ErrorIdFactory.CharTypeErrorId);
	}

	@Test
	public void testAnyHalfWidthFalse1() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.ANY_HALF_WIDTH, 10);
		Optional<String> result = StringValidator.validate(constraint, "ｶa12カ");
		Assert.assertEquals(result.get(), ErrorIdFactory.CharTypeErrorId);
	}

	// max length
	@Test
	public void testMaxLength() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.NUMERIC, 10);
		Optional<String> result = StringValidator.validate(constraint, "0123456789");
		Assert.assertEquals(result.isPresent(), false);
	}

	@Test
	public void testMaxLengthFalse() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.NUMERIC, 10);
		Optional<String> result = StringValidator.validate(constraint, "01234567891");
		Assert.assertEquals(result.get(), ErrorIdFactory.MaxLengthErrorId);
	}

	// test regular expression
		// StampNumber's regular expression
	private static String regularExpression = "^[a-zA-Z0-9\\s#$%&()~:|{}*+?@'<>_/;\"\\\\\\[\\]\\`-]{1,20}$";
	
	@Test
	public void testRegularExpressionTrue() {
		StringConstraint constraint = new StringConstraint(1, 30, regularExpression);
		Optional<String> result = StringValidator.validate(constraint, "Aa0 #$%&()~:|{}*+?@'");
		Assert.assertEquals(result.isPresent(), false);
	}

	@Test
	public void testRegularExpressionTrue1() {
		StringConstraint constraint = new StringConstraint(1, 30, regularExpression);
		Optional<String> result = StringValidator.validate(constraint, "<>_/;\\\"[]`-");
		Assert.assertEquals(result.isPresent(), false);
	}

	public void testRegularExpressionFalse00() {
		StringConstraint constraint = new StringConstraint(1, 30, regularExpression);
		Optional<String> result = StringValidator.validate(constraint, "あ");
		Assert.assertEquals(result.get(), ErrorIdFactory.RegExpErrorId);
	}

	public void testRegularExpressionFalse01() {
		StringConstraint constraint = new StringConstraint(1, 30, regularExpression);
		Optional<String> result = StringValidator.validate(constraint, "ア");
		Assert.assertEquals(result.get(), ErrorIdFactory.RegExpErrorId);
	}

	public void testRegularExpressionFalse02() {
		StringConstraint constraint = new StringConstraint(1, 30, regularExpression);
		Optional<String> result = StringValidator.validate(constraint, "ｱ");
		Assert.assertEquals(result.get(), ErrorIdFactory.RegExpErrorId);
	}

	public void testRegularExpressionFalse_Maxlength() {
		StringConstraint constraint = new StringConstraint(1, 16, regularExpression);
		Optional<String> result = StringValidator.validate(constraint, "01234567891234567");
		Assert.assertEquals(result.get(), ErrorIdFactory.RegExpErrorId);
	}

}