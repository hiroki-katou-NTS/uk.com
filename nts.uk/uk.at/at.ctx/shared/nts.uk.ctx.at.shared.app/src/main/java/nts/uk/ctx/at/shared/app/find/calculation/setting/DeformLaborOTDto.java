package nts.uk.ctx.at.shared.app.find.calculation.setting;

import java.math.BigDecimal;

import lombok.Data;

/**
 * @author yennh
 */
@Data
public class DeformLaborOTDto {
	/*排他バージョン*/
	private BigDecimal exclusVer;

	/*新規作成会社コード*/
	private String insCcd;

	/*新規作成日付*/
	private String insDate;

	/*新規作成PG*/
	private String insPg;

	/*新規作成社員コード*/
	private String insScd;

	/*変形法定内残業を計算する*/
	private BigDecimal legalOtCalc;

	/*更新会社コード*/
	private String updCcd;

	/*更新日付*/
	private String updDate;

	/*更新ＰＧ*/
	private String updPg;

	/*更新社員コード*/
	private String updScd;
}
