package nts.uk.ctx.exio.dom.input.validation.validationtestswitchcase;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;

import nts.uk.ctx.exio.dom.input.DataItem;
import nts.uk.ctx.exio.dom.input.validation.ValidationHelper.DUMMY;
import nts.uk.ctx.exio.dom.input.validation.condition.user.CompareValueCondition;
import nts.uk.ctx.exio.dom.input.validation.condition.user.Validation;
import nts.uk.ctx.exio.dom.input.validation.condition.user.type.date.DateCondition;

public class NotCond {
	@Test
	public void success() {
		Validation validation = new DateCondition(
				CompareValueCondition.NOT_COND,
				Optional.of(DUMMY.DATE),
				Optional.of(DUMMY.DATE));
		DataItem dummyItem = new DataItem(DUMMY.ITEM_NO, DUMMY.VALUE);
		
		assertThat(validation.validate(dummyItem)).isTrue();
	}		

}
