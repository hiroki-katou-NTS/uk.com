package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * エラーフラグ
 * @author do_dt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ErrorFlg {
	/**
	 * 雇用条件に一致しない
	 */
	private boolean employmentError;
	/**
	 * 性別条件に一致しない
	 */
	private boolean genderError;
	/**
	 * 年齢条件に一致しない
	 */
	private boolean ageError;
	/**
	 * 分類条件に一致しない
	 */
	private boolean classError;
}
