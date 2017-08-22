package nts.uk.ctx.at.shared.infra.repository.specialholiday;

import java.util.List;
import java.util.stream.Collectors;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.specialholiday.grantdate.GrantDateCom;
import nts.uk.ctx.at.shared.dom.specialholiday.grantdate.GrantDateSet;
import nts.uk.ctx.at.shared.dom.specialholiday.grantday.GrantRegular;
import nts.uk.ctx.at.shared.dom.specialholiday.grantday.GrantRegularRepository;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.KshstGrantDateCom;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.KshstGrantDateComPK;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.KshstGrantDateSet;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.KshstGrantDateSetPK;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.KshstGrantRegular;

public class JpaGrantRegularRepository extends JpaRepository implements GrantRegularRepository {
	private static final String SELECT_ALL;

	private static final String SELECT_ALL_COM;


	static {

		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KshstGrantRegular e");
		builderString.append(" WHERE e.kshstGrantRegularPK.companyId = :companyId");
		builderString.append(" WHERE e.kshstGrantRegularPK.specialHolidayCode = :specialHolidayCode");
		SELECT_ALL = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KshstGrantDateCom e");
		builderString.append(" WHERE e.kshstGrantDateComPK.companyId = :companyId");
		builderString.append(" WHERE e.kshstGrantDateComPK.specialHolidayCode = :specialHolidayCode");
		SELECT_ALL_COM = builderString.toString();

	}

	/**
	 * Convert to Domain Grant Regular
	 * 
	 * @param kshstGrantRegular
	 * @return
	 */
	private GrantRegular convertToDomain(KshstGrantRegular kshstGrantRegular) {
		GrantRegular grantRegular = GrantRegular.createFromJavaType(kshstGrantRegular.kshstGrantRegularPK.companyId,
				kshstGrantRegular.kshstGrantRegularPK.specialHolidayCode, kshstGrantRegular.grantStartDate,
				kshstGrantRegular.months, kshstGrantRegular.years, kshstGrantRegular.grantRegularMethod);
		return grantRegular;
	}

	/**
	 * Convert to Domain Grant Date Company
	 * 
	 * @param kshstGrantDateCom
	 * @return
	 */
	private GrantDateCom convertToDomainCom(KshstGrantDateCom kshstGrantDateCom) {
		List<GrantDateSet> grantDateSets = kshstGrantDateCom.grantDateSets.stream().map(x-> convertToDomainSet(x)).collect(Collectors.toList());
		GrantDateCom grantDateCom = GrantDateCom.createFromJavaType(kshstGrantDateCom.kshstGrantDateComPK.companyId,
				kshstGrantDateCom.kshstGrantDateComPK.specialHolidayCode, kshstGrantDateCom.grantDateAtr,
				kshstGrantDateCom.grantDate, grantDateSets);
		return grantDateCom;
	}

	/**
	 * Convert to Domain Grant Date Setting
	 * 
	 * @param kshstGrantDateCom
	 * @return
	 */
	private GrantDateSet convertToDomainSet(KshstGrantDateSet kshstGrantDateSet) {
		GrantDateSet grantDateSet = GrantDateSet.createFromJavaType(kshstGrantDateSet.kshstGrantDateSetPK.companyId,
				kshstGrantDateSet.kshstGrantDateSetPK.specialHolidayCode,
				kshstGrantDateSet.kshstGrantDateSetPK.grantDateType, kshstGrantDateSet.grantDateM,
				kshstGrantDateSet.grantDateY);
		return grantDateSet;
	}

	/**
	 * Convert to Database Type Grant Date Company
	 * 
	 * @param grantDateCom
	 * @return
	 */
	private KshstGrantDateCom convertToDbTypeCom(GrantDateCom grantDateCom) {
		KshstGrantDateCom kshstGrantDateCom = new KshstGrantDateCom();
		KshstGrantDateComPK kshstGrantDateComPK = new KshstGrantDateComPK(grantDateCom.getCompanyId(),
				grantDateCom.getSpecialHolidayCode().v());
		kshstGrantDateCom.grantDateAtr = grantDateCom.getGrantDateAtr().value;
		kshstGrantDateCom.grantDate = grantDateCom.getGrantDate().v();
		kshstGrantDateCom.kshstGrantDateComPK = kshstGrantDateComPK;

		List<KshstGrantDateSet> dateSetList = grantDateCom.getGrantDateSets().stream().map(x -> convertToDbTypeSet(x))
				.collect(Collectors.toList());
		kshstGrantDateCom.grantDateSets = dateSetList;

		return kshstGrantDateCom;
	}

	/**
	 * Convert to Database Type Grant Date Setting
	 * 
	 * @param grantDateSet
	 * @return
	 */
	private KshstGrantDateSet convertToDbTypeSet(GrantDateSet grantDateSet) {
		KshstGrantDateSet kshstGrantDateSet = new KshstGrantDateSet();
		KshstGrantDateSetPK kshstGrantDateSetPK = new KshstGrantDateSetPK(grantDateSet.getCompanyId(),
				grantDateSet.getSpecialHolidayCode().toString(), grantDateSet.getGrantDateType().value);
		kshstGrantDateSet.grantDateM = grantDateSet.getGrantDateMonth().v();
		kshstGrantDateSet.grantDateY = grantDateSet.getGrantDateYear().v();
		kshstGrantDateSet.kshstGrantDateSetPK = kshstGrantDateSetPK;
		return kshstGrantDateSet;
	}

	/**
	 * Find all Grant Regular by CompanyId & SpecialHolidayCode
	 */
	@Override
	public List<GrantRegular> findAll(String companyId, String specialHolidayCode) {
		return this.queryProxy().query(SELECT_ALL, KshstGrantRegular.class).setParameter("companyId", companyId)
				.setParameter("specialHolidayCode", specialHolidayCode).getList(c -> convertToDomain(c));
	}

	/**
	 * Find all Grant Date Company
	 */
	@Override
	public List<GrantDateCom> findAllCom(String companyId, String specialHolidayCode) {
		return this.queryProxy().query(SELECT_ALL_COM, KshstGrantDateCom.class).setParameter("companyId", companyId)
				.setParameter("specialHolidayCode", specialHolidayCode).getList(c -> convertToDomainCom(c));
	}

	/**
	 * Add Grant Date Company
	 */
	@Override
	public void add(GrantDateCom grantDateCom) {
		this.commandProxy().insert(convertToDbTypeCom(grantDateCom));
	}
}
