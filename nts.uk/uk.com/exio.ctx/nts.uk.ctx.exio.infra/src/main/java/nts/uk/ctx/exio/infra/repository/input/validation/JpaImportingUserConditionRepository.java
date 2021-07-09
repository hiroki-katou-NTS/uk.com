package nts.uk.ctx.exio.infra.repository.input.validation;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.input.validation.user.ImportingUserCondition;
import nts.uk.ctx.exio.dom.input.validation.user.ImportingUserConditionRepository;
import nts.uk.ctx.exio.infra.entity.input.validation.XimmtValidValue;

@Stateless
public class JpaImportingUserConditionRepository extends JpaRepository implements ImportingUserConditionRepository{

	@Override
	public Optional<ImportingUserCondition> get(String companyId, String settingCode, int itemNo){
		String sql = "select * from XIMMT_VALID_VALUE"
				+ " where CID = @cid"
				+ " and SETTING_CODE = @settingCode"
				+ " and ITEM_NO = @itemNo";

			return jdbcProxy().query(sql)
				.paramString("cid", companyId)
				.paramString("settingCode", settingCode)
				.paramInt("itemNo", itemNo)
				.getSingle(rec -> XimmtValidValue.MAPPER.toEntity(rec).toDomain());
	}
}
