package nts.uk.ctx.at.shared.app.command.yearholidaygrant;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceTbl;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author TanLV
 *
 */
@Value
public class LengthOfServiceCommand {
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

	/**
	 * Convert to domain object
	 * @return
	 */
	public LengthServiceTbl toDomain() {
		String companyId = AppContexts.user().companyId();
		
		return  LengthServiceTbl.createFromJavaType(companyId, yearHolidayCode, grantNum, allowStatus, standGrantDay,year, month);
	}
}
