package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRemainingData;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ManagaData {
	/**
	 * 特別休暇付与残数データ
	 */
	List<SpecialLeaveGrantRemainingData> remainDatas;
	/**
	 * 蓄積上限日数
	 */
	Optional<Integer> limitDays;
	/**
	 * 特別休暇付与残数データ : 付与予定
	 */
	List<SpecialLeaveGrantRemainingData> lstGrantDataMemory;//付与予定
	/**
	 * 特別休暇付与残数データ : 付与済
	 */
	List<SpecialLeaveGrantRemainingData> lstGrantDatabase;//付与済
}
