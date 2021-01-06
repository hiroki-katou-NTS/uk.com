package nts.uk.ctx.health.infra.api.sample;

import static java.util.stream.Collectors.*;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.Data;
import lombok.Value;
import lombok.val;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.health.dom.sample.SampleOshirase;
import nts.uk.ctx.health.dom.sample.SampleOshiraseAdapter;
import nts.uk.ctx.health.infra.api.HealthLifeApiSession;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class SampleOshiraseAdapterImpl implements SampleOshiraseAdapter {

	@Inject
	private HealthLifeApiSession session;
	
	@Override
	public List<SampleOshirase> get(String companyId, DatePeriod period) {
		
		val context = session.begin(companyId);

		val request = new Request(
				period.start().date(),
				period.end().date(),
				context.getHealthLifeCompanyCode());
		
		val response = context.post(
					"webapi/op/domain/gateway/externallinkage/uklinkage/authenticate",
					request,
					Response.class);
		
		return response.getList().stream()
				.map(d -> d.toDomain())
				.collect(toList());
	}

	@Value
	public static class Request {
		private final Date startDate;
		private final Date endDate;
		private final int companyCode;
	}
	
	@Data
	public static class Response {
		private List<SampleOshiraseDto> list;
	}
}
