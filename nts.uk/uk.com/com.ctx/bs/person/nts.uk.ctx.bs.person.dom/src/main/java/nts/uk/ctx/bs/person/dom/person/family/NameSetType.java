package nts.uk.ctx.bs.person.dom.person.family;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum NameSetType {
	// 1: 氏名 (FullName)
	FullName(1),
	
	// 2: 氏名カナ (FullNameKana)
	FullNameKana(2);

	public final int value;
}
