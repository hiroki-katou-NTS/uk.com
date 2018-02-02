package nts.uk.ctx.at.request.app.find.setting.applicationapprovalsetting.hdapplicationsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdapplicationsetting.NameWork;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdapplicationsetting.TimeHdAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.UseAtr;

@AllArgsConstructor
@Data
public class TimeHdAppSetDto {
	// 会社ID
	public String companyId;
	// 1日休超過をチェックする
	public int checkDay;
	// 60H超休を利用する
	public int use60h;
	// 出勤前2を利用する
	public int useAttend2;
	// 出勤前2名称
	public String nameBefore2;
	// 出勤前を利用する
	public int useBefore;
	// 出勤前名称
	public String nameBefore;
	// 実績表示区分
	public int actualDisp;
	// 実績超過をチェックする 
	public int checkOver;
	// 時間代休を利用する
	public int useTimeHd;
	// 時間年休を利用する
	public int useTimeYear;
	// 私用外出を利用する
	public int usePrivate;
	// 私用外出名称
	public String privateName;
	// 組合外出を利用する
	public int unionLeave;
	// 組合外出名称
	public String unionName;
	// 退勤後2を利用する
	public int useAfter2;
	// 退勤後2名称
	public String nameAfter2;
	// 退勤後を利用する
	public int useAfter;
	// 退勤後名称
	public String nameAfter;
	
	public static TimeHdAppSetDto convertToDto(TimeHdAppSet domain){
		return new TimeHdAppSetDto(domain.getCompanyId(), domain.getCheckDay().value, domain.getUse60h().value, 
				domain.getUseAttend2().value, domain.getNameBefore2().v(), domain.getUseBefore().value, domain.getNameBefore().v(), 
				domain.getActualDisp().value, domain.getCheckOver().value, domain.getUseTimeHd().value, domain.getUseTimeYear().value, 
				domain.getUsePrivate().value, domain.getPrivateName().v(), domain.getUnionLeave().value, domain.getUnionName().v(), 
				domain.getUseAfter2().value, domain.getNameAfter2().v(), domain.getUseAfter().value, domain.getNameAfter().v());
	}
}
