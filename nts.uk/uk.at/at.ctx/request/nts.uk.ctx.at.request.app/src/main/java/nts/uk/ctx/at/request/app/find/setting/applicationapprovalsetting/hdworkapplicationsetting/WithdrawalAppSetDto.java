package nts.uk.ctx.at.request.app.find.setting.applicationapprovalsetting.hdworkapplicationsetting;

/*import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.CalcStampMiss;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.OverrideSet;*/
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.WithdrawalAppSet;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class WithdrawalAppSetDto {
	/** 会社ID **/
	public String companyId;
	
	/** 事前反映設定 **/
	public int prePerflex;
	
	/** 休憩時間 **/
	public int breakTime;
	
	/** 勤務時間（出勤、退勤時刻） **/
	public int workTime;
	
	/** 休出時間未入力チェック **/
	public int checkHdTime;
	
	/** 休出申請勤務種類 **/
	public int typePaidLeave;
	
	/** 勤務変更設定 **/
	public int workChange;
	
	/** 時間初期表示 **/
	public int timeInit;
	
	/** 法内法外矛盾チェック **/
	public int checkOut;
	
	/** 代休先取り許可 **/
	public int prefixLeave;
	
	/** 休出時間指定単位  **/
	public int unitTime;
	
	/** 休暇申請同時申請設定 **/
	public int appSimul;
	
	/** 直帰区分 **/
	public int bounSeg;
	
	/** 直行区分 **/
	public int directDivi;
	
	/** 休出時間 **/
	public int restTime;
	
	// 実績超過打刻優先設定
	private int overrideSet;
	// 打刻漏れ計算区分
	private int calStampMiss;
	
	public static WithdrawalAppSetDto convertToDto(WithdrawalAppSet domain){
		return new WithdrawalAppSetDto(domain.getCompanyId(), domain.getPrePerflex().value, 
				domain.getBreakTime().value, domain.getWorkTime().value, domain.getCheckHdTime().value, 
				domain.getTypePaidLeave().value, domain.getWorkChange().value, domain.getTimeInit().value, 
				domain.getCheckOut().value, domain.getPrefixLeave().value, domain.getUnitTime().value, 
				domain.getAppSimul(), domain.getBounSeg().value, domain.getDirectDivi().value, domain.getRestTime().value,
				domain.getOverrideSet().value, domain.getCalStampMiss().value);
	}
	
	public WithdrawalAppSet toDomain() {
		return WithdrawalAppSet.createFromJavaType(companyId, prePerflex, breakTime, workTime, checkHdTime, typePaidLeave, workChange, timeInit, checkOut, prefixLeave, unitTime, appSimul, bounSeg, directDivi, restTime, overrideSet, calStampMiss);
	}
}
