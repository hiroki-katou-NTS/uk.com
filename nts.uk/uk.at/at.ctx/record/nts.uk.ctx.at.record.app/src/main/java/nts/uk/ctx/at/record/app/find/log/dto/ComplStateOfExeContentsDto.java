package nts.uk.ctx.at.record.app.find.log.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComplStateOfExeContentsDto {
	/**
	 * 実行内容
	 */
	private int executionContent;
	/**
	 * 従業員の実行状況
	 */
	private int status;
}
