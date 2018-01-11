package nts.uk.ctx.pereg.dom.person.setting.init.item;

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
	/** (設定なし): */
	NOSETTING(1),
	/** 固定値): 2 **/
	FIXEDVALUE(2),
	/** (ログイン者と同じ) */
	SAMEASLOGIN(3),
	/** (入社日と同じ): */
	SAMEASEMPLOYMENTDATE(4),
	/** (社員コードと同じ): */
	SAMEASEMPLOYEECODE(5),
	/** (システム日付): */
	SAMEASSYSTEMDATE(6),
	/** (氏名と同じ ): */
	SAMEASNAME(7),
	/** (氏名（カナ）と同じ): */
	SAMEASKANANAME(8);


	public final int value;

}
