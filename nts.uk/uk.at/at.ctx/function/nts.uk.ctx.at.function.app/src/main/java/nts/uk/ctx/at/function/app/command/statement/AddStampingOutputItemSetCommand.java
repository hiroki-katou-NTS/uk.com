package nts.uk.ctx.at.function.app.command.statement;

import lombok.Data;
import nts.uk.ctx.at.function.dom.statement.StampOutputSettingCode;
import nts.uk.ctx.at.function.dom.statement.StampOutputSettingName;
import nts.uk.ctx.at.function.dom.statement.StampingOutputItemSetGetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.shr.com.context.AppContexts;

@Data
public class AddStampingOutputItemSetCommand implements StampingOutputItemSetGetMemento{
	    // コード
		private String stampOutputSetCode;
		
		// 名称
		private String stampOutputSetName;
		
		// 打刻方法の出力
		private boolean outputEmbossMethod;
		
		// 就業時間帯の出力
		private boolean outputWorkHours; 
		
		// 設定場所の出力
		private boolean outputSetLocation;
		
		// 位置情報の出力
		private boolean outputPosInfor;
		
		// 残業時間の出力
		private boolean outputOT;
		
		// 深夜時間の出力
		private boolean outputNightTime;
		
		// 応援カードの出力
		private boolean outputSupportCard;

		@Override
		public CompanyId getCompanyID() {
			return new CompanyId(AppContexts.user().companyId());
		}

		@Override
		public StampOutputSettingCode getStampOutputSetCode() {
			return new StampOutputSettingCode(this.stampOutputSetCode);
		}

		@Override
		public StampOutputSettingName getStampOutputSetName() {
			return new StampOutputSettingName(this.stampOutputSetName);
		}

		@Override
		public boolean getOutputEmbossMethod() {
			return this.outputEmbossMethod;
		}

		@Override
		public boolean getOutputWorkHours() {
			return this.outputWorkHours;
		}

		@Override
		public boolean getOutputSetLocation() {
			return this.outputSetLocation;
		}

		@Override
		public boolean getOutputPosInfor() {
			return this.outputPosInfor;
		}

		@Override
		public boolean getOutputOT() {
			return this.outputOT;
		}

		@Override
		public boolean getOutputNightTime() {
			return this.outputNightTime;
		}

		@Override
		public boolean getOutputSupportCard() {
			return this.outputSupportCard;
		}
	
}
