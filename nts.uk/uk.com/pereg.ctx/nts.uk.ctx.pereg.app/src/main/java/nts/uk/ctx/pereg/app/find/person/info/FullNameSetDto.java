package nts.uk.ctx.pereg.app.find.person.info;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.bs.person.dom.person.info.fullnameset.FullNameSet;

@Getter
@AllArgsConstructor
public class FullNameSetDto {

	private String fullName;

	private String fullNameKana;

	static FullNameSetDto fromDomain(FullNameSet domain) {
		if (domain == null) {
			return null;
		}
		
		return new FullNameSetDto(domain.getFullName().v(), domain.getFullNameKana().v());
	}
}
