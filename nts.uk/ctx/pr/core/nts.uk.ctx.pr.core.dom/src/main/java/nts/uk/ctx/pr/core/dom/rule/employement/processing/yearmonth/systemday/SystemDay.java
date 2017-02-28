package nts.uk.ctx.pr.core.dom.rule.employement.processing.yearmonth.systemday;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.paymentdata.ProcessingNo;

@AllArgsConstructor
public class SystemDay extends AggregateRoot {

	@Getter
	private CompanyCode companyCode;

	@Getter
	private ProcessingNo processingNo;

	@Getter
	private SocialInsLevyMonAtr socialInsLevyMonAtr;

	@Getter
	private ResitaxStdMon resitaxStdMon;

	@Getter
	private ResitaxStdDay resitaxStdDay;

	@Getter
	private ResitaxBeginMon resitaxBeginMon;

	@Getter
	private PickupStdMonAtr pickupStdMonAtr;

	@Getter
	private PickupStdDay pickupStdDay;

	@Getter
	private PayStdDay payStdDay;

	@Getter
	private AccountDueMonAtr accountDueMonAtr;

	@Getter
	private AccountDueDay accountDueDay;

	public static SystemDay createSimpleFromJavaType(String companyCode, int processingNo, int socialInsLevyMonAtr,
			int resitaxStdMon, int resitaxStdDay, int resitaxBeginMon, int pickupStdMonAtr, int pickupStdDay,
			int payStdDay, int accountDueMonAtr, int accountDueDay) {

		return new SystemDay(new CompanyCode(companyCode), new ProcessingNo(processingNo),
				EnumAdaptor.valueOf(socialInsLevyMonAtr, SocialInsLevyMonAtr.class), new ResitaxStdMon(resitaxStdMon),
				new ResitaxStdDay(resitaxStdDay), new ResitaxBeginMon(resitaxBeginMon),
				EnumAdaptor.valueOf(pickupStdMonAtr, PickupStdMonAtr.class), new PickupStdDay(pickupStdDay),
				new PayStdDay(payStdDay), EnumAdaptor.valueOf(accountDueMonAtr, AccountDueMonAtr.class),
				new AccountDueDay(accountDueDay));
	}
}
