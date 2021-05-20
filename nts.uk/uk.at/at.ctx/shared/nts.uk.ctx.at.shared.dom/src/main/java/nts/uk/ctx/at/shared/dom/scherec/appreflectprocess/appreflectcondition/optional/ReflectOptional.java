package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.optional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.UpdateEditSttCreateBeforeAppReflect;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemValueOfDailyAttd;

/**
 * @author thanh_nx
 *
 *         任意項目の反映
 */
public class ReflectOptional {

	// 任意項目の反映
	public static DailyRecordOfApplication reflect(List<AnyItemValue> optionalItems,
			DailyRecordOfApplication dailyApp) {

		List<Integer> lstItemId = new ArrayList<Integer>();
		// [input. 任意項目(List)]でループ
		if (!dailyApp.getAnyItemValue().isPresent()) {
			dailyApp.setAnyItemValue(Optional.of(new AnyItemValueOfDailyAttd(new ArrayList<>())));
		}
		optionalItems.stream().forEach(optionalItem -> {

			dailyApp.getAnyItemValue().ifPresent(optionalDomain -> {

				// [日別勤怠(work）の任意項目]をチェック
				Optional<AnyItemValue> optValue = optionalDomain.getItems().stream().filter(data -> {
					return data.getItemNo().v() == optionalItem.getItemNo().v();
				}).findFirst();
				AnyItemValue itemProcess = optValue.orElse(null);
				if (!optValue.isPresent()) {
					// 該当の任意項目NOをキーにした[任意項目]を作成する
					itemProcess = AnyItemValue.createDefaultWithNo(optionalItem.getItemNo().v());
					optionalDomain.getItems().add(itemProcess);
				}

				// 該当の[任意項目]を日別勤怠(work）へセットする
				itemProcess.updateTimes(optionalItem.getTimes().orElse(null));
				itemProcess.updateAmount(optionalItem.getAmount().orElse(null));
				itemProcess.updateTime(optionalItem.getTime().orElse(null));
				lstItemId.add(640 + optionalItem.getItemNo().v());
			});

		});

		// 申請反映状態にする
		UpdateEditSttCreateBeforeAppReflect.update(dailyApp, lstItemId);

		return dailyApp;
	}

}
