package nts.uk.cnv.dom.td.alteration.content;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.EqualsAndHashCode;
import nts.uk.cnv.dom.td.alteration.AlterationType;
import nts.uk.cnv.dom.td.tabledesign.Indexes;
import nts.uk.cnv.dom.td.tabledesign.TableDesign;
import nts.uk.cnv.dom.td.tabledesign.TableDesignBuilder;

@EqualsAndHashCode(callSuper= false)
public class ChangeIndex extends AlterationContent {
	private final String indexName;
	private final List<String> columnNames;
	private final boolean clustred;
	private final boolean unique;

	public ChangeIndex(String indexName, List<String> columnNames, boolean clustred, boolean unique) {
		super(AlterationType.INDEX_CHANGE);
		this.indexName = indexName;
		this.columnNames = columnNames;
		this.clustred = clustred;
		this.unique = unique;
	}

	public static List<AlterationContent> create(Optional<TableDesign> base, Optional<TableDesign> altered) {
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
				result.add(new ChangeIndex(alterdIdx.getName(), alterdIdx.getColumns(), alterdIdx.isClustered(), alterdIdx.isUnique()));
			}
		}

		return result;
	}

	public static boolean applicable(Optional<TableDesign> base, Optional<TableDesign> altered) {
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
	public TableDesignBuilder apply(TableDesignBuilder builder) {
		return builder.index(this.indexName, this.columnNames, this.clustred, this.unique);
	}
}
