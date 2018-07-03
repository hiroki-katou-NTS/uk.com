package nts.uk.ctx.exio.dom.exo.exoutsummaryservice;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSet;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSetRepository;
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetail;
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetailRepository;
import nts.uk.ctx.exio.dom.exo.outitem.StdOutItem;
import nts.uk.ctx.exio.dom.exo.outitem.StdOutItemRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ExOutSummarySettingService {
	
	@Inject
	StdOutputCondSetRepository stdOutputCondSetRepo;
	
	@Inject
	OutCndDetailRepository outCndDetailRep;
	
	@Inject
	StdOutItemRepository stdOutItemRepo;
	
	//アルゴリズム「外部出力取得設定一覧」を実行する with type = fixed form (standard)
	private Optional<StdOutputCondSet> getExOutSetting(String conditionSetCd) {
		String cid = AppContexts.user().companyId();
		
		if(conditionSetCd.equals("") || conditionSetCd == null) {
			return stdOutputCondSetRepo.getStdOutputCondSetByCid(cid);
		} else {
			return stdOutputCondSetRepo.getStdOutputCondSetById(cid, conditionSetCd);
		}
	}
	
	//アルゴリズム「外部出力取得条件一覧」を実行する with type = fixed form (standard)
	private Optional<OutCndDetail> getExOutCond(String code) {
		Optional<OutCndDetail> OutCndDetailList = outCndDetailRep.getOutCndDetailByCode(code);
		return OutCndDetailList;
		// TODO: xử lý search code list
	}
	
	// アルゴリズム「外部出力取得項目一覧」を実行する with type = fixed form (standard)
	private int getExOutItemList(String outItemCd, String condSetCd) {
		String cid = AppContexts.user().companyId();
		Optional<StdOutItem> stdOutItem;
		
		if(outItemCd.equals("") || outItemCd == null) {
			stdOutItem = stdOutItemRepo.getStdOutItemByCidAndSetCd(cid, condSetCd);
		} else {
			stdOutItem = stdOutItemRepo.getStdOutItemById(cid, outItemCd, condSetCd);
		}
		
		return 1;
	}
}
