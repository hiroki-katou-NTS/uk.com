package nts.uk.ctx.pr.report.dom.payment.comparing.repository;

import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.report.dom.payment.comparing.entity.QlsptComparePrintSet;
import nts.uk.ctx.pr.report.dom.payment.comparing.entity.QlsptComparePrintSetPK;
import nts.uk.ctx.pr.report.dom.payment.comparing.setting.ComparingPrintSet;
import nts.uk.ctx.pr.report.dom.payment.comparing.setting.ComparingPrintSetRepository;

@Stateless
public class JpaComparingPrintSetRepository extends JpaRepository implements ComparingPrintSetRepository {

	private final String SELECT_COMPARING_PRINT_SET = "SELECT c FROM QlsptComparePrintSet c "
			+ "WHERE c.QlsptComparePrintSetPK.companyCode = :ccd";

	@Override
	public Optional<ComparingPrintSet> getComparingPrintSet(String companyCode) {
		return this.queryProxy().query(SELECT_COMPARING_PRINT_SET, QlsptComparePrintSet.class)
				.setParameter("ccd", companyCode).getSingle(c -> convertToDomain(c));
	}

	@Override
	public void insertComparingPrintSet(ComparingPrintSet comparingPrintSet) {
		this.commandProxy().insert(convertToEntity(comparingPrintSet));
	}

	@Override
	public void updateComparingPrintSet(ComparingPrintSet comparingPrintSet) {
		this.commandProxy().update(convertToEntity(comparingPrintSet));
	}

	@Override
	public void deleteComparingPrintSet(String companyCode) {
		this.commandProxy().remove(QlsptComparePrintSet.class, companyCode);
	}

	private static ComparingPrintSet convertToDomain(QlsptComparePrintSet entity) {
		return ComparingPrintSet.createFromJavaType(entity.qlsptComparePrintSetPK.companyCode, entity.plushBackColor,
				entity.minusBackColor, entity.showItemIfCfWithNull, entity.showItemIfSameValue, entity.showPayment,
				entity.totalSet, entity.sumEachDeprtSet, entity.sumDepHrchyIndexSet, entity.hrchyIndex1,
				entity.hrchyIndex2, entity.hrchyIndex3, entity.hrchyIndex4, entity.hrchyIndex5);
	}

	private static QlsptComparePrintSet convertToEntity(ComparingPrintSet domain) {
		val entity = new QlsptComparePrintSet();
		val entityPK = new QlsptComparePrintSetPK();
		entityPK.companyCode = domain.getCompanyCode();
		entity.qlsptComparePrintSetPK = entityPK;
		entity.plushBackColor = domain.getPlushBackColor().v();
		entity.minusBackColor = domain.getMinusBackColor().v();
		entity.showItemIfCfWithNull = domain.getShowItemIfCfWithNull().value;
		entity.showItemIfSameValue = domain.getShowItemIfSameValue().value;
		entity.showPayment = domain.getShowPayment().value;
		entity.totalSet = domain.getTotalSet().value;
		entity.sumEachDeprtSet = domain.getSumEachDeprtSet().value;
		entity.sumDepHrchyIndexSet = domain.getSumDepHrchyIndexSet().value;
		entity.hrchyIndex1 = domain.getHrchyIndex1().value;
		entity.hrchyIndex2 = domain.getHrchyIndex2().value;
		entity.hrchyIndex3 = domain.getHrchyIndex3().value;
		entity.hrchyIndex4 = domain.getHrchyIndex4().value;
		entity.hrchyIndex5 = domain.getHrchyIndex5().value;
		return entity;
	}

}
