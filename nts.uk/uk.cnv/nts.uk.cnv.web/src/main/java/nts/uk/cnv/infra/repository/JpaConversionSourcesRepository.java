package nts.uk.cnv.infra.repository;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.text.IdentifierUtil;
import nts.uk.cnv.core.dom.conversiontable.ConversionSource;
import nts.uk.cnv.core.infra.entity.conversiontable.ScvmtConversionSources;
import nts.uk.cnv.dom.conversiontable.ConversionSourcesRepository;
import nts.uk.cnv.dom.tabledesign.ErpTableDesignRepository;

@Stateless
public class JpaConversionSourcesRepository extends JpaRepository implements ConversionSourcesRepository {

	@Inject
	ErpTableDesignRepository erpTableRepo;

	@Override
	public Optional<ConversionSource> get(String sourceId) {
		Optional<ScvmtConversionSources> entity = this.queryProxy().find(sourceId, ScvmtConversionSources.class);

		return entity.map(e -> e.toDomain(erpTableRepo.getPkColumns(e.getSourceTableName())));
	}

	@Override
	public List<ConversionSource> getByCategory(String category) {
		String query = "SELECT cs FROM ScvmtConversionSources cs WHERE cs.categoryName = :category";

		return this.queryProxy().query(query, ScvmtConversionSources.class)
			.setParameter("category", category)
			.getList(entity -> entity.toDomain(erpTableRepo.getPkColumns(entity.getSourceTableName())));
	}

	@Override
	public String insert(ConversionSource source) {
		String newId = IdentifierUtil.randomUniqueId();

		ScvmtConversionSources entity = toEntity(source, newId);

		this.commandProxy().insert(entity);

		return newId;
	}

	@Override
	public void update(ConversionSource source) {
		this.commandProxy().update(toEntity(source, source.getSourceId()));
	}

	@Override
	public void delete(String sourceId) {
		this.commandProxy().remove(ScvmtConversionSources.class, sourceId);
	}

	private ScvmtConversionSources toEntity(ConversionSource source, String sourceId) {
		ScvmtConversionSources entity = new ScvmtConversionSources(
				sourceId,
				source.getCategory(),
				source.getSourceTableName(),
				source.getCondition(),
				source.getMemo(),
				source.getDateColumnName().orElse(null),
				source.getStartDateColumnName().orElse(null),
				source.getEndDateColumnName().orElse(null),
				source.getDateType().orElse(null)
			);
		return entity;
	}
}
