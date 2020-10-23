package nts.uk.screen.at.app.ktgwidget.find.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.approvalmanagement.ApprovalProcessingUseSetting;
import nts.uk.screen.at.app.ktgwidget.ktg001.ApprovedDataExecutionResultDto;

/**
 * UKDesign.UniversalK.就業.KTG_ウィジェット.KTG001_承認すべきデータ.ユースケース.起動する.起動する
 * @author tutt
 *
 */
@Setter
@Getter
public class ApprovedDataWidgetStartDto {
	
	//承認すべきデータのウィジェットを起動する
	public ApprovedDataExecutionResultDto approvedDataExecutionResultDto;
	
	//ドメインモデル「３６協定運用設定」を取得する
	
	//承認処理の利用設定を取得する
	public ApprovalProcessingUseSetting approvalProcessingUseSetting;

}
