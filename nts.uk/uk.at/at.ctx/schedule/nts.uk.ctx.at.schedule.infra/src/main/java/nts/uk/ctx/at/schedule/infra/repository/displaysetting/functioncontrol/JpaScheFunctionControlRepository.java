package nts.uk.ctx.at.schedule.infra.repository.displaysetting.functioncontrol;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.lang3.BooleanUtils;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.at.schedule.dom.displaysetting.functioncontrol.ScheFunctionControl;
import nts.uk.ctx.at.schedule.dom.displaysetting.functioncontrol.ScheFunctionControlRepository;
import nts.uk.ctx.at.schedule.infra.entity.displaysetting.functioncontrol.KscmtFuncCtr;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeForm;

/**
 * 
 * @author hiroko_miura
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaScheFunctionControlRepository extends JpaRepository implements ScheFunctionControlRepository{

	@Override
	public Optional<ScheFunctionControl> get(String companyId) {
		
		String sql = "SELECT * FROM KSCMT_FUNC_CTR WHERE CID = @companyId";
		
		return new NtsStatement(sql, this.jdbcProxy())
				.paramString("companyId", companyId)
				.getSingle(x -> KscmtFuncCtr.MAPPER.toEntity(x).toDomain());
	}

	@Override
	public void insert(String companyId, ScheFunctionControl funcCtrl) {
		this.commandProxy().insert(KscmtFuncCtr.of(companyId, funcCtrl));
	}

	@Override
	public void update(String companyId, ScheFunctionControl funcCtrl) {
		KscmtFuncCtr up = this.queryProxy()
				.find(companyId, KscmtFuncCtr.class)
				.get();
		
		up.changeableFix = BooleanUtils.toInteger(funcCtrl.isChangeableForm(WorkTimeForm.FIXED));
		up.changeableFlex = BooleanUtils.toInteger(funcCtrl.isChangeableForm(WorkTimeForm.FLEX));
		up.changeableFluid = BooleanUtils.toInteger(funcCtrl.isChangeableForm(WorkTimeForm.FLOW));
		up.displayActual = BooleanUtils.toInteger(funcCtrl.isDisplayActual());
		
		this.commandProxy().update(up);
	}

}
