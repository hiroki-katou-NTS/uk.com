package nts.uk.ctx.exio.infra.repository.input.validation;

import java.util.ArrayList;
import java.util.List;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.exio.dom.input.validation.ImportingUserConditionRepository;
import nts.uk.ctx.exio.dom.input.validation.condition.ImportingUserCondition;
import nts.uk.ctx.exio.infra.entity.input.validation.XimctUserCondition;

public class JpaImportingUserConditionRepository extends JpaRepository implements ImportingUserConditionRepository{
	
	@Override
	public List<ImportingUserCondition> get(String companyId, String settingCode, List<Integer> itemNoList){
		String sql = "select * "
						+ "from XIMMT_VALID_VALUE "
						+ "where CID = @cid "
						+ "SETTING_CODE = @settingCode"
						+ "ITEM_NO in @itemNos";
		List<ImportingUserCondition> result = new ArrayList<ImportingUserCondition>();
		CollectionUtil.split(itemNoList, 1000, subNoList -> {
			result.addAll(new NtsStatement(sql, this.jdbcProxy())
				.paramString("cid", companyId)
				.paramString("settingCode", settingCode)
				.getList(rec -> XimctUserCondition.MAPPER.toEntity(rec).toDomain()));
		});
		return result;
	}
}
