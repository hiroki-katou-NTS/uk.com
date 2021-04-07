package nts.uk.ctx.at.request.app.find.application.applicationlist;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class AppTypeSetDto {
	public String companyId;
	// 申請種類
	public Integer appType;
	// 定型理由の表示
	public Integer displayFixedReason;
	// 申請理由の表示
	public Integer displayAppReason;
	// 新規登録時に自動でメールを送信する (boolean: domain)
	public Integer sendMailWhenRegister;
	// 承認処理時に自動でメールを送信する
	public Integer sendMailWhenApproval;
	// 事前事後区分の初期表示
	public Integer displayInitialSegment;
	// 事前事後区分を変更できる (boolean: domain)
	public Integer canClassificationChange;
	
//	public static List<AppTypeSetDto> convertToDto(RequestSetting domain){
//		List<AppTypeSetting> appType = domain.getApplicationSetting().getListAppTypeSetting();
//		List<AppTypeSetDto> listDto = new ArrayList<>();
//		for(AppTypeSetting item: appType){
//			listDto.add(new AppTypeSetDto(domain.getCompanyID(),
//					item.getAppType().value,
//					item.getDisplayFixedReason().value,
//					item.getDisplayAppReason().value,
//					item.getSendMailWhenRegister() == true ? 1 : 0,
//					item.getSendMailWhenApproval() == true ? 1 :0,
//					item.getDisplayInitialSegment().value,
//					item.getCanClassificationChange() == true ? 1 :0));
//		}
//		return listDto;
//	}
}
