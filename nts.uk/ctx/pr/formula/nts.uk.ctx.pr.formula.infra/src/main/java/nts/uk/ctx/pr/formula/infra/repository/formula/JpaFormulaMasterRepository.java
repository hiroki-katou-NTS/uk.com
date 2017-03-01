package nts.uk.ctx.pr.formula.infra.repository.formula;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.formula.dom.formula.FormulaMaster;
import nts.uk.ctx.pr.formula.dom.repository.FormulaMasterRepository;
import nts.uk.ctx.pr.formula.infra.entity.formula.QcfmtFormula;

@RequestScoped
public class JpaFormulaMasterRepository extends JpaRepository implements FormulaMasterRepository {
	
	private final String FindAllByCompanyCode = "Select a From QcfmtFormula a "
												+ "where a.qcfmtFormulaPK.companyCode = :companyCode ";

	@Override
	public Optional<FormulaMaster> find(String companyCode, String formulaCode, String historyId) {
		return null;
	}

	@Override
	public Optional<FormulaMaster> findByBaseYearMonth(String companyCode, String formulaCode, int baseYM) {
		return null;
	}

	@Override
	public List<FormulaMaster> findAll(String companyCode) {
		return this.queryProxy().query(FindAllByCompanyCode,QcfmtFormula.class).
				setParameter("companyCode", companyCode).
				getList(f -> FormulaMaster.createFromJavaType(
						f.qcfmtFormulaPK.ccd,
						f.qcfmtFormulaPK.formulaCd,
						f.difficultyAtr.intValue(),
						f.formulaName));
	}

	@Override
	public void add(FormulaMaster formulaMaster) {
		this.commandProxy().insert(formulaMaster);
	}

	@Override
	public void remove(FormulaMaster formulaMaster) {
		
	}

}
