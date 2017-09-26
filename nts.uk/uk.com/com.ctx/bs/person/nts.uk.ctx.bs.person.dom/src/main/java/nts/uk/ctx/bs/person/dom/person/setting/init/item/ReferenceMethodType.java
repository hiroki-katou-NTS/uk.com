package nts.uk.ctx.bs.person.dom.person.setting.init.item;

import lombok.AllArgsConstructor;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * ReferenceMethodType
 * 
 * @author lanlt
 *
 */
@AllArgsConstructor
@StringMaxLength(1)
public enum ReferenceMethodType {
	/** 固定値): 1 **/
	FIXEDVALUE(1),

	/** (ログイン者と同じ) */
	SAMEASLOGIN(2),
	/** (入社日と同じ): */
	SAMEASEMPLOYMENTDATE(3),
	/** (社員コードと同じ): */
	SAMEASEMPLOYEECODE(4),
	/** (システム日付): */
	SAMEASBIRTHDATE(5),
	/** (氏名と同じ ): */
	SAMEASNAME(6),
	/** (氏名（カナ）と同じ): */
	SAMEASKANANAME(7),
	/** (設定なし): */
	NOSETTING(8);

	public final int value;

}
