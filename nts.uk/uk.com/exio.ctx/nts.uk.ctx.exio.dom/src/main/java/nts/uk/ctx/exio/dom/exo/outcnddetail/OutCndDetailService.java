package nts.uk.ctx.exio.dom.exo.outcnddetail;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.CtgItemData;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.DataType;
import nts.uk.ctx.exio.dom.exo.commonalgorithm.AcquisitionExOutSetting;
import nts.uk.ctx.exio.dom.exo.commonalgorithm.AcquisitionExternalOutputCategory;
import nts.uk.ctx.exio.dom.exo.commonalgorithm.RegisterMode;
import nts.uk.ctx.exio.dom.exo.commonalgorithm.RegistrationCondDetails;
import nts.uk.ctx.exio.dom.exo.condset.StandardAtr;

@Stateless
public class OutCndDetailService {
	@Inject
	private AcquisitionExternalOutputCategory acquisitionExternalOutputCategory;

	@Inject
	private AcquisitionExOutSetting mAcquisitionExOutSetting;

	@Inject
	private RegistrationCondDetails registrationCondDetails;

	/**
	 * 外部出力条件設定
	 * 
	 * @param categoryId
	 * @param ctgItemNo
	 * @return
	 */
	public CtgItemDataCndDetail outputExCndList(String condSetCd, int categoryId) {
		// アルゴリズム「外部出力カテゴリ取得項目」を実行する
		List<CtgItemData> itemDataList = acquisitionExternalOutputCategory.getExternalOutputCategoryItem(categoryId,
				null);
		// 取得した項目から、データ型が「在職区分」ものは除外する
		List<CtgItemData> arrayTemp = new ArrayList<CtgItemData>();
		for(CtgItemData temp:itemDataList){
			if(temp.getDataType() != DataType.ATWORK){
				arrayTemp.add(temp);
			}
		}
		// アルゴリズム「外部出力取得条件一覧」を実行する
		Optional<OutCndDetail> cndDetailOtp = mAcquisitionExOutSetting.getExOutCond(condSetCd, null,
				StandardAtr.STANDARD, false, null);
		return new CtgItemDataCndDetail(arrayTemp, cndDetailOtp);
	}

	/**
	 * 外部出力条件登録
	 */
	public void registerExOutCondition(Optional<OutCndDetail> outCndDetail, int standardAtr, int registerMode) {
		// アルゴリズム「外部出力登録条件詳細」を実行する
		registrationCondDetails.algorithm(outCndDetail, EnumAdaptor.valueOf(standardAtr, StandardAtr.class),
				EnumAdaptor.valueOf(registerMode, RegisterMode.class));
	}
}
