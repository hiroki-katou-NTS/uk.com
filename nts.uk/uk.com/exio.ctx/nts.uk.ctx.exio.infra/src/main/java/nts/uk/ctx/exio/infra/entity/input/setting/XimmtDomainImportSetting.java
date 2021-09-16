package nts.uk.ctx.exio.infra.entity.input.setting;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.exio.dom.input.canonicalize.ImportingMode;
import nts.uk.ctx.exio.dom.input.csvimport.ExternalImportCsvFileInfo;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;
import nts.uk.ctx.exio.dom.input.setting.DomainImportSetting;
import nts.uk.ctx.exio.dom.input.setting.assembly.ExternalImportAssemblyMethod;
import nts.uk.ctx.exio.dom.input.setting.assembly.mapping.ImportingMapping;
import nts.uk.ctx.exio.infra.entity.input.setting.assembly.XimmtItemMapping;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 *  受入設定
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Table(name = "XIMMT_DOMAIN_IMPORT_SETTING")
public class XimmtDomainImportSetting extends ContractUkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private XimmtDomainImportSettingPK pk; 
	
	/* 受入モード */
	@Column(name = "IMPORTING_MODE")
	private int importingMode;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="importSetting", orphanRemoval = true)
	public List<XimmtItemMapping> mappings;
	
	@Override
	protected Object getKey() {
		return pk;
	}
	
	public static final JpaEntityMapper<XimmtDomainImportSetting> MAPPER = new JpaEntityMapper<>(XimmtDomainImportSetting.class);
	
	public DomainImportSetting toDomain(ExternalImportCsvFileInfo csvFileInfo) {
		return new DomainImportSetting(
				ImportingDomainId.valueOf(pk.getExternalImportDomainId()),
				EnumAdaptor.valueOf(importingMode, ImportingMode.class), 
				new ExternalImportAssemblyMethod(
						csvFileInfo, 
						new ImportingMapping(mappings.stream()
								.map(m -> m.toDomain())
								.collect(Collectors.toList()))));
	}
}
