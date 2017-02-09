package nts.uk.ctx.pr.report.ws.wageledger;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.report.dom.wageledger.PaymentType;
import nts.uk.ctx.pr.report.dom.wageledger.WLCategory;
import nts.uk.ctx.pr.report.ws.wageledger.dto.AggregateItemDto;

@Path("ctx/pr/report/wageledger/aggregateitem")
@Produces("application/json")
public class AggregateItemWebservice extends WebService{
	
	@POST
	@Path("findAll")
	public List<AggregateItemDto> findAll() {
		// Fake data.
		List<AggregateItemDto> dtos = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			dtos.add(AggregateItemDto.builder()
					.code("AGR" + i)
					.name("Aggregate item " + i)
					.showNameZeroValue(i % 2 != 0)
					.showValueZeroValue(i % 2 == 0)
					.paymentType(PaymentType.Salary)
					.category(WLCategory.Payment)
					.build());
			dtos.add(AggregateItemDto.builder()
					.code("AGR" + i)
					.name("Aggregate item " + i)
					.showNameZeroValue(i % 2 != 0)
					.showValueZeroValue(i % 2 == 0)
					.paymentType(PaymentType.Salary)
					.category(WLCategory.Deduction)
					.build());
		}
		return dtos;
	}
}
