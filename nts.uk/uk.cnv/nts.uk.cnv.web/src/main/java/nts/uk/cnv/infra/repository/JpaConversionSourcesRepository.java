package nts.uk.cnv.infra.repository;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.text.IdentifierUtil;
import nts.uk.cnv.dom.conversiontable.ConversionSource;
import nts.uk.cnv.dom.conversiontable.ConversionSourcesRepository;
import nts.uk.cnv.infra.entity.conversiontable.ScvmtConversionSources;

@Stateless
public class JpaConversionSourcesRepository extends JpaRepository implements ConversionSourcesRepository {

	@Override
	public Optional<ConversionSource> get(String sourceId) {
		Optional<ScvmtConversionSources> entity = this.queryProxy().find(sourceId, ScvmtConversionSources.class);

		return entity.map(e -> e.toDomain());
	}

	@Override
	public List<ConversionSource> getByCategory(String category) {
		String query = "SELECT cs FROM ScvmtConversionSources cs WHERE cs.categoryName = :category";

		return this.queryProxy().query(query, ScvmtConversionSources.class)
			.setParameter("category", category)
			.getList(entity -> entity.toDomain());
	}

	@Override
	public String insert(ConversionSource source) {
		String newId = IdentifierUtil.randomUniqueId();

		ScvmtConversionSources entity = new ScvmtConversionSources(
				newId,
				source.getCategory(),
				source.getSourceTableName(),
				source.getCondition(),
				source.getMemo(),
				source.getDateColumnName().orElse(""),
				source.getStartDateColumnName().orElse(""),
				source.getEndDateColumnName().orElse("")
			);

		this.commandProxy().insert(entity);

		return newId;
	}

	@Override
	public void update(ConversionSource source) {
		this.commandProxy().update(source);
	}

	@Override
	public void delete(String sourceId) {
		this.commandProxy().remove(ScvmtConversionSources.class, sourceId);
	}
}
