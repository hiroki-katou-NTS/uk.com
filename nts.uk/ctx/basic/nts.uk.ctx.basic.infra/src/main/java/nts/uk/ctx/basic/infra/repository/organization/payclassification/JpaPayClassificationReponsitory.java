package nts.uk.ctx.basic.infra.repository.organization.payclassification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.basic.dom.organization.payclassification.PayClassification;
import nts.uk.ctx.basic.dom.organization.payclassification.PayClassificationCode;
import nts.uk.ctx.basic.dom.organization.payclassification.PayClassificationName;
import nts.uk.ctx.basic.dom.organization.payclassification.PayClassificationRepository;
import nts.uk.ctx.basic.infra.entity.organization.payclassification.QmnmtPayClass;
import nts.uk.ctx.basic.infra.entity.organization.payclassification.QmnmtPayClassPK;
import nts.uk.shr.com.primitive.Memo;

@Stateless
public class JpaPayClassificationReponsitory extends JpaRepository implements PayClassificationRepository {
	

	private static final String FIND_ALL;

	private static final String FIND_SINGLE;
	
	private static final String QUERY_IS_EXISTED;
	
	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT c");
		builderString.append(" FROM QmnmtPayClass c");
		builderString.append(" WHERE c.qmnmtPayClassPK.companyCode = :companyCode");
		FIND_ALL = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT c");
		builderString.append(" FROM QmnmtPayClass c");
		builderString.append(" WHERE c.qmnmtPayClassPK.companyCode = :companyCode");
		builderString.append(" AND c.qmnmtPayClassPK.payClassificationCode = :payClassificationCode");
		FIND_SINGLE = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT COUNT(c)");
		builderString.append(" WHERE c.qmnmtPayClassPK.companyCode = :companyCode");
		builderString.append(" AND c.qmnmtPayClassPK.payClassificationCode = :payClassificationCode");
		QUERY_IS_EXISTED = builderString.toString();
	}
	@Override
	public void add(PayClassification payClassification) {
		this.commandProxy().insert(convertToDbType(payClassification));

	}

	@Override
	public void update(PayClassification payClassification) {
		this.commandProxy().update(convertToDbType(payClassification));

	}

	@Override
	public void remove(String companyCode, PayClassificationCode payClassificationCode) {
		QmnmtPayClassPK qmnmtPayClassPK = new QmnmtPayClassPK(companyCode, payClassificationCode.toString());
		this.commandProxy().remove(QmnmtPayClass.class, qmnmtPayClassPK);

	}

	@Override
	public Optional<PayClassification> findSinglePayClassification(String companyCode,
			String payClassificationCode) {
		return this.queryProxy().query(FIND_SINGLE, QmnmtPayClass.class)
				.setParameter("companyCode", "'" + companyCode + "'")
				.setParameter("payClassificationCode", "'" + payClassificationCode.toString() + "'").getSingle().map(e -> {
					return Optional.of(convertToDomain(e));
				}).orElse(Optional.empty());
	}

	@Override
	public List<PayClassification> findAll(String companyCode) {
		List<QmnmtPayClass> resultList = this.queryProxy().query(FIND_ALL, QmnmtPayClass.class)
				.setParameter("companyCode",  companyCode  ).getList();
		return !resultList.isEmpty() ? resultList.stream().map(e -> {
			return convertToDomain(e);
		}).collect(Collectors.toList()) : new ArrayList<>();
	}

	@Override
	public boolean isExisted(String companyCode, PayClassificationCode payClassificationCode) {
		return this.queryProxy().query(QUERY_IS_EXISTED, long.class)
		.setParameter("companyCode", "'" + companyCode + "'")
		.setParameter("payClassificationCode", "'" + payClassificationCode.toString() + "'")
		.getSingle().get() > 0;
	}

	private QmnmtPayClass convertToDbType(PayClassification payClassification) {
		QmnmtPayClass qmnmtPayClass = new QmnmtPayClass();
		QmnmtPayClassPK qmnmtPayClassPK = new QmnmtPayClassPK(payClassification.getCompanyCode(),
				payClassification.getPayClassificationCode().toString());
		qmnmtPayClass.setMemo(qmnmtPayClass.getMemo() != null ? qmnmtPayClass.getMemo().toString() : "");
		qmnmtPayClass.setPayClassName(payClassification.getPayClassificationName().toString());
		qmnmtPayClass.setQmnmtPayClassPK(qmnmtPayClassPK);
		return qmnmtPayClass;
		
	}
	
	
	private PayClassification convertToDomain(QmnmtPayClass qmnmtPayClass) {
		return new PayClassification(
				new Memo(qmnmtPayClass.getMemo() != null ? qmnmtPayClass.getMemo() : ""),
				new PayClassificationName(qmnmtPayClass.getPayClassificationName()),
				new PayClassificationCode(qmnmtPayClass.getQmnmtPayClassPK().getPayClassificationCode()),
				qmnmtPayClass.getQmnmtPayClassPK().getCompanyCode());
		
	}

	@Override
	public List<PayClassification> getPayClassifications(String companyCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<PayClassification> getPayClassification(String companyCode, String payClassificationCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(String companyCode) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

//	private final String SELECT_NO_WHERE = "SELECT c FROM QmnmtPayClass c";
//	private final String SELECT_ALL_BY_COMPANY = SELECT_NO_WHERE + " WHERE c.qmnmtPayClassPK.companyCd = :companyCd";
//
//	private final PayClassification toDomain(QmnmtPayClass entity) {
//		val domain = PayClassification.createFromJavaType(entity.payClassName, entity.qmnmtPayClassPK.payClassCode,
//				entity.qmnmtPayClassPK.companyCd, entity.memo);
//
//		return domain;
//	}
//
//	private QmnmtPayClass toEntity(PayClassification domain) {
//		val entity = new QmnmtPayClass();
//
//		entity.qmnmtPayClassPK = new QmnmtPayClassPK();
//		entity.qmnmtPayClassPK.payClassCode = domain.getPayClassCode().v();
//		entity.payClassName = domain.getPayClassName().v();
//		entity.qmnmtPayClassPK.companyCd = domain.getCompanyCode();
//		entity.memo = domain.getMemo().v();
//
//		return entity;
//	}
//
//	@Override
//	public void add(PayClassification payClassification) {
//		this.commandProxy().insert(toEntity(payClassification));
//
//	}
//
//	@Override
//	public void update(PayClassification payClassification) {
//		this.commandProxy().update(toEntity(payClassification));
//
//	}
//
//	@Override
//	public List<PayClassification> getPayClassifications(String companyCode) {
//		return this.queryProxy().query(SELECT_ALL_BY_COMPANY, QmnmtPayClass.class)
//				.setParameter("companyCd", companyCode)
//
//				.getList(c -> toDomain(c));
//	}
//
//	@Override
//	public Optional<PayClassification> getPayClassification(String companyCode, String payClassCode) {
//		try {
//			return this.queryProxy().find(new QmnmtPayClassPK(companyCode, payClassCode), QmnmtPayClass.class)
//					.map(c -> toDomain(c));
//		} catch (Exception e) {
//			throw e;
//		}
//	}
//
//	@Override
//	public void remove(String companyCode) {
//		val objectKey = new QmnmtPayClassPK();
//		objectKey.companyCd = companyCode;
//
//		this.commandProxy().remove(QmnmtPayClass.class, objectKey);
//	}

}
