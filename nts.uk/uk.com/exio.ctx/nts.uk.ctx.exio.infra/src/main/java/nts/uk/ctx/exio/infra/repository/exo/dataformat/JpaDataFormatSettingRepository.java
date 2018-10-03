package nts.uk.ctx.exio.infra.repository.exo.dataformat;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.exo.dataformat.init.AwDataFormatSet;
import nts.uk.ctx.exio.dom.exo.dataformat.init.ChacDataFmSet;
import nts.uk.ctx.exio.dom.exo.dataformat.init.DataFormatSettingRepository;
import nts.uk.ctx.exio.dom.exo.dataformat.init.DateFormatSet;
import nts.uk.ctx.exio.dom.exo.dataformat.init.InTimeDataFmSet;
import nts.uk.ctx.exio.dom.exo.dataformat.init.NumberDataFmSet;
import nts.uk.ctx.exio.dom.exo.dataformat.init.TimeDataFmSet;
import nts.uk.ctx.exio.infra.entity.exo.dataformat.init.OiomtAwDataFormatSet;
import nts.uk.ctx.exio.infra.entity.exo.dataformat.init.OiomtAwDataFormatSetPk;
import nts.uk.ctx.exio.infra.entity.exo.dataformat.init.OiomtChacDataFmSet;
import nts.uk.ctx.exio.infra.entity.exo.dataformat.init.OiomtChacDataFmSetPk;
import nts.uk.ctx.exio.infra.entity.exo.dataformat.init.OiomtDateFormatSet;
import nts.uk.ctx.exio.infra.entity.exo.dataformat.init.OiomtDateFormatSetPk;
import nts.uk.ctx.exio.infra.entity.exo.dataformat.init.OiomtInTimeDataFmSet;
import nts.uk.ctx.exio.infra.entity.exo.dataformat.init.OiomtInTimeDataFmSetPk;
import nts.uk.ctx.exio.infra.entity.exo.dataformat.init.OiomtNumberDataFmSet;
import nts.uk.ctx.exio.infra.entity.exo.dataformat.init.OiomtNumberDataFmSetPk;
import nts.uk.ctx.exio.infra.entity.exo.dataformat.init.OiomtTimeDataFmSetO;
import nts.uk.ctx.exio.infra.entity.exo.dataformat.init.OiomtTimeDataFmSetPk;

@Stateless
public class JpaDataFormatSettingRepository extends JpaRepository implements DataFormatSettingRepository {

	private static final String SELECT_ALL_AT_WORK_QUERY_STRING = "SELECT f FROM OiomtAwDataFormatSet f";
	private static final String SELECT_ALL_NUMBER_QUERY_STRING = "SELECT f FROM OiomtNumberDataFmSet f";
	private static final String SELECT_ALL_CHARACTER_QUERY_STRING = "SELECT f FROM OiomtChacDataFmSet f";
	private static final String SELECT_ALL_TIME_QUERY_STRING = "SELECT f FROM OiomtTimeDataFmSetO f";
	private static final String SELECT_ALL_IN_TIME_QUERY_STRING = "SELECT f FROM OiomtInTimeDataFmSet f";
	private static final String SELECT_ALL_DATE_QUERY_STRING = "SELECT f FROM OiomtDateFormatSet f";
	private static final String SELECT_DATE_BY_CID = "SELECT f FROM OiomtDateFormatSet f WHERE f.dateFormatSetPk.cid =:cid";
	private static final String SELECT_TIME_BY_CID = "SELECT f FROM OiomtTimeDataFmSetO f WHERE f.timeDataFmSetPk.cid =:cid";
	private static final String SELECT_NUM_BY_CID = SELECT_ALL_NUMBER_QUERY_STRING
			+ " WHERE f.numberDataFmSetPk.cid =:cid";
	private static final String SELECT_CHARACTER_BY_CID_CD_CONVERT_CD = SELECT_ALL_CHARACTER_QUERY_STRING
			+ " WHERE f.chacDataFmSetPk.cid =:cid AND f.cdConvertCd =:cdConvertCd";

	@Override
	public List<AwDataFormatSet> getAllAwDataFormatSet() {
		return this.queryProxy().query(SELECT_ALL_AT_WORK_QUERY_STRING, OiomtAwDataFormatSet.class)
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
	public void remove(AwDataFormatSet domain) {
		this.commandProxy().remove(OiomtAwDataFormatSet.class, new OiomtAwDataFormatSetPk(domain.getCid()));
	}

	@Override
	public List<ChacDataFmSet> getAllChacDataFmSet() {
		return this.queryProxy().query(SELECT_ALL_CHARACTER_QUERY_STRING, OiomtChacDataFmSet.class)
				.getList(item -> item.toDomain());
	}

	@Override
	public Optional<ChacDataFmSet> getChacDataFmSetById(String cid) {
		val entity = this.queryProxy().find(new OiomtChacDataFmSetPk(cid), OiomtChacDataFmSet.class);
		if (entity.isPresent()) {
			return Optional.of(entity.get().toDomain());
		}
		return Optional.empty();
	}

	@Override
	public List<ChacDataFmSet> getChacDataFmSetByConvertCd(String cid, String convertCd) {
		return this.queryProxy().query(SELECT_CHARACTER_BY_CID_CD_CONVERT_CD, OiomtChacDataFmSet.class)
				.setParameter("cid", cid).setParameter("cdConvertCd", convertCd).getList(item -> item.toDomain());
	}

	@Override
	public void add(ChacDataFmSet domain) {
		this.commandProxy().insert(OiomtChacDataFmSet.toEntity(domain));
	}

	@Override
	public void update(ChacDataFmSet domain) {
		this.commandProxy().update(OiomtChacDataFmSet.toEntity(domain));
	}

	@Override
	public void remove(ChacDataFmSet domain) {
		this.commandProxy().remove(OiomtChacDataFmSet.class, new OiomtChacDataFmSetPk(domain.getCid()));
	}

	@Override
	public List<DateFormatSet> getAllDateFormatSet() {
		return this.queryProxy().query(SELECT_ALL_DATE_QUERY_STRING, OiomtDateFormatSet.class)
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
	public void remove(DateFormatSet domain) {
		this.commandProxy().remove(OiomtDateFormatSet.class, new OiomtDateFormatSetPk(domain.getCid()));
	}

	@Override
	public Optional<DateFormatSet> getDateFormatSetByCid(String cid) {
		return this.queryProxy().query(SELECT_DATE_BY_CID, OiomtDateFormatSet.class).setParameter("cid", cid)
				.getSingle(OiomtDateFormatSet::toDomain);
	}

	@Override
	public List<InTimeDataFmSet> getAllInTimeDataFmSet() {
		return this.queryProxy().query(SELECT_ALL_IN_TIME_QUERY_STRING, OiomtInTimeDataFmSet.class)
				.getList(item -> item.toDomain());
	}

	@Override
	public Optional<InTimeDataFmSet> getInTimeDataFmSetByCid(String cId) {
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
	public void remove(InTimeDataFmSet domain) {
		this.commandProxy().remove(OiomtInTimeDataFmSet.class, new OiomtInTimeDataFmSetPk(domain.getCid()));
	}

	@Override
	public List<NumberDataFmSet> getAllNumberDataFmSet() {
		return this.queryProxy().query(SELECT_ALL_NUMBER_QUERY_STRING, OiomtNumberDataFmSet.class)
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
	public void remove(NumberDataFmSet domain) {
		this.commandProxy().remove(OiomtNumberDataFmSet.class, new OiomtNumberDataFmSetPk(domain.getCid()));
	}

	@Override
	public Optional<NumberDataFmSet> getNumberDataFmSetByCid(String cid) {
		return this.queryProxy().query(SELECT_NUM_BY_CID, OiomtNumberDataFmSet.class).setParameter("cid", cid)
				.getSingle(OiomtNumberDataFmSet::toDomain);
	}
	
	@Override
	public List<TimeDataFmSet> getAllTimeDataFmSet() {
		return this.queryProxy().query(SELECT_ALL_TIME_QUERY_STRING, OiomtTimeDataFmSetO.class)
				.getList(item -> item.toDomain());
	}

	@Override
	public Optional<TimeDataFmSet> getTimeDataFmSetById() {
		return this.queryProxy().query(SELECT_TIME_BY_CID, OiomtTimeDataFmSetO.class).getSingle(c -> c.toDomain());
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
	public void remove(TimeDataFmSet domain) {
		this.commandProxy().remove(OiomtTimeDataFmSetO.class, new OiomtTimeDataFmSetPk());
	}

	@Override
	public Optional<TimeDataFmSet> getTimeDataFmSetByCid(String cid) {
		return this.queryProxy().query(SELECT_TIME_BY_CID, OiomtTimeDataFmSetO.class).setParameter("cid", cid)
				.getSingle(c -> c.toDomain());
	}
}
