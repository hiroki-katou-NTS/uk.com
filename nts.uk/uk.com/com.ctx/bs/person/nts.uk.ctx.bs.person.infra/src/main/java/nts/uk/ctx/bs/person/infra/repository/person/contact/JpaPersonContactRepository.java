package nts.uk.ctx.bs.person.infra.repository.person.contact;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.SneakyThrows;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsStatement;

import nts.arc.time.GeneralDateTime;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.person.dom.person.contact.EmergencyContact;
import nts.uk.ctx.bs.person.dom.person.contact.PersonContact;
import nts.uk.ctx.bs.person.dom.person.contact.PersonContactRepository;
import nts.uk.ctx.bs.person.infra.entity.person.contact.BpsmtPersonContact;
import nts.uk.ctx.bs.person.infra.entity.person.contact.BpsmtPersonContactPK;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaPersonContactRepository extends JpaRepository implements PersonContactRepository {

//	private static final String GET_BY_LIST = "SELECT pc FROM BpsmtPersonContact pc WHERE pc.bpsmtPersonContactPK.pid IN :personIdList";

	@Override
	public void add(PersonContact domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void update(PersonContact domain) {
		BpsmtPersonContactPK key = new BpsmtPersonContactPK(domain.getPersonId());
		Optional<BpsmtPersonContact> entity = this.queryProxy().find(key, BpsmtPersonContact.class);

		if (entity.isPresent()) {
			updateEntity(domain, entity.get());
			this.commandProxy().update(entity.get());
		}
	}

	@Override
	public void delete(String pID) {
		BpsmtPersonContactPK key = new BpsmtPersonContactPK(pID);
		this.commandProxy().remove(BpsmtPersonContact.class, key);
	}

	private PersonContact toDomain(BpsmtPersonContact entity) {
		return new PersonContact(entity.bpsmtPersonContactPK.pid, entity.cellPhoneNumber, entity.mailAdress,
				entity.mobileMailAdress, entity.memo1, entity.contactName1, entity.phoneNo1, entity.memo2,
				entity.contactName2, entity.phoneNo2);
	}

	/**
	 * Convert domain to entity
	 * 
	 * @param domain
	 * @return
	 */
	private BpsmtPersonContact toEntity(PersonContact domain) {
		BpsmtPersonContactPK key = new BpsmtPersonContactPK(domain.getPersonId());
		BpsmtPersonContact entity = new BpsmtPersonContact();
		entity.bpsmtPersonContactPK = key;
		entity.cellPhoneNumber = domain.getCellPhoneNumber().isPresent() ? domain.getCellPhoneNumber().get().v() : null;
		entity.mailAdress = domain.getMailAdress().isPresent() ? domain.getMailAdress().get().v() : null;
		entity.mobileMailAdress = domain.getMobileMailAdress().isPresent() ? domain.getMobileMailAdress().get().v()
				: null;
		if (domain.getEmergencyContact1().isPresent()) {
			EmergencyContact emergencyContact1 = domain.getEmergencyContact1().get();
			entity.memo1 = emergencyContact1.getMemo().map(i->i.v()).orElse(null);
			entity.contactName1 = emergencyContact1.getContactName().map(i->i.v()).orElse(null);
			entity.phoneNo1 = emergencyContact1.getPhoneNumber().map(i->i.v()).orElse(null);
		}

		if (domain.getEmergencyContact2().isPresent()) {
			EmergencyContact emergencyContact2 = domain.getEmergencyContact2().get();
			entity.memo2 = emergencyContact2.getMemo().map(i->i.v()).orElse(null);
			entity.contactName2 = emergencyContact2.getContactName().map(i->i.v()).orElse(null);
			entity.phoneNo2 = emergencyContact2.getPhoneNumber().map(i->i.v()).orElse(null);
		}
		return entity;
	}

	/**
	 * Update entity
	 * 
	 * @param domain
	 * @param entity
	 */
	private void updateEntity(PersonContact domain, BpsmtPersonContact entity) {
		entity.cellPhoneNumber = domain.getCellPhoneNumber().isPresent() ? domain.getCellPhoneNumber().get().v() : null;
		entity.mailAdress = domain.getMailAdress().isPresent() ? domain.getMailAdress().get().v() : null;
		entity.mobileMailAdress = domain.getMobileMailAdress().isPresent() ? domain.getMobileMailAdress().get().v()
				: null;
		
		entity.memo1 = null;
		entity.contactName1 = null;
		entity.phoneNo1 = null;

		entity.memo2 = null;
		entity.contactName2 = null;
		entity.phoneNo2 = null;
		
		if (domain.getEmergencyContact1().isPresent()) {
			EmergencyContact emergencyContact1 = domain.getEmergencyContact1().get();
			entity.memo1 = emergencyContact1.getMemo().map(i->i.v()).orElse(null);
			entity.contactName1 = emergencyContact1.getContactName().map(i->i.v()).orElse(null);
			entity.phoneNo1 = emergencyContact1.getPhoneNumber().map(i->i.v()).orElse(null);
		}

		if (domain.getEmergencyContact2().isPresent()) {
			EmergencyContact emergencyContact2 = domain.getEmergencyContact2().get();
			entity.memo2 = emergencyContact2.getMemo().map(i->i.v()).orElse(null);
			entity.contactName2 = emergencyContact2.getContactName().map(i->i.v()).orElse(null);
			entity.phoneNo2 = emergencyContact2.getPhoneNumber().map(i->i.v()).orElse(null);
		}

	}

	/**
	 * get domain PersonContact by person id
	 * 
	 * @param perId
	 *            -- person id
	 */
	@Override
	public Optional<PersonContact> getByPId(String perId) {
		BpsmtPersonContactPK key = new BpsmtPersonContactPK(perId);
		Optional<BpsmtPersonContact> entity = this.queryProxy().find(key, BpsmtPersonContact.class);
		if (entity.isPresent())
			return Optional.of(toDomain(entity.get()));
		else
			return Optional.empty();
	}

	// sửa thành jdbc, tăng tốc độ truy vấn 
	@Override
	@SneakyThrows
	public List<PersonContact> getByPersonIdList(List<String> personIds) {
		List<BpsmtPersonContact> entities = new ArrayList<>();
		
		CollectionUtil.split(personIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			String sql = "select * from BPSMT_PERSON_CONTACT"
					+ " where PID in (" + NtsStatement.In.createParamsString(subList) + ")";
			try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
				
				for (int i = 0; i < subList.size(); i++) {
					stmt.setString(i + 1, subList.get(i));
				}
				List<BpsmtPersonContact> ents = new NtsResultSet(stmt.executeQuery()).getList(rec -> {
					BpsmtPersonContact ent = new BpsmtPersonContact();
					ent.bpsmtPersonContactPK = new BpsmtPersonContactPK(rec.getString("PID"));
					ent.cellPhoneNumber = rec.getString("CELL_PHONE_NO");
					ent.mailAdress = rec.getString("MAIL_ADDRESS");
					ent.mobileMailAdress = rec.getString("MOBILE_MAIL_ADDRESS");
					ent.memo1 = rec.getString("MEMO1");
					ent.contactName1 = rec.getString("CONTACT_NAME_1");
					ent.phoneNo1 = rec.getString("PHONE_NO_1");
					ent.memo2 = rec.getString("MEMO2");
					ent.contactName2 = rec.getString("CONTACT_NAME_2");
					ent.phoneNo2 = rec.getString("PHONE_NO_2");
					return ent;
				});
				entities.addAll(ents);
				
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});
		
		return entities.stream().map(ent -> toDomain(ent)).collect(Collectors.toList());
	}

	@Override
	public void addAll(List<PersonContact> domains) {
		String INS_SQL = "INSERT INTO BPSMT_PERSON_CONTACT (INS_DATE, INS_CCD , INS_SCD , INS_PG,"
				+ " UPD_DATE , UPD_CCD , UPD_SCD , UPD_PG," 
				+ " PID, CELL_PHONE_NO, MAIL_ADDRESS, MOBILE_MAIL_ADDRESS,"
				+ " MEMO1, CONTACT_NAME_1, PHONE_NO_1,"
				+ " MEMO2, CONTACT_NAME_2, PHONE_NO_2)"
				+ " VALUES (INS_DATE_VAL, INS_CCD_VAL, INS_SCD_VAL, INS_PG_VAL,"
				+ " UPD_DATE_VAL, UPD_CCD_VAL, UPD_SCD_VAL, UPD_PG_VAL,"
				+ " PID_VAL, CELL_VAL, MAIL_VAL, MOBILE_VAL, "
				+ " M1_VAL, CONTACT_1_VAL, PHONE_1_VAL,"
				+ " M2_VAL, CONTACT_2_VAL, PHONE_2_VAL); ";
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
			
			sql = sql.replace("PID_VAL", "'" + c.getPersonId() + "'");
			sql = sql.replace("CELL_VAL", c.getCellPhoneNumber().isPresent() == true? "'"+c.getCellPhoneNumber().get().v()+"'" :"null");
			sql = sql.replace("MAIL_VAL", c.getMailAdress().isPresent() == true? "'" + c.getMailAdress().get().v()+ "'": "null");
			sql = sql.replace("MOBILE_VAL", c.getMobileMailAdress().isPresent() == true? "'" + c.getMobileMailAdress().get().v() + "'": "null");
			
			Optional<EmergencyContact> emergencyContact1 = c.getEmergencyContact1();
			
			if(emergencyContact1.isPresent()) {
				sql = sql.replace("M1_VAL", emergencyContact1.get().getMemo().isPresent() == true? "'" +  emergencyContact1.get().getMemo().get().v() + "'": "null");
				sql = sql.replace("CONTACT_1_VAL", emergencyContact1.get().getContactName().isPresent() == true? "'" +  emergencyContact1.get().getContactName().get().v() + "'": "null");
				sql = sql.replace("PHONE_1_VAL", emergencyContact1.get().getPhoneNumber().isPresent() == true? "'" +  emergencyContact1.get().getPhoneNumber().get().v() + "'": "null");
			}else {
				sql = sql.replace("M1_VAL", "null");
				sql = sql.replace("CONTACT_1_VAL", "null");
				sql = sql.replace("PHONE_1_VAL", "null");
			}
			
			Optional<EmergencyContact> emergencyContact2 = c.getEmergencyContact2();
			
			if(emergencyContact2.isPresent()) {
				sql = sql.replace("M2_VAL", emergencyContact2.get().getMemo().isPresent() == true? "'" +  emergencyContact2.get().getMemo().get().v() + "'": "null");
				sql = sql.replace("CONTACT_2_VAL", emergencyContact2.get().getContactName().isPresent() == true? "'" +  emergencyContact2.get().getContactName().get().v() + "'": "null");
				sql = sql.replace("PHONE_2_VAL", emergencyContact2.get().getPhoneNumber().isPresent() == true? "'" +  emergencyContact2.get().getPhoneNumber().get().v() + "'": "null");
			}else {
				sql = sql.replace("M2_VAL", "null");
				sql = sql.replace("CONTACT_2_VAL", "null");
				sql = sql.replace("PHONE_2_VAL", "null");
			}
			
			sb.append(sql);
		});
		
		int records = this.getEntityManager().createNativeQuery(sb.toString()).executeUpdate();
		System.out.println(records);
		
	}

	@Override
	public void updateAll(List<PersonContact> domains) {
		String UP_SQL = "UPDATE BPSMT_PERSON_CONTACT SET UPD_DATE = UPD_DATE_VAL, UPD_CCD = UPD_CCD_VAL, UPD_SCD = UPD_SCD_VAL, UPD_PG = UPD_PG_VAL,"
				+ " CELL_PHONE_NO = CELL_VAL , MAIL_ADDRESS = MAIL_VAL, MOBILE_MAIL_ADDRESS = MOBILE_VAL, "
				+ " MEMO1 = M1_VAL, CONTACT_NAME_1 = CONTACT_1_VAL, PHONE_NO_1 = PHONE_1_VAL,"
				+ " MEMO2 = M2_VAL, CONTACT_NAME_2 = CONTACT_2_VAL, PHONE_NO_2 = PHONE_2_VAL"
				+ " WHERE PID = PID_VAL;";
		String updCcd = AppContexts.user().companyCode();
		String updScd = AppContexts.user().employeeCode();
		String updPg = AppContexts.programId();
		
		StringBuilder sb = new StringBuilder();
		domains.stream().forEach(c ->{
			String sql = UP_SQL;
			sql = UP_SQL.replace("UPD_DATE_VAL", "'" + GeneralDateTime.now() +"'");
			sql = sql.replace("UPD_CCD_VAL", "'" + updCcd +"'");
			sql = sql.replace("UPD_SCD_VAL", "'" + updScd +"'");
			sql = sql.replace("UPD_PG_VAL", "'" + updPg +"'");
			
			sql = sql.replace("PID_VAL", "'" + c.getPersonId() + "'");
			sql = sql.replace("CELL_VAL", c.getCellPhoneNumber().isPresent() == true? "'"+c.getCellPhoneNumber().get().v()+"'" :"null");
			sql = sql.replace("MAIL_VAL", c.getMailAdress().isPresent() == true? "'" + c.getMailAdress().get().v()+ "'": "null");
			sql = sql.replace("MOBILE_VAL", c.getMobileMailAdress().isPresent() == true? "'" + c.getMobileMailAdress().get().v() + "'": "null");
			
			Optional<EmergencyContact> emergencyContact1 = c.getEmergencyContact1();
			
			if(emergencyContact1.isPresent()) {
				sql = sql.replace("M1_VAL", emergencyContact1.get().getMemo().isPresent() == true? "'" +  emergencyContact1.get().getMemo().get().v() + "'": "null");
				sql = sql.replace("CONTACT_1_VAL", emergencyContact1.get().getContactName().isPresent() == true? "'" +  emergencyContact1.get().getContactName().get().v() + "'": "null");
				sql = sql.replace("PHONE_1_VAL", emergencyContact1.get().getPhoneNumber().isPresent() == true? "'" +  emergencyContact1.get().getPhoneNumber().get().v() + "'": "null");
			}else {
				sql = sql.replace("M1_VAL", "null");
				sql = sql.replace("CONTACT_1_VAL", "null");
				sql = sql.replace("PHONE_1_VAL", "null");
			}
			
			Optional<EmergencyContact> emergencyContact2 = c.getEmergencyContact2();
			
			if(emergencyContact2.isPresent()) {
				sql = sql.replace("M2_VAL", emergencyContact2.get().getMemo().isPresent() == true? "'" +  emergencyContact2.get().getMemo().get().v() + "'": "null");
				sql = sql.replace("CONTACT_2_VAL", emergencyContact2.get().getContactName().isPresent() == true? "'" +  emergencyContact2.get().getContactName().get().v() + "'": "null");
				sql = sql.replace("PHONE_2_VAL", emergencyContact2.get().getPhoneNumber().isPresent() == true? "'" +  emergencyContact2.get().getPhoneNumber().get().v() + "'": "null");
			}else {
				sql = sql.replace("M2_VAL", "null");
				sql = sql.replace("CONTACT_2_VAL", "null");
				sql = sql.replace("PHONE_2_VAL", "null");
			}
			sb.append(sql);
		});
		int  records = this.getEntityManager().createNativeQuery(sb.toString()).executeUpdate();
		System.out.println(records);
		
	}

}
