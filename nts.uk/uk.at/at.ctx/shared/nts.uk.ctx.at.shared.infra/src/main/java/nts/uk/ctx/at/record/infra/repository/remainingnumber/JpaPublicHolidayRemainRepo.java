package nts.uk.ctx.at.record.infra.repository.remainingnumber;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.publicholiday.PublicHolidayRemain;
import nts.uk.ctx.at.record.dom.remainingnumber.publicholiday.PublicHolidayRemainRepository;
import nts.uk.ctx.at.record.infra.entity.remainingnumber.publicholiday.KrcmtPubHolidayRemain;

@Stateless
public class JpaPublicHolidayRemainRepo extends JpaRepository implements PublicHolidayRemainRepository{

	private PublicHolidayRemain toDomain(KrcmtPubHolidayRemain entity){
		return new PublicHolidayRemain(entity.cid, entity.employeeId, entity.remainNumber);
	}
	
	private KrcmtPubHolidayRemain toEntity(PublicHolidayRemain domain){
		KrcmtPubHolidayRemain entity = new KrcmtPubHolidayRemain();
		entity.employeeId = domain.getSID();
		entity.cid = domain.getCID();
		entity.remainNumber = domain.getRemainNumber().v();
		return entity;
	}
	
	@Override
	public Optional<PublicHolidayRemain> get(String sid) {
		Optional<KrcmtPubHolidayRemain> pubHoli = this.queryProxy().find(sid, KrcmtPubHolidayRemain.class);
		
		if (pubHoli.isPresent()){
			return Optional.of(toDomain(pubHoli.get()));
		}
		
		return Optional.empty();
	}

	@Override
	public void add(PublicHolidayRemain domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void update(PublicHolidayRemain domain) {
		this.commandProxy().update(toEntity(domain));
	}

	@Override
	public void delete(String sid) {
		this.commandProxy().remove(KrcmtPubHolidayRemain.class, sid);
		
	}
}
