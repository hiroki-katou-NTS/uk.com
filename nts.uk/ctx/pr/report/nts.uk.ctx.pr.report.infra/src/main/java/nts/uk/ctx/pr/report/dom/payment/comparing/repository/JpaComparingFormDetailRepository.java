package nts.uk.ctx.pr.report.dom.payment.comparing.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.report.dom.payment.comparing.ComparingFormDetail;
import nts.uk.ctx.pr.report.dom.payment.comparing.ComparingFormDetailRepository;
import nts.uk.ctx.pr.report.dom.payment.comparing.entity.QlsptPaycompFormDetail;
import nts.uk.ctx.pr.report.dom.payment.comparing.entity.QlsptPaycompFormDetailPK;

@Stateless
public class JpaComparingFormDetailRepository extends JpaRepository implements ComparingFormDetailRepository {

	private final String SELECT_COMPARING_FORM_DETAIL_BY_CATEGORY = "SELECT c FROM QlsptPaycompFormDetail c "
			+ "WHERE c.paycompFormDetailPK.companyCode = :ccd AND c.paycompFormDetailPK.formCode = :formCode "
			+ "AND c.paycompFormDetailPK.categoryATR = :categoryATR "
			+ "ORDER BY c.paycompFormDetailPK.categoryATR, c.dispOrder";

	private final String SELECT_A_COMPARING_FORM_DETAIL = "SELECT c FROM QlsptPaycompFormDetail c "
			+ "WHERE c.paycompFormDetailPK.companyCode = :ccd AND c.paycompFormDetailPK.formCode = :formCode "
			+ "AND c.paycompFormDetailPK.itemCode = :itemCode "
			+ "AND c.paycompFormDetailPK.categoryATR = :categoryATR "
			+ "ORDER BY c.paycompFormDetailPK.categoryATR, c.dispOrder";

	private final String SELECT_COMPARING_FORM_DETAIL_BY_FORMCODE = "SELECT c FROM QlsptPaycompFormDetail c "
			+ "WHERE c.paycompFormDetailPK.companyCode = :ccd AND c.paycompFormDetailPK.formCode = :formCode";

	// private final String DELETE_COMPARING_FORM_DETAIL = "Delete
	// QlsptPaycompFormDetail c "
	// + "WHERE c.paycompFormDetailPK.companyCode = :ccd AND
	// c.paycompFormDetailPK.formCode = :formCode";

	@Override
	public List<ComparingFormDetail> getComparingFormDetailByCategory_Atr(String companyCode, String formCode,
			int categoryATR) {
		return this.queryProxy().query(SELECT_COMPARING_FORM_DETAIL_BY_CATEGORY, QlsptPaycompFormDetail.class)
				.setParameter("ccd", companyCode).setParameter("formCode", formCode)
				.setParameter("categoryATR", categoryATR).getList(c -> convertToDomainFormDetail(c));

	}

	@Override
	public Optional<ComparingFormDetail> getComparingFormDetail(String companyCode, String formCode, String itemCode,
			int categoryAtr) {
		return this.queryProxy().query(SELECT_A_COMPARING_FORM_DETAIL, QlsptPaycompFormDetail.class)
				.setParameter("ccd", companyCode).setParameter("formCode", formCode).setParameter("itemCode", itemCode)
				.setParameter("categoryATR", categoryAtr).getSingle(c -> convertToDomainFormDetail(c));
	}

	@Override
	public void insertComparingFormDetail(List<ComparingFormDetail> comparingFormDetailList) {
		this.commandProxy().insertAll(comparingFormDetailList.stream()
				.map(s -> convertToEntityQlsptPaycompFormDetail(s)).collect(Collectors.toList()));
	}

	// @Override
	// public void updateComparingFormDetail(List<ComparingFormDetail>
	// comparingFormDetailList) {
	// this.commandProxy().updateAll(comparingFormDetailList.stream()
	// .map(s ->
	// convertToEntityQlsptPaycompFormDetail(s)).collect(Collectors.toList()));
	// }

	@Override
	public void deleteComparingFormDetail(String companyCode, String formCode) {
		List<QlsptPaycompFormDetail> paycompFormDetailPKList = this.queryProxy()
				.query(SELECT_COMPARING_FORM_DETAIL_BY_FORMCODE, QlsptPaycompFormDetail.class)
				.setParameter("ccd", companyCode).setParameter("formCode", formCode).getList();
		this.commandProxy().removeAll(paycompFormDetailPKList);
	}

	private static ComparingFormDetail convertToDomainFormDetail(QlsptPaycompFormDetail entity) {
		return ComparingFormDetail.createFromJavaType(entity.paycompFormDetailPK.companyCode,
				entity.paycompFormDetailPK.formCode, entity.paycompFormDetailPK.categoryATR,
				entity.paycompFormDetailPK.itemCode, entity.dispOrder);
	}

	private static QlsptPaycompFormDetail convertToEntityQlsptPaycompFormDetail(ComparingFormDetail domain) {
		val entity = new QlsptPaycompFormDetail();
		val entityPK = new QlsptPaycompFormDetailPK();
		entityPK.companyCode = domain.getCompanyCode();
		entityPK.formCode = domain.getFormCode().v();
		entityPK.categoryATR = domain.getCategoryAtr().value;
		entityPK.itemCode = domain.getItemCode().v();
		entity.paycompFormDetailPK = entityPK;
		entity.dispOrder = domain.getDispOrder().v();
		return entity;
	}

}
