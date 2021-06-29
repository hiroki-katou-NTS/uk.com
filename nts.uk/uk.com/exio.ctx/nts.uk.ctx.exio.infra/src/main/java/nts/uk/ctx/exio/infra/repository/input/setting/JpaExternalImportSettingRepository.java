package nts.uk.ctx.exio.infra.repository.input.setting;

import java.util.List;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportSetting;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportSettingRepository;
import nts.uk.ctx.exio.infra.entity.input.setting.XimmtImportSetting;

public class JpaExternalImportSettingRepository extends JpaRepository implements ExternalImportSettingRepository {
	
	@Override
	public List<ExternalImportSetting> get(String companyId, String settingCode) {
		String sql 	= "select * "
					+ "from XIMMT_IMPORT_SETTING "
					+ "where CID = @cid "
					+ "SETTING_CODE = @settingCode";
		return new NtsStatement(sql, this.jdbcProxy())
				.paramString("cid", companyId)
				.paramString("settingCode", settingCode)
				.getList(rec -> XimmtImportSetting.MAPPER.toEntity(rec).toDomain());
	}
}
