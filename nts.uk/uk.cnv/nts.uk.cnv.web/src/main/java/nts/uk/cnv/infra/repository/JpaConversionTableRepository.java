package nts.uk.cnv.infra.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.cnv.dom.conversionsql.WhereSentence;
import nts.uk.cnv.dom.conversiontable.ConversionTable;
import nts.uk.cnv.dom.conversiontable.ConversionTableRepository;
import nts.uk.cnv.dom.conversiontable.OneColumnConversion;
import nts.uk.cnv.dom.service.ConversionInfo;
import nts.uk.cnv.infra.entity.conversiontable.ScvmtConversionColumn;
import nts.uk.cnv.infra.entity.conversiontable.ScvmtConversionTable;

@Stateless
public class JpaConversionTableRepository extends JpaRepository implements ConversionTableRepository {

	@Override
	public Optional<ConversionTable> get(ConversionInfo info, String category) {
		String query = "SELECT c FROM ScvmtConversionTable c WHERE c.pk.categoryName = :category ORDER BY cp.sequenceNo ASC";
		return this.queryProxy().query(query, ScvmtConversionTable.class)
			.setParameter("category", category)
			.getList().stream()
				.map(cp -> cp.toDomain(
						info,
						getWhereSentences(cp.pk.categoryName, cp.pk.sequenceNo),
						getConversionMap(cp.pk.categoryName, cp.pk.sequenceNo)))
				.findFirst();
	}

	@Override
	public List<OneColumnConversion> findColumns(String tableName) {
		String query = "SELECT c FROM ScvmtConversionColumn c WHERE c.pk.targetTableName = :name";
		return this.queryProxy().query(query, ScvmtConversionColumn.class)
			.setParameter("name", tableName)
			.getList().stream()
				.map(cp -> cp.toDomain(null))
				.collect(Collectors.toList());
	}

	private List<WhereSentence> getWhereSentences(String categoryName, int sequenceNo) {
		//TODO:
		return null;
	}

	private List<OneColumnConversion> getConversionMap(String categoryName, int sequenceNo) {
		//TODO:
		return null;
	}

}
