package nts.uk.ctx.workflow.dom.service.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 申請者の対象申請の承認者一覧
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
@Setter
public class LevelOutput {
	
	/**
	 * エラーフラグ
	 */
	private int errorFlag;
	
	/**
	 * レベル
	 */
	private List<LevelInforOutput> levelInforLst;
	
	/*public ApprovalRootContentOutput toApprovalRootContent() {
		return new ApprovalRootContentOutput(
				approvalRootState, 
				errorFlag);
	}*/
	
	/**
	 * レベル情報
	 * @author Doan Duy Hung
	 *
	 */
	@AllArgsConstructor
	@Getter
	@Setter
	public static class LevelInforOutput {
		
		/**
		 * レベルNo
		 */
		private int levelNo;
		
		/**
		 * 承認形態
		 */
		private int approvalForm;
		
		/**
		 * 承認者指定区分
		 */
		private int approvalAtr;
		
		/**
		 * 承認者
		 */
		private List<LevelApproverList> approverLst;
		
		/**
		 * 承認者一覧
		 * @author Doan Duy Hung
		 *
		 */
		@AllArgsConstructor
		@Getter
		@Setter
		public static class LevelApproverList {
			
			/**
			 * 順序
			 */
			private int order;
			
			/**
			 * 特定職場ID
			 */
			private String specWkpID;
			
			/**
			 * 確定区分
			 */
			private boolean comfirmAtr;
			
			/**
			 * 承認者情報
			 */
			private List<LevelApproverInfo> approverInfoLst;
			
			/**
			 * 承認者情報
			 * @author Doan Duy Hung
			 *
			 */
			@AllArgsConstructor
			@Getter
			@Setter
			public static class LevelApproverInfo {
				
				/**
				 * 承認者ID
				 */
				private String approverID;
				
				/**
				 * 代行者ID
				 */
				private String agentID;
				
				/**
				 * 順序
				 */
				private Integer approverInListOrder;
			}
			
		}
		
	}
	
}
