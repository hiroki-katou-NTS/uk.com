package nts.uk.ctx.exio.infra.repository.exo.dataformat;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.exo.dataformat.init.NumberDataFmSet;
import nts.uk.ctx.exio.dom.exo.dataformat.init.NumberDataFmSetRepository;
import nts.uk.ctx.exio.infra.entity.exo.dataformat.OiomtNumberDataFmSet;
import nts.uk.ctx.exio.infra.entity.exo.dataformat.OiomtNumberDataFmSetPk;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaNumberDataFmSetRepository extends JpaRepository implements NumberDataFmSetRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiomtNumberDataFmSet f";

	@Override
	public List<NumberDataFmSet> getAllNumberDataFmSet() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, OiomtNumberDataFmSet.class)
				.getList(item -> item.toDomain());
	}

	@Override
	public Optional<NumberDataFmSet> getNumberDataFmSetById(String cId) {
		val entity = this.queryProxy().find(new OiomtNumberDataFmSetPk(cId), OiomtNumberDataFmSet.class);
		if (entity.isPresent()) {
			Optional.of(entity.get().toDomain());
		}
		return Optional.empty();
	}

	@Override
	public void add(NumberDataFmSet domain) {
		this.commandProxy().insert(OiomtNumberDataFmSet.toEntity(domain));
	}

	@Override
	public void update(NumberDataFmSet domain) {
		this.commandProxy().update(OiomtNumberDataFmSet.toEntity(domain));
	}

	@Override
	public void remove() {
		this.commandProxy().remove(OiomtNumberDataFmSet.class, new OiomtNumberDataFmSetPk());
	}
}
