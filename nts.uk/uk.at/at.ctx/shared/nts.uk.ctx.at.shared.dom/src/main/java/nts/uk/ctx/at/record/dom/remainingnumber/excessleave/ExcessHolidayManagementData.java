package nts.uk.ctx.at.record.dom.remainingnumber.excessleave;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;

/**
 * 超過有休管理データ
 * @author HopNT
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ExcessHolidayManagementData extends AggregateRoot {

	private String id;
	
	// 会社ID
	private String cID;
	
	// 社員ID	
	private String sID;
	
	// 付与日
	private GeneralDate grantDate;
	
	// 使用期限
	private GeneralDate expiredDate;
	
	// 期限切れ状態
	private LeaveExpirationStatus expiredState;
	
	// 登録種別
	private GrantRemainRegisterType registrationType;
	
	// 超過有休数情報
	private ExcessHolidayInfo info;
	
	public ExcessHolidayManagementData(String id, String cID, String sID, GeneralDate grantDate, GeneralDate expiredDate, int expiredState, int registType,
			int occurrencesNumber, int usedNumber, int remainNumer){
		this.id = id;
		this.cID = cID;
		this.sID = sID;
		this.grantDate = grantDate;
		this.expiredDate = expiredDate;
		this.expiredState = EnumAdaptor.valueOf(expiredState, LeaveExpirationStatus.class);
		this.registrationType = EnumAdaptor.valueOf(registType, GrantRemainRegisterType.class);
		this.info = new ExcessHolidayInfo(occurrencesNumber, usedNumber, remainNumer);
	}
}
