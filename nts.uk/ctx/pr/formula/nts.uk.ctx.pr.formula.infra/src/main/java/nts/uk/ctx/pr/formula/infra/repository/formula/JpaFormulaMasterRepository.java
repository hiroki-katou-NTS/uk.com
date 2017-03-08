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

	private final String FIND_ALL_BY_COMPANYCODE = "SELECT a FROM QcfmtFormula a "
			+ " WHERE a.qcfmtFormulaPK.companyCode = :companyCode ";

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

	private FormulaMaster toDomain(QcfmtFormula f) {
		FormulaMaster formulaMaster = FormulaMaster.createFromJavaType(f.qcfmtFormulaPK.ccd, f.qcfmtFormulaPK.formulaCd,
				f.difficultyAtr, f.formulaName);
		return formulaMaster;
	}

	private QcfmtFormula toEntity(FormulaMaster command) {
		val entity = new QcfmtFormula();

		entity.qcfmtFormulaPK = new QcfmtFormulaPK();
		entity.qcfmtFormulaPK.ccd = command.getCompanyCode();
		entity.qcfmtFormulaPK.formulaCd = command.getFormulaCode().v();
		entity.difficultyAtr = new BigDecimal(command.getDifficultyAtr().value);
		entity.formulaName = command.getFormulaName().v();

		return entity;
	}

}
