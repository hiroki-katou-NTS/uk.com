package nts.uk.cnv.dom.td.alteration;

import java.util.List;

import javax.ejb.Stateless;

import nts.uk.cnv.dom.td.alteration.summary.AlterationSummary;
import nts.uk.cnv.dom.td.schema.prospect.definition.TableProspectBuilder;
import nts.uk.cnv.dom.td.schema.snapshot.SchemaSnapshot;
import nts.uk.cnv.dom.td.schema.snapshot.TableSnapshot;
import nts.uk.cnv.dom.td.tabledefinetype.UkDataType;

@Stateless
public class CreateAlterationDdlService {

	public String createByOrderEvent(Require require, String orderId) {
		List<AlterationSummary> alterSummaries = require.getAlterSummaryBy(orderId);

		UkDataType dataType = new UkDataType();
		StringBuilder sb = new StringBuilder();

		SchemaSnapshot latestSs = require.getSchemaLatest();
		alterSummaries.stream()
			.map(summary -> require.getAlter(summary.getAlterId()))
			.forEach(alter -> {
				TableSnapshot tss = require.getSnapshot(latestSs.getSnapshotId(), alter.getTableId());
				TableProspectBuilder builder = new TableProspectBuilder(tss);
				sb.append(alter.createAlterDdl(builder, dataType));
			});
		return sb.toString();
	}

	public interface Require {
		List<AlterationSummary> getAlterSummaryBy(String orderId);
		SchemaSnapshot getSchemaLatest();
		TableSnapshot getSnapshot(String snapshotId, String tableId);
		Alteration getAlter(String alterationId);
	}
}
