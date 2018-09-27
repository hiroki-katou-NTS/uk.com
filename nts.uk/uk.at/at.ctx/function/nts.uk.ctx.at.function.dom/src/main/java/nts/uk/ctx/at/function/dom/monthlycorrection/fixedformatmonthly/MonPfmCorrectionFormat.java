package nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly;

import java.util.stream.Collectors;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 会社の月別実績の修正のフォーマット
 * @author tutk
 *
 */
@Getter
public class MonPfmCorrectionFormat extends AggregateRoot {
	/**会社ID*/
	private String companyID;
	/**コード*/
	private MonthlyPerformanceFormatCode monthlyPfmFormatCode;
	/**名称*/
	private MonPfmCorrectionFormatName monPfmCorrectionFormatName;
	/**表示項目*/
	private MonthlyActualResults displayItem;
	public MonPfmCorrectionFormat(String companyID, MonthlyPerformanceFormatCode monthlyPfmFormatCode, MonPfmCorrectionFormatName monPfmCorrectionFormatName, MonthlyActualResults displayItem) {
		super();
		this.companyID = companyID;
		this.monthlyPfmFormatCode = monthlyPfmFormatCode;
		this.monPfmCorrectionFormatName = monPfmCorrectionFormatName;
		this.displayItem = displayItem;
	}
	
	public MonPfmCorrectionFormat clone() {
		return new MonPfmCorrectionFormat(companyID,monthlyPfmFormatCode,monPfmCorrectionFormatName,displayItem.clone());
	}
}
