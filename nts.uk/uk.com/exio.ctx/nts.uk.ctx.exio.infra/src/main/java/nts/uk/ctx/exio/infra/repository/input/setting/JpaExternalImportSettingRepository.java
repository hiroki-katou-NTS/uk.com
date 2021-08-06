package nts.uk.ctx.exio.infra.repository.input.setting;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportSetting;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportSettingRepository;
import nts.uk.ctx.exio.infra.entity.input.setting.XimmtImportSetting;
import nts.uk.ctx.exio.infra.entity.input.setting.XimmtImportSettingPK;
import nts.uk.ctx.exio.infra.entity.input.setting.assembly.XimmtItemMapping;
import nts.uk.ctx.exio.infra.entity.input.setting.assembly.XimmtItemMappingPK;

@Stateless
public class JpaExternalImportSettingRepository extends JpaRepository implements ExternalImportSettingRepository {
	
	@Override
	public void insert(ExternalImportSetting domain) {
		this.commandProxy().insert(toEntitiy(domain));
	}
	
	@Override
	public void update(ExternalImportSetting domain) {
		this.commandProxy().update(toEntitiy(domain));
		
	}
	
	@Override
	public void delete(String companyId, ExternalImportCode settingCode) {
		val pk = new XimmtImportSettingPK(companyId, settingCode.toString());
		this.commandProxy().remove(XimmtImportSetting.class, pk);
		
	}
	
	@Override
	public List<ExternalImportSetting> getAll(String companyId) {
		String sql 	= " select f "
				+ " from XimmtImportSetting f "
				+ " where f.pk.companyId = :companyID ";
		
		return this.queryProxy().query(sql, XimmtImportSetting.class)
				.setParameter("companyID", companyId)
				.getList(rec -> rec.toDomain());
	}
	
	@Override
	public Optional<ExternalImportSetting> get(String companyId, ExternalImportCode settingCode) {
		String sql 	= " select f "
					+ " from XimmtImportSetting f "
					+ " where f.pk.companyId = :companyID "
					+ " and f.pk.code = :settingCD";
		
		return this.queryProxy().query(sql, XimmtImportSetting.class)
				.setParameter("companyID", companyId)
				.setParameter("settingCD", settingCode.toString())
				.getSingle(rec -> rec.toDomain());
	}
	
	private XimmtImportSetting toEntitiy(ExternalImportSetting domain) {
		return new XimmtImportSetting(
				new XimmtImportSettingPK(domain.getCompanyId(), domain.getCode().toString()), 
				domain.getName().toString(), 
				domain.getExternalImportGroupId().value, 
				domain.getImportingMode().value, 
				domain.getAssembly().getCsvFileInfo().getItemNameRowNumber().v(), 
				domain.getAssembly().getCsvFileInfo().getImportStartRowNumber().v(), 
				domain.getAssembly().getMapping().getMappings().stream()
				.map(m -> new XimmtItemMapping(
						new XimmtItemMappingPK(
								domain.getCompanyId(), 
								domain.getCode().v(), 
								m.getItemNo()), 
						m.getCsvColumnNo().isPresent()? m.getCsvColumnNo().get(): null, 
						m.getFixedValue().isPresent()? m.getFixedValue().get().getValue(): null))
				.collect(Collectors.toList()));
	}
}
