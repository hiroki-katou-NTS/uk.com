package nts.uk.ctx.exio.infra.entity.input.setting;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.exio.dom.input.csvimport.ExternalImportCsvFileInfo;
import nts.uk.ctx.exio.dom.input.csvimport.ExternalImportRowNumber;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportName;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportSetting;
import nts.uk.ctx.exio.infra.entity.input.setting.assembly.XimmtItemMapping;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 *  受入設定
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Table(name = "XIMMT_IMPORT_SETTING")
public class XimmtImportSetting extends ContractUkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private XimmtImportSettingPK pk; 
	
	/* 受入設定名称 */
	@Column(name = "NAME")
	private String name;
		
	/* CSV項目名取得行 */
	@Column(name = "ITEM_NAME_ROW_NUMBER")
	private int itemNameRowNumber;
	
	/* CSV受入開始行 */
	@Column(name = "IMPORT_START_ROW_NUMBER")
	private int importStartRowNumber;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="importSetting", orphanRemoval = true)
	public List<XimmtItemMapping> mappings;
	
	@Override
	protected Object getKey() {
		return pk;
	}
	
	public static final JpaEntityMapper<XimmtImportSetting> MAPPER = new JpaEntityMapper<>(XimmtImportSetting.class);
	
	public ExternalImportSetting toDomain() {		
		return new ExternalImportSetting(
				this.pk.getCompanyId(), 
				new ExternalImportCode(this.pk.getCode()), 
				new ExternalImportName(this.name),
				new ExternalImportCsvFileInfo(
						new ExternalImportRowNumber(this.itemNameRowNumber),
						new ExternalImportRowNumber(importStartRowNumber)),
				new ArrayList<>());
	}
}
