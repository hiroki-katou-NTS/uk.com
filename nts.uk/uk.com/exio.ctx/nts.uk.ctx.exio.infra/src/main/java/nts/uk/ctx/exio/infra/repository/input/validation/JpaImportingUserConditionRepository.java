package nts.uk.ctx.exio.infra.repository.input.validation;

import java.util.ArrayList;
import java.util.List;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.exio.dom.input.validation.ImportingUserConditionRepository;
import nts.uk.ctx.exio.dom.input.validation.condition.ImportingUserCondition;
import nts.uk.ctx.exio.infra.entity.input.validation.OiomtUserCondition;

public class JpaImportingUserConditionRepository extends JpaRepository implements ImportingUserConditionRepository{
	
	@Override
	public List<ImportingUserCondition> get(String companyId, String settingCode){
//		String sql = "select * "
//						+ "from OIOMT_USER_CONDITION "
//						+ "where CID = @cid "
//						+ "SETTING_CODE = @settingCode";
//		return new NtsStatement(sql, this.jdbcProxy())
//				.paramString("cid", companyId)
//				.paramString("settingCode", settingCode)
//				.getList(rec -> OiomtUserCondition.MAPPER.toEntity(rec).toDomain());
		return new ArrayList<ImportingUserCondition>();
	}
}
