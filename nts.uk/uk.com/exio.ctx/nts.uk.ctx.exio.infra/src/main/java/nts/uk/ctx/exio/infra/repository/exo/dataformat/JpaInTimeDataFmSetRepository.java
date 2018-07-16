package nts.uk.ctx.exio.infra.repository.exo.dataformat;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import lombok.val;
import nts.uk.ctx.exio.dom.exo.dataformat.InTimeDataFmSet;
import nts.uk.ctx.exio.dom.exo.dataformat.InTimeDataFmSetRepository;
import nts.uk.ctx.exio.infra.entity.exo.dataformat.OiomtInTimeDataFmSet;
import nts.uk.ctx.exio.infra.entity.exo.dataformat.OiomtInTimeDataFmSetPk;
import nts.uk.shr.com.context.AppContexts;
import nts.arc.layer.infra.data.JpaRepository;

@Stateless
public class JpaInTimeDataFmSetRepository extends JpaRepository implements InTimeDataFmSetRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiomtInTimeDataFmSet f";

	@Override
	public List<InTimeDataFmSet> getAllInTimeDataFmSet() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, OiomtInTimeDataFmSet.class)
				.getList(item -> item.toDomain());
	}

	@Override
	public Optional<InTimeDataFmSet> getInTimeDataFmSetById() {
		String cId = AppContexts.user().companyId();
		val entity = this.queryProxy().find(new OiomtInTimeDataFmSetPk(cId), OiomtInTimeDataFmSet.class);
		if (entity.isPresent()) {
			return Optional.of(entity.get().toDomain());
		}
		return Optional.empty();
	}

	@Override
	public void add(InTimeDataFmSet domain) {
		this.commandProxy().insert(OiomtInTimeDataFmSet.toEntity(domain));
	}

	@Override
	public void update(InTimeDataFmSet domain) {
		this.commandProxy().update(OiomtInTimeDataFmSet.toEntity(domain));
	}

	@Override
	public void remove() {
		this.commandProxy().remove(OiomtInTimeDataFmSet.class, new OiomtInTimeDataFmSetPk());
	}
}
