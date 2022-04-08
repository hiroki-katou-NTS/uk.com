package nts.uk.screen.com.app.cmf.cmf001.f.save;

import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.Optional;

import lombok.Value;
import lombok.val;
import nts.uk.ctx.exio.dom.input.canonicalize.ImportingMode;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.StringifiedValue;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;
import nts.uk.ctx.exio.dom.input.setting.DomainImportSetting;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportSetting;
import nts.uk.ctx.exio.dom.input.setting.ImportSettingBaseType;
import nts.uk.ctx.exio.dom.input.setting.assembly.ExternalImportAssemblyMethod;
import nts.uk.ctx.exio.dom.input.setting.assembly.mapping.ImportingItemMapping;
import nts.uk.ctx.exio.dom.input.setting.assembly.mapping.ImportingMapping;
import nts.uk.ctx.exio.dom.input.setting.assembly.mapping.fetch.SubstringFetch;

@Value
public class Cmf001fSaveCommand {

	String settingCode;
	List<Layout> layouts;
	
	public ExternalImportSetting update(ExternalImportSetting old) {
		
		val domainSettings = layouts.stream()
				.map(l -> l.update(old.getDomainSetting(l.getImportingDomainId())))
				.collect(toMap(l -> l.getDomainId(), l -> l));
		
		return new ExternalImportSetting(
				ImportSettingBaseType.CSV_BASE,
				old.getCompanyId(),
				old.getCode(),
				old.getName(),
				old.getCsvFileInfo(),
				domainSettings);
	}
	
	public ExternalImportCode getExternalImportCode() {
		return new ExternalImportCode(settingCode);
	}
	
	@Value
	public static class Layout {
		
		int domainId;
		List<LayoutItem> items;
		
		public DomainImportSetting update(Optional<DomainImportSetting> old) {
			return old.map(o -> update(o)).orElseGet(() -> createNew());
		}
		
		public DomainImportSetting update(DomainImportSetting old) {
			val mergedItems = items.stream()
					.map(i -> i.update(getItemMapping(old, i.getItemNo())))
					.collect(toList());
			return create(mergedItems);
		}
		
		public DomainImportSetting createNew() {
			val newItems = items.stream()
					.map(item -> item.createNew())
					.collect(toList());
			return create(newItems);
		}
		
		private ImportingDomainId getImportingDomainId() {
			return ImportingDomainId.valueOf(domainId);
		}
		
		private static Optional<ImportingItemMapping> getItemMapping(DomainImportSetting setting, int itemNo) {
			return setting.getAssembly().getMapping().getMappings().stream()
					.filter(m -> m.getItemNo() == itemNo)
					.findFirst();
		}
		
		private DomainImportSetting create(List<ImportingItemMapping> itemMappings) {
			val assembly = new ExternalImportAssemblyMethod(new ImportingMapping(itemMappings));
			return new DomainImportSetting(getImportingDomainId(), ImportingMode.DELETE_RECORD_BEFOREHAND, assembly);
		}
		
	}
	
	@Value
	public static class LayoutItem {
		
		int itemNo;
		boolean fixedValue;
		Integer csvColumnNo;
		
		public ImportingItemMapping createNew() {
			return toDomain(Optional.empty(), Optional.empty());
		}
		
		public ImportingItemMapping update(Optional<ImportingItemMapping> old) {
			return old
					.map(o -> toDomain(o.getSubstringFetch(), o.getFixedValue()))
					.orElseGet(() -> createNew());
		}
		
		private ImportingItemMapping toDomain(Optional<SubstringFetch> substring, Optional<StringifiedValue> fixedValue) {
			return new ImportingItemMapping(
					itemNo,
					this.isFixedValue(),
					Optional.ofNullable(csvColumnNo),
					substring,
					fixedValue);
		}
		
	}
	
}
