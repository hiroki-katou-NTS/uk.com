package nts.uk.ctx.basic.infra.repository.system.era;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.dom.system.era.Era;
import nts.uk.ctx.basic.dom.system.era.EraRepository;
import nts.uk.ctx.basic.infra.entity.system.era.CmnmtEra;
import nts.uk.ctx.basic.infra.entity.system.era.CmnmtEraPk;

@Stateless
public class JpaEraRepository extends JpaRepository implements EraRepository {
	private static final String SEL_1 = "SELECT e FROM CmnmtEra e";
	private static final String SEL_LATEST_ERA =  SEL_1 + " WHERE e.end_D = :endDate";
	//private static final String SEL_3 = ""
	private final Era toDomain(CmnmtEra entity){
		val domain = Era.createFromDataType(entity.era_Name,
				entity.era_Mark,
				(entity.cmnmtEraPk.startDate),
				(entity.end_D),
				entity.fix_Atr);
		return domain;
	}
	private static CmnmtEra toEntity(Era domain){
		CmnmtEra entity = new CmnmtEra();
		entity.era_Name = domain.getEraName().v();
		entity.era_Mark = domain.getEraMark().v();
		//entity.cmnmtEraPk = new CmnmtEra(domain.getStartDate());
		//GeneralDate.(entity.cmnmtEraPk.str_D);
		entity.cmnmtEraPk = new CmnmtEraPk(domain.getStartDate());
		entity.end_D = domain.getEndDate();
		entity.fix_Atr = domain.getFixAttribute().value;
		return entity;
	}
	@Override
	public List<Era> getEras() {
		// TODO Auto-generated method stub
		return this.queryProxy()
				.query(SEL_1, CmnmtEra.class)
				.getList(c -> toDomain(c));
	}
//	@Override
//	public Optional<Era> getEraDetail(GeneralDate startDate) {
//		// TODO Auto-generated method stub
//		return this.queryProxy().find(new CmnmtEraPk(startDate.localDate()), 
//				CmnmtEra.class).map(c -> toDomain(c));
//	}
	@Override
	public void add(Era era) {
		// TODO Auto-generated method stub
		this.commandProxy().insert(toEntity(era));
	}
	@Override
	public void update(Era era) {
		// TODO Auto-generated method stub
		this.commandProxy().update(toEntity(era));
	}
	@Override
	public void delete(GeneralDate startDate) {
		val objectKey = new CmnmtEraPk(startDate);
		this.commandProxy().remove(CmnmtEra.class, objectKey);
	}
	@Override
	public Optional<Era> getEraDetail(GeneralDate startDate) {
		// TODO Auto-generated method stub
		return this.queryProxy().find(new CmnmtEraPk(startDate), 
				CmnmtEra.class).map(c -> toDomain(c));
	}

	@Override
	public Optional<Era> getLatestEra(){
		return this.queryProxy().query(SEL_LATEST_ERA, CmnmtEra.class)
				.setParameter("endDate", GeneralDate.ymd(9999, 12, 31))
				.getSingle(s -> toDomain(s));
	}
}
