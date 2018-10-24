package nts.uk.ctx.at.record.pub.dailymonthlyprocessing;

import java.util.List;

public interface TargetPersonPub {
	List<TargetPersonExport> getTargetPerson(String empCalAndSumExecLogId);
}
