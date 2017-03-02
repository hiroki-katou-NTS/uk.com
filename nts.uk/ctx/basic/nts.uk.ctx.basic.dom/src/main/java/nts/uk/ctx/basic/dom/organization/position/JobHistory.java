package nts.uk.ctx.basic.dom.organization.position;
	import java.time.LocalDate;
	import lombok.Getter;
	import nts.arc.layer.dom.AggregateRoot;
	import nts.arc.time.GeneralDate;
	import nts.uk.ctx.basic.dom.company.CompanyCode;

	@Getter
	public class JobHistory extends AggregateRoot{
		
		private GeneralDate startDate;
		
		private GeneralDate endDate;		
				
		private String historyId;
		
		private CompanyCode companyCode;
			
		public JobHistory(GeneralDate startDate , GeneralDate endDate, 
				CompanyCode companyCode, String historyId) {
			super();

			this.startDate = startDate;
			this.endDate = endDate;
			this.historyId = historyId;
			this.companyCode = companyCode;
		}

		public static JobHistory createSimpleFromJavaType(
				String startDate, 
				String endDate , 
				String companyCode, 
				String historyId)
		{
			return new JobHistory(
					GeneralDate.localDate(LocalDate.parse(startDate)),
					GeneralDate.localDate(LocalDate.parse(endDate)),
					new CompanyCode(companyCode), 
					historyId);
		}
		

	}
