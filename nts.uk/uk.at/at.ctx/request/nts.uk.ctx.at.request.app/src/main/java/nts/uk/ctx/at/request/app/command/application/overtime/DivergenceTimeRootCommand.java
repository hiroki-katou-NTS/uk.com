package nts.uk.ctx.at.request.app.command.application.overtime;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeErrorCancelMethod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeGetMemento;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeName;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeUseSet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceType;

@AllArgsConstructor
@NoArgsConstructor
public class DivergenceTimeRootCommand implements DivergenceTimeGetMemento{
	// 乖離時間NO
	public Integer divergenceTimeNo;

	// 会社ID
	public String companyId;

	// 使用区分
	public Integer divTimeUseSet;

	// 乖離時間名称
	public String divTimeName;

	// 乖離の種類
	public Integer divType;

	// 乖離時間のエラーの解除方法
	public DivergenceTimeErrorCancelMethodCommand errorCancelMedthod;

	// 対象項目一覧
	public List<Integer> targetItems;
	
	
	public DivergenceTimeRoot toDomain() {
		return new DivergenceTimeRoot(this);
	}


	@Override
	public Integer getDivergenceTimeNo() {
		return this.divergenceTimeNo;
	}


	@Override
	public String getCompanyId() {
		return companyId;
	}


	@Override
	public DivergenceTimeUseSet getDivTimeUseSet() {
		return DivergenceTimeUseSet.valueOf(this.divTimeUseSet);
	}


	@Override
	public DivergenceTimeName getDivTimeName() {
		return new DivergenceTimeName(this.divTimeName);
	}


	@Override
	public DivergenceType getDivType() {
		return DivergenceType.valueOf(this.divType);
	}


	@Override
	public DivergenceTimeErrorCancelMethod getErrorCancelMedthod() {
		DivergenceTimeErrorCancelMethod object = new DivergenceTimeErrorCancelMethod();

		object.setReasonInputed(this.errorCancelMedthod.reasonInputed);
		object.setReasonSelected(this.errorCancelMedthod.reasonSelected);
		return object;
	}


	@Override
	public List<Integer> getTargetItems() {
		return targetItems;
	}
}
