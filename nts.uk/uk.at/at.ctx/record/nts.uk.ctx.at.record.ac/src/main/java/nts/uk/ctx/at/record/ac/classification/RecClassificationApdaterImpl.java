package nts.uk.ctx.at.record.ac.classification;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.tuple.Pair;

import nts.uk.ctx.at.record.dom.adapter.classification.RecClassificationAdapter;
import nts.uk.ctx.bs.employee.pub.classification.SyClassificationPub;

@Stateless
public class RecClassificationApdaterImpl implements RecClassificationAdapter {

	@Inject
	private SyClassificationPub pub;

	@Override
	public Map<String, String> getClassificationMapCodeName(String companyId, List<String> clsCds) {
		return this.pub.getClassificationMapCodeName(companyId, clsCds);
	}

	@Override
	public List<Pair<String, String>> getClassificationByCompanyId(String companyId) {
		return pub.getClassificationByCompanyId(companyId).stream()
				.map(x -> Pair.of(x.getClassificationCode(), x.getClassificationName())).collect(Collectors.toList());
	}

}
