package nts.uk.ctx.at.shared.app.find.yearholidaygrant;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceTbl;

/**
 * 
 * B - Screen
 * @author TanLV
 *
 */
@Data
@AllArgsConstructor
public class LengthOfServiceTblDto {
	/* 会社ID */
	private String companyId;

	/* 年休付与テーブル設定コード */
	private String yearHolidayCode;
	
	/* 付与回数 */
	private int grantNum;
	
	/* 一斉付与する */
	private int allowStatus;
	
	/* 付与基準日 */
	private int standGrantDay;

	/* 年数 */
	private int year;
	
	/* 月数 */
	private int month;
	
	public static LengthOfServiceTblDto fromDomain(LengthServiceTbl domain){
		return new LengthOfServiceTblDto(domain.getCompanyId(), 
									domain.getYearHolidayCode().v(), 
									domain.getGrantNum().v(), 
									domain.getAllowStatus().value, 
									domain.getStandGrantDay().value, 
									domain.getYear().v(),
									domain.getMonth().v());
	}
}
