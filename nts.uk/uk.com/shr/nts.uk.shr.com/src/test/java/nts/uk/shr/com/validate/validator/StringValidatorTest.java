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
		Assert.assertEquals(result.get(), ErrorIdFactory.getCharTypeErrorId());
	}
	
	@Test
	public void testAlphabetFalse2() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.ALPHABET, 10);
		Optional<String> result = StringValidator.validate(constraint, "aBcあ");
		Assert.assertEquals(result.get(), ErrorIdFactory.getCharTypeErrorId());
	}
	
	@Test
	public void testAlphabetFalse3() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.ALPHABET, 10);
		Optional<String> result = StringValidator.validate(constraint, "aBcカ");
		Assert.assertEquals(result.get(), ErrorIdFactory.getCharTypeErrorId());
	}
	
	@Test
	public void testAlphabetFalse4() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.ALPHABET, 10);
		Optional<String> result = StringValidator.validate(constraint, "aB c");
		Assert.assertEquals(result.get(), ErrorIdFactory.getCharTypeErrorId());
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
		Assert.assertEquals(result.get(), ErrorIdFactory.getCharTypeErrorId());
	}
	
	@Test
	public void testNumericFalse1() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.NUMERIC, 10);
		Optional<String> result = StringValidator.validate(constraint, "102あ");
		Assert.assertEquals(result.get(), ErrorIdFactory.getCharTypeErrorId());
	}
	
	@Test
	public void testNumericFalse2() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.NUMERIC, 10);
		Optional<String> result = StringValidator.validate(constraint, "a102カ");
		Assert.assertEquals(result.get(), ErrorIdFactory.getCharTypeErrorId());
	}
	
	@Test
	public void testNumericFalse3() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.NUMERIC, 10);
		Optional<String> result = StringValidator.validate(constraint, "102　");
		Assert.assertEquals(result.get(), ErrorIdFactory.getCharTypeErrorId());
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
		Assert.assertEquals(result.get(), ErrorIdFactory.getCharTypeErrorId());
	}
	
	@Test
	public void testAlphaNumericFalse1() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.ALPHA_NUMERIC, 10);
		Optional<String> result = StringValidator.validate(constraint, "aBc102カ");
		Assert.assertEquals(result.get(), ErrorIdFactory.getCharTypeErrorId());
	}
	
	@Test
	public void testAlphaNumericFalse2() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.ALPHA_NUMERIC, 10);
		Optional<String> result = StringValidator.validate(constraint, "aBc102 ");
		Assert.assertEquals(result.get(), ErrorIdFactory.getCharTypeErrorId());
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
		Assert.assertEquals(result.get(), ErrorIdFactory.getCharTypeErrorId());
	}
	
	public void testHiraganaFalse1() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.HIRAGANA, 10);
		Optional<String> result = StringValidator.validate(constraint, "ありがとう1");
		Assert.assertEquals(result.get(), ErrorIdFactory.getCharTypeErrorId());
	}
	
	public void testHiraganaFalse2() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.HIRAGANA, 10);
		Optional<String> result = StringValidator.validate(constraint, "ありがとうカ");
		Assert.assertEquals(result.get(), ErrorIdFactory.getCharTypeErrorId());
	}
	
	public void testHiraganaFalse3() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.HIRAGANA, 10);
		Optional<String> result = StringValidator.validate(constraint, "ありがとう　");
		Assert.assertEquals(result.get(), ErrorIdFactory.getCharTypeErrorId());
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
		Assert.assertEquals(result.get(), ErrorIdFactory.getCharTypeErrorId());
	}
	
	@Test
	public void testKatakanaFalse1() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.KATAKANA, 10);
		Optional<String> result = StringValidator.validate(constraint, "アメリカ1");
		Assert.assertEquals(result.get(), ErrorIdFactory.getCharTypeErrorId());
	}
	
	@Test
	public void testKatakanaFalse2() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.KATAKANA, 10);
		Optional<String> result = StringValidator.validate(constraint, "アメリカあ");
		Assert.assertEquals(result.get(), ErrorIdFactory.getCharTypeErrorId());
	}
	
	@Test
	public void testKatakanaFalse3() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.KATAKANA, 10);
		Optional<String> result = StringValidator.validate(constraint, "アメリカ ");
		Assert.assertEquals(result.get(), ErrorIdFactory.getCharTypeErrorId());
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
		Assert.assertEquals(result.get(), ErrorIdFactory.getCharTypeErrorId());
	}
	
	@Test
	public void testAnyHalfWidthFalse1() {
		StringConstraint constraint = new StringConstraint(1, StringCharType.ANY_HALF_WIDTH, 10);
		Optional<String> result = StringValidator.validate(constraint, "ｶa12カ");
		Assert.assertEquals(result.get(), ErrorIdFactory.getCharTypeErrorId());
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
		Assert.assertEquals(result.get(), ErrorIdFactory.getMaxLengthErrorId());
	}
}
