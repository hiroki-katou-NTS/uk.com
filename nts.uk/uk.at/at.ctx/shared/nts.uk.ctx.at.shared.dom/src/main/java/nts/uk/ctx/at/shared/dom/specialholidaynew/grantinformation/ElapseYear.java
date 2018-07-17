package nts.uk.ctx.at.shared.dom.specialholidaynew.grantinformation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.DomainObject;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.specialholidaynew.grantcondition.UseAtr;

/**
 * 経過年数に対する付与日数
 * 
 * @author tanlv
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ElapseYear extends DomainObject {
	/** 会社ID */
	private String companyId;

	/** 特別休暇コード */
	private int specialHolidayCode;
	
	/** 付与テーブルコード */
	private String grantDateCode;
	
	/** 付与テーブルコード */
	private Double grantedDays;
	
	/** 付与テーブルコード */
	private Integer months;
	
	/** 付与テーブルコード */
	private Integer years;
	
	@Override
	public void validate() {
		super.validate();
	}
}
