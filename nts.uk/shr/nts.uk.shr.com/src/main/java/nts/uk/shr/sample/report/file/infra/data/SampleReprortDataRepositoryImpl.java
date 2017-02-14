package nts.uk.shr.sample.report.file.infra.data;

import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.shr.sample.report.app.SampleReportDataItem;
import nts.uk.shr.sample.report.app.SampleReportDataRepository;

@Stateless
public class SampleReprortDataRepositoryImpl implements SampleReportDataRepository {
	public List<SampleReportDataItem> getItems() {
		return Arrays.asList(
				new SampleReportDataItem("001", "test"),
				new SampleReportDataItem("002", "帳票データ"),
				new SampleReportDataItem("003", "aaaaaaaaaaaaaaaaaaaaaaaaa"));
	}
}
