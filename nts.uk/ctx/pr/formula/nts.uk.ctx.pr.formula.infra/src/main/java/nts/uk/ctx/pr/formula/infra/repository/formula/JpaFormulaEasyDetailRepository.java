package nts.uk.ctx.pr.formula.infra.repository.formula;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.formula.dom.formula.FormulaEasyDetail;
import nts.uk.ctx.pr.formula.dom.primitive.EasyFormulaCode;
import nts.uk.ctx.pr.formula.dom.primitive.FormulaCode;
import nts.uk.ctx.pr.formula.dom.repository.FormulaEasyDetailRepository;
import nts.uk.ctx.pr.formula.infra.entity.formula.QcfmtFormula;
import nts.uk.ctx.pr.formula.infra.entity.formula.QcfmtFormulaEasyDetail;
import nts.uk.ctx.pr.formula.infra.entity.formula.QcfmtFormulaEasyDetailPK;
import nts.uk.ctx.pr.formula.infra.entity.formula.QcfmtFormulaPK;

@Stateless
public class JpaFormulaEasyDetailRepository extends JpaRepository implements FormulaEasyDetailRepository {

	private final String REMOVE_EASY_DETAIL_CONDITION = "DELETE FROM QcfmtFormulaEasyDetail a "
			+ " WHERE a.qcfmtFormulaEasyDetailPK.companyCode = :companyCode "
			+ " AND a.qcfmtFormulaEasyDetailPK.formulaCode = :formulaCode "
			+ " AND a.qcfmtFormulaEasyDetailPK.historyId = :historyId ";

	private final String FIND_WITHOUT_PRI_KEY = "SELECT a FROM QcfmtFormulaEasyDetail a "
			+ " WHERE a.qcfmtFormulaEasyDetailPK.companyCode = :companyCode "
			+ " AND a.qcfmtFormulaEasyDetailPK.formulaCode = :formulaCode "
			+ " AND a.qcfmtFormulaEasyDetailPK.historyId = :historyId ";

	@Override
	public Optional<FormulaEasyDetail> findByPriKey(String companyCode, FormulaCode formulaCode, String historyId,
			EasyFormulaCode easyFormulaCode) {
		return this.queryProxy()
				.find(new QcfmtFormulaEasyDetailPK(companyCode, formulaCode.v(), historyId, easyFormulaCode.v()),
						QcfmtFormulaEasyDetail.class)
				.map(f -> toDomain(f));
	}

	@Override
	public void remove(String companyCode, FormulaCode formulaCode, String historyId) {

		this.getEntityManager().createQuery(REMOVE_EASY_DETAIL_CONDITION).setParameter("companyCode", companyCode)
				.setParameter("formulaCode", formulaCode.v()).setParameter("historyId", historyId).executeUpdate();
	}

	@Override
	public void add(FormulaEasyDetail formulaEasyDetail) {
		this.commandProxy().insert(toEntity(formulaEasyDetail));
	}

	@Override
	public Optional<FormulaEasyDetail> findWithOutPriKey(String companyCode, FormulaCode formulaCode,
			String historyId) {
		return this.queryProxy().query(FIND_WITHOUT_PRI_KEY, QcfmtFormulaEasyDetail.class)
				.setParameter("companyCode", companyCode).setParameter("formulaCode", formulaCode.v())
				.setParameter("historyId", historyId).getSingle(f -> toDomain(f));
	}

	private QcfmtFormulaEasyDetail toEntity(FormulaEasyDetail formulaEasyDetail) {

		val entity = new QcfmtFormulaEasyDetail();

		entity.qcfmtFormulaEasyDetailPK = new QcfmtFormulaEasyDetailPK();
		entity.qcfmtFormulaEasyDetailPK.companyCode = formulaEasyDetail.getCompanyCode();
		entity.qcfmtFormulaEasyDetailPK.easyFormulaCd = formulaEasyDetail.getFormulaCode().v();
		entity.qcfmtFormulaEasyDetailPK.formulaCode = formulaEasyDetail.getFormulaCode().v();
		entity.qcfmtFormulaEasyDetailPK.historyId = formulaEasyDetail.getHistoryId();
		entity.easyFormulaName = formulaEasyDetail.getEasyFormulaName().v();
		entity.easyFormulaTypeAttribute = new BigDecimal(formulaEasyDetail.getEasyFormulaTypeAtr().value);
		entity.baseFixedAmount = formulaEasyDetail.getBaseFixedAmount().v();
		entity.baseAmountDevision = new BigDecimal(formulaEasyDetail.getBaseAmountDevision().value);
		entity.baseFixedValue = formulaEasyDetail.getBaseFixedValue().v();
		entity.baseValueDevision = new BigDecimal(formulaEasyDetail.getBaseValueDevision().value);
		entity.premiumRate = formulaEasyDetail.getPremiumRate().v();
		entity.roundProcessingDevision = new BigDecimal(formulaEasyDetail.getRoundProcessingDevision().value);
		entity.coefficientDivision = formulaEasyDetail.getCoefficientDivision().v();
		entity.coefficientFixedValue = formulaEasyDetail.getCoefficientFixedValue().v();
		entity.adjustmentDevision = new BigDecimal(formulaEasyDetail.getAdjustmentDevision().value);
		entity.totalRounding = new BigDecimal(formulaEasyDetail.getTotalRounding().value);
		entity.minLimitValue = formulaEasyDetail.getMinValue().v();
		entity.maxLimitValue = formulaEasyDetail.getMaxValue().v();

		return entity;
	}

	private FormulaEasyDetail toDomain(QcfmtFormulaEasyDetail qcfmtFormulaEasyDetail) {
		FormulaEasyDetail formulaEasyDetail = FormulaEasyDetail.createFromJavaType(
				qcfmtFormulaEasyDetail.qcfmtFormulaEasyDetailPK.companyCode,
				qcfmtFormulaEasyDetail.qcfmtFormulaEasyDetailPK.formulaCode,
				qcfmtFormulaEasyDetail.qcfmtFormulaEasyDetailPK.historyId,
				qcfmtFormulaEasyDetail.qcfmtFormulaEasyDetailPK.easyFormulaCd, 
				qcfmtFormulaEasyDetail.easyFormulaName,
				qcfmtFormulaEasyDetail.easyFormulaTypeAttribute, qcfmtFormulaEasyDetail.baseFixedAmount,
				qcfmtFormulaEasyDetail.baseAmountDevision, qcfmtFormulaEasyDetail.baseFixedValue,
				qcfmtFormulaEasyDetail.baseValueDevision, qcfmtFormulaEasyDetail.premiumRate,
				qcfmtFormulaEasyDetail.roundProcessingDevision, qcfmtFormulaEasyDetail.coefficientDivision,
				qcfmtFormulaEasyDetail.coefficientFixedValue, qcfmtFormulaEasyDetail.adjustmentDevision,
				qcfmtFormulaEasyDetail.totalRounding, qcfmtFormulaEasyDetail.minLimitValue,
				qcfmtFormulaEasyDetail.maxLimitValue);

		return formulaEasyDetail;
	}

}
