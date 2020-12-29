package nts.uk.ctx.bs.company.dom.company;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.bs.company.dom.company.primitive.ContractCd;

public class CompanyTest {

	@Test
	public void testGetYearBySpecifying() {
		
		Company com = new Company(new CompanyCode("0001"), null, EnumAdaptor.valueOf(4, MonthStr.class), null, null,
				null, null, null, new ContractCd("000000000"), null, null);
		
		assertThat(com.getPeriodTheYear(2021))
				.isEqualTo(new YearMonthPeriod(new YearMonth(202104), new YearMonth(202203)));
	}

}
