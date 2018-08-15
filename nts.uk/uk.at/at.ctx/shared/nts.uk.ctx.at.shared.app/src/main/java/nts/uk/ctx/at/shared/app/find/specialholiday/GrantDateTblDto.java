package nts.uk.ctx.at.shared.app.find.specialholiday;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantDateTbl;

@Value
public class GrantDateTblDto {
	/** 付与テーブルコード */
	private String grantDateCode;
	
	/** 付与テーブル名称 */
	private String grantDateName;
	
	/** 規定のテーブルとする */
	private boolean isSpecified;
	
	/** テーブル以降の固定付与をおこなう */
	private boolean fixedAssign;
	
	/** テーブル以降の固定付与をおこなう */
	private Integer numberOfDays;
	
	public static GrantDateTblDto fromDomain(GrantDateTbl grantDateTbl) {
		return new GrantDateTblDto(
				grantDateTbl.getGrantDateCode().v(),
				grantDateTbl.getGrantDateName().v(),
				grantDateTbl.isSpecified(),
				grantDateTbl.isFixedAssign(),
				grantDateTbl.getNumberOfDays()
		);
	}
}
