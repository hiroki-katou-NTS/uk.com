package nts.uk.ctx.exio.infra.repository.input.validation;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.exio.dom.input.validation.user.ImportingUserCondition;
import nts.uk.ctx.exio.dom.input.validation.user.ImportingUserConditionRepository;
import nts.uk.ctx.exio.infra.entity.input.validation.XimmtValidValue;

@Stateless
public class JpaImportingUserConditionRepository extends JpaRepository implements ImportingUserConditionRepository{
	
	@Override
	public List<ImportingUserCondition> get(String companyId, String settingCode, List<Integer> itemNoList){
		String sql = "select * from XIMMT_VALID_VALUE"
				+ " where CID = @cid"
				+ " and SETTING_CODE = @settingCode"
				+ " and ITEM_NO in @itemNos";
		
		return NtsStatement.In.split(itemNoList, subNoList -> {
			return jdbcProxy().query(sql)
				.paramString("cid", companyId)
				.paramString("settingCode", settingCode)
				.paramInt("itemNos", subNoList)
				.getList(rec -> XimmtValidValue.MAPPER.toEntity(rec).toDomain());
		});
	}
}
