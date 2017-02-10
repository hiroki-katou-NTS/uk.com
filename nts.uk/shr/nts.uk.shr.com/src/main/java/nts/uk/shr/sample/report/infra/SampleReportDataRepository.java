package nts.uk.shr.sample.report.infra;

import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;

@Stateless
public class SampleReportDataRepository {

	public List<SampleReportDataItem> getItems() {
		return Arrays.asList(
				new SampleReportDataItem("001", "test"),
				new SampleReportDataItem("002", "帳票データ"),
				new SampleReportDataItem("003", "aaaaaaaaaaaaaaaaaaaaaaaaa"));
	}
}
