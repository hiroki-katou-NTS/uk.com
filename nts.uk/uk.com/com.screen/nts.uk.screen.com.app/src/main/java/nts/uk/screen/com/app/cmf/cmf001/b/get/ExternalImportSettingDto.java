package nts.uk.screen.com.app.cmf.cmf001.b.get;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Value;
import nts.uk.ctx.exio.dom.input.canonicalize.ImportingMode;
import nts.uk.ctx.exio.dom.input.csvimport.ExternalImportCsvFileInfo;
import nts.uk.ctx.exio.dom.input.csvimport.ExternalImportRowNumber;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;
import nts.uk.ctx.exio.dom.input.setting.DomainImportSetting;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportName;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportSetting;
import nts.uk.ctx.exio.dom.input.setting.ImportSettingBaseType;
import nts.uk.ctx.exio.dom.input.setting.assembly.ExternalImportAssemblyMethod;
import nts.uk.shr.com.context.AppContexts;

@Value
public class ExternalImportSettingDto {

	/** 会社ID */
	private String companyId;

	/** 受入設定コード */
	private String code;

	/** 受入設定名称 */
	private String name;

	/** 受入ドメインID */
	private int domain;

	/** 受入モード */
	private int mode;

	/** CSVの項目名取得行 */
	private int itemNameRow;

	/** CSVの取込開始行 */
	private int importStartRow;

	/** レイアウト項目リスト */
	private List<Integer> itemNoList;

	public void merge(RequireMerge require, ExternalImportSetting domain) {

		domain.setName(new ExternalImportName(name));
		domain.setCsvFileInfo(toCsvFileInfo());

		DomainImportSetting setting = domain.getDomainSetting(this.domain).get();
		setting.setImportingMode(ImportingMode.valueOf(mode));
		setting.merge(require, itemNoList, domain.getCode(), setting.getDomainId());
	}

	public static interface RequireMerge extends
		ExternalImportSetting.RequireMerge{
	}

	public static ExternalImportSettingDto fromDomain(ExternalImportSetting setting) {

		DomainImportSetting domainSetting = setting.getDomainSetting().get();
		
		return new ExternalImportSettingDto(
				setting.getCompanyId(),
				setting.getCode().toString(),
				setting.getName().toString(),
				domainSetting.getDomainId().value,
				domainSetting.getImportingMode().value,
				setting.getCsvFileInfo().getItemNameRowNumber().hashCode(),
				setting.getCsvFileInfo().getImportStartRowNumber().hashCode(),
				domainSetting.getAssembly().getMapping().getMappings().stream()
				.map(m -> m.getItemNo())
				.collect(Collectors.toList()));
	}

	public ExternalImportSetting toDomain() {
		DomainImportSetting domainSetting = new DomainImportSetting(
			ImportingDomainId.valueOf(domain),
			ImportingMode.DELETE_RECORD_BEFOREHAND,
			ExternalImportAssemblyMethod.create(itemNoList));
		Map<ImportingDomainId, DomainImportSetting> domainSettings = new HashMap<>();
		domainSettings.put(ImportingDomainId.valueOf(domain), domainSetting);
		return new ExternalImportSetting(
				ImportSettingBaseType.DOMAIN_BASE,
				AppContexts.user().companyId(),
				new ExternalImportCode(code),
				new ExternalImportName(name),
				toCsvFileInfo(),
				domainSettings);
	}

	private ExternalImportCsvFileInfo toCsvFileInfo() {
		return new ExternalImportCsvFileInfo(
				new ExternalImportRowNumber(itemNameRow),
				new ExternalImportRowNumber(importStartRow));
	}

}
