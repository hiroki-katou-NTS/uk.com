package nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition;

import java.util.List;

public interface FixedExtraMonFunAdapter {
	List<FixedExtraMonFunImport> getByEralCheckID(String monAlarmCheckID);
	
	void addFixedExtraMon(FixedExtraMonFunImport fixedExtraMon);
	
	void updateFixedExtraMon(FixedExtraMonFunImport fixedExtraMon);
	
	void deleteFixedExtraMon(String monAlarmCheckID);
}
