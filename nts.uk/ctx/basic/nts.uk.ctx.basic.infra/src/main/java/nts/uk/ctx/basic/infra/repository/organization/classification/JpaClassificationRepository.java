package nts.uk.ctx.basic.infra.repository.organization.classification;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.basic.dom.organization.classification.Classification;
import nts.uk.ctx.basic.dom.organization.classification.ClassificationCode;
import nts.uk.ctx.basic.dom.organization.classification.ClassificationMemo;
import nts.uk.ctx.basic.dom.organization.classification.ClassificationName;
import nts.uk.ctx.basic.dom.organization.classification.ClassificationRepository;
import nts.uk.ctx.basic.infra.entity.organization.classification.CmnmtClass;
import nts.uk.ctx.basic.infra.entity.organization.classification.CmnmtClassPK;

@Stateless
public class JpaClassificationRepository extends JpaRepository implements ClassificationRepository {

	@Override
	public void add(Classification classification) {
		this.commandProxy().insert(convertToDbType(classification));

	}

	@Override
	public void update(Classification classification) {
		this.commandProxy().update(convertToDbType(classification));

	}

	@Override
	public void remove(int companyCode, ClassificationCode classificationCode) {
		CmnmtClassPK cmnmtClassPK = new CmnmtClassPK(companyCode, classificationCode.toString());
		this.commandProxy().remove(CmnmtClass.class, cmnmtClassPK);

	}

	@Override
	public Optional<Classification> findSingleClassification(int companyCode, ClassificationCode classificationCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Classification> findAll(int companyCode) {
		// TODO Auto-generated method stub
		return null;
	}

	private CmnmtClass convertToDbType(Classification classification) {
		CmnmtClass cmnmtClass = new CmnmtClass();
		CmnmtClassPK cmnmtClassPK = new CmnmtClassPK(classification.getCompanyCode(),
				classification.getClassificationCode().toString());
		cmnmtClass.setMemo(classification.getClassificationMemo() != null
				? classification.getClassificationMemo().toString() : null);
		cmnmtClass.setName(classification.getClassificationName() != null
				? classification.getClassificationName().toString() : null);
		cmnmtClass.setOutCode(classification.getClassificationOutCode() != null
				? classification.getClassificationOutCode().toString() : null);
		cmnmtClass.setCmnmtClassPK(cmnmtClassPK);
		return cmnmtClass;
	}

	private Classification convertToDomain(CmnmtClass cmnmtClass) {
		Classification classification = new Classification(cmnmtClass.getCmnmtClassPK().getCompanyCode(),
				new ClassificationCode(cmnmtClass.getCmnmtClassPK().getClassificationCode()),
				new ClassificationName(cmnmtClass.getName()), new ClassificationCode(cmnmtClass.getOutCode()),
				new ClassificationMemo(cmnmtClass.getMemo()));
		return classification;
	}

}
