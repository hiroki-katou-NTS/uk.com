package nts.uk.ctx.pr.formula.infra.repository.formula;

import java.math.BigDecimal;
import java.util.List;

import javax.enterprise.context.RequestScoped;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.formula.dom.formula.FormulaHistory;
import nts.uk.ctx.pr.formula.dom.repository.FormulaHistoryRepository;
import nts.uk.ctx.pr.formula.infra.entity.formula.QcfmtFormulaHist;
import nts.uk.ctx.pr.formula.infra.entity.formula.QcfmtFormulaHistPK;

@RequestScoped
public class JpaFormulaHistoryRepository extends JpaRepository implements FormulaHistoryRepository {
	
	private final String FindAllByCompanyCode = "Select a From QcfmtFormulaHist a "
			+ "where a.qcfmtFormulaHistPK.companyCode = :companyCode ";

	@Override
	public List<FormulaHistory> findAll(String companyCode) {
		
		return this.queryProxy().query(FindAllByCompanyCode, QcfmtFormulaHist.class)
				.setParameter("companyCode", companyCode)
				.getList(f -> FormulaHistory.createFromJavaType(
						f.qcfmtFormulaHistPK.companyCode,
						f.qcfmtFormulaHistPK.formulaCode,
						f.qcfmtFormulaHistPK.historyId,
						f.startDate.intValue(),
						f.endDate.intValue()));		
	}

	@Override
	public void add(FormulaHistory formulaHistory) {
		this.commandProxy().insert(toEntity(formulaHistory));		
	}
	
	private QcfmtFormulaHist toEntity(FormulaHistory command){
		val entity = new QcfmtFormulaHist();
		
		entity.qcfmtFormulaHistPK = new QcfmtFormulaHistPK();
		entity.qcfmtFormulaHistPK.companyCode = command.getCompanyCode();
		entity.qcfmtFormulaHistPK.formulaCode = command.getFormulaCode().v();
		entity.qcfmtFormulaHistPK.historyId = command.getHistoryId().v();
		entity.startDate = new BigDecimal(command.getStartDate().v());
		entity.endDate = new BigDecimal(command.getEndDate().v());
		
		return entity;
	}

}
