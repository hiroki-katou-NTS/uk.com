package nts.uk.ctx.exio.dom.input.setting;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.csvimport.ExternalImportCsvFileInfo;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;
import nts.uk.ctx.exio.dom.input.setting.assembly.ExternalImportAssemblyMethod;

/**
 * 受入設定
 */
@Getter
public class ExternalImportSetting implements DomainAggregate {
	/**設定ベース種類*/
	private ImportSettingBaseType baseType;

	/** 会社ID */
	private String companyId;

	/** 受入設定コード */
	private ExternalImportCode code;

	/** 受入設定名称 */
	@Setter
	private ExternalImportName name;

	/** CSVファイル情報 */
	@Setter
	private ExternalImportCsvFileInfo csvFileInfo;

	/** ドメイン受入設定 **/
	private Map<ImportingDomainId, DomainImportSetting> domainSettings;
	
	public ExternalImportSetting(
			ImportSettingBaseType baseType,
			String companyId,
			ExternalImportCode code,
			ExternalImportName name,
			ExternalImportCsvFileInfo csvFileInfo,
			Map<ImportingDomainId, DomainImportSetting> domainSettings) {

		this.baseType = baseType;
		this.companyId = companyId;
		this.code = code;
		this.name = name;
		this.csvFileInfo = csvFileInfo;
		this.domainSettings = domainSettings;
	}

	/**
	 * マッピングを更新する
	 * @param itemList
	 */
	public void merge(DomainImportSetting.RequireMerge require, List<Integer> itemList) {
		if(domainSettings.size() > 1) throw new RuntimeException("複数ドメインを受け入れる場合は変更できません");
		domainSettings.get(0).merge(require, itemList, code);
	}

	/**
	 * ドメインが変更されたのでマッピングを作り直す
	 * @param domainId
	 * @param items
	 */
	public void changeDomain(DomainImportSetting.RequireChangeDomain require, ImportingDomainId domainId, List<Integer> items) {
		if(domainSettings.size() > 1) throw new RuntimeException("複数ドメインを受け入れる場合は変更できません");
		domainSettings.get(0).changeDomain(require, domainId, items, code);
	}

	public void assemble(DomainImportSetting.RequireAssemble require, ExecutionContext context, InputStream csvFileStream) {
		domainSettings.forEach((domainId, setting) -> {
			setting.assemble(require, context, csvFileInfo, csvFileStream);
		});
	}
	
	public static interface RequireMerge extends DomainImportSetting.RequireMerge {
	}
	public static interface RequireChangeDomain extends DomainImportSetting.RequireChangeDomain {
	}
	public static interface RequireAssemble extends DomainImportSetting.RequireAssemble {
	}

	public ExternalImportAssemblyMethod getAssembly(int domainId) {
		return getDomainSetting(domainId).get().getAssembly();
	}

	public Optional<DomainImportSetting> getDomainSetting() {
		return getDomainSetting(0);
	}
	
	public Optional<DomainImportSetting> getDomainSetting(int domain) {
		if (baseType == ImportSettingBaseType.DOMAIN_BASE)  return Optional.of(this.domainSettings.get(0));

		ImportingDomainId domainId = ImportingDomainId.valueOf(domain);
		return this.domainSettings.containsKey(domainId)
				? Optional.of(this.domainSettings.get(domainId))
				: Optional.empty();
	}
}
