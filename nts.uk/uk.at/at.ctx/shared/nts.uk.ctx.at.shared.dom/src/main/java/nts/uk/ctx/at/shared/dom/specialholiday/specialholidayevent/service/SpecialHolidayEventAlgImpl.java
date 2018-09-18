package nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.relationship.Relationship;
import nts.uk.ctx.at.shared.dom.relationship.primitives.RelationshipCode;
import nts.uk.ctx.at.shared.dom.relationship.repository.RelationshipRepository;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.MaxNumberDayType;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.SpecialHolidayEvent;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.SpecialHolidayEventRepository;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.grantdayperrelationship.GrantDayPerRelationship;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.grantdayperrelationship.GrantDayRelationship;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.grantdayperrelationship.GrantDayRelationshipRepository;
import nts.uk.ctx.at.shared.dom.worktype.algorithm.SpecHdFrameForWkTypeSetService;
@Stateless
public class SpecialHolidayEventAlgImpl implements SpecialHolidayEventAlgorithm{

	@Inject
	private SpecHdFrameForWkTypeSetService specHdFrameService;
	@Inject
	private SpecialHolidayEventRepository repoSpecHdEvent;
	@Inject
	private GrantDayRelationshipRepository repoGrantDayRela;
	@Inject
	private RelationshipRepository repoRelationship;
	/**
	 * 指定する勤務種類が事象に応じた特別休暇かチェックする
	 * @param companyId - 会社ID
	 * @param workTypeCD - 勤務種類コード
	 * @return (事象に応じた特休フラグ(true, false); ドメインモデル「事象に対する特別休暇」 (optional); 特休枠NO (optional))
	 */
	@Override
	public CheckWkTypeSpecHdEventOutput checkWkTypeSpecHdForEvent(String companyId, String workTypeCD) {
		//事象に応じた特休フラグ=false(初期化)
		CheckWkTypeSpecHdEventOutput result = new CheckWkTypeSpecHdEventOutput(false, Optional.empty(), Optional.empty());
		//指定する勤務種類の特休枠を取得する
		Optional<Integer> frame = specHdFrameService.getSpecHdFrameForWkType(companyId, workTypeCD);
		if(!frame.isPresent()){//その以外
			return result;
		}
		//終了状態：取得成功
		//ドメインモデル「事象に対する特別休暇」を取得する
		Optional<SpecialHolidayEvent> specHd = repoSpecHdEvent.findByKey(companyId, frame.get());
		if(!specHd.isPresent()){//0件
			return result;
		}
		//取得できた
		//事象に応じた特休フラグ=true
		result.setSpecHdForEventFlag(true);
		//ドメインモデル「事象に対する特別休暇」を返す
		result.setSpecHdEvent(specHd);
		result.setFrameNo(frame);
		return result;
	}
	/**
	 * 指定する特休枠の上限日数を取得する
 	 * @param companyId - 会社ID
 	 * @param specHdFrame - 特休枠
 	 * @param specHdEvent - ドメインモデル「事象に対する特別休暇」
 	 * @param relationCD - 続柄コード(optional)
	 * @return
	 */
	@Override
	public MaxDaySpecHdOutput getMaxDaySpecHd(String companyId, Integer specHdFrame, SpecialHolidayEvent specHdEvent,
			Optional<String> relationCD) {
		//上限日数=0
		int maxDay = 0;
		//喪主加算日数=0
		int dayOfRela = 0;
		//INPUT．ドメインモデル「事象に対する特別休暇」．上限日数．種類をチェックする
		if(specHdEvent.getMaxNumberDay().equals(MaxNumberDayType.LIMIT_FIXED_DAY)){//種類が固定日数を上限とする(Type = FixedDayGrant)
			//上限日数=INPUT．ドメインモデル「事象に対する特別休暇」．上限日数．固定上限日数
			maxDay = specHdEvent.getFixedDayGrant() == null ? 0 : specHdEvent.getFixedDayGrant().v();
			return new MaxDaySpecHdOutput(maxDay, dayOfRela);
		}
		//種類が続柄ごとに上限を設定する(Type =GrantDayPerRelationship)
		Optional<GrantDayPerRelationship>  rela = repoGrantDayRela.getGrandDayFullByFrameNo(companyId, specHdFrame);
		if(!rela.isPresent()){
			return new MaxDaySpecHdOutput(maxDay, dayOfRela);
		}
		GrantDayPerRelationship relaShip = rela.get();
		//条件： コード=INPUT．続柄コード
		GrantDayRelationship gradDay = null;
		if(relationCD.isPresent()){
			gradDay = this.findGrandDayByRelaCD(relaShip.getLstGrandDayRelaShip(), relationCD.get());
		}else{
			gradDay = relaShip.getLstGrandDayRelaShip().isEmpty()? null : relaShip.getLstGrandDayRelaShip().get(0);
		}
		if(gradDay == null){
			return new MaxDaySpecHdOutput(maxDay, dayOfRela);
		}
		//上限日数=取得した「続柄に対する上限日数」．付与日数
		maxDay = gradDay.getGrantedDay().v();
		//喪主加算日数=INPUT．「続柄に対する上限日数」．喪主時加算日数
		dayOfRela = gradDay.getMorningHour() == null || gradDay.getMorningHour().v() == null ? 0 : gradDay.getMorningHour().v();
		return new MaxDaySpecHdOutput(maxDay, dayOfRela);
	}
	/**
	 * find grand day by relation ship code
	 * @param lstGrandDay
	 * @param relaCD
	 * @return
	 */
	private GrantDayRelationship findGrandDayByRelaCD(List<GrantDayRelationship> lstGrandDay, String relaCD){
		for (GrantDayRelationship grantDay : lstGrandDay) {
			if(grantDay.getRelationshipCd().v().equals(relaCD)){
				return grantDay;
			}
		}
		return null;
	}
	/**
	 * 指定する特休枠の続柄に対する上限日数を取得する
	 * @param companyId - 会社ID
	 * @param specHdFrame - 特休枠
	 * @return 続柄毎の上限日数リスト:　<コード, 続柄名, 上限日数>(List)
	 */
	@Override
	public List<DateSpecHdRelationOutput> getMaxDaySpecHdByRelaFrameNo(String companyId, Integer specHdFrame) {
		// TODO Auto-generated method stub
		//OUTPUT．続柄毎の上限日数リストを空リストに初期化
		List<DateSpecHdRelationOutput> result = new ArrayList<>();
		//ドメインモデル「続柄毎の上限日数」を取得す - (lấy domain [GrantDayPerRelationship])
		Optional<GrantDayPerRelationship>  grandPerOp = repoGrantDayRela.getGrandDayFullByFrameNo(companyId, specHdFrame);
		if(!grandPerOp.isPresent()){//0件
			return result;
		}
		List<GrantDayRelationship> lstGrantDay = grandPerOp.get().getLstGrandDayRelaShip();
		List<Relationship> lstRelaShip = repoRelationship.findAll(companyId);
		for (GrantDayRelationship grantDay : lstGrantDay) {
			//Bug100572 - ver21
			Relationship rela = this.findRelaName(lstRelaShip, grantDay.getRelationshipCd());
			//OUTPUT．続柄毎の上限日数リストのコードに、ドメインモデル「続柄」に存在しないリスト項目を削除する
			if(rela == null){
				continue;
			}
			//全ての「続柄に対する上限日数」をOUTPUT．続柄毎の上限日数リストに追加する
			//ドメインモデル「続柄」．名称をOUTPUT．続柄毎の上限日数リストの続柄名に入れる
			result.add(new DateSpecHdRelationOutput(grantDay.getRelationshipCd().v(), rela.getRelationshipName().v(), grantDay.getGrantedDay().v(), rela.isThreeParentOrLess()));
		}
		return result;
	}

	private Relationship findRelaName(List<Relationship> lstRelaShip, RelationshipCode relaCD){
		for (Relationship relationship : lstRelaShip) {
			if(relationship.getRelationshipCode().equals(relaCD)){
				return relationship;
			}
		}
		return null;
	}
}
