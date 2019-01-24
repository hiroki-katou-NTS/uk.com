package nts.uk.ctx.pr.core.infra.repository.wageprovision.formula;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.*;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.formula.QpbmtBasicCalculationFormula;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.formula.QpbmtBasicCalculationStandardAmount;

@Stateless
public class JpaBasicCalculationFormulaRepository extends JpaRepository implements BasicCalculationFormulaRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtBasicCalculationFormula f";
    private static final String SELECT_BY_HISTORY = SELECT_ALL_QUERY_STRING + " WHERE f.basicCalFormPk.historyID =:historyID ";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE f.basicCalFormPk.historyID =:historyID AND  f.basicCalFormPk.masterUseCode =:masterUseCode ";
    private static final String SELECT_STANDARD_AMOUNT = "SELECT f FROM QpbmtBasicCalculationStandardAmount f WHERE f.basicCalStdAmountPk.historyID =:historyID AND  f.basicCalStdAmountPk.masterUseCode =:masterUseCode ";

    private static final String REMOVE_BY_HISTORY = "DELETE FROM QpbmtBasicCalculationFormula f WHERE f.basicCalFormPk.historyID =:historyID ";
    private static final String REMOVE_STANDARD_AMOUNT_BY_HISTORY = "DELETE FROM QpbmtBasicCalculationStandardAmount f WHERE f.basicCalStdAmountPk.historyID =:historyID ";
    private static final String REMOVE_BY_FORMULA_CODE = "DELETE FROM QpbmtBasicCalculationFormula f WHERE f.basicCalFormPk.formulaCode =:formulaCode ";
    private static final String REMOVE_STANDARD_AMOUNT_BY_FORMULA_CODE = "DELETE FROM QpbmtBasicCalculationStandardAmount f WHERE f.basicCalStdAmountPk.formulaCode =:formulaCode ";

    @Override
    public List<BasicCalculationFormula> getBasicCalculationFormulaByHistoryID(String historyID){
        List<QpbmtBasicCalculationFormula> listBasicCalculationFormula = this.queryProxy().query(SELECT_BY_HISTORY, QpbmtBasicCalculationFormula.class)
        .setParameter("historyID", historyID).getList();
        List<BasicCalculationFormula> basicCalculationFormulas = new ArrayList<>();
        listBasicCalculationFormula.forEach(entity -> {
            Optional<BasicCalculationFormula> basicCalculationFormula = this.getBasicCalculationFormulaByKey(historyID, entity.basicCalFormPk.masterUseCode);
            basicCalculationFormula.ifPresent(i -> {
                basicCalculationFormulas.add(i);
            });
        });
        return basicCalculationFormulas;

    }

    public Optional<BasicCalculationFormula> getBasicCalculationFormulaByKey (String historyID, String masterUseCode) {
        Optional<QpbmtBasicCalculationFormula> basicCalculationFormula = this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtBasicCalculationFormula.class)
                .setParameter("historyID", historyID).setParameter("masterUseCode", masterUseCode).getSingle();
        List<QpbmtBasicCalculationStandardAmount> basicCalculationStandardAmounts = this.queryProxy().query(SELECT_STANDARD_AMOUNT, QpbmtBasicCalculationStandardAmount.class)
                .setParameter("historyID", historyID).setParameter("masterUseCode", masterUseCode).getList();
        if (!basicCalculationFormula.isPresent()) return Optional.empty();
        return Optional.of(this.toBasicCalculationFormula(basicCalculationFormula.get(), basicCalculationStandardAmounts));
    }


    public BasicCalculationFormula toBasicCalculationFormula (QpbmtBasicCalculationFormula basicCalculationForm, List<QpbmtBasicCalculationStandardAmount> basicCalculationStandardAmount) {
        BasicCalculationForm domain = null;
        if (basicCalculationForm.calculationFormulaCls == CalculationFormulaClassification.FORMULA.value && basicCalculationForm.formulaType !=null) domain = this.toBasicCalculationForm(basicCalculationForm, basicCalculationStandardAmount);
        return new BasicCalculationFormula(basicCalculationForm.basicCalFormPk.formulaCode, basicCalculationForm.basicCalFormPk.historyID, basicCalculationForm.basicCalFormPk.masterUseCode, basicCalculationForm.calculationFormulaCls, basicCalculationForm.basicCalculationFormula, domain);
    }

    public BasicCalculationForm toBasicCalculationForm (QpbmtBasicCalculationFormula basicCalculationForm, List<QpbmtBasicCalculationStandardAmount> basicCalculationStandardAmount) {
        Optional<BasicCalculationItemCategory> basicCalculationItemCategory = Optional.empty();
        if (basicCalculationForm.formulaType == FormulaType.CALCULATION_FORMULA_TYPE3.value) basicCalculationItemCategory = this.toBasicCalculationItemCategory(basicCalculationForm);
        return new BasicCalculationForm(this.toBasicCalculationStandardAmount(basicCalculationForm, basicCalculationStandardAmount), this.toBasicCalculationFactorClassification(basicCalculationForm), basicCalculationForm.formulaType, basicCalculationForm.roundingResult, basicCalculationForm.adjustmentCls, basicCalculationItemCategory, basicCalculationForm.premiumRate, basicCalculationForm.roundingMethod);
    }

    public BasicCalculationStandardAmount toBasicCalculationStandardAmount (QpbmtBasicCalculationFormula basicCalculationForm, List<QpbmtBasicCalculationStandardAmount> basicCalculationStandardAmount) {
        return new BasicCalculationStandardAmount(basicCalculationForm.standardAmountCls, basicCalculationForm.standardFixedValue, basicCalculationStandardAmount.stream().map(item -> item.basicCalStdAmountPk.targetItemCode).collect(Collectors.toList()));
    }

    public BasicCalculationFactorClassification toBasicCalculationFactorClassification (QpbmtBasicCalculationFormula basicCalculationForm) {
        return new BasicCalculationFactorClassification(new CoefficientItem(basicCalculationForm.attendanceItem, basicCalculationForm.coefficientCls), basicCalculationForm.coefficientFixedValue);
    }

    public Optional<BasicCalculationItemCategory> toBasicCalculationItemCategory (QpbmtBasicCalculationFormula basicCalculationForm) {
        if (basicCalculationForm.baseItemCls == null) return Optional.empty();
        return Optional.of(new BasicCalculationItemCategory(basicCalculationForm.baseItemCls, basicCalculationForm.baseItemFixedValue));
    }

    @Override
    public void upsertAll(String historyID, List<BasicCalculationFormula> domains){
        this.removeByHistory(historyID);
        domains.forEach(domain -> {
            this.commandProxy().insert(QpbmtBasicCalculationFormula.toEntity(domain));
            this.commandProxy().insertAll(QpbmtBasicCalculationStandardAmount.toEntity(domain));
        });
    }

    @Override
    public void removeByHistory(String historyID) {
        this.getEntityManager().createQuery(REMOVE_BY_HISTORY).setParameter("historyID", historyID).executeUpdate();
        this.getEntityManager().createQuery(REMOVE_STANDARD_AMOUNT_BY_HISTORY).setParameter("historyID", historyID).executeUpdate();
    }
}
