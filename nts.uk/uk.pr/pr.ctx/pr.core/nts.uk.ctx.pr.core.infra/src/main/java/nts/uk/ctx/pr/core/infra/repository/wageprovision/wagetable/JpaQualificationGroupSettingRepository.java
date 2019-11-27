package nts.uk.ctx.pr.core.infra.repository.wageprovision.wagetable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.QualificationGroupSetting;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.QualificationGroupSettingRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.wagetable.QpbmtQualificationGroupSetting;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.wagetable.QpbmtQualificationGroupSettingPk;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaQualificationGroupSettingRepository extends JpaRepository
		implements QualificationGroupSettingRepository {

	private static final String FIND_BY_COMPANY = "SELECT a FROM QpbmtQualificationGroupSetting a WHERE a.pk.cid =:cid ORDER BY a.pk.qualificationGroupCode";
	private static final String FIND_BY_COMPANY_AND_CODE = "SELECT a FROM QpbmtQualificationGroupSetting a WHERE a.pk.cid =:cid AND a.pk.qualificationGroupCode =:qualificationGroupCode";

	@Override
	public List<QualificationGroupSetting> getQualificationGroupSettingByCompanyID() {
		return this.queryProxy().query(FIND_BY_COMPANY, QpbmtQualificationGroupSetting.class)
				.setParameter("cid", AppContexts.user().companyId()).getList(i -> i.toDomain());
	}

	@Override
	public Optional<QualificationGroupSetting> getQualificationGroupSettingById(String qualificationGroupCode) {
		Optional<QpbmtQualificationGroupSetting> optEntity = this.queryProxy()
				.query(FIND_BY_COMPANY_AND_CODE, QpbmtQualificationGroupSetting.class)
				.setParameter("cid", AppContexts.user().companyId())
				.setParameter("qualificationGroupCode", qualificationGroupCode).getSingle();
		return optEntity.isPresent() ? Optional.of(optEntity.get().toDomain()) : Optional.empty();
	}

	@Override
	public void add(QualificationGroupSetting domain) {
		this.commandProxy().insert(QpbmtQualificationGroupSetting.fromDomain(domain));
	}

	@Override
	public void update(QualificationGroupSetting domain) {
		this.commandProxy().update(QpbmtQualificationGroupSetting.fromDomain(domain));
	}

	@Override
	public void remove(String companyId, String code) {
		this.commandProxy().remove(QpbmtQualificationGroupSetting.class,
				new QpbmtQualificationGroupSettingPk(companyId, code));
	}

	@Override
	public List<String> getUsedQualificationCodeByCompanyID(String targetGroupCode) {
		List<QpbmtQualificationGroupSetting> otherSettings = this.queryProxy()
				.query(FIND_BY_COMPANY, QpbmtQualificationGroupSetting.class)
				.setParameter("cid", AppContexts.user().companyId()).getList().stream()
				.filter(i -> !i.pk.qualificationGroupCode.equals(targetGroupCode)).collect(Collectors.toList());
		Set<String> result = new HashSet<>();
		for (QpbmtQualificationGroupSetting g : otherSettings) {
			result.addAll(g.eligibleQualificationCodes.stream().map(i -> i.pk.eligibleQualificationCode)
					.collect(Collectors.toSet()));
		}
		return new ArrayList<>(result);
	}

}
