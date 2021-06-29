package nts.uk.ctx.exio.infra.repository.input.setting.assembly;

import java.util.List;
import java.util.Optional;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.exio.dom.input.setting.assembly.ExternalImportAssemblyMethod;
import nts.uk.ctx.exio.dom.input.setting.assembly.ExternalImportAssemblyMethodRepository;
import nts.uk.ctx.exio.dom.input.setting.assembly.mapping.FixedItemMapping;
import nts.uk.ctx.exio.dom.input.setting.assembly.mapping.ImportItemMapping;
import nts.uk.ctx.exio.infra.entity.input.setting.assembly.XimmtMappingFixedItem;
import nts.uk.ctx.exio.infra.entity.input.setting.assembly.XimmtMappingImportItem;

public class JpaExternalImportAssemblyMethodRepository extends JpaRepository implements ExternalImportAssemblyMethodRepository {
	
	@Override
	public Optional<ExternalImportAssemblyMethod> get(String companyId, String settingCode) {
		val fixedItem = this.getFixed(companyId, settingCode);
		val importItem = this.getImport(companyId, settingCode);
		if(fixedItem.isEmpty() && importItem.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(new ExternalImportAssemblyMethod(settingCode, null, importItem, fixedItem));
	}
	
	private List<FixedItemMapping> getFixed(String companyId, String settingCode){
		String sql 	= "select * "
					+ "from XIMMT_MAPPING_FIXED_ITEM "
					+ "where CID = @cid "
					+ "SETTING_CODE = @settingCode";
		return new NtsStatement(sql, this.jdbcProxy())
				.paramString("cid", companyId)
				.paramString("settingCode", settingCode)
				.getList(rec -> XimmtMappingFixedItem.MAPPER.toEntity(rec).toDomain());
	}
	
	private List<ImportItemMapping> getImport(String companyId, String settingCode){
		String sql 	= "select * "
					+ "from XIMMT_MAPPING_IMPORT_ITEM "
					+ "where CID = @cid "
					+ "SETTING_CODE = @settingCode";
		return new NtsStatement(sql, this.jdbcProxy())
				.paramString("cid", companyId)
				.paramString("settingCode", settingCode)
				.getList(rec -> XimmtMappingImportItem.MAPPER.toEntity(rec).toDomain());
	}

}
