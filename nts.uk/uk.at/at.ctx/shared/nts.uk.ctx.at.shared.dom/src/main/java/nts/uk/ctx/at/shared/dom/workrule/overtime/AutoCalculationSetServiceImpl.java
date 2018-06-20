package nts.uk.ctx.at.shared.dom.workrule.overtime;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.adapter.workplace.SharedAffWorkPlaceHisAdapter;
import nts.uk.ctx.at.shared.dom.common.usecls.ApplyAtr;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalAtrOvertime;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalFlexOvertimeSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalOvertimeSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalRestTimeSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.BaseAutoCalSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.TimeLimitUpperLimitSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.com.ComAutoCalSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.com.ComAutoCalSettingRepository;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.job.JobAutoCalSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.job.JobAutoCalSettingRepository;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.use.UseUnitAutoCalSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.use.UseUnitAutoCalSettingRepository;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.wkp.WkpAutoCalSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.wkp.WkpAutoCalSettingRepository;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.wkpjob.WkpJobAutoCalSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.wkpjob.WkpJobAutoCalSettingRepository;

/**
 * 自動計算設定の取得
 * 
 * @author nampt
 *
 */
@Stateless
public class AutoCalculationSetServiceImpl implements AutoCalculationSetService {

	@Inject
	private SharedAffWorkPlaceHisAdapter sharedAffWorkPlaceHisAdapter;

	@Inject
	private UseUnitAutoCalSettingRepository useUnitAutoCalSettingRepository;

	@Inject
	private WkpJobAutoCalSettingRepository wkpJobAutoCalSettingRepository;

	@Inject
	private JobAutoCalSettingRepository jobAutoCalSettingRepository;

	@Inject
	private WkpAutoCalSettingRepository wkpAutoCalSettingRepository;

	@Inject
	private ComAutoCalSettingRepository comAutoCalSettingRepository;

	@Override
	public BaseAutoCalSetting getAutoCalculationSetting(String companyID, String employeeID, GeneralDate processingDate,
			String workPlaceId, String jobTitleId) {

		BaseAutoCalSetting baseAutoCalSetting = new BaseAutoCalSetting();
		// ドメインモデル「時間外の自動計算の利用単位設定」を取得する(get data domain 「時間外の自動計算の利用単位設定」)
		Optional<UseUnitAutoCalSetting> useUnitAutoCalSetting = this.useUnitAutoCalSettingRepository
				.getAllUseUnitAutoCalSetting(companyID);

		if (useUnitAutoCalSetting.isPresent()) {

			// 職場・職位の自動計算設定をする＝TRUE
			if (useUnitAutoCalSetting.get().getUseJobwkpSet() == ApplyAtr.USE) {
				// ドメインモデル「職場・職位別自動計算設定」を取得(get data domain 「職場・職位別自動計算設定」)
				Optional<WkpJobAutoCalSetting> wkpJobAutoCalSetting = this.wkpJobAutoCalSettingRepository
						.getWkpJobAutoCalSetting(companyID, workPlaceId, jobTitleId);

				if (wkpJobAutoCalSetting.isPresent()) {
					baseAutoCalSetting = new BaseAutoCalSetting(wkpJobAutoCalSetting.get().getNormalOTTime(),
							wkpJobAutoCalSetting.get().getFlexOTTime(), wkpJobAutoCalSetting.get().getRestTime());
				} else {
					baseAutoCalSetting = this.getJobTitleCase(companyID, workPlaceId, jobTitleId, processingDate,
							useUnitAutoCalSetting);
				}
			} else {
				baseAutoCalSetting = this.getJobTitleCase(companyID, workPlaceId, jobTitleId, processingDate,
						useUnitAutoCalSetting);
			}

		}

		return baseAutoCalSetting;
	}

	private BaseAutoCalSetting getJobTitleCase(String companyID, String workPlaceID, String jobTitleID,
			GeneralDate processingDate, Optional<UseUnitAutoCalSetting> useUnitAutoCalSetting) {

		BaseAutoCalSetting baseAutoCalSetting = new BaseAutoCalSetting();

		// 職位の自動計算設定をする＝TRUE
		if (useUnitAutoCalSetting.get().getUseJobSet() == ApplyAtr.USE) {
			Optional<JobAutoCalSetting> jobAutoCalSetting = this.jobAutoCalSettingRepository
					.getJobAutoCalSetting(companyID, jobTitleID);

			if (!jobAutoCalSetting.isPresent()) {
				baseAutoCalSetting = this.getWorkPlaceCase(companyID, workPlaceID, processingDate,
						useUnitAutoCalSetting);
			} else {
				baseAutoCalSetting = new BaseAutoCalSetting(jobAutoCalSetting.get().getNormalOTTime(),
						jobAutoCalSetting.get().getFlexOTTime(), jobAutoCalSetting.get().getRestTime());
			}
		} else {
			baseAutoCalSetting = this.getWorkPlaceCase(companyID, workPlaceID, processingDate, useUnitAutoCalSetting);
		}

		return baseAutoCalSetting;
	}

	private BaseAutoCalSetting getWorkPlaceCase(String companyID, String workPlaceID, GeneralDate processingDate,
			Optional<UseUnitAutoCalSetting> useUnitAutoCalSetting) {

		BaseAutoCalSetting baseAutoCalSetting = new BaseAutoCalSetting();

		// 職場の自動計算設定をする＝TRUE
		if (useUnitAutoCalSetting.get().getUseWkpSet() == ApplyAtr.USE) {
			Optional<WkpAutoCalSetting> wkpAutoCalSetting = this.getUpperLevelWorkPlaceSetting(companyID, workPlaceID,
					processingDate);

			if (wkpAutoCalSetting.isPresent()) {
				baseAutoCalSetting = new BaseAutoCalSetting(wkpAutoCalSetting.get().getNormalOTTime(),
						wkpAutoCalSetting.get().getFlexOTTime(), wkpAutoCalSetting.get().getRestTime());
			} else {
				Optional<ComAutoCalSetting> comAutoCalSetting = this.comAutoCalSettingRepository
						.getAllComAutoCalSetting(companyID);

				baseAutoCalSetting = new BaseAutoCalSetting(comAutoCalSetting.get().getNormalOTTime(),
						comAutoCalSetting.get().getFlexOTTime(), comAutoCalSetting.get().getRestTime());
			}
		} else {
			Optional<ComAutoCalSetting> comAutoCalSet = this.comAutoCalSettingRepository
					.getAllComAutoCalSetting(companyID);

			baseAutoCalSetting = new BaseAutoCalSetting(comAutoCalSet.get().getNormalOTTime(),
					comAutoCalSet.get().getFlexOTTime(), comAutoCalSet.get().getRestTime());
		}

		return baseAutoCalSetting;
	}

	/**
	 * 上位階層の職場の設定を取得する
	 * 
	 * @param companyId
	 * @param workPlaceId
	 * @param processingDate
	 * @return
	 */
	private Optional<WkpAutoCalSetting> getUpperLevelWorkPlaceSetting(String companyId, String workPlaceId,
			GeneralDate processingDate) {
		List<String> workPlaceIDList = this.sharedAffWorkPlaceHisAdapter.findParentWpkIdsByWkpId(companyId, workPlaceId,
				processingDate);

		Optional<WkpAutoCalSetting> wkpAutoCalSetting = Optional.empty();
		if (!workPlaceIDList.isEmpty()) {
			for (String workPlaceID : workPlaceIDList) {

				wkpAutoCalSetting = this.wkpAutoCalSettingRepository.getWkpAutoCalSetting(companyId, workPlaceID);

				if (wkpAutoCalSetting.isPresent()) {
					return wkpAutoCalSetting;
				}
			}
		}
		return wkpAutoCalSetting;
	}

}
