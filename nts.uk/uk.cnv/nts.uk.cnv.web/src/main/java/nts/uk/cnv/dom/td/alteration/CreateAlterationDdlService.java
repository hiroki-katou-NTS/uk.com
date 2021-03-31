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
import nts.uk.cnv.dom.td.tabledefinetype.databasetype.DatabaseType;

@Stateless
public class CreateAlterationDdlService {

	public String createByOrderEvent(CreateByOrderEventRequire require, String orderId, DatabaseType type) {
		List<AlterationSummary> alterSummaries = require.getAlterSummaryBy(orderId);

		return createDdl(require, alterSummaries, type);
	}

	public String createByDeliveryEvent(CreateByDeliveryEventRequire require, String deliveryId, DatabaseType type) {
		List<AlterationSummary> alterSummaries = require.getAlterSummaryBy(deliveryId);

		return createDdl(require, alterSummaries, type);
	}

	private String createDdl(Require require, List<AlterationSummary> alterSummaries, DatabaseType type) {
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
				sb.append(alter.createAlterDdl(builder, type.spec()));
				alter.apply(builder);
			});
		return sb.toString();
	}

	public interface CreateByOrderEventRequire extends Require {
		List<AlterationSummary> getAlterSummaryBy(String orderId);
	}

	public interface CreateByDeliveryEventRequire extends Require {
		List<AlterationSummary> getAlterSummaryBy(String deliveryId);
	}

	public interface Require{
		SchemaSnapshot getSchemaLatest();
		Optional<TableSnapshot> getSnapshot(String snapshotId, String tableId);
		Alteration getAlter(String alterationId);
	}
}
