package nts.uk.ctx.exio.infra.repository.input.setting;

import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportSetting;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportSettingRepository;
import nts.uk.ctx.exio.infra.entity.input.setting.XimmtImportSetting;
import nts.uk.ctx.exio.infra.entity.input.setting.XimmtImportSettingPK;

@Stateless
public class JpaExternalImportSettingRepository extends JpaRepository implements ExternalImportSettingRepository {
	
	@Override
	public void insert(ExternalImportSetting domain) {
		this.commandProxy().insert(XimmtImportSetting.toEntitiy(domain));
	}
	
	@Override
	public void update(ExternalImportSetting domain) {
		this.commandProxy().update(XimmtImportSetting.toEntitiy(domain));
		
	}
	
	@Override
	public void delete(String companyId, ExternalImportCode settingCode) {
		val pk = new XimmtImportSettingPK(companyId, settingCode.toString());
		this.commandProxy().remove(XimmtImportSetting.class, pk);
		
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
}
