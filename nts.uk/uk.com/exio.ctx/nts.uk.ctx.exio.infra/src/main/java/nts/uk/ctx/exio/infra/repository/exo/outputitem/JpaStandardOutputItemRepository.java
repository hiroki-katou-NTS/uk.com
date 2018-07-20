package nts.uk.ctx.exio.infra.repository.exo.outputitem;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting.AwDataFormatSetting;
import nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting.CharacterDataFmSetting;
import nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting.DateFormatSetting;
import nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting.InstantTimeDataFmSetting;
import nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting.NumberDataFmSetting;
import nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting.TimeDataFmSetting;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItem;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItemRepository;
import nts.uk.ctx.exio.infra.entity.exo.dataformat.dataformatsetting.OiomtAtWorkClsDfs;
import nts.uk.ctx.exio.infra.entity.exo.dataformat.dataformatsetting.OiomtCharacterDfs;
import nts.uk.ctx.exio.infra.entity.exo.dataformat.dataformatsetting.OiomtDateDfs;
import nts.uk.ctx.exio.infra.entity.exo.dataformat.dataformatsetting.OiomtInstantTimeDfs;
import nts.uk.ctx.exio.infra.entity.exo.dataformat.dataformatsetting.OiomtNumberDfs;
import nts.uk.ctx.exio.infra.entity.exo.dataformat.dataformatsetting.OiomtTimeDfs;
import nts.uk.ctx.exio.infra.entity.exo.outputitem.OiomtStdOutItem;
import nts.uk.ctx.exio.infra.entity.exo.outputitem.OiomtStdOutItemPk;
import nts.uk.ctx.exio.infra.entity.exo.outputitemorder.OiomtStdOutItemOrder;

@Stateless
public class JpaStandardOutputItemRepository extends JpaRepository implements StandardOutputItemRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiomtStdOutItem f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.stdOutItemPk.cid =:cid AND  f.stdOutItemPk.outItemCd =:outItemCd AND  f.stdOutItemPk.condSetCd =:condSetCd ";
	private static final String SELECT_BY_CID_AND_SET_CODE = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.stdOutItemPk.cid =:cid AND  f.stdOutItemPk.condSetCd =:condSetCd ";

	private static final String DELETE_BY_CID_CNDSETCD = "DELETE f FROM OiomtStdOutItem f "
			+ "WHERE f.stdOutItemOrderPk.cid =:cid AND  f.stdOutItemOrderPk.condSetCd =:condSetCd";
	
	private static final String SELECT_AW_DATA_FORMAT_BY_KEY_STRING = "SELECT f FROM OiomtAtWorkClsDfs f"
			+ " WHERE  f.OiomtAtWorkClsDfsPk.cid =:cid AND  f.OiomtAtWorkClsDfsPk.outItemCd =:outItemCd AND  f.OiomtAtWorkClsDfsPk.condSetCd =:condSetCd ";

	private static final String SELECT_CHAR_FORMAT_BY_KEY_STRING = "SELECT f FROM OiomtCharacterDfs f"
			+ " WHERE  f.OiomtCharacterDfsPk.cid =:cid AND  f.OiomtCharacterDfsPk.outItemCd =:outItemCd AND  f.OiomtCharacterDfsPk.condSetCd =:condSetCd ";

	private static final String SELECT_DATE_FORMAT_BY_KEY_STRING = "SELECT f FROM OiomtDateDfs f"
			+ " WHERE  f.OiomtDateDfsPk.cid =:cid AND  f.OiomtDateDfsPk.outItemCd =:outItemCd AND  f.OiomtDateDfsPk.condSetCd =:condSetCd ";
	
	private static final String SELECT_INSTANT_TIME_FORMAT_BY_KEY_STRING = "SELECT f FROM OiomtInstantTimeDfs f"
			+ " WHERE  f.OiomtInstantTimeDfsPk.cid =:cid AND  f.OiomtInstantTimeDfsPk.outItemCd =:outItemCd AND  f.OiomtInstantTimeDfsPk.condSetCd =:condSetCd ";
	
	private static final String SELECT_NUMBER_FORMAT_BY_KEY_STRING = "SELECT f FROM OiomtNumberDfs f"
			+ " WHERE  f.OiomtNumberDfsPk.cid =:cid AND  f.OiomtNumberDfsPk.outItemCd =:outItemCd AND  f.OiomtNumberDfsPk.condSetCd =:condSetCd ";
	
	private static final String SELECT_TIME_FORMAT_BY_KEY_STRING = "SELECT f FROM OiomtTimeDfs f"
			+ " WHERE  f.OiomtTimeDfsPk.cid =:cid AND  f.OiomtTimeDfsPk.outItemCd =:outItemCd AND  f.OiomtTimeDfsPk.condSetCd =:condSetCd ";
	
	@Override
	public List<StandardOutputItem> getAllStdOutItem() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, OiomtStdOutItem.class).getList(item -> item.toDomain());
	}

	@Override
	public Optional<StandardOutputItem> getStdOutItemById(String cid, String outItemCd, String condSetCd) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, OiomtStdOutItem.class).setParameter("cid", cid)
				.setParameter("outItemCd", outItemCd).setParameter("condSetCd", condSetCd).getSingle(c -> c.toDomain());
	}

	@Override
	public List<StandardOutputItem> getStdOutItemByCidAndSetCd(String cid, String condSetCd) {
		return this.queryProxy().query(SELECT_BY_CID_AND_SET_CODE, OiomtStdOutItem.class).setParameter("cid", cid)
				.setParameter("condSetCd", condSetCd).getList(c -> c.toDomain());
	}

	@Override
	public void add(StandardOutputItem domain) {
		this.commandProxy().insert(OiomtStdOutItem.toEntity(domain));
	}

	@Override
	public void update(StandardOutputItem domain) {
		this.commandProxy().update(OiomtStdOutItem.toEntity(domain));
	}

	@Override
	public void remove(String cid, String outItemCd, String condSetCd) {
		this.commandProxy().remove(OiomtStdOutItem.class, new OiomtStdOutItemPk(cid, outItemCd, condSetCd));
	}

	@Override
	public void remove(String cid, String condSetCd) {
		this.queryProxy().query(DELETE_BY_CID_CNDSETCD, OiomtStdOutItemOrder.class).setParameter("cid", cid)
				.setParameter("condSetCd", condSetCd);
	}

	@Override
	public Optional<AwDataFormatSetting> getAwDataFormatSettingByID(String cid, String conditionSettingCode,
			String outputItemCode) {
		return this.queryProxy().query(SELECT_AW_DATA_FORMAT_BY_KEY_STRING, OiomtAtWorkClsDfs.class)
				.setParameter("cid", cid)
				.setParameter("outItemCd", outputItemCode)
				.setParameter("condSetCd", conditionSettingCode)
				.getSingle(c -> c.toDomain());
	}

	@Override
	public Optional<CharacterDataFmSetting> getCharacterDataFmSettingByID(String cid, String conditionSettingCode,
			String outputItemCode) {
		return this.queryProxy().query(SELECT_CHAR_FORMAT_BY_KEY_STRING, OiomtCharacterDfs.class)
				.setParameter("cid", cid)
				.setParameter("outItemCd", outputItemCode)
				.setParameter("condSetCd", conditionSettingCode)
				.getSingle(c -> c.toDomain());
	}

	@Override
	public Optional<DateFormatSetting> getDateFormatSettingByID(String cid, String conditionSettingCode,
			String outputItemCode) {
		return this.queryProxy().query(SELECT_DATE_FORMAT_BY_KEY_STRING, OiomtDateDfs.class)
				.setParameter("cid", cid)
				.setParameter("outItemCd", outputItemCode)
				.setParameter("condSetCd", conditionSettingCode)
				.getSingle(c -> c.toDomain());
	}

	@Override
	public Optional<InstantTimeDataFmSetting> getInstantTimeDataFmSettingByID(String cid, String conditionSettingCode,
			String outputItemCode) {
		return this.queryProxy().query(SELECT_INSTANT_TIME_FORMAT_BY_KEY_STRING, OiomtInstantTimeDfs.class)
				.setParameter("cid", cid)
				.setParameter("outItemCd", outputItemCode)
				.setParameter("condSetCd", conditionSettingCode)
				.getSingle(c -> c.toDomain());
	}

	@Override
	public Optional<NumberDataFmSetting> getNumberDataFmSettingByID(String cid, String conditionSettingCode,
			String outputItemCode) {
		return this.queryProxy().query(SELECT_NUMBER_FORMAT_BY_KEY_STRING, OiomtNumberDfs.class)
				.setParameter("cid", cid)
				.setParameter("outItemCd", outputItemCode)
				.setParameter("condSetCd", conditionSettingCode)
				.getSingle(c -> c.toDomain());
	}

	@Override
	public Optional<TimeDataFmSetting> getTimeDataFmSettingByID(String cid, String conditionSettingCode,
			String outputItemCode) {
		return this.queryProxy().query(SELECT_TIME_FORMAT_BY_KEY_STRING, OiomtTimeDfs.class)
				.setParameter("cid", cid)
				.setParameter("outItemCd", outputItemCode)
				.setParameter("condSetCd", conditionSettingCode)
				.getSingle(c -> c.toDomain());
	}

}
