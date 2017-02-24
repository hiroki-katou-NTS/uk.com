package nts.uk.ctx.pr.core.dom.itemperiod;

import java.time.YearMonth;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.core.dom.company.CompanyCode;

public class ItemPeriod extends AggregateRoot {

	@Getter
	private CompanyCode companyCode;

	@Getter
	private ItemClass itemclass;

	@Getter
	private String ItemCode;

	@Getter
	private DateUseClassification expirationDateUseClassification;

	@Getter
	private YearMonth startYear;

	@Getter
	private YearMonth endYear;

	@Getter
	private UsageClassification cycleUsageClassification;

	@Getter
	private UsageClassification januaryUsageClassification;

	@Getter
	private UsageClassification februaryUsageClassification;

	@Getter
	private UsageClassification marchUsageClassification;

	@Getter
	private UsageClassification aprilUsageClassification;

	@Getter
	private UsageClassification mayUsageClassification;

	@Getter
	private UsageClassification juneUsageClassification;

	@Getter
	private UsageClassification julyUsageClassification;

	@Getter
	private UsageClassification augustUsageClassification;

	@Getter
	private UsageClassification septemberUsageClassification;

	@Getter
	private UsageClassification octoberUsageClassification;

	@Getter
	private UsageClassification novemberUsageClassification;

	@Getter
	private UsageClassification decemberUsageClassification;

	public ItemPeriod(CompanyCode companyCode, ItemClass itemclass, String itemCode,
			DateUseClassification expirationDateUseClassification, YearMonth startYear, YearMonth endYear,
			UsageClassification cycleUsageClassification, UsageClassification januaryUsageClassification,
			UsageClassification februaryUsageClassification, UsageClassification marchUsageClassification,
			UsageClassification aprilUsageClassification, UsageClassification mayUsageClassification,
			UsageClassification juneUsageClassification, UsageClassification julyUsageClassification,
			UsageClassification augustUsageClassification, UsageClassification septemberUsageClassification,
			UsageClassification octoberUsageClassification, UsageClassification novemberUsageClassification,
			UsageClassification decemberUsageClassification) {
		super();
		this.companyCode = companyCode;
		this.itemclass = itemclass;
		ItemCode = itemCode;
		this.expirationDateUseClassification = expirationDateUseClassification;
		this.startYear = startYear;
		this.endYear = endYear;
		this.cycleUsageClassification = cycleUsageClassification;
		this.januaryUsageClassification = januaryUsageClassification;
		this.februaryUsageClassification = februaryUsageClassification;
		this.marchUsageClassification = marchUsageClassification;
		this.aprilUsageClassification = aprilUsageClassification;
		this.mayUsageClassification = mayUsageClassification;
		this.juneUsageClassification = juneUsageClassification;
		this.julyUsageClassification = julyUsageClassification;
		this.augustUsageClassification = augustUsageClassification;
		this.septemberUsageClassification = septemberUsageClassification;
		this.octoberUsageClassification = octoberUsageClassification;
		this.novemberUsageClassification = novemberUsageClassification;
		this.decemberUsageClassification = decemberUsageClassification;
	}

}
