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

	private static final String FIND_SINGLE;

	private static final String QUERY_IS_EXISTED;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT c");
		builderString.append(" FROM CmnmtClass c");
		builderString.append(" WHERE c.cmnmtClassPK.companyCode = :companyCode");
		FIND_ALL = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM CmnmtClass e");
		builderString.append(" WHERE e.cmnmtClassPK.companyCode = :companyCode");
		builderString.append(" AND e.cmnmtClassPK.classificationCode = :classificationCode");
		FIND_SINGLE = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT COUNT(e)");
		builderString.append(" WHERE e.cmnmtClassPK.companyCode = :companyCode");
		builderString.append(" AND e.cmnmtClassPK.classificationCode = :classificationCode");
		QUERY_IS_EXISTED = builderString.toString();
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
		return this.queryProxy().query(FIND_SINGLE, CmnmtClass.class)
				.setParameter("companyCode", "'" + companyCode + "'")
				.setParameter("classificationCode", "'" + classificationCode.toString() + "'").getSingle().map(e -> {
					return Optional.of(convertToDomain(e));
				}).orElse(Optional.empty());
	}
	
	
	@Override
	public List<Classification> findAll(String companyCode) {
		System.out.println("========== findAll");
		List<CmnmtClass> resultList = this.queryProxy().query(FIND_ALL, CmnmtClass.class)
				.setParameter("companyCode",   companyCode )
				.getList();
		return !resultList.isEmpty() ? resultList.stream().map(e -> {
			return convertToDomain(e);
		}).collect(Collectors.toList()) : new ArrayList<>();
	}

	@Override
	public boolean isExisted(String companyCode, ClassificationCode classificationCode) {
		return this.queryProxy().query(QUERY_IS_EXISTED, long.class)
				.setParameter("companyCode", "'" + companyCode + "'")
				.setParameter("classificationCode", "'" + classificationCode.toString() + "'").getSingle().get() > 0;
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
