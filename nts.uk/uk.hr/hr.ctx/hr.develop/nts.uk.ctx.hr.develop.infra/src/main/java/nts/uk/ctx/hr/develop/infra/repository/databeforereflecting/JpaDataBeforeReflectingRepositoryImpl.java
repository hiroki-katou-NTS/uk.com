package nts.uk.ctx.hr.develop.infra.repository.databeforereflecting;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.text.StringUtil;
import nts.uk.ctx.hr.develop.infra.entity.databeforereflecting.PreReflecData;
import nts.uk.ctx.hr.develop.infra.entity.databeforereflecting.PreReflecDataPk;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.common.DataBeforeReflectingPerInfo;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.common.DataBeforeReflectingRepository;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.common.OnHoldFlag;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.common.RequestFlag;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.common.Status;

@Stateless
public class JpaDataBeforeReflectingRepositoryImpl extends JpaRepository implements DataBeforeReflectingRepository {

	public static final String SELECT_BY_SID = "SELECT c FROM PreReflecData c WHERE c.sId IN :sIds";

	public static final String SELECT_BY_WORKID_SIDS = "SELECT c FROM PreReflecData c WHERE c.workId = :workId and c.sId IN :listSid";

	public static final String SELECT_BY_WORKID_CID = "SELECT c FROM PreReflecData c "
			+ " WHERE 1=1 and c.workId = :workId " + " and c.companyId = :cid "
			+ " and c.stattus != 3 ORDER BY date_01 ASC;";

	public static final String DELETE_BY_HIST_ID = "DELETE FROM PreReflecData c WHERE c.historyId = :histId";

	@Override
	public void addData(List<DataBeforeReflectingPerInfo> listDomain) {
		if (listDomain.isEmpty()) {
			return;
		}
		
		List<String> sIds = listDomain.stream().map(i -> i.sId).collect(Collectors.toList());
		
		List<PreReflecData> entitys = this.queryProxy()
				.query(SELECT_BY_SID, PreReflecData.class)
				.setParameter("sIds", sIds).getList();
		
		if(!entitys.isEmpty()) {
			throw new BusinessException("Msg_1992");
		}
		
		List<PreReflecData> listEntity = listDomain.stream().map(i -> {
			PreReflecData entity = new PreReflecData();
			return toEntity(i, entity);
		}).collect(Collectors.toList());

		this.commandProxy().insertAll(listEntity);
		this.getEntityManager().flush();

	}
	
	@Override
	public void addDataNoCheckSid(List<DataBeforeReflectingPerInfo> listDomain) {
		
		if (listDomain.isEmpty()) {
			return;
		}
		
		List<PreReflecData> listEntity = listDomain.stream().map(i -> {
			PreReflecData entity = new PreReflecData();
			return toEntity(i, entity);
		}).collect(Collectors.toList());

		this.commandProxy().insertAll(listEntity);
		this.getEntityManager().flush();

	}

	@Override
	public void updateData(List<DataBeforeReflectingPerInfo> listDomain) {
		if (listDomain.isEmpty()) {
			return;
		}

		listDomain.forEach(dm -> {
			String histId = dm.getHistoryId();
			Optional<PreReflecData> entityOpt = this.queryProxy().find(new PreReflecDataPk(histId), PreReflecData.class);
			if (entityOpt.isPresent()) {
				// Update entity
				PreReflecData entity = entityOpt.get();
				updateEntity(dm, entity);
				this.commandProxy().update(entity);
				System.out.println("Update entity Success - " + listDomain.indexOf(dm));

			} else {
				System.out.println("entity does not exist");
			}
		});
	}

	@Override
	public void deleteData(List<DataBeforeReflectingPerInfo> listDomain) {
		if (listDomain.isEmpty()) {
			return;
		}

		listDomain.forEach(dm -> {
			String histId = dm.getHistoryId();
			Optional<PreReflecData> entityOpt = this.queryProxy().find(new PreReflecDataPk(histId), PreReflecData.class);
			if (entityOpt.isPresent()) {
				PreReflecDataPk pk = new PreReflecDataPk(dm.getHistoryId());
				this.commandProxy().remove(PreReflecData.class, pk);
				System.out.println("Delete entity Success - " + listDomain.indexOf(dm));

			} else {
				System.out.println("entity does not exist");
			}
		});
	}

	@Override
	public void deleteDataByHistId(String histId) {
		Optional<PreReflecData> entityOpt = this.queryProxy().find(new PreReflecDataPk(histId), PreReflecData.class);
		if (entityOpt.isPresent()) {
			PreReflecDataPk pk = new PreReflecDataPk(histId);
			this.commandProxy().remove(PreReflecData.class, pk);
			System.out.println("Delete entity Success ");

		} else {
			System.out.println("entity does not exist");
		}
	}

	@Override
	public List<DataBeforeReflectingPerInfo> getData(int workId, List<String> listSid) {
		if (listSid.isEmpty()) {
			return new ArrayList<>();
		}

		List<DataBeforeReflectingPerInfo> listDomain = this.queryProxy()
				.query(SELECT_BY_WORKID_SIDS, PreReflecData.class).setParameter("workId", workId)
				.setParameter("listSid", listSid).getList(dm -> toDomain(dm));
		return listDomain;
	}

	@Override
	public List<DataBeforeReflectingPerInfo> getData(String cid, Integer workId, List<String> listSid,
			Optional<Boolean> includReflected, Optional<String> sortByColumnName, Optional<String> orderType) {

		String sql = "SELECT c FROM PreReflecData c WHERE 1=1 ";

		if (!StringUtil.isNullOrEmpty(cid, true)) {
			sql = sql + " and c.companyId  = '" + cid + "'";
		}

		if (workId != null) {
			sql = sql + " and c.workId  = '" + workId + "'";
		}

		if (!listSid.isEmpty()) {
			String inClause = "";
			if (listSid.size() ==  1) {
				inClause = inClause + "('" + listSid.get(0) + "')"  ;
			}else{
				for (int i = 0; i < listSid.size(); i++) {
					if (i == 0) {
						inClause = inClause + "('" + listSid.get(0) + "'"  ;
					} else if (i == listSid.size() - 1) {
						inClause = inClause + ",'" + listSid.get(i) + "')"  ;
					} else {
						inClause = inClause + ",'" + listSid.get(i) + "'"  ;
					}
				}
			}
			
			sql = sql + " and c.sId IN " + inClause ;
		}

		if ((!includReflected.isPresent()) || (includReflected.isPresent() && includReflected.get() == false)) {
			sql = sql + " and c.stattus != 3 ";
		}

		if (sortByColumnName.isPresent()) {
			sql = sql + " ORDER BY c." + sortByColumnName.get() ;
		}

		if (sortByColumnName.isPresent() && orderType.isPresent()) {
			sql = sql + " " + orderType.get() ;
		}

		EntityManager em = this.getEntityManager();
		TypedQuery<PreReflecData> query = em.createQuery(sql, PreReflecData.class);

		List<PreReflecData> listEntity = query.getResultList();

		List<DataBeforeReflectingPerInfo> listDomain = listEntity.stream().map(dm -> toDomain(dm))
				.collect(Collectors.toList());

		return listDomain;
	}
	
	private void updateEntity(DataBeforeReflectingPerInfo domain, PreReflecData entity) {
		
		entity.stattus = domain.stattus.value;
		
		entity.releaseDate = domain.releaseDate;
		entity.onHoldFlag = domain.onHoldFlag.value;
		entity.registerDate = domain.registerDate;
		
		entity.select_id_01 = domain.select_id_01; // retirementCategory
		entity.select_id_02 = domain.select_id_02; // retirementCategory
		entity.select_id_03 = domain.select_id_03; // retirementCategory
		entity.select_id_04 = domain.select_id_04; // retirementCategory
		entity.select_id_05 = domain.select_id_05; // retirementCategory
		entity.select_id_06 = domain.select_id_06; // retirementCategory
		entity.select_id_07 = domain.select_id_07; // retirementCategory
		entity.select_id_08 = domain.select_id_08; // retirementCategory
		entity.select_id_09 = domain.select_id_09; // retirementCategory
		entity.select_id_10 = domain.select_id_10; // retirementCategory
		
		entity.select_code_01 = domain.select_code_01; // retirementCategory
		entity.select_code_02 = domain.select_code_02; // retirementCategory
		entity.select_code_03 = domain.select_code_03; // retirementReasonCtgCode1
		entity.select_code_04 = domain.select_code_04; // retirementReasonCtgCode1
		entity.select_code_05 = domain.select_code_05; // retirementReasonCtgCode1
		entity.select_code_06 = domain.select_code_06; // retirementReasonCtgCode1
		entity.select_code_07 = domain.select_code_07; // retirementReasonCtgCode1
		entity.select_code_08 = domain.select_code_08; // retirementReasonCtgCode1
		entity.select_code_09 = domain.select_code_09; // retirementReasonCtgCode1
		entity.select_code_10 = domain.select_code_10; // retirementReasonCtgCode1
		
		entity.select_name_01 = domain.select_name_01; // retirementReasonCtgName1
		entity.select_name_02 = domain.select_name_02; // retirementReasonCtgName2
		entity.select_name_03 = domain.select_name_03; // retirementReasonCtgName2
		entity.select_name_04 = domain.select_name_04; // retirementReasonCtgName1
		entity.select_name_05 = domain.select_name_05; // retirementReasonCtgName2
		entity.select_name_06 = domain.select_name_06; // retirementReasonCtgName2
		entity.select_name_07 = domain.select_name_07; // retirementReasonCtgName1
		entity.select_name_08 = domain.select_name_08; // retirementReasonCtgName2
		entity.select_name_09 = domain.select_name_09; // retirementReasonCtgName2
		entity.select_name_10 = domain.select_name_10; // retirementReasonCtgName2
		
		entity.str_01 = domain.str_01 == null ? null : domain.str_01.toString(); // retirementRemarks
		entity.str_02 = domain.str_02 == null ? null : domain.str_02.toString(); // retirementReasonVal
		entity.str_03 = domain.str_03 == null ? null : domain.str_03.toString(); // retirementRemarks
		entity.str_04 = domain.str_04 == null ? null : domain.str_04.toString(); // retirementReasonVal
		entity.str_05 = domain.str_05 == null ? null : domain.str_05.toString(); // retirementRemarks
		entity.str_06 = domain.str_06 == null ? null : domain.str_06.toString(); // retirementReasonVal
		entity.str_07 = domain.str_07 == null ? null : domain.str_07.toString(); // retirementRemarks
		entity.str_08 = domain.str_08 == null ? null : domain.str_08.toString(); // retirementReasonVal
		entity.str_09 = domain.str_09 == null ? null : domain.str_09.toString(); // retirementRemarks
		entity.str_10 = domain.str_10 == null ? null : domain.str_10.toString(); // retirementReasonVal
		
		entity.date_01 = domain.date_01; // retirementDate
		entity.date_02 = domain.date_02; // dismissalNoticeDate
		entity.date_03 = domain.date_03; // dismissalNoticeDateAllow
		entity.date_04 = domain.date_04; // dismissalNoticeDate
		entity.date_05 = domain.date_05; // dismissalNoticeDateAllow
		entity.date_06 = domain.date_06; // dismissalNoticeDate
		entity.date_07 = domain.date_07; // dismissalNoticeDateAllow
		entity.date_08 = domain.date_08; // dismissalNoticeDate
		entity.date_09 = domain.date_09; // dismissalNoticeDateAllow
		entity.date_10 = domain.date_10; // dismissalNoticeDate
		
		entity.int_01 = domain.int_01;
		entity.int_02 = domain.int_02;
		entity.int_03 = domain.int_03;
		entity.int_04 = domain.int_04;
		entity.int_05 = domain.int_05;
		entity.int_06 = domain.int_06;
		entity.int_07 = domain.int_07;
		entity.int_08 = domain.int_08;
		entity.int_09 = domain.int_09;
		entity.int_10 = domain.int_10;
	}

	private PreReflecData toEntity(DataBeforeReflectingPerInfo domain, PreReflecData entity) {
		entity.preReflecDataPk = new PreReflecDataPk(domain.historyId);
		entity.contractCode = domain.contractCode;
		entity.companyId = domain.companyId;
		entity.companyCode = domain.companyCode;
		entity.pId = domain.pId;
		entity.sId = domain.sId;
		entity.scd = domain.scd;
		entity.workId = domain.workId;
		entity.personName = domain.personName;
		entity.workName = domain.workName;
		entity.requestFlag = domain.requestFlag.value;
		entity.registerDate = domain.registerDate;
		entity.releaseDate = domain.releaseDate;
		entity.onHoldFlag = domain.onHoldFlag.value;
		entity.stattus = domain.stattus.value;
		entity.histId_Refer = domain.histId_Refer;
		entity.date_01 = domain.date_01;
		entity.date_02 = domain.date_02;
		entity.date_03 = domain.date_03;
		entity.date_04 = domain.date_04;
		entity.date_05 = domain.date_05;
		entity.date_06 = domain.date_06;
		entity.date_07 = domain.date_07;
		entity.date_08 = domain.date_08;
		entity.date_09 = domain.date_09;
		entity.date_10 = domain.date_10;
		entity.int_01 = domain.int_01;
		entity.int_02 = domain.int_02;
		entity.int_03 = domain.int_03;
		entity.int_04 = domain.int_04;
		entity.int_05 = domain.int_05;
		entity.int_06 = domain.int_06;
		entity.int_07 = domain.int_07;
		entity.int_08 = domain.int_08;
		entity.int_09 = domain.int_09;
		entity.int_10 = domain.int_10;
		entity.int_11 = domain.int_11;
		entity.int_12 = domain.int_12;
		entity.int_13 = domain.int_13;
		entity.int_14 = domain.int_14;
		entity.int_15 = domain.int_15;
		entity.int_16 = domain.int_16;
		entity.int_17 = domain.int_17;
		entity.int_18 = domain.int_18;
		entity.int_19 = domain.int_19;
		entity.int_20 = domain.int_20;

		entity.num_01 = domain.num_01;
		entity.num_02 = domain.num_02;
		entity.num_03 = domain.num_03;
		entity.num_04 = domain.num_04;
		entity.num_05 = domain.num_05;
		entity.num_06 = domain.num_06;
		entity.num_07 = domain.num_07;
		entity.num_08 = domain.num_08;
		entity.num_09 = domain.num_09;
		entity.num_10 = domain.num_10;
		entity.num_11 = domain.num_11;
		entity.num_12 = domain.num_12;
		entity.num_13 = domain.num_13;
		entity.num_14 = domain.num_14;
		entity.num_15 = domain.num_15;
		entity.num_16 = domain.num_16;
		entity.num_17 = domain.num_17;
		entity.num_18 = domain.num_18;
		entity.num_19 = domain.num_19;
		entity.num_20 = domain.num_20;

		entity.select_code_01 = domain.select_code_01;
		entity.select_code_02 = domain.select_code_02;
		entity.select_code_03 = domain.select_code_03;
		entity.select_code_04 = domain.select_code_04;
		entity.select_code_05 = domain.select_code_05;
		entity.select_code_06 = domain.select_code_06;
		entity.select_code_07 = domain.select_code_07;
		entity.select_code_08 = domain.select_code_08;
		entity.select_code_09 = domain.select_code_09;
		entity.select_code_10 = domain.select_code_10;
		entity.select_id_01 = domain.select_id_01;
		entity.select_id_02 = domain.select_id_02;
		entity.select_id_03 = domain.select_id_03;
		entity.select_id_04 = domain.select_id_04;
		entity.select_id_05 = domain.select_id_05;
		entity.select_id_06 = domain.select_id_06;
		entity.select_id_07 = domain.select_id_07;
		entity.select_id_08 = domain.select_id_08;
		entity.select_id_09 = domain.select_id_09;
		entity.select_id_10 = domain.select_id_10;
		entity.select_name_01 = domain.select_name_01;
		entity.select_name_02 = domain.select_name_02;
		entity.select_name_03 = domain.select_name_03;
		entity.select_name_04 = domain.select_name_04;
		entity.select_name_05 = domain.select_name_05;
		entity.select_name_06 = domain.select_name_06;
		entity.select_name_07 = domain.select_name_07;
		entity.select_name_08 = domain.select_name_08;
		entity.select_name_09 = domain.select_name_09;
		entity.select_name_10 = domain.select_name_10;
		entity.str_01 = domain.str_01 == null ? null : domain.str_01.toString();
		entity.str_02 = domain.str_02 == null ? null : domain.str_02.toString();
		entity.str_03 = domain.str_03 == null ? null : domain.str_03.toString();
		entity.str_04 = domain.str_04 == null ? null : domain.str_04.toString();
		entity.str_05 = domain.str_05 == null ? null : domain.str_05.toString();
		entity.str_06 = domain.str_06 == null ? null : domain.str_06.toString();
		entity.str_07 = domain.str_07 == null ? null : domain.str_07.toString();
		entity.str_08 = domain.str_08 == null ? null : domain.str_08.toString();
		entity.str_09 = domain.str_09 == null ? null : domain.str_09.toString();
		entity.str_10 = domain.str_10;
		return entity;
	}

	private DataBeforeReflectingPerInfo toDomain(PreReflecData entity) {
		return DataBeforeReflectingPerInfo.createFromJavaType(entity.preReflecDataPk.getHistoryId(),
				entity.contractCode, entity.companyId, entity.companyCode, entity.pId, entity.sId, entity.scd,
				entity.workId, entity.personName, entity.workName,
				EnumAdaptor.valueOf(entity.requestFlag, RequestFlag.class), entity.registerDate, entity.releaseDate,
				EnumAdaptor.valueOf(entity.onHoldFlag, OnHoldFlag.class),
				EnumAdaptor.valueOf(entity.stattus, Status.class), entity.histId_Refer, entity.date_01, entity.date_02,
				entity.date_03, entity.date_04, entity.date_05, entity.date_06, entity.date_07, entity.date_08,
				entity.date_09, entity.date_10, entity.int_01, entity.int_02, entity.int_03, entity.int_04,
				entity.int_05, entity.int_06, entity.int_07, entity.int_08, entity.int_09, entity.int_10, entity.int_11,
				entity.int_12, entity.int_13, entity.int_14, entity.int_15, entity.int_16, entity.int_17, entity.int_18,
				entity.int_19, entity.int_20, entity.num_01, entity.num_02, entity.num_03, entity.num_04, entity.num_05,
				entity.num_06, entity.num_07, entity.num_08, entity.num_09, entity.num_10, entity.num_11, entity.num_12,
				entity.num_13, entity.num_14, entity.num_15, entity.num_16, entity.num_17, entity.num_18, entity.num_19,
				entity.num_20, entity.select_code_01, entity.select_code_02, entity.select_code_03,
				entity.select_code_04, entity.select_code_05, entity.select_code_06, entity.select_code_07,
				entity.select_code_08, entity.select_code_09, entity.select_code_10, entity.select_id_01,
				entity.select_id_02, entity.select_id_03, entity.select_id_04, entity.select_id_05, entity.select_id_06,
				entity.select_id_07, entity.select_id_08, entity.select_id_09, entity.select_id_10,
				entity.select_name_01, entity.select_name_02, entity.select_name_03, entity.select_name_04,
				entity.select_name_05, entity.select_name_06, entity.select_name_07, entity.select_name_08,
				entity.select_name_09, entity.select_name_10, entity.str_01, entity.str_02, entity.str_03,
				entity.str_04, entity.str_05, entity.str_06, entity.str_07, entity.str_08, entity.str_09,
				entity.str_10);
	}

}
