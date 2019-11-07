package nts.uk.ctx.bs.employee.infra.repository.employee.contact;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.SneakyThrows;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsStatement;

import nts.arc.time.GeneralDateTime;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.dom.employee.contact.EmployeeInfoContact;
import nts.uk.ctx.bs.employee.dom.employee.contact.EmployeeInfoContactRepository;
import nts.uk.ctx.bs.employee.infra.entity.employee.contact.BsymtEmpInfoContact;
import nts.uk.ctx.bs.employee.infra.entity.employee.contact.BsymtEmpInfoContactPK;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaEmployeeInfoContactRepository extends JpaRepository implements EmployeeInfoContactRepository {

	//private static final String GET_BY_LIST = "SELECT ec FROM BsymtEmpInfoContact ec WHERE ec.bsymtEmpInfoContactPK.sid IN :employeeIds";

	@Override
	public void add(EmployeeInfoContact domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void update(EmployeeInfoContact domain) {

		BsymtEmpInfoContactPK key = new BsymtEmpInfoContactPK(domain.getSid());

		Optional<BsymtEmpInfoContact> entity = this.queryProxy().find(key, BsymtEmpInfoContact.class);

		if (entity.isPresent()) {
			updateEntity(domain, entity.get());
			this.commandProxy().update(entity.get());
		}
	}

	@Override
	public void delete(String sid) {
		BsymtEmpInfoContactPK key = new BsymtEmpInfoContactPK(sid);
		this.commandProxy().remove(BsymtEmpInfoContact.class, key);
	}

	/**
	 * Convert domain to entity
	 * 
	 * @param domain
	 * @return
	 */
	private BsymtEmpInfoContact toEntity(EmployeeInfoContact domain) {
		BsymtEmpInfoContact entity = new BsymtEmpInfoContact();
		entity.bsymtEmpInfoContactPK = new BsymtEmpInfoContactPK(domain.getSid());
		entity.cid = domain.getCId();
		entity.cellPhoneNo = domain.getCellPhoneNo().isPresent() ? domain.getCellPhoneNo().get().v() : null;
		entity.mailAdress = domain.getMailAddress().isPresent() ? domain.getMailAddress().get().v() : null;
		entity.phoneMailAddress = domain.getPhoneMailAddress().isPresent() ? domain.getPhoneMailAddress().get().v()
				: null;
		entity.seatDialIn = domain.getSeatDialIn().isPresent() ? domain.getSeatDialIn().get().v() : null;
		entity.seatExtensionNo = domain.getSeatExtensionNo().isPresent() ? domain.getSeatExtensionNo().get().v() : null;
		return entity;
	}

	/**
	 * Update entity
	 * 
	 * @param domain
	 * @param entity
	 */
	private void updateEntity(EmployeeInfoContact domain, BsymtEmpInfoContact entity) {
		entity.cellPhoneNo = domain.getCellPhoneNo().isPresent() ? domain.getCellPhoneNo().get().v() : null;
		entity.mailAdress = domain.getMailAddress().isPresent() ? domain.getMailAddress().get().v() : null;
		entity.phoneMailAddress = domain.getPhoneMailAddress().isPresent() ? domain.getPhoneMailAddress().get().v()
				: null;
		entity.seatDialIn = domain.getSeatDialIn().isPresent() ? domain.getSeatDialIn().get().v() : null;
		entity.seatExtensionNo = domain.getSeatExtensionNo().isPresent() ? domain.getSeatExtensionNo().get().v() : null;
	}

	@Override
	public Optional<EmployeeInfoContact> findByEmpId(String sId) {

		Optional<BsymtEmpInfoContact> empContact = this.queryProxy().find(new BsymtEmpInfoContactPK(sId),
				BsymtEmpInfoContact.class);
		if (empContact.isPresent()) {
			return Optional.of(toDomain(empContact.get()));
		} else {
			return Optional.empty();
		}
	}
	
	// chuyển sang jdbc, tăng tốc độ response
	@Override
	@SneakyThrows
	public List<EmployeeInfoContact> findByListEmpId(List<String> employeeIds) {
		if (employeeIds.isEmpty()) {
			return new ArrayList<>();
		}
		List<EmployeeInfoContact> result = new ArrayList<>();
		
		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			String sql = "SELECT * FROM BSYMT_EMP_INFO_CONTACT "
						+ " WHERE SID IN (" + NtsStatement.In.createParamsString(subList) + ")";
			
			try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
				for (int i = 0; i < subList.size(); i++) {
					stmt.setString( i + 1, subList.get(i));
				}
				
				List<EmployeeInfoContact> empInfoContact = new NtsResultSet(stmt.executeQuery()).getList(rec -> {
					BsymtEmpInfoContact entity= new BsymtEmpInfoContact(new BsymtEmpInfoContactPK(rec.getString("SID")),  
						rec.getString("CID"), 
						rec.getString("CELL_PHONE_NO"),
						rec.getString("MAIL_ADDRESS"), 
						rec.getString("PHONE_MAIL_ADDRESS"),
						rec.getString("SEAT_DIAL_IN"), 
						rec.getString("SEAT_EXTENSION_NO"));
					return toDomain(entity);
				});
				result.addAll(empInfoContact);
				
			}catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});
		
		return result;
	}

	private static EmployeeInfoContact toDomain(BsymtEmpInfoContact entity) {

		EmployeeInfoContact domain = EmployeeInfoContact.createFromJavaType(entity.cid,
				entity.bsymtEmpInfoContactPK.sid, entity.mailAdress, entity.seatDialIn, entity.seatExtensionNo,
				entity.phoneMailAddress, entity.cellPhoneNo);
		return domain;
	}

	@Override
	public void addAll(List<EmployeeInfoContact> domains) {
		String INS_SQL = "INSERT INTO BSYMT_EMP_INFO_CONTACT (INS_DATE, INS_CCD , INS_SCD , INS_PG,"
				+ " UPD_DATE , UPD_CCD , UPD_SCD , UPD_PG," 
				+ " SID, CID, CELL_PHONE_NO, MAIL_ADDRESS,"
				+ " PHONE_MAIL_ADDRESS, SEAT_DIAL_IN, SEAT_EXTENSION_NO)"
				+ " VALUES (INS_DATE_VAL, INS_CCD_VAL, INS_SCD_VAL, INS_PG_VAL,"
				+ " UPD_DATE_VAL, UPD_CCD_VAL, UPD_SCD_VAL, UPD_PG_VAL,"
				+ " SID_VAL, CID_VAL, CELL_VAL, "
				+ " MAIL_VAL, PHONE_VAL, SEAT_DIAL_VAL, SEAT_NO_VAL); ";	
		String insCcd = AppContexts.user().companyCode();
		String insScd = AppContexts.user().employeeCode();
		String insPg = AppContexts.programId();
		
		String updCcd = insCcd;
		String updScd = insScd;
		String updPg = insPg;
		StringBuilder sb = new StringBuilder();
		domains.stream().forEach(c ->{
			String sql = INS_SQL;
			sql = sql.replace("INS_DATE_VAL", "'" + GeneralDateTime.now() + "'");
			sql = sql.replace("INS_CCD_VAL", "'" + insCcd + "'");
			sql = sql.replace("INS_SCD_VAL", "'" + insScd + "'");
			sql = sql.replace("INS_PG_VAL", "'" + insPg + "'");

			sql = sql.replace("UPD_DATE_VAL", "'" + GeneralDateTime.now() + "'");
			sql = sql.replace("UPD_CCD_VAL", "'" + updCcd + "'");
			sql = sql.replace("UPD_SCD_VAL", "'" + updScd + "'");
			sql = sql.replace("UPD_PG_VAL", "'" + updPg + "'");
			
			sql = sql.replace("SID_VAL", "'" + c.getSid() + "'");
			sql = sql.replace("CID_VAL", "'"+ c.getCId() + "'");
			sql = sql.replace("CELL_VAL",  c.getCellPhoneNo().isPresent() ? "'"+ c.getCellPhoneNo().get().v() + "'" : "null");
			sql = sql.replace("MAIL_VAL", c.getMailAddress().isPresent() ? "'" +c.getMailAddress().get().v()+"'" : "null");

			sql = sql.replace("PHONE_VAL", c.getPhoneMailAddress().isPresent() ? "'" + c.getPhoneMailAddress().get().v() +"'" : "null");
			sql = sql.replace("SEAT_DIAL_VAL", c.getSeatDialIn().isPresent() ? "'"+ c.getSeatDialIn().get().v()+ "'" : "null");
			sql = sql.replace("SEAT_NO_VAL", c.getSeatExtensionNo().isPresent() ? "'" + c.getSeatExtensionNo().get().v()+ "'" : "null");
			
			sb.append(sql);
		});
		
		int records = this.getEntityManager().createNativeQuery(sb.toString()).executeUpdate();
		System.out.println(records);
		
	}

	@Override
	public void updateAll(List<EmployeeInfoContact> domains) {
		String UP_SQL = "UPDATE BSYMT_EMP_INFO_CONTACT SET UPD_DATE = UPD_DATE_VAL, UPD_CCD = UPD_CCD_VAL, UPD_SCD = UPD_SCD_VAL, UPD_PG = UPD_PG_VAL,"
				+ " CELL_PHONE_NO = CELL_VAL , MAIL_ADDRESS = MAIL_VAL, PHONE_MAIL_ADDRESS = PHONE_VAL, "
				+ " SEAT_DIAL_IN = SEAT_DIAL_VAL, SEAT_EXTENSION_NO = SEAT_NO_VAL"
				+ " WHERE SID = SID_VAL AND CID = CID_VAL;";
		String updCcd = AppContexts.user().companyCode();
		String updScd = AppContexts.user().employeeCode();
		String updPg = AppContexts.programId();
		StringBuilder sb = new StringBuilder();
		domains.stream().forEach(c ->{
			String sql = UP_SQL;

			sql = sql.replace("UPD_DATE_VAL", "'" + GeneralDateTime.now() + "'");
			sql = sql.replace("UPD_CCD_VAL", "'" + updCcd + "'");
			sql = sql.replace("UPD_SCD_VAL", "'" + updScd + "'");
			sql = sql.replace("UPD_PG_VAL", "'" + updPg + "'");
			
			sql = sql.replace("SID_VAL", "'" + c.getSid() + "'");
			sql = sql.replace("CID_VAL", "'"+ c.getCId() + "'");
			sql = sql.replace("CELL_VAL",  c.getCellPhoneNo().isPresent() ? "'"+ c.getCellPhoneNo().get().v() + "'" : "null");
			sql = sql.replace("MAIL_VAL", c.getMailAddress().isPresent() ? "'" +c.getMailAddress().get().v()+"'" : "null");

			sql = sql.replace("PHONE_VAL", c.getPhoneMailAddress().isPresent() ? "'" + c.getPhoneMailAddress().get().v() +"'" : "null");
			sql = sql.replace("SEAT_DIAL_VAL", c.getSeatDialIn().isPresent() ? "'"+ c.getSeatDialIn().get().v()+ "'" : "null");
			sql = sql.replace("SEAT_NO_VAL", c.getSeatExtensionNo().isPresent() ? "'" + c.getSeatExtensionNo().get().v()+ "'" : "null");
			
			sb.append(sql);
		});
		
		int  records = this.getEntityManager().createNativeQuery(sb.toString()).executeUpdate();
		System.out.println(records);
	}

}
