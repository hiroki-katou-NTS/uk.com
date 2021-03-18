package nts.uk.cnv.infra.td.repository;

import java.util.List;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.cnv.dom.td.feature.Feature;
import nts.uk.cnv.dom.td.feature.FeatureRepository;
import nts.uk.cnv.infra.td.entity.feature.NemTdFeature;

public class JpaFeatureRepository extends JpaRepository implements FeatureRepository
{

	@Override
	public void insert(Feature domain) {
		this.commandProxy().insert(NemTdFeature.toEntity(domain));
		
	}

	@Override
	public List<Feature> get() {
		String jpql = "SELECT f FROM NemTdFeature f";

		return this.queryProxy().query(jpql, NemTdFeature.class)
				.getList(entity -> entity.toDomain());
	}
}
