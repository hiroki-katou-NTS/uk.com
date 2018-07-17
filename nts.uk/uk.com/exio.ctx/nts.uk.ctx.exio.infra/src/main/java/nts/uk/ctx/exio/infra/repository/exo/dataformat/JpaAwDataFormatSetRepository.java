package nts.uk.ctx.exio.infra.repository.exo.dataformat;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.exo.dataformat.init.AwDataFormatSet;
import nts.uk.ctx.exio.dom.exo.dataformat.init.AwDataFormatSetRepository;
import nts.uk.ctx.exio.infra.entity.exo.dataformat.init.OiomtAwDataFormatSet;
import nts.uk.ctx.exio.infra.entity.exo.dataformat.init.OiomtAwDataFormatSetPk;

@Stateless
public class JpaAwDataFormatSetRepository extends JpaRepository implements AwDataFormatSetRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiomtAwDataFormatSet f";

	@Override
	public List<AwDataFormatSet> getAllAwDataFormatSet() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, OiomtAwDataFormatSet.class)
				.getList(item -> item.toDomain());
	}

	@Override
	public Optional<AwDataFormatSet> getAwDataFormatSetById(String cid) {
		val entity = this.queryProxy().find(new OiomtAwDataFormatSetPk(cid), OiomtAwDataFormatSet.class);
		if (entity.isPresent()) {
			return Optional.of(entity.get().toDomain());
		}
		return Optional.empty();
	}

	@Override
	public void add(AwDataFormatSet domain) {
		this.commandProxy().insert(OiomtAwDataFormatSet.toEntity(domain));
	}

	@Override
	public void update(AwDataFormatSet domain) {
		this.commandProxy().update(OiomtAwDataFormatSet.toEntity(domain));
	}

	@Override
	public void remove() {
		this.commandProxy().remove(OiomtAwDataFormatSet.class, new OiomtAwDataFormatSetPk());
	}
}
