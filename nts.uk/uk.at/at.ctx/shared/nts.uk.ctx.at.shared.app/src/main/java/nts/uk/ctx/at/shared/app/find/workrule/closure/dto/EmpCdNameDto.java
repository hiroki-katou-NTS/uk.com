package nts.uk.ctx.at.shared.app.find.workrule.closure.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmpCdNameDto {
	/** The code. */
	private String code;

	/** The name. */
	private String name;

	/**
	 *  締めＩＤ
	 */
	private Integer closureId;
}
