package nts.uk.ctx.exio.infra.repository.exo.outcnddetail;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetail;
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetailRepository;
import nts.uk.ctx.exio.infra.entity.exo.outcnddetail.OiomtOutCndDetail;
import nts.uk.ctx.exio.infra.entity.exo.outcnddetail.OiomtOutCndDetailPk;

@Stateless
public class JpaOutCndDetailRepository extends JpaRepository implements OutCndDetailRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiomtOutCndDetail f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.outCndDetailPk.cid =:cid AND  f.outCndDetailPk.conditionSettingCd =:conditionSettingCd ";

	@Override
	public List<OutCndDetail> getAllOutCndDetail() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, OiomtOutCndDetail.class)
				.getList(item -> toDomain(item));
	}

	@Override
	public Optional<OutCndDetail> getOutCndDetailById(String cid, String conditionSettingCd) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, OiomtOutCndDetail.class).setParameter("cid", cid)
				.setParameter("conditionSettingCd", conditionSettingCd).getSingle(c -> toDomain(c));
	}

	@Override
	public void add(OutCndDetail domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void update(OutCndDetail domain) {
		this.commandProxy().update(toEntity(domain));
	}

	@Override
	public void remove(String cid, String conditionSettingCd) {
	    Optional<OiomtOutCndDetail> outCndDetail = this.queryProxy().find(new OiomtOutCndDetailPk(cid, conditionSettingCd), OiomtOutCndDetail.class);
	    if (outCndDetail.isPresent()) {
	        this.commandProxy().remove(OiomtOutCndDetail.class, new OiomtOutCndDetailPk(cid, conditionSettingCd));
	        this.getEntityManager().flush();
	    }
	}

	public static OiomtOutCndDetail toEntity(OutCndDetail domain) {
		return new OiomtOutCndDetail(domain.getCid(), domain.getConditionSettingCd().v(),
				domain.getExterOutCdnSql().v(),domain.getListOutCndDetailItem());
	}

	public static OutCndDetail toDomain(OiomtOutCndDetail entity) {
		return new OutCndDetail(entity.outCndDetailPk.cid, entity.outCndDetailPk.conditionSettingCd,
				entity.exterOutCdnSql, entity.getListOutCndDetailItem());
	}
}
