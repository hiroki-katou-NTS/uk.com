package nts.uk.ctx.at.record.dom.workrecord.workfixed;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;

public class WorkFixedTest {

	@Test
	public void getters() {
		WorkFixed workfixed = new WorkFixed(new TestWorkFixedMemento());
		NtsAssert.invokeGetters(workfixed);
	}
}

class TestWorkFixedMemento implements WorkFixedGetMemento {

	@Override
	public Integer getClosureId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getConfirmPId() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String getWorkPlaceId() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public ConfirmClsStatus getConfirmClsStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GeneralDate getFixedDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public YearMonth getProcessYm() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCid() {
		// TODO Auto-generated method stub
		return "";
	}
	
}