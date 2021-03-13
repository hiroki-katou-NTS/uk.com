package nts.uk.cnv.dom.td.alteration.content;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.EqualsAndHashCode;
import nts.uk.cnv.dom.td.alteration.AlterationType;
import nts.uk.cnv.dom.td.schema.prospect.TableProspectBuilder;
import nts.uk.cnv.dom.td.schema.tabledesign.Indexes;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;

@EqualsAndHashCode(callSuper= false)
public class ChangeIndex extends AlterationContent {
	private final String indexName;
	private final List<String> columnNames;
	private final boolean clustred;

	public ChangeIndex(String indexName, List<String> columnNames, boolean clustred) {
		super(AlterationType.INDEX_CHANGE);
		this.indexName = indexName;
		this.columnNames = columnNames;
		this.clustred = clustred;
	}

	public static List<AlterationContent> create(Optional<? extends TableDesign> base, Optional<TableDesign> altered) {
		List<Indexes> baseIndexes = base.get().getIndexes().stream()
				.filter(idx -> idx.isIndex())
				.collect(Collectors.toList());
		List<Indexes> alteredIndexes = altered.get().getIndexes().stream()
				.filter(idx -> idx.isIndex())
				.collect(Collectors.toList());

		List<AlterationContent> result = new ArrayList<>();
		for(int i=0; i<alteredIndexes.size(); i++) {
			Indexes baseIdx = baseIndexes.get(i);
			Indexes alterdIdx = alteredIndexes.get(i);
			if(!baseIdx.equals(alterdIdx)) {
				result.add(new ChangeIndex(alterdIdx.getName(), alterdIdx.getColumns(), alterdIdx.isClustered()));
			}
		}

		return result;
	}

	public static boolean applicable(Optional<? extends TableDesign> base, Optional<TableDesign> altered) {
		if(!base.isPresent() || !altered.isPresent()) {
			return false;
		}

		List<Indexes> baseIndexes = base.get().getIndexes().stream()
				.filter(idx -> idx.isIndex())
				.collect(Collectors.toList());
		List<Indexes> alteredIndexes = altered.get().getIndexes().stream()
				.filter(idx -> idx.isIndex())
				.collect(Collectors.toList());

		if(baseIndexes.size() != alteredIndexes.size()) {
			return true;
		}

		for(int i=0; i<alteredIndexes.size(); i++) {
			Indexes baseIdx = baseIndexes.get(i);
			Indexes alterdIdx = alteredIndexes.get(i);
			if(!baseIdx.equals(alterdIdx)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public TableProspectBuilder apply(String alterationId, TableProspectBuilder builder) {
		return builder.index(
				alterationId,
				this.indexName, this.columnNames, this.clustred);
	}
}
