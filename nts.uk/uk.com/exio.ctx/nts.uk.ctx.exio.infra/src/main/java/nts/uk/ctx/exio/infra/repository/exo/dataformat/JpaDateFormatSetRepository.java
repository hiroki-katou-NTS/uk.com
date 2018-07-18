package nts.uk.ctx.exio.infra.repository.exo.dataformat;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.exo.dataformat.init.DateFormatSet;
import nts.uk.ctx.exio.dom.exo.dataformat.init.DateFormatSetRepository;
import nts.uk.ctx.exio.infra.entity.exo.dataformat.init.OiomtDateFormatSet;
import nts.uk.ctx.exio.infra.entity.exo.dataformat.init.OiomtDateFormatSetPk;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaDateFormatSetRepository extends JpaRepository implements DateFormatSetRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiomtDateFormatSet f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE ";
	private static final String SELECT_BY_CID = SELECT_BY_KEY_STRING + "f.dateFormatSetPk.cid = :cid";

	@Override
	public List<DateFormatSet> getAllDateFormatSet() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, OiomtDateFormatSet.class)
				.getList(item -> item.toDomain());
	}

	@Override
	public Optional<DateFormatSet> getDateFormatSetById(String cId) {
		val entity = this.queryProxy().find(new OiomtDateFormatSetPk(cId), OiomtDateFormatSet.class);
		if (entity.isPresent()) {
			return Optional.of(entity.get().toDomain());
		}
		return Optional.empty();
	}

	@Override
	public void add(DateFormatSet domain) {
		this.commandProxy().insert(OiomtDateFormatSet.toEntity(domain));
	}

	@Override
	public void update(DateFormatSet domain) {
		this.commandProxy().update(OiomtDateFormatSet.toEntity(domain));
	}

	@Override
	public void remove() {
		this.commandProxy().remove(OiomtDateFormatSet.class, new OiomtDateFormatSetPk());
	}

	@Override
	public Optional<DateFormatSet> getDateFormatSetByCid(String cid) {
		return this.queryProxy().query(SELECT_BY_CID, OiomtDateFormatSet.class).setParameter("cid", cid)
				.getSingle(OiomtDateFormatSet::toDomain);
	}
}
