package nts.uk.ctx.exio.infra.repository.input.setting;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import org.apache.commons.lang3.tuple.Pair;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.input.csvimport.ExternalImportCsvFileInfo;
import nts.uk.ctx.exio.dom.input.setting.DomainImportSetting;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportSetting;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportSettingRepository;
import nts.uk.ctx.exio.infra.entity.input.setting.XimmtDomainImportSetting;
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
		delete(domain.getCompanyId(), domain.getCode());
		insert(domain);
	}

	@Override
	public void delete(String companyId, ExternalImportCode settingCode) {
		
		
		val tables = Arrays.asList(
				Pair.of("XIMMT_IMPORT_SETTING", "CODE"),
				Pair.of("XIMMT_ITEM_MAPPING", "SETTING_CODE"));
		
		tables.forEach(table -> {
			
			String sql = "delete from " + table.getLeft()
					+ " where CID = @cid"
					+ " and " + table.getRight() + " = @code";

			this.jdbcProxy().query(sql)
				.paramString("cid", companyId)
				.paramString("code", settingCode.v())
				.execute();
		});
	}

	@Override
	public List<ExternalImportSetting> getAll(String companyId) {
		String sql 	= " select f "
				+ " from XimmtImportSetting f "
				+ " where f.pk.companyId = :companyID ";

		List<ExternalImportSetting> domains = this.queryProxy().query(sql, XimmtImportSetting.class)
				.setParameter("companyID", companyId)
				.getList(rec -> rec.toDomain());
		
		domains.forEach(d -> {
			List<DomainImportSetting> domainImportSettings = domainImportSettings(
					companyId, d.getCode(), d.getCsvFileInfo());
			d.getDomainSettings().addAll(domainImportSettings);
		});
		return domains;
	}

	@Override
	public Optional<ExternalImportSetting> get(String companyId, ExternalImportCode settingCode) {
		
		String sql 	= " select f "
					+ " from XimmtImportSetting f "
					+ " where f.pk.companyId = :companyID "
					+ " and f.pk.code = :settingCD";

		Optional<ExternalImportSetting> domain = this.queryProxy().query(sql, XimmtImportSetting.class)
				.setParameter("companyID", companyId)
				.setParameter("settingCD", settingCode.toString())
				.getSingle(rec -> rec.toDomain());
		
		if(domain.isPresent()) {
			domain.get().getDomainSettings().addAll(domainImportSettings(companyId, settingCode, domain.get().getCsvFileInfo()));
		}
		return domain;
	}
	
	private List<DomainImportSetting> domainImportSettings(String companyId, ExternalImportCode settingCode, ExternalImportCsvFileInfo csvFileInfo){
		String sql 	= " select d "
					+ " from XimmtDomainImportSetting d "
					+ " where d.pk.companyId = :companyID "
					+ " and d.pk.code = :settingCD";

		return this.queryProxy().query(sql, XimmtDomainImportSetting.class)
				.setParameter("companyID", companyId)
				.setParameter("settingCD", settingCode.toString())
				.getList(rec -> rec.toDomain(csvFileInfo));
	}

	private XimmtImportSetting toEntitiy(ExternalImportSetting domain) {
		DomainImportSetting domainSetting = domain.getDomainSetting().get();
		return new XimmtImportSetting(
				new XimmtImportSettingPK(domain.getCompanyId(), domain.getCode().toString()),
				domain.getName().toString(),
				domain.getCsvFileInfo().getItemNameRowNumber().v(),
				domain.getCsvFileInfo().getImportStartRowNumber().v(),
				domainSetting.getAssembly().getMapping().getMappings().stream()
				.map(m -> new XimmtItemMapping(
						new XimmtItemMappingPK(
								domain.getCompanyId(),
								domain.getCode().v(),
								domainSetting.getDomainId().value,
								m.getItemNo()),
						m.getCsvColumnNo().isPresent()? m.getCsvColumnNo().get(): null,
						m.getFixedValue().isPresent()? m.getFixedValue().get().getValue(): null))
				.collect(Collectors.toList()));
	}

	@Override
	public boolean exist(String companyId, ExternalImportCode settingCode) {
		return this.get(companyId, settingCode).isPresent();
	}
}
