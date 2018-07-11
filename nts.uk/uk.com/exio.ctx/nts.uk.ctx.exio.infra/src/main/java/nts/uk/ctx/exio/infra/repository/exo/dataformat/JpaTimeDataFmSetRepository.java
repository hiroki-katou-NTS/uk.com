package nts.uk.ctx.exio.infra.repository.exo.dataformat;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.exo.dataformat.TimeDataFmSet;
import nts.uk.ctx.exio.dom.exo.dataformat.TimeDataFmSetRepository;
import nts.uk.ctx.exio.infra.entity.exo.dataformat.OiomtTimeDataFmSetO;
import nts.uk.ctx.exio.infra.entity.exo.dataformat.OiomtTimeDataFmSetPk;

@Stateless
public class JpaTimeDataFmSetRepository extends JpaRepository implements TimeDataFmSetRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiomtTimeDataFmSet f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE ";
	private static final String SELECT_BY_CID = "SELECT f FROM OiomtTimeDataFmSetO f WHERE f.timeDataFmSetPk.cid =:cid";

	@Override
	public List<TimeDataFmSet> getAllTimeDataFmSet() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, OiomtTimeDataFmSetO.class)
				.getList(item -> item.toDomain());
	}

	@Override
	public Optional<TimeDataFmSet> getTimeDataFmSetById() {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, OiomtTimeDataFmSetO.class).getSingle(c -> c.toDomain());
	}

	@Override
	public void add(TimeDataFmSet domain) {
		this.commandProxy().insert(OiomtTimeDataFmSetO.toEntity(domain));
	}

	@Override
	public void update(TimeDataFmSet domain) {
		this.commandProxy().update(OiomtTimeDataFmSetO.toEntity(domain));
	}

	@Override
	public void remove() {
		this.commandProxy().remove(OiomtTimeDataFmSetO.class, new OiomtTimeDataFmSetPk());
	}

	@Override
	public Optional<TimeDataFmSet> getTimeDataFmSetByCid(String cid) {
		return this.queryProxy().query(SELECT_BY_CID, OiomtTimeDataFmSetO.class).setParameter("cid", cid)
				.getSingle(c -> c.toDomain());
	}
}
