package nts.uk.ctx.at.schedule.infra.repository.budget.premium;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.budget.premium.ExtraTime;
import nts.uk.ctx.at.schedule.dom.budget.premium.ExtraTimeRepository;
import nts.uk.ctx.at.schedule.dom.budget.premium.PremiumName;
import nts.uk.ctx.at.schedule.dom.budget.premium.UseClassification;
import nts.uk.ctx.at.schedule.infra.entity.budget.premium.KmlspExtraTimePK;
import nts.uk.ctx.at.schedule.infra.entity.budget.premium.KmlstExtraTime;

@Stateless
public class JpaExtraTimeRepository extends JpaRepository implements ExtraTimeRepository{

	private final String findAll = "SELECT a from KmlstExtraTime a where a.extraItemPK.CID = :CID";
	
	@Override
	public void update(ExtraTime extraTime) {
		this.commandProxy().update(convertToEntity(extraTime));
		
	}
	
	@Override
	public List<ExtraTime> findByCompanyID(String companyID) {
		return this.queryProxy().query(findAll, KmlstExtraTime.class).setParameter("CID", companyID)
				.getList(x -> convertToDomain(x));
	}
	
	private KmlstExtraTime convertToEntity(ExtraTime extraTime){
		return new KmlstExtraTime(
				new KmlspExtraTimePK(extraTime.getCompanyID(), extraTime.getExtraItemID()), 
				extraTime.getPremiumName().v(), 
				extraTime.getTimeItemID(), 
				extraTime.getUseClassification().value);
	}
	
	private ExtraTime convertToDomain(KmlstExtraTime kmlstExtraTime){
		return new ExtraTime(
				kmlstExtraTime.extraItemPK.companyID, 
				kmlstExtraTime.extraItemPK.extraItemID, 
				new PremiumName(kmlstExtraTime.premiumName), 
				kmlstExtraTime.timeItemCD, 
				EnumAdaptor.valueOf(kmlstExtraTime.useAtr, UseClassification.class));
	}
}
