package nts.uk.ctx.at.shared.dom.person;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class FullNameSetImport {
	/** 氏名 - FullName */
	private String fullName;

	/** 氏名カナ - FullNameKana */
	private String fullNameKana;
}
