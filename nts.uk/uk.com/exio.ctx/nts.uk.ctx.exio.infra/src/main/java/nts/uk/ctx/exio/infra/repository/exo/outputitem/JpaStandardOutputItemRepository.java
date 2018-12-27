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
import nts.uk.ctx.exio.infra.entity.exo.dataformat.dataformatsetting.OiomtAtWorkClsDfsPk;
import nts.uk.ctx.exio.infra.entity.exo.dataformat.dataformatsetting.OiomtCharacterDfs;
import nts.uk.ctx.exio.infra.entity.exo.dataformat.dataformatsetting.OiomtCharacterDfsPk;
import nts.uk.ctx.exio.infra.entity.exo.dataformat.dataformatsetting.OiomtDateDfs;
import nts.uk.ctx.exio.infra.entity.exo.dataformat.dataformatsetting.OiomtDateDfsPk;
import nts.uk.ctx.exio.infra.entity.exo.dataformat.dataformatsetting.OiomtInstantTimeDfs;
import nts.uk.ctx.exio.infra.entity.exo.dataformat.dataformatsetting.OiomtInstantTimeDfsPk;
import nts.uk.ctx.exio.infra.entity.exo.dataformat.dataformatsetting.OiomtNumberDfs;
import nts.uk.ctx.exio.infra.entity.exo.dataformat.dataformatsetting.OiomtNumberDfsPk;
import nts.uk.ctx.exio.infra.entity.exo.dataformat.dataformatsetting.OiomtTimeDfs;
import nts.uk.ctx.exio.infra.entity.exo.dataformat.dataformatsetting.OiomtTimeDfsPk;
import nts.uk.ctx.exio.infra.entity.exo.outputitem.OiomtCtgItem;
import nts.uk.ctx.exio.infra.entity.exo.outputitem.OiomtStdOutItem;
import nts.uk.ctx.exio.infra.entity.exo.outputitem.OiomtStdOutItemPk;

@Stateless
public class JpaStandardOutputItemRepository extends JpaRepository implements StandardOutputItemRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiomtStdOutItem f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.stdOutItemPk.cid =:cid AND  f.stdOutItemPk.outItemCd =:outItemCd AND  f.stdOutItemPk.condSetCd =:condSetCd ";
	private static final String SELECT_BY_CID_AND_SET_CODE = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.stdOutItemPk.cid =:cid AND  f.stdOutItemPk.condSetCd =:condSetCd ORDER BY  f.stdOutItemPk.outItemCd";

	private static final String SELECT_AW_DATA_FORMAT_BY_KEY_STRING = "SELECT f FROM OiomtAtWorkClsDfs f"
			+ " WHERE  f.atWorkClsDfsPk.cid =:cid AND  f.atWorkClsDfsPk.outItemCd =:outItemCd AND  f.atWorkClsDfsPk.condSetCd =:condSetCd ";

	private static final String SELECT_CHAR_FORMAT_BY_KEY_STRING = "SELECT f FROM OiomtCharacterDfs f"
			+ " WHERE  f.characterDfsPk.cid =:cid AND  f.characterDfsPk.outItemCd =:outItemCd AND  f.characterDfsPk.condSetCd =:condSetCd ";

	private static final String SELECT_DATE_FORMAT_BY_KEY_STRING = "SELECT f FROM OiomtDateDfs f"
			+ " WHERE  f.dateDfsPk.cid =:cid AND  f.dateDfsPk.outItemCd =:outItemCd AND  f.dateDfsPk.condSetCd =:condSetCd ";

	private static final String SELECT_INSTANT_TIME_FORMAT_BY_KEY_STRING = "SELECT f FROM OiomtInstantTimeDfs f"
			+ " WHERE  f.instantTimeDfsPk.cid =:cid AND  f.instantTimeDfsPk.outItemCd =:outItemCd AND  f.instantTimeDfsPk.condSetCd =:condSetCd ";

	private static final String SELECT_NUMBER_FORMAT_BY_KEY_STRING = "SELECT f FROM OiomtNumberDfs f"
			+ " WHERE  f.numberDfsPk.cid =:cid AND  f.numberDfsPk.outItemCd =:outItemCd AND  f.numberDfsPk.condSetCd =:condSetCd ";

	private static final String SELECT_TIME_FORMAT_BY_KEY_STRING = "SELECT f FROM OiomtTimeDfs f"
			+ " WHERE  f.timeDfsPk.cid =:cid AND  f.timeDfsPk.outItemCd =:outItemCd AND  f.timeDfsPk.condSetCd =:condSetCd ";

	private static final String SELECT_AW_DATA_FORMAT = "SELECT f FROM OiomtAtWorkClsDfs f"
			+ " WHERE  f.atWorkClsDfsPk.cid =:cid AND f.atWorkClsDfsPk.condSetCd =:condSetCd ";

	private static final String SELECT_CHAR_FORMAT = "SELECT f FROM OiomtCharacterDfs f"
			+ " WHERE  f.characterDfsPk.cid =:cid AND f.characterDfsPk.condSetCd =:condSetCd ";

	private static final String SELECT_DATE_FORMAT = "SELECT f FROM OiomtDateDfs f"
			+ " WHERE  f.dateDfsPk.cid =:cid AND f.dateDfsPk.condSetCd =:condSetCd ";

	private static final String SELECT_INSTANT_TIME_FORMAT = "SELECT f FROM OiomtInstantTimeDfs f"
			+ " WHERE  f.instantTimeDfsPk.cid =:cid AND f.instantTimeDfsPk.condSetCd =:condSetCd ";

	private static final String SELECT_NUMBER_FORMAT = "SELECT f FROM OiomtNumberDfs f"
			+ " WHERE  f.numberDfsPk.cid =:cid AND f.numberDfsPk.condSetCd =:condSetCd ";

	private static final String SELECT_TIME_FORMAT = "SELECT f FROM OiomtTimeDfs f"
			+ " WHERE  f.timeDfsPk.cid =:cid AND f.timeDfsPk.condSetCd =:condSetCd ";
	
	private static final String DELETE_OUT_ITEM = "DELETE FROM OiomtStdOutItem f "
	        + "WHERE f.stdOutItemPk.cid =:cid AND f.stdOutItemPk.condSetCd =:condSetCd ";
	
	private static final String DELETE_AW_DATA_FORMAT = "DELETE FROM OiomtAtWorkClsDfs f "
            + "WHERE f.atWorkClsDfsPk.cid =:cid AND f.atWorkClsDfsPk.condSetCd =:condSetCd ";
	
	private static final String CHAR_FORMAT = "DELETE FROM OiomtCharacterDfs f "
            + "WHERE f.characterDfsPk.cid =:cid AND f.characterDfsPk.condSetCd =:condSetCd ";
    
	private static final String DATE_FORMAT = "DELETE FROM OiomtDateDfs f "
            + "WHERE f.dateDfsPk.cid =:cid AND f.dateDfsPk.condSetCd =:condSetCd ";
	
	private static final String INSTANT_TIME_FORMAT = "DELETE FROM OiomtInstantTimeDfs f "
            + "WHERE f.instantTimeDfsPk.cid =:cid AND f.instantTimeDfsPk.condSetCd =:condSetCd ";
	
	private static final String NUMBER_FORMAT = "DELETE FROM OiomtNumberDfs f "
            + "WHERE f.numberDfsPk.cid =:cid AND f.numberDfsPk.condSetCd =:condSetCd ";
	
	private static final String TIME_FORMAT = "DELETE FROM OiomtTimeDfs f "
            + "WHERE f.timeDfsPk.cid =:cid AND f.timeDfsPk.condSetCd =:condSetCd ";
	private static final String CATEGORY_ITEM = "DELETE FROM OiomtCtgItem f "
            + "WHERE f.ctgItemPk.cid =:cid AND f.ctgItemPk.condSetCd =:condSetCd ";
	
	private static final String SELECT_CHAR_FORMAT_BY_CD_CONVERT_CD = "SELECT f FROM OiomtCharacterDfs f"
			+ " WHERE  f.characterDfsPk.cid =:cid AND f.cdConvertCd =:convertCd ";
	
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
	public void add(List<StandardOutputItem> domain) {
		this.commandProxy().insertAll(OiomtStdOutItem.toEntity(domain));
	}

	@Override
	public void update(StandardOutputItem domain) {
		this.commandProxy().update(OiomtStdOutItem.toEntity(domain));
	}

	@Override
	public void remove(String cid, String condSetCd) {
	    this.getEntityManager().createQuery(DELETE_AW_DATA_FORMAT, OiomtAtWorkClsDfs.class).setParameter("cid", cid).setParameter("condSetCd", condSetCd).executeUpdate();
	    this.getEntityManager().createQuery(CHAR_FORMAT, OiomtCharacterDfs.class).setParameter("cid", cid).setParameter("condSetCd", condSetCd).executeUpdate();
	    this.getEntityManager().createQuery(DATE_FORMAT, OiomtDateDfs.class).setParameter("cid", cid).setParameter("condSetCd", condSetCd).executeUpdate();
	    this.getEntityManager().createQuery(INSTANT_TIME_FORMAT, OiomtInstantTimeDfs.class).setParameter("cid", cid).setParameter("condSetCd", condSetCd).executeUpdate();
	    this.getEntityManager().createQuery(NUMBER_FORMAT, OiomtNumberDfs.class).setParameter("cid", cid).setParameter("condSetCd", condSetCd).executeUpdate();
	    this.getEntityManager().createQuery(TIME_FORMAT, OiomtTimeDfs.class).setParameter("cid", cid).setParameter("condSetCd", condSetCd).executeUpdate();
	    this.getEntityManager().createQuery(CATEGORY_ITEM, OiomtCtgItem.class).setParameter("cid", cid).setParameter("condSetCd", condSetCd).executeUpdate();
	    this.getEntityManager().createQuery(DELETE_OUT_ITEM, OiomtStdOutItem.class).setParameter("cid", cid).setParameter("condSetCd", condSetCd).executeUpdate();
	}
	
	@Override
    public void remove(String cid, String outItemCd, String condSetCd) {
        this.getAwDataFormatSettingByID(cid, condSetCd, outItemCd).ifPresent(e -> {
            this.commandProxy().remove(OiomtAtWorkClsDfs.class, new OiomtAtWorkClsDfsPk(cid, condSetCd, outItemCd));
        });
        this.getCharacterDataFmSettingByID(cid, condSetCd, outItemCd).ifPresent(e -> {
            this.commandProxy().remove(OiomtCharacterDfs.class, new OiomtCharacterDfsPk(cid, condSetCd, outItemCd));
        });
        this.getDateFormatSettingByID(cid, condSetCd, outItemCd).ifPresent(e -> {
            this.commandProxy().remove(OiomtDateDfs.class, new OiomtDateDfsPk(cid, condSetCd, outItemCd));
        });
        this.getInstantTimeDataFmSettingByID(cid, condSetCd, outItemCd).ifPresent(e -> {
            this.commandProxy().remove(OiomtInstantTimeDfs.class, new OiomtInstantTimeDfsPk(cid, condSetCd, outItemCd));
        });
        this.getNumberDataFmSettingByID(cid, condSetCd, outItemCd).ifPresent(e -> {
            this.commandProxy().remove(OiomtNumberDfs.class, new OiomtNumberDfsPk(cid, condSetCd, outItemCd));
        });
        this.getTimeDataFmSettingByID(cid, condSetCd, outItemCd).ifPresent(e -> {
            this.commandProxy().remove(OiomtTimeDfs.class, new OiomtTimeDfsPk(cid, condSetCd, outItemCd));
        });
        this.getStdOutItemById(cid, outItemCd, condSetCd).ifPresent(e -> {
            this.commandProxy().remove(OiomtStdOutItem.class, new OiomtStdOutItemPk(cid, outItemCd, condSetCd));
        });
    }

	@Override
	public Optional<AwDataFormatSetting> getAwDataFormatSettingByID(String cid, String conditionSettingCode,
			String outputItemCode) {
		return this.queryProxy().query(SELECT_AW_DATA_FORMAT_BY_KEY_STRING, OiomtAtWorkClsDfs.class)
				.setParameter("cid", cid).setParameter("outItemCd", outputItemCode)
				.setParameter("condSetCd", conditionSettingCode).getSingle(c -> c.toDomain());
	}

	@Override
	public void register(AwDataFormatSetting domain) {
		Optional<OiomtAtWorkClsDfs> entity = this.queryProxy().find(new OiomtAtWorkClsDfsPk(domain.getCid(),
				domain.getConditionSettingCode().v(), domain.getOutputItemCode().v()), OiomtAtWorkClsDfs.class);
		if (entity.isPresent()) {
			this.commandProxy().update(OiomtAtWorkClsDfs.toEntity(domain));
		} else {
			this.commandProxy().insert(OiomtAtWorkClsDfs.toEntity(domain));
		}
	}

	@Override
	public Optional<CharacterDataFmSetting> getCharacterDataFmSettingByID(String cid, String conditionSettingCode,
			String outputItemCode) {
		return this.queryProxy().query(SELECT_CHAR_FORMAT_BY_KEY_STRING, OiomtCharacterDfs.class)
				.setParameter("cid", cid).setParameter("outItemCd", outputItemCode)
				.setParameter("condSetCd", conditionSettingCode).getSingle(c -> c.toDomain());
	}

	@Override
	public void register(CharacterDataFmSetting domain) {
		Optional<OiomtCharacterDfs> entity = this.queryProxy().find(new OiomtCharacterDfsPk(domain.getCid(),
				domain.getConditionSettingCode().v(), domain.getOutputItemCode().v()), OiomtCharacterDfs.class);
		if (entity.isPresent()) {
			this.commandProxy().update(OiomtCharacterDfs.toEntity(domain));
		} else {
			this.commandProxy().insert(OiomtCharacterDfs.toEntity(domain));
		}
	}

	@Override
	public Optional<DateFormatSetting> getDateFormatSettingByID(String cid, String conditionSettingCode,
			String outputItemCode) {
		return this.queryProxy().query(SELECT_DATE_FORMAT_BY_KEY_STRING, OiomtDateDfs.class).setParameter("cid", cid)
				.setParameter("outItemCd", outputItemCode).setParameter("condSetCd", conditionSettingCode)
				.getSingle(c -> c.toDomain());
	}

	@Override
	public void register(DateFormatSetting domain) {
		Optional<OiomtDateDfs> entity = this.queryProxy().find(new OiomtDateDfsPk(domain.getCid(),
				domain.getConditionSettingCode().v(), domain.getOutputItemCode().v()), OiomtDateDfs.class);
		if (entity.isPresent()) {
			this.commandProxy().update(OiomtDateDfs.toEntity(domain));
		} else {
			this.commandProxy().insert(OiomtDateDfs.toEntity(domain));
		}
	}

	@Override
	public Optional<InstantTimeDataFmSetting> getInstantTimeDataFmSettingByID(String cid, String conditionSettingCode,
			String outputItemCode) {
		return this.queryProxy().query(SELECT_INSTANT_TIME_FORMAT_BY_KEY_STRING, OiomtInstantTimeDfs.class)
				.setParameter("cid", cid).setParameter("outItemCd", outputItemCode)
				.setParameter("condSetCd", conditionSettingCode).getSingle(c -> c.toDomain());
	}

	@Override
	public void register(InstantTimeDataFmSetting domain) {
		Optional<OiomtInstantTimeDfs> entity = this.queryProxy().find(new OiomtInstantTimeDfsPk(domain.getCid(),
				domain.getConditionSettingCode().v(), domain.getOutputItemCode().v()), OiomtInstantTimeDfs.class);
		if (entity.isPresent()) {
			this.commandProxy().update(OiomtInstantTimeDfs.toEntity(domain));
		} else {
			this.commandProxy().insert(OiomtInstantTimeDfs.toEntity(domain));
		}
	}

	@Override
	public Optional<NumberDataFmSetting> getNumberDataFmSettingByID(String cid, String conditionSettingCode,
			String outputItemCode) {
		return this.queryProxy().query(SELECT_NUMBER_FORMAT_BY_KEY_STRING, OiomtNumberDfs.class)
				.setParameter("cid", cid).setParameter("outItemCd", outputItemCode)
				.setParameter("condSetCd", conditionSettingCode).getSingle(c -> c.toDomain());
	}

	@Override
	public void register(NumberDataFmSetting domain) {
		Optional<OiomtNumberDfs> entity = this.queryProxy().find(new OiomtNumberDfsPk(domain.getCid(),
				domain.getConditionSettingCode().v(), domain.getOutputItemCode().v()), OiomtNumberDfs.class);
		if (entity.isPresent()) {
			this.commandProxy().update(OiomtNumberDfs.toEntity(domain));
		} else {
			this.commandProxy().insert(OiomtNumberDfs.toEntity(domain));
		}
	}

	@Override
	public Optional<TimeDataFmSetting> getTimeDataFmSettingByID(String cid, String conditionSettingCode,
			String outputItemCode) {
		return this.queryProxy().query(SELECT_TIME_FORMAT_BY_KEY_STRING, OiomtTimeDfs.class).setParameter("cid", cid)
				.setParameter("outItemCd", outputItemCode).setParameter("condSetCd", conditionSettingCode)
				.getSingle(c -> c.toDomain());
	}

	@Override
	public void register(TimeDataFmSetting domain) {
		Optional<OiomtTimeDfs> entity = this.queryProxy().find(new OiomtTimeDfsPk(domain.getCid(),
				domain.getConditionSettingCode().v(), domain.getOutputItemCode().v()), OiomtTimeDfs.class);
		if (entity.isPresent()) {
			this.commandProxy().update(OiomtTimeDfs.toEntity(domain));
		} else {
			this.commandProxy().insert(OiomtTimeDfs.toEntity(domain));
		}
	}

	@Override
	public List<AwDataFormatSetting> getAwDataFormatSetting(String cid, String conditionSettingCode) {
		return this.queryProxy().query(SELECT_AW_DATA_FORMAT, OiomtAtWorkClsDfs.class).setParameter("cid", cid)
				.setParameter("condSetCd", conditionSettingCode).getList(c -> c.toDomain());
	}

	@Override
	public List<CharacterDataFmSetting> getCharacterDataFmSetting(String cid, String conditionSettingCode) {
		return this.queryProxy().query(SELECT_CHAR_FORMAT, OiomtCharacterDfs.class).setParameter("cid", cid)
				.setParameter("condSetCd", conditionSettingCode).getList(c -> c.toDomain());
	}

	@Override
	public List<DateFormatSetting> getDateFormatSetting(String cid, String conditionSettingCode) {
		return this.queryProxy().query(SELECT_DATE_FORMAT, OiomtDateDfs.class).setParameter("cid", cid)
				.setParameter("condSetCd", conditionSettingCode).getList(c -> c.toDomain());
	}

	@Override
	public List<InstantTimeDataFmSetting> getInstantTimeDataFmSetting(String cid, String conditionSettingCode) {
		return this.queryProxy().query(SELECT_INSTANT_TIME_FORMAT, OiomtInstantTimeDfs.class).setParameter("cid", cid)
				.setParameter("condSetCd", conditionSettingCode).getList(c -> c.toDomain());
	}

	@Override
	public List<NumberDataFmSetting> getNumberDataFmSetting(String cid, String conditionSettingCode) {
		return this.queryProxy().query(SELECT_NUMBER_FORMAT, OiomtNumberDfs.class)
				.setParameter("cid", cid).setParameter("condSetCd", conditionSettingCode).getList(c -> c.toDomain());
	}

	@Override
	public List<TimeDataFmSetting> getTimeDataFmSetting(String cid, String conditionSettingCode) {
		return this.queryProxy().query(SELECT_TIME_FORMAT, OiomtTimeDfs.class).setParameter("cid", cid)
				.setParameter("condSetCd", conditionSettingCode).getList(c -> c.toDomain());
	}

	@Override
	public List<CharacterDataFmSetting> getCharacterDataFmSettingByByConvertCd(String cid, String convertCd) {
		return this.queryProxy().query(SELECT_CHAR_FORMAT_BY_CD_CONVERT_CD, OiomtCharacterDfs.class)
				.setParameter("cid", cid).setParameter("convertCd", convertCd).getList(c -> c.toDomain());
	}

}
