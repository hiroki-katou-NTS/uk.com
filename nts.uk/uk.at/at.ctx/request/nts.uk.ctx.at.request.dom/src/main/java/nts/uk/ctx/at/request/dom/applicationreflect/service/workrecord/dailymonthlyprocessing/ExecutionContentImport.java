package nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.dailymonthlyprocessing;

public enum ExecutionContentImport {
		//0: 日別作成 //tạo
		DAILY_CREATION(0,"日別作成"),

		//1: 日別計算 //tính toán
		DAILY_CALCULATION(1,"日別計算"),

		//2: 承認結果反映 //phản ánh
		REFLRCT_APPROVAL_RESULT(2,"承認結果反映"),

		//3: 月別集計 //tổng hợp	
		MONTHLY_AGGREGATION(3,"月別集計");

		public final int value;
		public String nameId; 

		private ExecutionContentImport(int value,String nameId) {
			this.value = value;
			this.nameId = nameId;
		}
}
