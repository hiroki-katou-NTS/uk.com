package nts.uk.screen.com.app.cmf.cmf001.f.save;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Value;
import nts.uk.ctx.exio.dom.input.canonicalize.ImportingMode;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.StringifiedValue;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;
import nts.uk.ctx.exio.dom.input.setting.DomainImportSetting;
import nts.uk.ctx.exio.dom.input.setting.assembly.ExternalImportAssemblyMethod;
import nts.uk.ctx.exio.dom.input.setting.assembly.mapping.ImportingItemMapping;
import nts.uk.ctx.exio.dom.input.setting.assembly.mapping.ImportingMapping;

@Value
public class Cmf001fSaveCommand {
	private String code;
	private int domainId;
	private List<CsvBaseImportDomainItemDto> items;

	public DomainImportSetting toDomain() {
		List<ImportingItemMapping> mappings = this.items.stream()
				.map(mapping -> new ImportingItemMapping(
						mapping.getItemNo(),
						Optional.ofNullable(mapping.getCsvItemNo()),
						Optional.ofNullable(StringifiedValue.of(mapping.getFixedValue()))))
				.collect(Collectors.toList());
				
		return new DomainImportSetting(
					ImportingDomainId.valueOf(this.domainId),
					ImportingMode.DELETE_RECORD_BEFOREHAND,
					new ExternalImportAssemblyMethod(
							new ImportingMapping(mappings))
				);
	}
}
