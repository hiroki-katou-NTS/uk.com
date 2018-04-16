/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.exio.app.find.exi.execlog;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class ErrorContentDto.
 */
// エラーの内容
@Getter
@Setter
public class ErrorContentDto {
	private String nameSetting;
	private ExacExeResultLogDto resultLog;
	private ExacErrorLogDto[] errorLog;
}
