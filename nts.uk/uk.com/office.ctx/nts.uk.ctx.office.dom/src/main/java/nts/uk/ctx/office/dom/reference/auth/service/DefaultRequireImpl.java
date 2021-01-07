package nts.uk.ctx.office.dom.reference.auth.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.office.dom.favorite.adapter.EmployeeJobHistImport;
import nts.uk.ctx.office.dom.favorite.adapter.EmployeePositionAdapter;
import nts.uk.ctx.office.dom.reference.auth.SpecifyAuthInquiry;
import nts.uk.ctx.office.dom.reference.auth.SpecifyAuthInquiryRepository;

@AllArgsConstructor
public class DefaultRequireImpl implements DetermineEmpIdListDomainService.Require {

	private SpecifyAuthInquiryRepository repo;

	private EmployeePositionAdapter adapter;

	@Override
	public Optional<SpecifyAuthInquiry> getByCidAndRoleId(String cid, String roleId) {
		return repo.getByCidAndRoleId(cid, roleId);
	}

	@Override
	public Map<String, EmployeeJobHistImport> getPositionBySidsAndBaseDate(List<String> sIds, GeneralDate baseDate) {
		return adapter.getPositionBySidsAndBaseDate(sIds, baseDate);
	}
}
