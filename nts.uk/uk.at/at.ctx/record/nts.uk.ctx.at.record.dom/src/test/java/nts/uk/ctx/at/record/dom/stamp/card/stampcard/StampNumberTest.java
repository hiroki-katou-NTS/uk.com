package nts.uk.ctx.at.record.dom.stamp.card.stampcard;

import static org.junit.Assert.*;

import org.junit.Test;

import nts.arc.primitive.PrimitiveValueConstraintException;

public class StampNumberTest {

	@Test
	public void test() {
		new StampNumber("<10A_-%:`\"a").validate();
	}

	@Test(expected = PrimitiveValueConstraintException.class)
	public void test2() {
		new StampNumber("あ").validate();
	}
	
	@Test(expected = PrimitiveValueConstraintException.class)
	public void test3() {
		new StampNumber("123456789012345678901").validate();
	}

}
