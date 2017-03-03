package nts.uk.ctx.pr.formula.infra.repository.formula;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.formula.dom.formula.FormulaEasyDetail;
import nts.uk.ctx.pr.formula.dom.primitive.EasyFormulaCode;
import nts.uk.ctx.pr.formula.dom.primitive.FormulaCode;
import nts.uk.ctx.pr.formula.dom.repository.FormulaEasyDetailRepository;
import nts.uk.ctx.pr.formula.infra.entity.formula.QcfmtFormulaEasyDetail;
import nts.uk.ctx.pr.formula.infra.entity.formula.QcfmtFormulaEasyDetailPK;

@Stateless
public class JpaFormulaEasyDetailRepository extends JpaRepository implements FormulaEasyDetailRepository {

	private final String REMOVE_EASY_DETAIL_CONDITION = "DELETE From QcfmtFormulaEasyDetail a "
			+ "where a.QcfmtFormulaEasyDetailPK.companyCode = :companyCode AND"
			+ "where a.QcfmtFormulaEasyDetailPK.formulaCode = :formulaCode AND"
			+ "where a.QcfmtFormulaEasyDetailPK.historyId = :historyId";

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
	
	private static FormulaEasyDetail toDomain(QcfmtFormulaEasyDetail f){
		FormulaEasyDetail formulaEasyDetail = FormulaEasyDetail.createFromJavaType(f.qcfmtFormulaEasyDetailPK.companyCode, f.qcfmtFormulaEasyDetailPK.formulaCode,
				f.qcfmtFormulaEasyDetailPK.historyId, f.qcfmtFormulaEasyDetailPK.easyFormulaCd,
				f.easyFormulaName, f.easyFormulaTypeAttribute, f.baseFixedAmount, f.baseAmountDevision,
				f.baseFixedValue, f.baseValueDevision, f.premiumRate, f.roundProcessingDevision, f.coefficientDivision,
				f.coefficientFixedValue, f.adjustmentDevision, f.totalRounding, f.minLimitValue, f.maxLimitValue);
		
		return formulaEasyDetail;
	}

}
