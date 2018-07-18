package nts.uk.ctx.exio.app.find.exo.executionlog;

import lombok.AllArgsConstructor;
import lombok.Value;

import nts.uk.ctx.exio.dom.exo.executionlog.ExOutOpMng;

/**
 * 外部出力動作管理
 */
@AllArgsConstructor
@Value
public class ExOutOpMngDto {
	private String exOutProId;

	public static ExOutOpMngDto fromDomain(ExOutOpMng domain) {
		return new ExOutOpMngDto(domain.getExOutProId());
	}
}
