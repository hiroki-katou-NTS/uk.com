package nts.uk.ctx.at.shared.infra.repository.dailyattdcal.declare;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.declare.DeclareHolidayWorkFrame;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.declare.DeclareOvertimeFrame;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.declare.DeclareSet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.declare.DeclareSetRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.declare.HdwkFrameEachHdAtr;
import nts.uk.ctx.at.shared.infra.entity.dailyattdcal.declare.KrcmtDeclareSet;

/**
 * リポジトリ実装：申告設定
 * @author shuichi_ishida
 */
@Stateless
public class JpaDeclareSetRepository extends JpaRepository implements DeclareSetRepository {

	@Override
	public Optional<DeclareSet> find(String companyId) {
		return this.queryProxy().find(companyId, KrcmtDeclareSet.class)
				.map(c -> convertToDomain(c));
	}
	
	@Override
	public void addOrUpdate(DeclareSet declareSet) {
		Optional<KrcmtDeclareSet> entityOpt =
				this.queryProxy().find(declareSet.getCompanyId(), KrcmtDeclareSet.class);
		KrcmtDeclareSet entity = new KrcmtDeclareSet();
		if (entityOpt.isPresent()){
			entity = entityOpt.get();
		}
		else{
			entity.companyId = declareSet.getCompanyId();
		}
		
		entity.usageAtr = declareSet.getUsageAtr().value;
		entity.frameSet = declareSet.getFrameSet().value;
		entity.mnAutoCalc = declareSet.getMidnightAutoCalc().value;
		DeclareOvertimeFrame overtimeFrame = declareSet.getOvertimeFrame();
		entity.earlyOtFrame = (overtimeFrame.getEarlyOvertime().isPresent() ?
				overtimeFrame.getEarlyOvertime().get().v() : null);
		entity.earlyOtMnFrame = (overtimeFrame.getEarlyOvertimeMn().isPresent() ?
				overtimeFrame.getEarlyOvertimeMn().get().v() : null);
		entity.overtimeFrame = (overtimeFrame.getOvertime().isPresent() ?
				overtimeFrame.getOvertime().get().v() : null);
		entity.overtimeMnFrame = (overtimeFrame.getOvertimeMn().isPresent() ?
				overtimeFrame.getOvertimeMn().get().v() : null);
		DeclareHolidayWorkFrame holidayWorkFrame = declareSet.getHolidayWorkFrame();
		entity.hdwkUsage = holidayWorkFrame.getHolidayWorkUsage().value;
		entity.hdwkMnUsage = holidayWorkFrame.getHolidayWorkMnUsage().value;
		Optional<HdwkFrameEachHdAtr> hdwkFrameOpt = holidayWorkFrame.getHolidayWork();
		if (hdwkFrameOpt.isPresent()){
			entity.hdwkStatFrame = hdwkFrameOpt.get().getStatutory().v();
			entity.hdwkNotStatFrame = hdwkFrameOpt.get().getNotStatutory().v();
			entity.hdwkNotStatHdFrame = hdwkFrameOpt.get().getNotStatHoliday().v();
		}
		else{
			entity.hdwkStatFrame = null;
			entity.hdwkNotStatFrame = null;
			entity.hdwkNotStatHdFrame = null;
		}
		Optional<HdwkFrameEachHdAtr> hdwkMnFrameOpt = holidayWorkFrame.getHolidayWorkMn();
		if (hdwkMnFrameOpt.isPresent()){
			entity.hdwkMnStatFrame = hdwkMnFrameOpt.get().getStatutory().v();
			entity.hdwkMnNotStatFrame = hdwkMnFrameOpt.get().getNotStatutory().v();
			entity.hdwkMnNotStatHdFrame = hdwkMnFrameOpt.get().getNotStatHoliday().v();
		}
		else{
			entity.hdwkMnStatFrame = null;
			entity.hdwkMnNotStatFrame = null;
			entity.hdwkMnNotStatHdFrame = null;
		}
		
		if (entityOpt.isPresent()){
			this.commandProxy().update(entity);
		}
		else{
			this.commandProxy().insert(entity);
		}
	}
	
	/**
	 * ドメイン変換
	 * @param entity エンティティ：申告設定
	 * @return ドメイン：申告設定
	 */
	private DeclareSet convertToDomain(KrcmtDeclareSet entity){
		
		return DeclareSet.createFromJavaType(
				entity.companyId,
				entity.usageAtr,
				entity.frameSet,
				entity.mnAutoCalc,
				DeclareOvertimeFrame.createFromJavaType(
						entity.earlyOtFrame,
						entity.earlyOtMnFrame,
						entity.overtimeFrame,
						entity.overtimeMnFrame),
				DeclareHolidayWorkFrame.createFromJavaType(
						entity.hdwkUsage,
						entity.hdwkMnUsage,
						HdwkFrameEachHdAtr.createFromJavaType(
								entity.hdwkStatFrame,
								entity.hdwkNotStatFrame,
								entity.hdwkNotStatHdFrame),
						HdwkFrameEachHdAtr.createFromJavaType(
								entity.hdwkMnStatFrame,
								entity.hdwkMnNotStatFrame,
								entity.hdwkMnNotStatHdFrame)));
	}
}
