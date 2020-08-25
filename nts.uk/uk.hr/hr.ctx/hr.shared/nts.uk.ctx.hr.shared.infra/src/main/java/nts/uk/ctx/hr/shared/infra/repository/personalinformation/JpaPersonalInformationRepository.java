package nts.uk.ctx.hr.shared.infra.repository.personalinformation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.PersonalInformation;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.PersonalInformationRepository;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.personalInfo.GetPersonInfoHRInput;
import nts.uk.ctx.hr.shared.infra.entity.personalinformation.PpedtData;

/**
 * 
 * @author chungnt
 *
 */
@Stateless
public class JpaPersonalInformationRepository extends JpaRepository implements PersonalInformationRepository {
	private PpedtData entity = new PpedtData();
	
	public static final String GET_DISPATCHED_INFORMATION = "SELECT a FROM PpedtData a WHERE a.contractCd = :contractCd"
			+ " AND a.cId = :cId" + " AND a.workId = :workId"
			+ " AND a.startDate <= baseDate AND s.endDate >= baseDate ";
	
	public static final String GET_DISPATCHED_INFORMATION_BY_STR10 = "SELECT a FROM PpedtData a WHERE a.contractCd = :contractCd"
			+ " AND a.str10 = :cId" + " AND a.workId = :workId"
			+ " AND a.startDate <= baseDate AND s.endDate >= baseDate ";
	
	public static final String GET_PERSONAL_INFO_BY_CID_SID_WORKID_DATE = "SELECT a FROM PpedtData a WHERE a.cId = :cId"
			+ " AND a.sid IN :sids" + " AND a.workId = :workId"
			+ " AND a.startDate <= baseDate AND s.endDate >= baseDate ";
	
	public static final String GET_PERSONAL_INFO_BY_QUALIFICATION_ID = "SELECT a FROM PpedtData a WHERE a.cId = :cId"
			+ " AND a.selectId01 IN :qualificationIds" + " AND a.workId = :workId"
			+ " AND a.startDate <= baseDate AND s.endDate >= baseDate ";
	

	// Insert
	@Override
	public void insert(PersonalInformation domain) {
		this.commandProxy().insert(entity.toEntity(domain));
	}

	// Update
	@Override
	public void update(PersonalInformation domain) {
		Optional<PpedtData> personal = this.queryProxy().find(domain.getHistId(), PpedtData.class);

		if (personal.isPresent()) {
			this.commandProxy().update(entity.toEntity(domain));
		}
	}

	// Delete
	@Override
	public void delete(String hisId) {
		Optional<PpedtData> personal = this.queryProxy().find(hisId, PpedtData.class);
		if (personal.isPresent()) {
			this.commandProxy().remove(personal.get());
		}
	}

	/*
	 * input.getColumnName() <= phải truyền đúng tên cột của entity PpedtData
	 */
	@Override
	public List<PersonalInformation> get(GetPersonInfoHRInput input) {
		String GET_PERSONALINFO = "SELECT a FROM PpedtData a WHERE a.cId = :cId AND a.workId = :workId";
		List<PpedtData> personalInformations = new ArrayList<>();

		if (input.getColumnName() != null && input.getTypeSort() != null) {

			if (input.getTypeSort().toUpperCase().equals("ASC") || input.getTypeSort().toUpperCase().equals("DESC")) {
				GET_PERSONALINFO = GET_PERSONALINFO
						.concat(" ORDER BY a." + input.getColumnName() + " " + input.getTypeSort());
			}
		}

		personalInformations = this.queryProxy().query(GET_PERSONALINFO, PpedtData.class)
				.setParameter("cId", input.getCompanyId()).setParameter("workId", Integer.parseInt(input.getBusinessId())).getList();

		if (input.getEmployeeId() != null) {
			List<PpedtData> ppdetData = new ArrayList<>();

			for (int i = 0; i < input.getEmployeeId().size(); i++) {

				for (int j = 0; j < personalInformations.size(); j++) {

					if (input.getEmployeeId().get(i).equals(personalInformations.get(j).getSid())) {
						ppdetData.add(personalInformations.get(j));
					}
				}
			}

			personalInformations = ppdetData;
		}

		if (input.getPersonalId() != null) {
			List<PpedtData> ppdetData = new ArrayList<>();

			for (int i = 0; i < input.getPersonalId().size(); i++) {

				for (int j = 0; j < personalInformations.size(); j++) {

					if (input.getPersonalId().get(i).equals(personalInformations.get(j).getPersonName())) {
						ppdetData.add(personalInformations.get(j));
					}
				}
			}

			personalInformations = ppdetData;
		}

		return personalInformations.stream().map(m -> m.toDomain()).collect(Collectors.toList());
	}

	@Override
	public List<PersonalInformation> getDispatchedInfos(String contractCd, String cId,
			GeneralDate baseDate) {

		List<PpedtData> personalInformations = this.queryProxy().query(GET_DISPATCHED_INFORMATION, PpedtData.class)
				.setParameter("contractCd", contractCd).setParameter("cId", cId).setParameter("workId", 6)
				.setParameter("baseDate", baseDate).getList();

		return personalInformations.stream().map(m -> m.toDomain()).collect(Collectors.toList());
	}

	@Override
	public List<PersonalInformation> getDispatchedInfoByStr10s(String contractCd, String cId,
			GeneralDate baseDate) {

		List<PpedtData> personalInformations = this.queryProxy().query(GET_DISPATCHED_INFORMATION_BY_STR10, PpedtData.class)
				.setParameter("contractCd", contractCd).setParameter("cId", cId).setParameter("workId", 6)
				.setParameter("baseDate", baseDate).getList();

		return personalInformations.stream().map(m -> m.toDomain()).collect(Collectors.toList());
	}

	@Override
	public List<PersonalInformation> getLstPersonInfoByCIdSIdsWorkId(String cId, List<String> sids, int workId,
			GeneralDate baseDate) {
		List<PpedtData> ppedtDatas = new ArrayList<>();

		CollectionUtil.split(sids, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			ppedtDatas.addAll(this.queryProxy().query(GET_PERSONAL_INFO_BY_CID_SID_WORKID_DATE,
					PpedtData.class)
				.setParameter("cId", cId)
				.setParameter("sids", subList)
				.setParameter("workId", workId)
				.setParameter("baseDate", baseDate).getList());
		});
		if (ppedtDatas.isEmpty())
			return Collections.emptyList();

		return ppedtDatas.stream().map(m -> m.toDomain()).collect(Collectors.toList());
	}

	@Override
	public List<PersonalInformation> getLstPersonInfoByQualificationIds(String cId, List<String> qualificationIds,
			int workId, GeneralDate baseDate) {
		return null;
	}
}