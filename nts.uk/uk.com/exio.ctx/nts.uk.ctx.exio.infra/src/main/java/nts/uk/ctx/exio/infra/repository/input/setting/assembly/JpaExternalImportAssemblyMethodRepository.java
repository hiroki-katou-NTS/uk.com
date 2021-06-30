package nts.uk.ctx.exio.infra.repository.input.setting.assembly;

import java.util.List;
import java.util.Optional;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;
import nts.uk.ctx.exio.dom.input.setting.assembly.ExternalImportAssemblyMethod;
import nts.uk.ctx.exio.dom.input.setting.assembly.ExternalImportAssemblyMethodRepository;
import nts.uk.ctx.exio.dom.input.setting.assembly.mapping.FixedItemMapping;
import nts.uk.ctx.exio.dom.input.setting.assembly.mapping.ImportItemMapping;
import nts.uk.ctx.exio.infra.entity.input.setting.assembly.XimmtMappingFixedItem;
import nts.uk.ctx.exio.infra.entity.input.setting.assembly.XimmtMappingImportItem;

public class JpaExternalImportAssemblyMethodRepository extends JpaRepository implements ExternalImportAssemblyMethodRepository {
	
	@Override
	public Optional<ExternalImportAssemblyMethod> get(String companyId, ExternalImportCode settingCode) {
		val fixedItem = this.getFixed(companyId, settingCode);
		val importItem = this.getImport(companyId, settingCode);
		if(fixedItem.isEmpty() && importItem.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(new ExternalImportAssemblyMethod(settingCode.toString(), null, importItem, fixedItem));
	}
	
	private List<FixedItemMapping> getFixed(String companyId, ExternalImportCode settingCode){
		String sql 	= " select f "
					+ " from XimmtMappingFixedItem f "
					+ " where f.pk.companyId = @companyID "
					+ " and f.pk.code = @settingCD";
		return this.queryProxy().query(sql, XimmtMappingFixedItem.class)
				.setParameter("companyID", companyId)
				.setParameter("settingCD", settingCode.toString())
				.getList(rec -> rec.toDomain());
	}
	
	private List<ImportItemMapping> getImport(String companyId, ExternalImportCode settingCode){
		String sql 	= "select f "
					+ " from XimmtMappingImportItem f "
					+ " where f.pk.companyId = @companyID "
					+ " and f.pk.code = @settingCD";
		return this.queryProxy().query(sql, XimmtMappingImportItem.class)
				.setParameter("companyID", companyId)
				.setParameter("settingCD", settingCode.toString())
				.getList(rec -> rec.toDomain());
	}
}
