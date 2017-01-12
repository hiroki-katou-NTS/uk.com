package nts.uk.ctx.basic.infra.repository.organization.classification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.basic.dom.organization.classification.Classification;
import nts.uk.ctx.basic.dom.organization.classification.ClassificationCode;
import nts.uk.ctx.basic.dom.organization.classification.ClassificationName;
import nts.uk.ctx.basic.dom.organization.classification.ClassificationRepository;
import nts.uk.ctx.basic.infra.entity.organization.classification.CmnmtClass;
import nts.uk.ctx.basic.infra.entity.organization.classification.CmnmtClassPK;
import nts.uk.shr.com.primitive.Memo;

@Stateless
public class JpaClassificationRepository extends JpaRepository implements ClassificationRepository {

	private static final String FIND_ALL;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM CmnmtClass e");
		builderString.append(" WHERE e.cmnmtClassPK.companyCode =: companyCode");
		FIND_ALL = builderString.toString();
	}

	@Override
	public void add(Classification classification) {
		this.commandProxy().insert(convertToDbType(classification));

	}

	@Override
	public void update(Classification classification) {
		this.commandProxy().update(convertToDbType(classification));

	}

	@Override
	public void remove(String companyCode, ClassificationCode classificationCode) {
		CmnmtClassPK cmnmtClassPK = new CmnmtClassPK(companyCode, classificationCode.toString());
		this.commandProxy().remove(CmnmtClass.class, cmnmtClassPK);

	}

	@Override
	public Optional<Classification> findSingleClassification(String companyCode,
			ClassificationCode classificationCode) {
		CmnmtClassPK cmnmtClassPK = new CmnmtClassPK(companyCode, classificationCode.toString());
		return this.queryProxy().find(cmnmtClassPK, CmnmtClass.class).map(e -> {
			return Optional.of(convertToDomain(e));
		}).orElse(Optional.empty());
	}

	@Override
	public List<Classification> findAll(String companyCode) {
		List<CmnmtClass> resultList = this.queryProxy().query(FIND_ALL, CmnmtClass.class)
				.setParameter("companyCode", "'" + companyCode + "'").getList();
		return !resultList.isEmpty() ? resultList.stream().map(e -> {
			return convertToDomain(e);
		}).collect(Collectors.toList()) : new ArrayList<>();
	}

	private CmnmtClass convertToDbType(Classification classification) {
		CmnmtClass cmnmtClass = new CmnmtClass();
		CmnmtClassPK cmnmtClassPK = new CmnmtClassPK(classification.getCompanyCode(),
				classification.getClassificationCode().toString());
		cmnmtClass.setMemo(classification.getClassificationMemo().toString());
		cmnmtClass.setName(classification.getClassificationName().toString());
		cmnmtClass.setOutCode(classification.getClassificationOutCode().toString());
		cmnmtClass.setCmnmtClassPK(cmnmtClassPK);
		return cmnmtClass;
	}

	private Classification convertToDomain(CmnmtClass cmnmtClass) {
		return new Classification(cmnmtClass.getCmnmtClassPK().getCompanyCode(),
				new ClassificationCode(cmnmtClass.getCmnmtClassPK().getClassificationCode()),
				new ClassificationName(cmnmtClass.getName()), new ClassificationCode(cmnmtClass.getOutCode()),
				new Memo(cmnmtClass.getMemo()));
	}

}
