package nts.uk.ctx.basic.infra.repository.organization.position;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.dom.organization.position.Position;
import nts.uk.ctx.basic.dom.organization.position.PositionRepository;
import nts.uk.ctx.basic.infra.entity.organization.position.CmnmtJobTittle;
import nts.uk.ctx.basic.infra.entity.organization.position.CmnmtJobTittlePK;

@Stateless
public class JpaPositionReponsitory extends JpaRepository implements PositionRepository {

	private final String SELECT_NO_WHERE = "SELECT c FROM CmnmtJobTittle c";
	private final String SELECT_ALL_BY_COMPANY = SELECT_NO_WHERE + " WHERE c.cmnmtJobTittlePK.companyCd = :companyCd";

	private final Position toDomain(CmnmtJobTittle entity) {
		val domain = Position.createFromJavaType(GeneralDate.localDate(entity.endYm), entity.jobName, entity.cmnmtJobTittlePK.jobCode,entity.jobOutCode ,GeneralDate.localDate(entity.strYm), entity.cmnmtJobTittlePK.historyID, entity.cmnmtJobTittlePK.companyCd, entity.memo);

		return domain;
	}

	private CmnmtJobTittle toEntity(Position domain) {
		val entity = new CmnmtJobTittle();

		entity.cmnmtJobTittlePK = new CmnmtJobTittlePK();
		entity.cmnmtJobTittlePK.companyCd = domain.getCompanyCode();
		entity.cmnmtJobTittlePK.jobCode = domain.getJobCode().v();
		entity.cmnmtJobTittlePK.historyID = domain.getHistoryID();
		entity.endYm = domain.getEndDate().localDate();
		entity.strYm = domain.getStartDate().localDate();

		
		return entity;
	}

	@Override
	public void add(Position position) {
		this.commandProxy().insert(toEntity(position));

	}

	@Override
	public void update(Position position) {
		this.commandProxy().update(toEntity(position));

	}

	@Override
	public void remove(String companyCode) {
		val objectKey = new CmnmtJobTittlePK();
		objectKey.companyCd = companyCode;

		this.commandProxy().remove(CmnmtJobTittle.class, objectKey);
	}

	@Override
	public List<Position> getPositions(String companyCode) {
		return this.queryProxy().query(SELECT_ALL_BY_COMPANY, CmnmtJobTittle.class)
				.setParameter("companyCd", companyCode)

				.getList(c -> toDomain(c));
	}

	@Override
	public Optional<Position> getPosition(String companyCode, String jobCode, String historyID) {
		try {
			return this.queryProxy().find(new CmnmtJobTittlePK(companyCode, jobCode ,historyID), CmnmtJobTittle.class)
					.map(c -> toDomain(c));
		} catch (Exception e) {
			throw e;
		}
	}

}
