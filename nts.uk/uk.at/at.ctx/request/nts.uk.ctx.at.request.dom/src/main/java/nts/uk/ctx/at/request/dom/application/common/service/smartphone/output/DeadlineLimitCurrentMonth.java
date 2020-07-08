package nts.uk.ctx.at.request.dom.application.common.service.smartphone.output;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * refactor 4
 * UKDesign.UniversalK.就業.KAF_申請.KAFS00_申請部品（スマホ）.KAFS00_A_申請メッセージ.アルゴリズム.起動する.当月の締切制限
 * @author Doan Duy Hung
 *
 */
@Getter
public class DeadlineLimitCurrentMonth {

	/**
	 * 利用区分
	 */
	private boolean useAtr;
	
	/**
	 * 申請締切日
	 */
	@Setter
	private Optional<GeneralDate> opAppDeadline;
	
	public DeadlineLimitCurrentMonth(boolean useAtr) {
		this.useAtr = useAtr;
		this.opAppDeadline = Optional.empty();
	}
	
}
