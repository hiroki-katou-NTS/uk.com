package nts.uk.ctx.exio.infra.entity.input.setting;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
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
import nts.uk.ctx.exio.dom.input.csvimport.BaseCsvInfo;
import nts.uk.ctx.exio.dom.input.csvimport.ExternalImportCsvFileInfo;
import nts.uk.ctx.exio.dom.input.csvimport.ExternalImportRowNumber;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportName;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportSetting;
import nts.uk.ctx.exio.dom.input.setting.ImportSettingBaseType;
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
	@Column(name = "BASE_TYPE")
	private int baseType;
	
	/* 受入設定名称 */
	@Column(name = "NAME")
	private String name;
		
	/* CSV項目名取得行 */
	@Column(name = "ITEM_NAME_ROW_NUMBER")
	private int itemNameRowNumber;
	
	/* CSV受入開始行 */
	@Column(name = "IMPORT_START_ROW_NUMBER")
	private int importStartRowNumber;
	
	/* CSVファイルID */
	@Column(name = "BASE_CSV_FILE_ID")
	private String baseCsvFileId;

	/* ベースCSV */
	@OneToMany(cascade=CascadeType.ALL, mappedBy="importSetting", orphanRemoval = true)
	private List<XimmtBaseCsvColumns> baseColumns;
	
	@Override
	protected Object getKey() {
		return pk;
	}
	
	public ExternalImportSetting toDomain() {
		ImportSettingBaseType type = ImportSettingBaseType.valueOf(this.baseType);
		List<String> baseCsvColumns = this.baseColumns.stream()
			.map(col -> col.getColumnName())
			.collect(Collectors.toList());
		
		Optional<BaseCsvInfo> baseCsv = (type == ImportSettingBaseType.CSV_BASE)
					? Optional.of(new BaseCsvInfo(this.baseCsvFileId, baseCsvColumns))
					: Optional.empty();
		return new ExternalImportSetting(
				type,
				this.pk.getCompanyId(), 
				new ExternalImportCode(this.pk.getCode()), 
				new ExternalImportName(this.name),
				new ExternalImportCsvFileInfo(
						new ExternalImportRowNumber(this.itemNameRowNumber),
						new ExternalImportRowNumber(importStartRowNumber),
						baseCsv),
				new HashMap<>());
	}
}
