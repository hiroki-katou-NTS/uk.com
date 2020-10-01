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
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.at.schedule.dom.displaysetting.functioncontrol.ScheFunctionControl;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeForm;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * KSCMT_FUNC_CTR
 * @author hiroko_miura
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_FUNC_CTR")
public class KscmtFuncCtr extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final JpaEntityMapper<KscmtFuncCtr> MAPPER = new JpaEntityMapper<>(KscmtFuncCtr.class);
	
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
	 * convert to entity
	 * @param domain
	 * @return entity
	 */
	public static KscmtFuncCtr of (String companyId, ScheFunctionControl domain) {
		
		return new KscmtFuncCtr (
					companyId
				,	BooleanUtils.toInteger(domain.isChangeableForm(WorkTimeForm.FIXED))
				,	BooleanUtils.toInteger(domain.isChangeableForm(WorkTimeForm.FLEX))
				,	BooleanUtils.toInteger(domain.isChangeableForm(WorkTimeForm.FLOW))
				,	BooleanUtils.toInteger(domain.isDisplayActual()));
	}
	
	public ScheFunctionControl toDomain () {
		
		List<WorkTimeForm> lstWork = new ArrayList<>();
		
		if (this.changeableFix == 1)
			lstWork.add(WorkTimeForm.FIXED);
		
		if (this.changeableFlex == 1)
			lstWork.add(WorkTimeForm.FLEX);
		
		if (this.changeableFluid == 1)
			lstWork.add(WorkTimeForm.FLOW);
		
		return new ScheFunctionControl (lstWork, (this.displayActual == 1));
	}
}
