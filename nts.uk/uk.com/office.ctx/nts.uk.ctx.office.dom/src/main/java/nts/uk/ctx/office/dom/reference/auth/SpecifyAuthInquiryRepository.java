package nts.uk.ctx.office.dom.reference.auth;

import java.util.List;
import java.util.Optional;

/*
 * Repository UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.在席照会.在席照会で参照できる権限の指定
 */
public interface SpecifyAuthInquiryRepository {
	/**
	 * [1] insert(在席照会で参照できる権限の指定)
	 * 
	 * @param domain 在席照会で参照できる権限の指定
	 */
	public void insert(SpecifyAuthInquiry domain);

	/**
	 * [2] update(在席照会で参照できる権限の指定)
	 * 
	 * @param domain 在席照会で参照できる権限の指定
	 */
	public void update(SpecifyAuthInquiry domain);

	/**
	 * [3]取得する
	 * 
	 * @param cid    会社ID
	 * @param roleId ロールID
	 * @return Optional<SpecifyAuthInquiry> Optional<在席照会で参照できる権限の指定>
	 */
	public Optional<SpecifyAuthInquiry> getByCidAndRoleId(String cid, String roleId);

	/**
	 * [4]取得する
	 * 
	 * @param cid 会社ID
	 * @return List<SpecifyAuthInquiry> List<在席照会で参照できる権限の指定>
	 */
	public List<SpecifyAuthInquiry> getByCid(String cid);
}
