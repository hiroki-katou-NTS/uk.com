package nts.uk.ctx.at.schedule.infra.repository.schedule.setting.displaycontrol;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.schedule.setting.displaycontrol.DisplayControlRepository;
import nts.uk.ctx.at.schedule.dom.schedule.setting.displaycontrol.ScheDispControl;
import nts.uk.ctx.at.schedule.dom.schedule.setting.displaycontrol.SchePerInfoAtr;
import nts.uk.ctx.at.schedule.dom.schedule.setting.displaycontrol.ScheQualifySet;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.KscmtDispCtrl;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.KscmtDispCtrlPK;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.KscmtSchePerInfoAtr;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.KscmtSchePerInfoAtrPk;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.KscstScheQualifySet;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.KscstScheQualifySetPK;

@Stateless
public class JpaScheSettingDispControlRepository extends JpaRepository implements DisplayControlRepository {

	/**
	 * Get Schedule Function Control data
	 */
	@Override
	public Optional<ScheDispControl> getScheDispControl(String companyId) {
		KscmtDispCtrlPK primaryKey = new KscmtDispCtrlPK(companyId);

		return this.queryProxy().find(primaryKey, KscmtDispCtrl.class).map(x -> toDomain(x));
	}

	/**
	 * Add Schedule Function Control data
	 */
	@Override
	public void addScheDispControl(ScheDispControl scheDispControl) {
		this.commandProxy().insert(convertToDbType(scheDispControl));		
	}

	/**
	 * Update Schedule Function Control data
	 */
	@Override
	public void updateScheDispControl(ScheDispControl scheDispControl) {
		KscmtDispCtrlPK primaryKey = new KscmtDispCtrlPK(scheDispControl.getCompanyId());		
		KscmtDispCtrl kscmtDispCtrl = this.queryProxy().find(primaryKey, KscmtDispCtrl.class).get();
		
		kscmtDispCtrl.personSyQualify = scheDispControl.getPersonSyQualify().v();
		kscmtDispCtrl.pubHolidayShortageAtr = scheDispControl.getPubHolidayShortageAtr().value == 0 ? false : true;
		kscmtDispCtrl.pubHolidayExcessAtr = scheDispControl.getPubHolidayExcessAtr().value == 0 ? false : true;
		kscmtDispCtrl.symbolAtr = scheDispControl.getSymbolAtr().value == 0 ? false : true;
		kscmtDispCtrl.symbolHalfDayAtr = scheDispControl.getSymbolHalfDayAtr().value == 0 ? false : true;
		kscmtDispCtrl.symbolHalfDayName = scheDispControl.getSymbolHalfDayName();
		
		List<KscmtSchePerInfoAtr> schePerInfoAtr = null;
		if(scheDispControl.getSchePerInfoAtr() != null) {
			schePerInfoAtr = scheDispControl.getSchePerInfoAtr().stream().map(x -> {
				KscmtSchePerInfoAtrPk key = new KscmtSchePerInfoAtrPk(scheDispControl.getCompanyId(), x.getPersonInfoAtr().value);
				return new KscmtSchePerInfoAtr(key);
			}).collect(Collectors.toList());
		}		
		
		List<KscstScheQualifySet> scheQualifySet = null;
		if(scheDispControl.getScheQualifySet() != null) {
			scheQualifySet = scheDispControl.getScheQualifySet().stream().map(x -> {
				KscstScheQualifySetPK key = new KscstScheQualifySetPK(scheDispControl.getCompanyId(), x.getQualifyCode());
				return new KscstScheQualifySet(key);
			}).collect(Collectors.toList());
		}		
		
		kscmtDispCtrl.schePerInfoAtr = schePerInfoAtr;
		kscmtDispCtrl.scheQualifySet = scheQualifySet;
		
		this.commandProxy().update(kscmtDispCtrl);
	}

	/**
	 * To domain ScheDispControl
	 * @param entity
	 * @return
	 */
	private static ScheDispControl toDomain(KscmtDispCtrl entity) {
		if (entity == null) {
			return null;
		}

		List<SchePerInfoAtr> schePerInfoAtr = new ArrayList<>();
		for (KscmtSchePerInfoAtr obj : entity.schePerInfoAtr) {
			schePerInfoAtr.add(toDomainSchePerInfoAtr(obj));
		}
		
		List<ScheQualifySet> scheQualifySet = new ArrayList<>();
		for (KscstScheQualifySet obj : entity.scheQualifySet) {
			scheQualifySet.add(toDomainScheQualifySet(obj));
		}
		
		ScheDispControl domain = ScheDispControl.createFromJavaType(entity.kscmtDispCtrlPK.companyId,
				entity.personSyQualify, entity.symbolHalfDayAtr == true ? 1 : 0, entity.symbolAtr == true ? 1 : 0,
				entity.pubHolidayExcessAtr == true ? 1 : 0, entity.pubHolidayShortageAtr == true ? 1 : 0, entity.symbolHalfDayName,
				schePerInfoAtr, scheQualifySet);
		
		return domain;
	}
	
	/**
	 * To domain ScheQualifySet
	 * @param entity
	 * @return
	 */
	private static ScheQualifySet toDomainScheQualifySet(KscstScheQualifySet entity) {
		ScheQualifySet domain = ScheQualifySet.createFromJavaType(entity.kscstScheQualifySetPK.companyId,
				entity.kscstScheQualifySetPK.qualifyCode);
		
		return domain;
	}

	/**
	 * To domain SchePerInfoAtr
	 * @param entity
	 * @return
	 */
	private static SchePerInfoAtr toDomainSchePerInfoAtr(KscmtSchePerInfoAtr entity) {
		SchePerInfoAtr domain = SchePerInfoAtr.createFromJavaType(entity.kscmtSchePerInfoAtrPk.companyId,
				entity.kscmtSchePerInfoAtrPk.personInfoAtr);
		
		return domain;
	}

	/**
	 * Convert to DB type
	 * 
	 * @param scheDispControl
	 * @return
	 */
	private KscmtDispCtrl convertToDbType(ScheDispControl scheDispControl) {
		KscmtDispCtrlPK primaryKey = new KscmtDispCtrlPK(scheDispControl.getCompanyId());
		
		List<KscmtSchePerInfoAtr> schePerInfoAtr = null;
		if(scheDispControl.getSchePerInfoAtr() != null) {
			schePerInfoAtr = scheDispControl.getSchePerInfoAtr().stream().map(x -> {
				KscmtSchePerInfoAtrPk key = new KscmtSchePerInfoAtrPk(scheDispControl.getCompanyId(), x.getPersonInfoAtr().value);
				return new KscmtSchePerInfoAtr(key);
			}).collect(Collectors.toList());			
		}
		
		List<KscstScheQualifySet> scheQualifySet = null;
		if(scheDispControl.getScheQualifySet() != null) {
			scheDispControl.getScheQualifySet().stream().map(x -> {
				KscstScheQualifySetPK key = new KscstScheQualifySetPK(scheDispControl.getCompanyId(), x.getQualifyCode());
				return new KscstScheQualifySet(key);
			}).collect(Collectors.toList());
		}
		
		return new KscmtDispCtrl(
				primaryKey,
				scheDispControl.getPersonSyQualify().v(),
				scheDispControl.getPubHolidayShortageAtr().value == 0 ? false : true,
				scheDispControl.getPubHolidayExcessAtr().value == 0 ? false : true,
				scheDispControl.getSymbolAtr().value == 0 ? false : true,
				scheDispControl.getSymbolHalfDayAtr().value == 0 ? false : true,
				scheDispControl.getSymbolHalfDayName(),
				schePerInfoAtr,
				scheQualifySet);
	}
}
