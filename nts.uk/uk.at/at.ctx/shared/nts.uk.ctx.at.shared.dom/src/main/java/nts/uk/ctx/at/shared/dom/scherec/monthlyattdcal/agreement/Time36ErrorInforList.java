package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * refactor 5
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.shared(勤務予定、勤務実績).月の勤怠計算.36協定実績.エラー情報一覧
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class Time36ErrorInforList {
	
	/**
	 * 一覧
	 */
	private List<Time36AgreementError> time36AgreementErrorLst;
}
