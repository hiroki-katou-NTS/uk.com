package nts.uk.ctx.pr.formula.infra.repository.formula;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.formula.dom.formula.FormulaMaster;
import nts.uk.ctx.pr.formula.dom.primitive.FormulaCode;
import nts.uk.ctx.pr.formula.dom.repository.FormulaMasterRepository;
import nts.uk.ctx.pr.formula.infra.entity.formula.QcfmtFormula;
import nts.uk.ctx.pr.formula.infra.entity.formula.QcfmtFormulaPK;

@Stateless
public class JpaFormulaMasterRepository extends JpaRepository implements FormulaMasterRepository {

	private static final String FIND_ALL_BY_COMPANYCODE;

	private static final String IS_EXISTED_FORMULA;

	private static final String FIND_FORMULA_NAME_BY_CODES;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT COUNT(a) ");
		builderString.append("FROM QcfmtFormula a ");
		builderString.append("WHERE a.qcfmtFormulaPK.companyCode = :companyCode ");
		IS_EXISTED_FORMULA = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM QcfmtFormula a ");
		builderString.append("WHERE a.qcfmtFormulaPK.companyCode = :companyCode ");
		builderString.append("ORDER BY a.qcfmtFormulaPK.companyCode, a.qcfmtFormulaPK.formulaCd ");
		FIND_ALL_BY_COMPANYCODE = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM QcfmtFormula a ");
		builderString.append("WHERE a.qcfmtFormulaPK.companyCode = :companyCode ");
		builderString.append("AND a.qcfmtFormulaPK.formulaCd IN :formulaCd ");
		builderString.append("ORDER BY a.qcfmtFormulaPK.companyCode, a.qcfmtFormulaPK.formulaCd ");
		FIND_FORMULA_NAME_BY_CODES = builderString.toString();
	}

	@Override
	public Optional<FormulaMaster> findByBaseYearMonth(String companyCode, FormulaCode formulaCode, YearMonth baseYM) {
		return null;
	}

	@Override
	public List<FormulaMaster> findAll(String companyCode) {
		return this.queryProxy().query(FIND_ALL_BY_COMPANYCODE, QcfmtFormula.class)
				.setParameter("companyCode", companyCode).getList(f -> toDomain(f));
	}

	@Override
	public Optional<FormulaMaster> findByCompanyCodeAndFormulaCode(String companyCode, FormulaCode formulaCode) {
		return this.queryProxy().find(new QcfmtFormulaPK(companyCode, formulaCode.v()), QcfmtFormula.class)
				.map(c -> toDomain(c));
	}

	@Override
	public void add(FormulaMaster formulaMaster) {
		this.commandProxy().insert(toEntity(formulaMaster));
	}

	@Override
	public void remove(FormulaMaster formulaMaster) {

	}

	@Override
	public void update(FormulaMaster formulaMaster) {
		this.commandProxy().update(toEntity(formulaMaster));
	}

	private FormulaMaster toDomain(QcfmtFormula qcfmtFormula) {
		FormulaMaster formulaMaster = FormulaMaster.createFromJavaType(qcfmtFormula.qcfmtFormulaPK.ccd,
				qcfmtFormula.qcfmtFormulaPK.formulaCd, qcfmtFormula.difficultyAtr, qcfmtFormula.formulaName);
		return formulaMaster;
	}

	private QcfmtFormula toEntity(FormulaMaster formulaMasterMaster) {
		val entity = new QcfmtFormula();

		entity.qcfmtFormulaPK = new QcfmtFormulaPK();
		entity.qcfmtFormulaPK.ccd = formulaMasterMaster.getCompanyCode();
		entity.qcfmtFormulaPK.formulaCd = formulaMasterMaster.getFormulaCode().v();
		entity.difficultyAtr = new BigDecimal(formulaMasterMaster.getDifficultyAtr().value);
		entity.formulaName = formulaMasterMaster.getFormulaName().v();

		return entity;
	}

	@Override
	public boolean isExistedFormula(String companyCode) {
		return this.queryProxy().query(IS_EXISTED_FORMULA, long.class).setParameter("companyCode", companyCode)
				.getSingle().get() > 0;
	}

	@Override
	public List<FormulaMaster> findByCompanyCodeAndFormulaCodes(String companyCode, List<FormulaCode> FormulaCode) {
		return this.queryProxy().query(FIND_FORMULA_NAME_BY_CODES, QcfmtFormula.class)
				.setParameter("companyCode", companyCode).setParameter("formulaCd", FormulaCode)
				.getList(f -> toDomain(f));
	}

}
