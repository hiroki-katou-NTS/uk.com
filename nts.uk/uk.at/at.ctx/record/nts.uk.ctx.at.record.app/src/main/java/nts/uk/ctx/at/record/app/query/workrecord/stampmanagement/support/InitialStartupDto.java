package nts.uk.ctx.at.record.app.query.workrecord.stampmanagement.support;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyInfo;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.info.WorkplaceInfor;

/**
 * 
 * @author NWS_namnv
 *
 */
@AllArgsConstructor
@Getter
public class InitialStartupDto {
	
	// 応援カード
	private List<SupportCardDto> supportCards;
	
	// 会社情報
	private List<CompanyInfo> companyInfos;
	
	// 職場情報
	private List<WorkplaceInfor> workplaceInfors;
	
	// 応援カード編集設定
	private SupportCardSettingDto supportCardEdit;

}
