package nts.uk.ctx.at.request.dom.application.stamp.output;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.WorkLocationNameImported;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.WorkplaceNameImported;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.stamp.AppRecordImage;
import nts.uk.ctx.at.request.dom.application.stamp.AppStamp;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.stampsetting.AppStampSetting;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.stampapplication.StampAppReflect;

/**
 * Refactor4
 * @author hoangnd
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
//打刻申請起動時の表示情報
public class AppStampOutput {
//	打刻申請設定
	private AppStampSetting appStampSetting;
	
//	申請表示情報
	private AppDispInfoStartupOutput appDispInfoStartupOutput;
	
//	レコーダイメージ申請
	private Optional<AppRecordImage> appRecordImage = Optional.empty();
	
//	打刻エラー情報
	private Optional<List<ErrorStampInfo>> errorListOptional = Optional.empty();
	
//	打刻申請
	private Optional<AppStamp> appStampOptional = Optional.empty();
	
//	打刻申請の反映
	private Optional<StampAppReflect> appStampReflectOptional = Optional.empty();
	
//	臨時勤務利用
	private Optional<Boolean> useTemporary = Optional.empty();

//  場所名
	private List<WorkLocationNameImported> workLocationNames = Collections.emptyList();
	
//  職場名
	private List<WorkplaceNameImported> workplaceNames = Collections.emptyList();
	
// 応援を利用する
	private boolean useCheering;

// 最大応援回数
	private int maxOfCheer;
}
