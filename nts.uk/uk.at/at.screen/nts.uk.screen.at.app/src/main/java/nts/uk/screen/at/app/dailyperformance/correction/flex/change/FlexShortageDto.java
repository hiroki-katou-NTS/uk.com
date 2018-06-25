package nts.uk.screen.at.app.dailyperformance.correction.flex.change;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;
import nts.uk.screen.at.app.dailyperformance.correction.dto.month.DPMonthParent;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlexShortageDto {

	// フレックス不足時間
		private ItemValue value18;
		private ItemValue value19;
		private ItemValue value21;
		private ItemValue value189;
		private ItemValue value190;
		private ItemValue value191;
		
		private boolean canflex;
		private boolean showFlex;
		private DPMonthParent monthParent;
		//private boolean retiredFlag;
		
		private CalcFlexChangeDto calc;
		private String redConditionMessage;
		private String messageNotForward;

		public FlexShortageDto(ItemValue value18, ItemValue value19, ItemValue value21, ItemValue value189, ItemValue value190, ItemValue value191) {
			this.value18 = value18;
			this.value19 = value19;
			this.value21 = value21;
			this.value189 = value189;
			this.value190 = value190;
			this.value191 = value191;
		}
		
		public FlexShortageDto createCanFlex(boolean flex){
			this.canflex = flex;
			return this;
		}
		
		public FlexShortageDto createShowFlex(boolean flex){
			this.showFlex = flex;
			return this;
		}
		
		public FlexShortageDto createMonthParent(DPMonthParent dPMonthParent){
			this.monthParent = dPMonthParent;
			return this;
		}
		
//		public FlexShortageDto createRetiredFlag(boolean flag){
//			this.retiredFlag = flag;
//			return this;
//		}
		
		public FlexShortageDto createCalcFlex(CalcFlexChangeDto calc){
			this.calc = calc;
			return this;
		}
		
		public FlexShortageDto createRedConditionMessage(String condition){
			this.redConditionMessage = condition;
			return this;
		}
		
		public FlexShortageDto createNotForward(String message){
			this.messageNotForward = message;
			return this;
		}
}
