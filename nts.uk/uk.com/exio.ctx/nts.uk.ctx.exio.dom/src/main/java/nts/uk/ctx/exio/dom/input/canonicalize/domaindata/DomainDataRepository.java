package nts.uk.ctx.exio.dom.input.canonicalize.domaindata;

/**
 * ドメインのテーブルに対して直接アクセスする汎用Repository
 */
public interface DomainDataRepository {

	boolean exists(DomainDataId id);
	
	void delete(DomainDataId id);
}
