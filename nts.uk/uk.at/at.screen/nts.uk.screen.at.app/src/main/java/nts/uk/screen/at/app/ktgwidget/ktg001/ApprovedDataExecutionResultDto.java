package nts.uk.screen.at.app.ktgwidget.ktg001;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.screen.at.app.ktgwidget.find.dto.ApprovedAppStatusDetailedSettingDto;
import nts.uk.screen.at.app.ktgwidget.find.dto.ClosureIdPresentClosingPeriodDto;

/**
 * UKDesign.UniversalK.就業.KTG_ウィジェット.KTG001_承認すべきデータ.アルゴリズム.承認すべきデータのウィジェットを起動する.承認すべきデータの実行結果
 * 
 * @author tutt
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApprovedDataExecutionResultDto {
	// 勤怠担当者である
	private Boolean haveParticipant;

	// 名称
	private String topPagePartName;

	// 承認すべき申請データ
	private Boolean appDisplayAtr;

	// 承認すべき日の実績が存在する
	private Boolean dayDisplayAtr;

	// 承認すべき月の実績が存在す
	private Boolean monthDisplayAtr;

	// 承認すべき36協定が存在する
	private Boolean agrDisplayAtr;

	// 承認すべき申請状況の詳細設定
	private List<ApprovedAppStatusDetailedSettingDto> approvedAppStatusDetailedSettings;

	// 締めID, 現在の締め期間
	private List<ClosureIdPresentClosingPeriodDto> closingPeriods;
}
