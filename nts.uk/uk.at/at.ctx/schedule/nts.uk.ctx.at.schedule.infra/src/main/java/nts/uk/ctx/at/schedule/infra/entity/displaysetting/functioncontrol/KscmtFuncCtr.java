package nts.uk.ctx.at.schedule.infra.entity.displaysetting.functioncontrol;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.BooleanUtils;

import lombok.AllArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.displaysetting.functioncontrol.ScheFunctionControl;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeForm;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@AllArgsConstructor
@Entity
@Setter
@Table(name = "KSCMT_FUNC_CTR")
public class KscmtFuncCtr extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected Object getKey() {
		return this.companyId;
	}
	
	/**
	 * 会社ID
	 */
	@Id
	@Column(name = "CID")
	public String companyId;
	
	/**
	 * 固定(通常)勤務の時刻が修正できるか
	 */
	@Column(name = "CHANGEABLE_CLOCK_FIX")
	public int changeableFix;
	
	/**
	 * フレックス勤務の時刻が修正できるか
	 */
	@Column(name = "CHANGEABLE_CLOCK_FLEX")
	public int changeableFlex;
	
	/**
	 * 流動勤務の時刻が修正できるか
	 */
	@Column(name = "CHANGEABLE_CLOCK_FLUID")
	public int changeableFluid;
	
	/**
	 * 実績表示できるか
	 */
	@Column(name = "DISPLAY_ACTUAL")
	public int displayActual;
	
	
	/**
	 * convert to domain
	 * @param entity
	 * @return domain
	 */
	public static ScheFunctionControl of (KscmtFuncCtr entity) {
		
		List<WorkTimeForm> lstWork = new ArrayList<>();
		
		if (entity.changeableFix == 1)
			lstWork.add(WorkTimeForm.FIXED);
		
		if (entity.changeableFlex == 1)
			lstWork.add(WorkTimeForm.FLEX);
		
		if (entity.changeableFluid == 1)
			lstWork.add(WorkTimeForm.FLOW);
		
		
		return new ScheFunctionControl(lstWork, (entity.displayActual == 1) );
	}
	
	/**
	 * convert to entity
	 * @param domain
	 * @return entity
	 */
	public static KscmtFuncCtr toEntity (String companyId, ScheFunctionControl domain) {
		
		return new KscmtFuncCtr (
				companyId,
				BooleanUtils.toInteger(domain.isChangeableForm(WorkTimeForm.FIXED)),
				BooleanUtils.toInteger(domain.isChangeableForm(WorkTimeForm.FLEX)),
				BooleanUtils.toInteger(domain.isChangeableForm(WorkTimeForm.FLOW)),
				BooleanUtils.toInteger(domain.isDisplayActual()));
	}
}
