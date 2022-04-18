package nts.uk.screen.com.app.cmf.cmf001.c.save;

import java.util.Optional;

import lombok.Value;
import lombok.val;
import nts.uk.ctx.exio.app.input.setting.assembly.revise.ReviseItemDto;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;
import nts.uk.ctx.exio.dom.input.importableitem.ItemType;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;
import nts.uk.ctx.exio.dom.input.setting.assembly.mapping.fetch.CharacterPosition;
import nts.uk.ctx.exio.dom.input.setting.assembly.mapping.fetch.SubstringFetch;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.ReviseItem;

@Value
public class Cmf001cSaveCommand {
	
	String settingCode;
	int domainId;
	int itemNo;
	
	String mappingSource;
	Integer csvFetchStart;
	Integer csvFetchEnd;
	String fixedValue;
	ReviseItemDto.RevisingValue revisingValue;
	
	public ExternalImportCode getExternalImportCode() {
		return new ExternalImportCode(settingCode);
	}
	
	public boolean isCsvMapping() {
		return "CSV".equals(mappingSource);
	}
	
	public boolean isFixedValue() {
		return "固定値".equals(mappingSource);
	}
	
	public Optional<SubstringFetch> toSubstringFetch() {
		
		if (csvFetchStart == null && csvFetchEnd == null) {
			return Optional.empty();
		}

		val start = CharacterPosition.fromIntegerExpression(csvFetchStart);
		val end = CharacterPosition.fromIntegerExpression(csvFetchEnd);
		val fetch = new SubstringFetch(start, end);
		return Optional.of(fetch);
	}
	
	public Optional<ReviseItem> toDomainReviseItem(String companyId, ItemType itemType) {
		
		if (!isCsvMapping()) {
			return Optional.empty();
		}
		
		val revisingValueDomain = revisingValue.toDomain(itemType);
		if (revisingValueDomain == null) {
			return Optional.empty();
		}
		
		val domain = new ReviseItem(
				companyId,
				getExternalImportCode(),
				ImportingDomainId.valueOf(domainId),
				itemNo,
				revisingValueDomain);
		
		return Optional.of(domain);
	}
}
