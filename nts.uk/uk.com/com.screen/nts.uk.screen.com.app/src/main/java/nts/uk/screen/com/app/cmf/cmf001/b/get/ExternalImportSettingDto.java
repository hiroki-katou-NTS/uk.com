package nts.uk.screen.com.app.cmf.cmf001.b.get;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Value;
import lombok.val;
import nts.uk.ctx.exio.dom.input.canonicalize.ImportingMode;
import nts.uk.ctx.exio.dom.input.csvimport.ExternalImportCsvFileInfo;
import nts.uk.ctx.exio.dom.input.csvimport.ExternalImportRowNumber;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;
import nts.uk.ctx.exio.dom.input.setting.*;
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

	/** CSVの項目名取得行 */
	private int itemNameRow;

	/** CSVの取込開始行 */
	private int importStartRow;
	
	private String csvFileId;
	
	private List<ImportDomainDto> domains;
	
	private List<BaseCsvInfoDto> csvItems;

	public static interface RequireMerge extends
		ExternalImportSetting.RequireMerge{
	}

	public static ExternalImportSettingDto fromDomain(FromCsvBaseSettingToDomainRequire toDomainRequire, ExternalImportSetting setting) {

		List<ImportDomainDto> domains = setting.getDomainSettings().stream()
			.map(ds -> new ImportDomainDto(ds.getDomainId().value, ds.getAssembly().getAllItemNo()))
			.collect(Collectors.toList());

		List<BaseCsvInfoDto> items = (setting.getBaseType() != ImportSettingBaseType.CSV_BASE || !setting.getCsvFileInfo().getBaseCsvFileId().isPresent())
				? new ArrayList<BaseCsvInfoDto>()
				:  toDomainRequire.createBaseCsvInfo(setting.getCsvFileInfo()).orElse(new ArrayList<BaseCsvInfoDto>());
		
		return new ExternalImportSettingDto(
				setting.getCompanyId(),
				setting.getCode().toString(),
				setting.getName().toString(),
				setting.getCsvFileInfo().getItemNameRowNumber().hashCode(),
				setting.getCsvFileInfo().getImportStartRowNumber().hashCode(),
				setting.getCsvFileInfo().getBaseCsvFileId().orElse(""),
				domains,
				items);
	}
	
	public ExternalImportSetting toDomainAsCsvBase() {
		Map<ImportingDomainId, DomainImportSetting> domainSettings = this.domains.stream()
				.map(d -> new DomainImportSetting(
					ImportingDomainId.valueOf(d.getDomainId()),
					ImportingMode.DELETE_RECORD_BEFOREHAND,
					ExternalImportAssemblyMethod.create(d.getItemNoList())))
				.collect(Collectors.toMap(DomainImportSetting::getDomainId, d -> d));
			
			return new ExternalImportSetting(
					ImportSettingBaseType.CSV_BASE,
					AppContexts.user().companyId(),
					new ExternalImportCode(code),
					new ExternalImportName(name),
					toCsvFileInfo(),
					domainSettings);
	}

	public ExternalImportSetting toDomainAsDomainBase(RequireToDomain require, Optional<ExternalImportSetting> oldSetting) {
		
		val settingCode = new ExternalImportCode(code);
		
		return new ExternalImportSetting(
				ImportSettingBaseType.DOMAIN_BASE,
				AppContexts.user().companyId(),
				settingCode,
				new ExternalImportName(name),
				toCsvFileInfo(),
				merge(require, settingCode, domains, oldSetting));
	}
	
	public interface RequireToDomain extends DomainImportSetting.RequireMerge {
	}
	
	private static Map<ImportingDomainId, DomainImportSetting> merge(
			RequireToDomain require,
			ExternalImportCode settingCode,
			List<ImportDomainDto> domains,
			Optional<ExternalImportSetting> oldSetting) {
		
		val results = new HashMap<ImportingDomainId, DomainImportSetting>();
		
		for (val domainDto : domains) {
			val oldDomain = oldSetting.flatMap(s -> s.getDomainSetting(domainDto.getImportingDomainId()));
			val merged = merge(require, settingCode, domainDto, oldDomain);
			results.put(merged.getDomainId(), merged);
		}
		
		return results;
	}
	
	private static DomainImportSetting merge(
			RequireToDomain require,
			ExternalImportCode settingCode,
			ImportDomainDto dto,
			Optional<DomainImportSetting> oldOpt) {
		
		if (!oldOpt.isPresent()) {
			return new DomainImportSetting(
					dto.getImportingDomainId(),
					ImportingMode.DELETE_RECORD_BEFOREHAND,
					ExternalImportAssemblyMethod.create(dto.getItemNoList()));
		}
		
		val old = oldOpt.get();
		old.merge(require, dto.getItemNoList(), settingCode, dto.getImportingDomainId());
		
		return old;
	}

	public ExternalImportCsvFileInfo toCsvFileInfo() {
		return new ExternalImportCsvFileInfo(
				new ExternalImportRowNumber(itemNameRow),
				new ExternalImportRowNumber(importStartRow),
				 Optional.ofNullable(this.csvFileId));
	}

}
