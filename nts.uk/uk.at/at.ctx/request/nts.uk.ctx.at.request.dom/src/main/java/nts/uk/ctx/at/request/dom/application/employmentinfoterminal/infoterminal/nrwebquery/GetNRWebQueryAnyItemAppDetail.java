package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.ApplicationArgument;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRQueryAnyItemApp;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRQueryAnyItemAppDetail;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRQueryApp;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRWebQuerySidDateParameter;
import nts.uk.ctx.at.request.dom.application.optional.OptionalItemApplication;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItem;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemAtr;

/**
 * @author thanh_nx
 *
 *         NRWeb照会任意項目申請を取得
 */
public class GetNRWebQueryAnyItemAppDetail {

	// [S-1] プロセス
	public static List<NRQueryAnyItemApp> process(Require require, NRWebQuerySidDateParameter param,
			DatePeriod period) {

		if (!param.getNrWebQuery().getApplication().isPresent()) {
			return new ArrayList<>();
		}
		// $タイプ
		ApplicationArgument type = param.getNrWebQuery().getApplication().get()
				.argumentCheck(ApplicationType.WORK_CHANGE_APPLICATION);
		List<OptionalItemApplication> lstOptionalItemApplicationList = new ArrayList<>();
		if (type == ApplicationArgument.PT1) {
			lstOptionalItemApplicationList.addAll(require.findOptionalItemWithSidDate(param.getCid(), param.getSid(),
					param.getNrWebQuery().getApplication().get().getDate().get()));
		} else if (type == ApplicationArgument.PT2) {
			lstOptionalItemApplicationList.addAll(require.findOptionalItemWithSidDateApptype(param.getCid(),
					param.getSid(), param.getNrWebQuery().getApplication().get().getDate().get(),
					param.getNrWebQuery().getApplication().get().getDateTime().get(),
					param.getNrWebQuery().getApplication().get().getJikbn().get()));
		} else if (type == ApplicationArgument.PT3) {
			lstOptionalItemApplicationList
					.addAll(require.findOptionalItemWithSidDatePeriod(param.getCid(), param.getSid(), period));
		} else {
			return new ArrayList<>();
		}
		return lstOptionalItemApplicationList.stream().flatMap(x -> createAppData(require, param.getCid(), x).stream())
				.collect(Collectors.toList());
	}

	private static List<NRQueryAnyItemApp> createAppData(Require require, String cid, OptionalItemApplication app) {
		val master = require.findOptionalItemByListNos(cid,
				app.getOptionalItems().stream().map(x -> x.getItemNo().v()).collect(Collectors.toList()));
		return app.getReflectionStatus().getListReflectionStatusOfDay().stream().map(x -> {
			NRQueryApp appQuery = NRQueryApp.create(app, x);
			List<NRQueryAnyItemAppDetail> anyItemDetailLst = app.getOptionalItems().stream().map(anyItem -> {
				val typeData = master.stream()
						.filter(y -> y.getOptionalItemNo().v() == anyItem.getItemNo().v().intValue()).findFirst();
				if (!typeData.isPresent()) {
					return null;
				}
				if (typeData.get().getOptionalItemAtr() == OptionalItemAtr.TIME) {
					return new NRQueryAnyItemAppDetail(typeData.get().getOptionalItemName().v(),
							NRQueryApp.createValueFormatTime(String.valueOf(anyItem.getRowTime())));
				} else if (typeData.get().getOptionalItemAtr() == OptionalItemAtr.NUMBER) {
					return new NRQueryAnyItemAppDetail(typeData.get().getOptionalItemName().v(),
							String.valueOf(anyItem.getRowTimes()));
				}
				return new NRQueryAnyItemAppDetail(typeData.get().getOptionalItemName().v(),
						NRQueryApp.createValueFormatMoney(String.valueOf(anyItem.getRowAmount())));
			}).filter(z -> z != null).collect(Collectors.toList());
			return new NRQueryAnyItemApp(appQuery, anyItemDetailLst);
		}).collect(Collectors.toList());
	}

	public static interface Require {
		// [R-1] 申請者 と申請日から任意項目申請を取得する
		public List<OptionalItemApplication> findOptionalItemWithSidDate(String companyId, String sid,
				GeneralDate date);

		// [R-2] 任意項目申請を取得する
		public List<OptionalItemApplication> findOptionalItemWithSidDateApptype(String companyId, String sid,
				GeneralDate date, GeneralDateTime inputDate, PrePostAtr prePostAtr);

		// [R-3] 申請者 と期間から任意項目申請を取得する
		public List<OptionalItemApplication> findOptionalItemWithSidDatePeriod(String companyId, String sid,
				DatePeriod period);

		// [R-4] 任意項目NOリストから任意項目を取得する
		// OptionalItemRepository.findByListNos
		public List<OptionalItem> findOptionalItemByListNos(String companyId, List<Integer> optionalitemNos);

	}
}
