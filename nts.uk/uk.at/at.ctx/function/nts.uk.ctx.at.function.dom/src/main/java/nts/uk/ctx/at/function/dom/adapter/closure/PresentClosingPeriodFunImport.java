package nts.uk.ctx.at.function.dom.adapter.closure;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;

@Getter
@Setter
@NoArgsConstructor
public class PresentClosingPeriodFunImport {
	// 処理年月
		private YearMonth processingYm;

		// 締め開始日
		private GeneralDate closureStartDate;

		// 締め終了日
		private GeneralDate closureEndDate;

		public PresentClosingPeriodFunImport(YearMonth processingYm, GeneralDate closureStartDate,
				GeneralDate closureEndDate) {
			super();
			this.processingYm = processingYm;
			this.closureStartDate = closureStartDate;
			this.closureEndDate = closureEndDate;
		}
		
}
