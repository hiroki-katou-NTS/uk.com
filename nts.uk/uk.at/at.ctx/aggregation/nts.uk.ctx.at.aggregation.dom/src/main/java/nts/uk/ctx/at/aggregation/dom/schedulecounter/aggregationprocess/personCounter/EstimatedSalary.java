package nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.personCounter;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.Value;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.estimate.EstimateAmount;
import nts.uk.shr.com.color.ColorCode;

/**
 * 想定給与額
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.スケジュール集計.集計処理.個人計.個人計の想定給与額カテゴリを集計する.想定給与額
 * @author kumiko_otake
 */
@Value
public class EstimatedSalary {

	/** 給与額 **/
	private final BigDecimal salary;

	/** 目安金額 **/
	private final EstimateAmount reference;

	/** 目安金額背景色 **/
	private final Optional<ColorCode> background;

}
