package nts.uk.ctx.pr.formula.infra.repository.formula;

import java.math.BigDecimal;
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
			+ " WHERE a.QcfmtFormulaEasyDetailPK.companyCode = :companyCode AND "
			+ " AND a.QcfmtFormulaEasyDetailPK.formulaCode = :formulaCode AND "
			+ " AND a.QcfmtFormulaEasyDetailPK.historyId = :historyId ";

	@Override
	public Optional<FormulaEasyDetail> findByPriKey(String companyCode, FormulaCode formulaCode, String historyId,
			EasyFormulaCode easyFormulaCode) {
		return this.queryProxy().find(new QcfmtFormulaEasyDetailPK(companyCode, formulaCode.v(), historyId, easyFormulaCode.v()),
				QcfmtFormulaEasyDetail.class).map(f -> toDomain(f));
	}

	@Override
	public void remove(FormulaEasyDetail formulaEasyDetail) {

		this.getEntityManager().createQuery(REMOVE_EASY_DETAIL_CONDITION)
				.setParameter("companyCode", formulaEasyDetail.getCompanyCode())
				.setParameter("formulaCode", formulaEasyDetail.getFormulaCode().v())
				.setParameter("historyId", formulaEasyDetail.getHistoryId()).executeUpdate();
	}
	
	@Override
	public void add(FormulaEasyDetail formulaEasyDetail) {
		this.commandProxy().insert(toEntity(formulaEasyDetail));
	}
	
	private QcfmtFormulaEasyDetail toEntity(FormulaEasyDetail formulaEasyDetail){
		
		val entity =  new QcfmtFormulaEasyDetail();
		
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
	
	private FormulaEasyDetail toDomain(QcfmtFormulaEasyDetail f){
		FormulaEasyDetail formulaEasyDetail = FormulaEasyDetail.createFromJavaType(f.qcfmtFormulaEasyDetailPK.companyCode, f.qcfmtFormulaEasyDetailPK.formulaCode,
				f.qcfmtFormulaEasyDetailPK.historyId, f.qcfmtFormulaEasyDetailPK.easyFormulaCd,
				f.easyFormulaName, f.easyFormulaTypeAttribute, f.baseFixedAmount, f.baseAmountDevision,
				f.baseFixedValue, f.baseValueDevision, f.premiumRate, f.roundProcessingDevision, f.coefficientDivision,
				f.coefficientFixedValue, f.adjustmentDevision, f.totalRounding, f.minLimitValue, f.maxLimitValue);
		
		return formulaEasyDetail;
	}

}
