package nts.uk.cnv.infra.repository;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.cnv.dom.conversiontable.ConversionSource;
import nts.uk.cnv.dom.conversiontable.ConversionSourcesRepository;
import nts.uk.cnv.dom.service.ConversionInfo;
import nts.uk.cnv.infra.entity.conversiontable.ScvmtConversionSources;

@Stateless
public class JpaConversionSourcesRepository extends JpaRepository implements ConversionSourcesRepository {

	@Override
	public ConversionSource get(ConversionInfo info, String sourceId) {
		Optional<ScvmtConversionSources> entity = this.queryProxy().find(sourceId, ScvmtConversionSources.class);

		return entity.get().toDomain(info);
	}


}
