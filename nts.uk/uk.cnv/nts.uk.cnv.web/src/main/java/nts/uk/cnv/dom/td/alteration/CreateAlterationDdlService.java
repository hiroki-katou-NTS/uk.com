package nts.uk.cnv.dom.td.alteration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
		Map<String,TableProspectBuilder> builderMap = new HashMap<>();
		alterSummaries.stream()
			.map(summary -> require.getAlter(summary.getAlterId()))
			.forEach(alter -> {
				if(!builderMap.containsKey(alter.getTableId())) {
					Optional<TableSnapshot> tss = require.getSnapshot(latestSs.getSnapshotId(), alter.getTableId());
					builderMap.put(alter.getTableId(),
						tss.isPresent()
							? new TableProspectBuilder(tss.get())
							: TableProspectBuilder.empty());
				}
				TableProspectBuilder builder = builderMap.get(alter.getTableId());
				sb.append(alter.createAlterDdl(builder, dataType));
				alter.apply(builder);

				//builderMap.put(alter.getTableId(), builder);
			});
		return sb.toString();
	}

	public interface Require {
		List<AlterationSummary> getAlterSummaryBy(String orderId);
		SchemaSnapshot getSchemaLatest();
		Optional<TableSnapshot> getSnapshot(String snapshotId, String tableId);
		Alteration getAlter(String alterationId);
	}
}
