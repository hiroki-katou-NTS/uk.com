package nts.uk.ctx.at.schedule.infra.repository.displaysetting.functioncontrol;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.lang3.BooleanUtils;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.uk.ctx.at.schedule.dom.displaysetting.functioncontrol.ScheFunctionControl;
import nts.uk.ctx.at.schedule.dom.displaysetting.functioncontrol.ScheFunctionControlRepository;
import nts.uk.ctx.at.schedule.infra.entity.displaysetting.functioncontrol.KscmtFuncCtr;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeForm;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaScheFunctionControlRepository extends JpaRepository implements ScheFunctionControlRepository{

	@Override
	public Optional<ScheFunctionControl> get(String companyId) {
		
		String sql = "SELECT * FROM KSCMT_FUNC_CTR WHERE CID = ?";
		
		try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
			stmt.setString(1, companyId);
			
			return new NtsResultSet(stmt.executeQuery()).getSingle(rec -> {
				List<WorkTimeForm> lst = new ArrayList<>();
				if (rec.getInt("CHANGEABLE_CLOCK_FIX") == 1) 
					lst.add(WorkTimeForm.FIXED);
				if (rec.getInt("CHANGEABLE_CLOCK_FLEX") == 1)
					lst.add(WorkTimeForm.FLEX);
				if (rec.getInt("CHANGEABLE_CLOCK_FLUID") == 1)
					lst.add(WorkTimeForm.FLOW);
				
				return new ScheFunctionControl(lst, (rec.getInt("DISPLAY_ACTUAL") == 1));
			});
					
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void insert(String companyId, ScheFunctionControl funcCtrl) {
		this.commandProxy().insert(KscmtFuncCtr.toEntity(companyId, funcCtrl));
	}

	@Override
	public void update(String companyId, ScheFunctionControl funcCtrl) {
		KscmtFuncCtr entiey = KscmtFuncCtr.toEntity(companyId, funcCtrl);
		KscmtFuncCtr up = this.queryProxy().find(entiey.companyId, KscmtFuncCtr.class).get();
		
		up.setChangeableFix(BooleanUtils.toInteger(funcCtrl.isChangeableForm(WorkTimeForm.FIXED)));
		up.setChangeableFlex(BooleanUtils.toInteger(funcCtrl.isChangeableForm(WorkTimeForm.FLEX)));
		up.setChangeableFluid(BooleanUtils.toInteger(funcCtrl.isChangeableForm(WorkTimeForm.FLOW)));
		up.setDisplayActual(BooleanUtils.toInteger(funcCtrl.isDisplayActual()));
		
		this.commandProxy().update(up);
	}

}
