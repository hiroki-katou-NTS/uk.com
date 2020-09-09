package nts.uk.ctx.at.request.dom.application.common.service.other.output;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

/**
 * refactor 4
 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム.実績内容の取得.表示する実績内容
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ActualContentDisplay {
	
	/**
	 * 年月日
	 */
	private GeneralDate date;
	
	/**
	 * 実績詳細
	 */
	private Optional<AchievementDetail> opAchievementDetail;

}
