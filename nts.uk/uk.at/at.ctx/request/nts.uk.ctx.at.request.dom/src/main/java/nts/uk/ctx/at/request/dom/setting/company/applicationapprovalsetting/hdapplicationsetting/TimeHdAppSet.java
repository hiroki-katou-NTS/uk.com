package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdapplicationsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.UseAtr;
/**
 * 時間休申請設定
 * @author yennth
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TimeHdAppSet extends AggregateRoot{
	// 会社ID
	private String companyId;
	// 1日休超過をチェックする
	private UseAtr checkDay;
	// 60H超休を利用する
	private UseAtr use60h;
	// 出勤前2を利用する
	private UseAtr useAttend2;
	// 出勤前2名称
	private NameWork nameBefore2;
	// 出勤前を利用する
	private UseAtr useBefore;
	// 出勤前名称
	private NameWork nameBefore;
	// 実績表示区分
	private UseAtr actualDisp;
	// 実績超過をチェックする 
	private UseAtr checkOver;
	// 時間代休を利用する
	private UseAtr useTimeHd;
	// 時間年休を利用する
	private UseAtr useTimeYear;
	// 私用外出を利用する
	private UseAtr usePrivate;
	// 私用外出名称
	private NameWork privateName;
	// 組合外出を利用する
	private UseAtr unionLeave;
	// 組合外出名称
	private NameWork unionName;
	// 退勤後2を利用する
	private UseAtr useAfter2;
	// 退勤後2名称
	private NameWork nameAfter2;
	// 退勤後を利用する
	private UseAtr useAfter;
	// 退勤後名称
	private NameWork nameAfter;
	
	public static TimeHdAppSet createFromJavaType(String companyId, int checkDay, int use60h, int useAttend2, 
			String nameBefore2, int useBefore, String nameBefore, int actualDisp, int checkOver, int useTimeHd, 
			int useTimeYear, int usePrivate, String privateName, int unionLeave, String unionName, int useAfter2, 
			String nameAfter2, int useAfter, String nameAfter){
		return new TimeHdAppSet(companyId, 
				EnumAdaptor.valueOf(checkDay, UseAtr.class), 
				EnumAdaptor.valueOf(use60h, UseAtr.class), 
				EnumAdaptor.valueOf(useAttend2, UseAtr.class), new NameWork(nameBefore2), 
				EnumAdaptor.valueOf(useBefore, UseAtr.class), new NameWork(nameBefore), 
				EnumAdaptor.valueOf(actualDisp, UseAtr.class), 
				EnumAdaptor.valueOf(checkOver, UseAtr.class), 
				EnumAdaptor.valueOf(useTimeHd, UseAtr.class), 
				EnumAdaptor.valueOf(useTimeYear, UseAtr.class), 
				EnumAdaptor.valueOf(usePrivate, UseAtr.class), new NameWork(privateName), 
				EnumAdaptor.valueOf(unionLeave, UseAtr.class), new NameWork(unionName), 
				EnumAdaptor.valueOf(useAfter2, UseAtr.class), new NameWork(nameAfter2), 
				EnumAdaptor.valueOf(useAfter, UseAtr.class), new NameWork(nameAfter));
	}
}
