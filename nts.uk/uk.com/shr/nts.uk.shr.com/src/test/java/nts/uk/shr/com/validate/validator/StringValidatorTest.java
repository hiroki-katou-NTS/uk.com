package nts.uk.shr.com.validate.validator;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

import nts.gul.util.value.ValueWithType;
import nts.uk.shr.com.validate.constraint.implement.StringCharType;
import nts.uk.shr.com.validate.constraint.implement.StringConstraint;

public class StringValidatorTest {

	// ALPHABET
	@Test
	public void testAlphabet() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.ALPHABET, 10);
		Optional<String> result = constraint.validate(new ValueWithType("aBc"));
		Assert.assertEquals(result.isPresent(), false);
	}

	@Test
	public void testAlphabetFalse1() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.ALPHABET, 10);
		Optional<String> result = constraint.validate(new ValueWithType("aBc1"));
		Assert.assertEquals(result.get(), ErrorIdFactory.CharTypeErrorId);
	}

	@Test
	public void testAlphabetFalse2() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.ALPHABET, 10);
		Optional<String> result = constraint.validate(new ValueWithType("aBcあ"));
		Assert.assertEquals(result.get(), ErrorIdFactory.CharTypeErrorId);
	}

	@Test
	public void testAlphabetFalse3() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.ALPHABET, 10);
		Optional<String> result = constraint.validate(new ValueWithType("aBcカ"));
		Assert.assertEquals(result.get(), ErrorIdFactory.CharTypeErrorId);
	}

	@Test
	public void testAlphabetFalse4() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.ALPHABET, 10);
		Optional<String> result = constraint.validate(new ValueWithType("aB c"));
		Assert.assertEquals(result.get(), ErrorIdFactory.CharTypeErrorId);
	}

	// NUMERIC

	@Test
	public void testNumeric() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.NUMERIC, 10);
		Optional<String> result = constraint.validate(new ValueWithType("102"));
		Assert.assertEquals(result.isPresent(), false);
	}

	@Test
	public void testNumericFalse() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.NUMERIC, 10);
		Optional<String> result = constraint.validate(new ValueWithType("a102"));
		Assert.assertEquals(result.get(), ErrorIdFactory.CharTypeErrorId);
	}

	@Test
	public void testNumericFalse1() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.NUMERIC, 10);
		Optional<String> result = constraint.validate(new ValueWithType("102あ"));
		Assert.assertEquals(result.get(), ErrorIdFactory.CharTypeErrorId);
	}

	@Test
	public void testNumericFalse2() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.NUMERIC, 10);
		Optional<String> result = constraint.validate(new ValueWithType("a102カ"));
		Assert.assertEquals(result.get(), ErrorIdFactory.CharTypeErrorId);
	}

	@Test
	public void testNumericFalse3() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.NUMERIC, 10);
		Optional<String> result = constraint.validate(new ValueWithType("102　"));
		Assert.assertEquals(result.get(), ErrorIdFactory.CharTypeErrorId);
	}

	// ALPHA_NUMERIC

	@Test
	public void testAlphaNumeric() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.ALPHA_NUMERIC, 10);
		Optional<String> result = constraint.validate(new ValueWithType("aBc102"));
		Assert.assertEquals(result.isPresent(), false);
	}

	@Test
	public void testAlphaNumericFalse() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.ALPHA_NUMERIC, 10);
		Optional<String> result = constraint.validate(new ValueWithType("aBc102あ"));
		Assert.assertEquals(result.get(), ErrorIdFactory.CharTypeErrorId);
	}

	@Test
	public void testAlphaNumericFalse1() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.ALPHA_NUMERIC, 10);
		Optional<String> result = constraint.validate(new ValueWithType("aBc102カ"));
		Assert.assertEquals(result.get(), ErrorIdFactory.CharTypeErrorId);
	}

	@Test
	public void testAlphaNumericFalse2() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.ALPHA_NUMERIC, 10);
		Optional<String> result = constraint.validate(new ValueWithType("aBc102 "));
		Assert.assertEquals(result.get(), ErrorIdFactory.CharTypeErrorId);
	}

	// HIRAGANA
	@Test
	public void testHiragana() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.HIRAGANA, 10);
		Optional<String> result = constraint.validate(new ValueWithType("ありがとう"));
		Assert.assertEquals(result.isPresent(), false);
	}

	public void testHiraganaFalse() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.HIRAGANA, 10);
		Optional<String> result = constraint.validate(new ValueWithType("ありがとうa"));
		Assert.assertEquals(result.get(), ErrorIdFactory.CharTypeErrorId);
	}

	public void testHiraganaFalse1() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.HIRAGANA, 10);
		Optional<String> result = constraint.validate(new ValueWithType("ありがとう1"));
		Assert.assertEquals(result.get(), ErrorIdFactory.CharTypeErrorId);
	}

	public void testHiraganaFalse2() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.HIRAGANA, 10);
		Optional<String> result = constraint.validate(new ValueWithType("ありがとうカ"));
		Assert.assertEquals(result.get(), ErrorIdFactory.CharTypeErrorId);
	}

	public void testHiraganaFalse3() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.HIRAGANA, 10);
		Optional<String> result = constraint.validate(new ValueWithType("ありがとう　"));
		Assert.assertEquals(result.get(), ErrorIdFactory.CharTypeErrorId);
	}

	// KATAKANA

	@Test
	public void testKatakana() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.KATAKANA, 10);
		Optional<String> result = constraint.validate(new ValueWithType("アメリカ"));
		Assert.assertEquals(result.isPresent(), false);
	}

	@Test
	public void testKatakanaFalse() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.KATAKANA, 10);
		Optional<String> result = constraint.validate(new ValueWithType("アメリカa"));
		Assert.assertEquals(result.get(), ErrorIdFactory.CharTypeErrorId);
	}

	@Test
	public void testKatakanaFalse1() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.KATAKANA, 10);
		Optional<String> result = constraint.validate(new ValueWithType("アメリカ1"));
		Assert.assertEquals(result.get(), ErrorIdFactory.CharTypeErrorId);
	}

	@Test
	public void testKatakanaFalse2() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.KATAKANA, 10);
		Optional<String> result = constraint.validate(new ValueWithType("アメリカあ"));
		Assert.assertEquals(result.get(), ErrorIdFactory.CharTypeErrorId);
	}

	@Test
	public void testKatakanaFalse3() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.KATAKANA, 10);
		Optional<String> result = constraint.validate(new ValueWithType("アメリカ "));
		Assert.assertEquals(result.get(), ErrorIdFactory.CharTypeErrorId);
	}

	@Test
	public void testAnyHalfWidth() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.ANY_HALF_WIDTH, 10);
		Optional<String> result = constraint.validate(new ValueWithType("ｶa12 "));
		Assert.assertEquals(result.isPresent(), false);
	}

	@Test
	public void testAnyHalfWidthFalse() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.ANY_HALF_WIDTH, 10);
		Optional<String> result = constraint.validate(new ValueWithType("ｶa12あ"));
		Assert.assertEquals(result.get(), ErrorIdFactory.CharTypeErrorId);
	}

	@Test
	public void testAnyHalfWidthFalse1() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.ANY_HALF_WIDTH, 10);
		Optional<String> result = constraint.validate(new ValueWithType("ｶa12カ"));
		Assert.assertEquals(result.get(), ErrorIdFactory.CharTypeErrorId);
	}

	// max length
	@Test
	public void testMaxLength() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.NUMERIC, 10);
		Optional<String> result = constraint.validate(new ValueWithType("0123456789"));
		Assert.assertEquals(result.isPresent(), false);
	}

	@Test
	public void testMaxLengthFalse() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.NUMERIC, 10);
		Optional<String> result = constraint.validate(new ValueWithType("01234567891"));
		Assert.assertEquals(result.get(), ErrorIdFactory.MaxLengthErrorId);
	}

	// test regular expression
	// StampNumber's regular expression
	private static String regularExpression = "^[a-zA-Z0-9\\s#$%&()~:|{}*+?@'<>_/;\"\\\\\\[\\]\\`-]{1,20}$";

	@Test
	public void testRegularExpressionTrue() {
		StringConstraint constraint = new StringConstraint(1, 30, regularExpression);
		Optional<String> result = constraint.validate(new ValueWithType("Aa0 #$%&()~:|{}*+?@'"));
		Assert.assertEquals(result.isPresent(), false);
	}

	@Test
	public void testRegularExpressionTrue1() {
		StringConstraint constraint = new StringConstraint(1, 30, regularExpression);
		Optional<String> result = constraint.validate(new ValueWithType("<>_/;\\\"[]`-"));
		Assert.assertEquals(result.isPresent(), false);
	}

	public void testRegularExpressionFalse00() {
		StringConstraint constraint = new StringConstraint(1, 30, regularExpression);
		Optional<String> result = constraint.validate(new ValueWithType("あ"));
		Assert.assertEquals(result.get(), ErrorIdFactory.RegExpErrorId);
	}

	public void testRegularExpressionFalse01() {
		StringConstraint constraint = new StringConstraint(1, 30, regularExpression);
		Optional<String> result = constraint.validate(new ValueWithType("ア"));
		Assert.assertEquals(result.get(), ErrorIdFactory.RegExpErrorId);
	}

	public void testRegularExpressionFalse02() {
		StringConstraint constraint = new StringConstraint(1, 30, regularExpression);
		Optional<String> result = constraint.validate(new ValueWithType("ｱ"));
		Assert.assertEquals(result.get(), ErrorIdFactory.RegExpErrorId);
	}

	public void testRegularExpressionFalse_Maxlength() {
		StringConstraint constraint = new StringConstraint(1, 16, regularExpression);
		Optional<String> result = constraint.validate(new ValueWithType("01234567891234567"));
		Assert.assertEquals(result.get(), ErrorIdFactory.RegExpErrorId);
	}

}
