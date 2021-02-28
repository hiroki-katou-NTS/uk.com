package nts.uk.cnv.infra.cnv.repository;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.text.IdentifierUtil;
import nts.uk.cnv.dom.cnv.conversiontable.ConversionSource;
import nts.uk.cnv.dom.cnv.conversiontable.ConversionSourcesRepository;
import nts.uk.cnv.infra.cnv.entity.conversiontable.ScvmtConversionSources;

@Stateless
public class JpaConversionSourcesRepository extends JpaRepository implements ConversionSourcesRepository {

	@Override
	public ConversionSource get(String sourceId) {
		Optional<ScvmtConversionSources> entity = this.queryProxy().find(sourceId, ScvmtConversionSources.class);

		return entity.get().toDomain();
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
				source.getMemo()
			);

		this.commandProxy().insert(entity);

		return newId;
	}

	@Override
	public void delete(String sourceId) {
		this.commandProxy().remove(ScvmtConversionSources.class, sourceId);
	}


}
