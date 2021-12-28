package nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.DomainObject;
import nts.gul.serialize.binary.SerializableWithOptional;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 労働時間計算時の休暇分を含める設定
 * @author shuichi_ishida
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class IncludeVacationSetForCalcWorkTime extends DomainObject implements SerializableWithOptional {

	private static final long serialVersionUID = 1L;
	
	/** 含める */
	protected NotUseAtr addition = NotUseAtr.NOT_USE;
	/** 通常、変形の所定超過時 */
	protected Optional<CalculationMethodForNormalWorkAndDeformedLaborOverTime> deformationExceedsPredeterminedValue = Optional.empty();
	
	/**
	 * 休暇分を就業時間に含めるか判断する
	 * @return true：含める、false：含めない
	 */
	public boolean isCalculateIncludVacation() {
		
		if (addition == NotUseAtr.NOT_USE) return false;
		
		return true;
	}
}
