package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.directgoback;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.scherec.application.gobackdirectly.GoBackDirectlyShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.ReflectDirectBounceClassifi;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.ReflectWorkInformation;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.workchangeapp.ReflectWorkChangeApp.WorkInfoDto;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeUnit;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@EqualsAndHashCode(callSuper=false)
@Data
@AllArgsConstructor
@NoArgsConstructor
//直行直帰申請の反映
public class GoBackReflect extends AggregateRoot{
//	会社ID
	private String companyId;
//	勤務情報を反映する
	private ApplicationStatus reflectApplication;
	
	public String getContent() {
		return null;
	}

	public static GoBackReflect create(String companyId, int reflectAtr) {
		return new GoBackReflect(companyId, EnumAdaptor.valueOf(reflectAtr, ApplicationStatus.class));
	}
	
	/**
	 * @author thanh_nx
	 *
	 *         直行直帰申請の反映
	 */
	public Collection<Integer> reflect(Require require, GoBackDirectlyShare appGoback,
			DailyRecordOfApplication dailyApp) {

		Set<Integer> lstResult = new HashSet<>();
		// [勤務情報を反映する]をチェック
		if (this.getReflectApplication() == ApplicationStatus.DO_REFLECT) {
			// 1:反映する
			lstResult.addAll(processWorkType(require, appGoback, dailyApp));
		}

		if (this.getReflectApplication() == ApplicationStatus.DO_NOT_REFLECT_1
				|| this.getReflectApplication() == ApplicationStatus.DO_REFLECT_1) {
			// 2：申請時に決める、3：申請時に決める
			if (appGoback.getIsChangedWork().isPresent() && appGoback.getIsChangedWork().get() == NotUseAtr.USE) {
				lstResult.addAll(processWorkType(require, appGoback, dailyApp));
			}
		}

		// 直行直帰区分の反映
		lstResult.addAll(ReflectDirectBounceClassifi.reflect(dailyApp, appGoback.getStraightLine(),
				appGoback.getStraightDistinction()));
		return lstResult;
	}

	//勤務情報の処理
	private  List<Integer> processWorkType(Require require, GoBackDirectlyShare appGoback,
			DailyRecordOfApplication dailyApp) {
		// 勤務種類を取得する
		Optional<WorkType> workTypeOpt = require.workType(companyId,
				dailyApp.getWorkInformation().getRecordInfo().getWorkTypeCode());
		if (!checkWorkType(workTypeOpt)) {
			// [input. 直行直帰申請. 勤務情報]を勤務情報DTOへセット
			// [勤務種類コード、就業時間帯コード]を勤務情報DTOへセット
			WorkInfoDto workInfoDto = appGoback.getDataWork().map(x -> {
				return new WorkInfoDto(Optional.ofNullable(x.getWorkTypeCode()), x.getWorkTimeCodeNotNull());
			}).orElse(new WorkInfoDto(Optional.empty(), Optional.empty()));
			
			// 勤務情報の反映
			return ReflectWorkInformation.reflectInfo(require, companyId, workInfoDto, dailyApp, Optional.of(true),
					Optional.of(true));
		}
		return new ArrayList<>();
	}

	private  boolean checkWorkType(Optional<WorkType> workTypeOpt) {

		if (!workTypeOpt.isPresent()) {
			return true;
		}

		if (workTypeOpt.get().getDailyWork().getWorkTypeUnit() == WorkTypeUnit.OneDay && (workTypeOpt.get()
				.getDailyWork().getClassification() == WorkTypeClassification.Shooting
				|| workTypeOpt.get().getDailyWork().getClassification() == WorkTypeClassification.HolidayWork)) {
			return true;
		}

		if (workTypeOpt.get().getDailyWork().getWorkTypeUnit() != WorkTypeUnit.OneDay
				&& workTypeOpt.get().getDailyWork().getClassification() == WorkTypeClassification.Shooting) {

			return true;
		}
		return false;
	}

	public static interface Require extends ReflectWorkInformation.Require { }
}
